package com.iktaun.Contemporary_Construction.blocks.custom.light;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * 路灯杆底座方块（仿栅栏连接逻辑，使用 multipart）
 * - 本体（body）永远显示
 * - 底座A（show_base_a）：下方完整方块时显示
 * - 底座B（show_base_b）：下方台阶/不完整方块时显示
 * - 底座A和B互斥，若下方为柱子则两者都 false
 */
public class StreetLightPoleBaseBlock extends Block {

    public static final BooleanProperty SHOW_BASE_A = BooleanProperty.create("show_base_a");
    public static final BooleanProperty SHOW_BASE_B = BooleanProperty.create("show_base_b");

    // ----- 碰撞箱（与模型匹配） -----
    private static final VoxelShape BODY = Block.box(6, 0, 6, 10, 16, 10);
    private static final VoxelShape BASE_A = Block.box(5, 0, 5, 11, 4, 11);
    private static final VoxelShape BASE_B = Block.box(5, 0, 5, 11, 2, 11);

    private static final VoxelShape SHAPE_BODY_ONLY = BODY;
    private static final VoxelShape SHAPE_BODY_A = Shapes.or(BODY, BASE_A);
    private static final VoxelShape SHAPE_BODY_B = Shapes.or(BODY, BASE_B);

    public StreetLightPoleBaseBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(SHOW_BASE_A, true)
                .setValue(SHOW_BASE_B, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SHOW_BASE_A, SHOW_BASE_B);
    }

    // ----- 放置时 -----
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return getStateForBelow(context.getLevel(), context.getClickedPos());
    }

    // ----- 邻居更新时 -----
    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState,
                                  LevelAccessor level, BlockPos currentPos, BlockPos neighborPos) {
        if (direction == Direction.DOWN) {
            return getStateForBelow(level, currentPos);
        }
        return state;
    }

    // ----- 核心判定逻辑（返回完整 BlockState） -----
    private BlockState getStateForBelow(BlockGetter level, BlockPos pos) {
        BlockPos belowPos = pos.below();
        BlockState belowState = level.getBlockState(belowPos);
        VoxelShape belowShape = belowState.getCollisionShape(level, belowPos);

        // 1. 空碰撞箱 → 仅本体（无底座）
        if (belowShape.isEmpty()) {
            return this.defaultBlockState().setValue(SHOW_BASE_A, false).setValue(SHOW_BASE_B, false);
        }

        double maxY = belowShape.bounds().maxY;

        // 2. 不完整方块（maxY < 1.0）→ 底座B
        if (maxY < 1.0) {
            return this.defaultBlockState().setValue(SHOW_BASE_A, false).setValue(SHOW_BASE_B, true);
        }

        // 3. 水平方向不完整（柱子）→ 仅本体
        if (isPillarShape(belowShape)) {
            return this.defaultBlockState().setValue(SHOW_BASE_A, false).setValue(SHOW_BASE_B, false);
        }

        // 4. 完整方块 → 底座A
        return this.defaultBlockState().setValue(SHOW_BASE_A, true).setValue(SHOW_BASE_B, false);
    }

    private boolean isPillarShape(VoxelShape shape) {
        var bounds = shape.bounds();
        boolean fullX = bounds.minX <= 0.001 && bounds.maxX >= 0.999;
        boolean fullZ = bounds.minZ <= 0.001 && bounds.maxZ >= 0.999;
        return !fullX || !fullZ;
    }

    // ----- 动态碰撞箱 -----
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (state.getValue(SHOW_BASE_A)) return SHAPE_BODY_A;
        if (state.getValue(SHOW_BASE_B)) return SHAPE_BODY_B;
        return SHAPE_BODY_ONLY;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return this.getShape(state, level, pos, context);
    }
}