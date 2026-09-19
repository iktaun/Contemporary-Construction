package com.iktaun.Contemporary_Construction.blocks.custom.other;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

/**
 * ================================
 * 盲道砖（Tactile Paving）
 * ================================
 * 功能：仿照原版铁轨（RailBlock）的平面连接逻辑，自动根据周围同类型盲道砖调整自身形状。
 * 形状状态只有三种：
 *   - NORTH_SOUTH ： 南北直线
 *   - EAST_WEST   ： 东西直线
 *   - CROSS       ： 十字交叉（包含所有垂直转弯、T型、十字型，不区分具体方向）
 * 特点：
 *   - 不支持上下坡（无 ASCENDING 形态）
 *   - 支持含水（Waterlogged）
 *   - 碰撞箱为完整方块（可轻松改为薄片）
 *   - 放置或邻块变化时自动重算形状
 * 适用场景：人行道盲道引导砖、装饰性地砖等需要自动连接显示的方块。
 *
 * @author 您的Mod开发团队
 * @version 1.0
 */
public class TactilePaving extends Block implements SimpleWaterloggedBlock {

    // ==========================================
    // 1. 自定义枚举 —— 定义三种连接形状
    // ==========================================
    /**
     * 必须实现 StringRepresentable，以便 Minecraft 能正确将枚举值序列化为
     * 方块状态字符串（存储在存档中）。
     */
    public enum TactileShape implements StringRepresentable {
        NORTH_SOUTH("north_south"),   // 南北直线
        EAST_WEST("east_west"),       // 东西直线
        CROSS("cross");               // 十字交叉

        private final String serializedName;

        TactileShape(String serializedName) {
            this.serializedName = serializedName;
        }

        @Override
        public String getSerializedName() {
            return serializedName;
        }
    }

    // ==========================================
    // 2. 方块状态属性
    // ==========================================
    /** 连接形状属性，类型为自定义枚举 */
    public static final EnumProperty<TactileShape> SHAPE =
            EnumProperty.create("shape", TactileShape.class);

    /** 是否含水（来自 SimpleWaterloggedBlock） */
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    // ==========================================
    // 3. 碰撞箱（视觉/物理形状）
    // ==========================================
    /**
     * 完整方块碰撞箱（16×16×16 像素）。
     * 若想做成薄片（如 1 像素高），可改为：
     *   Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D)
     */
    protected static final VoxelShape AABB = Block.box(0, 0, 0, 16, 0.75, 16);

    // ==========================================
    // 4. 构造函数
    // ==========================================
    public TactilePaving(BlockBehaviour.Properties properties) {
        super(properties);
        // 注册默认状态：南北直线，不含水
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(SHAPE, TactileShape.NORTH_SOUTH)
                .setValue(WATERLOGGED, Boolean.FALSE)
        );
    }

    // ==========================================
    // 5. 重写方法
    // ==========================================

    /**
     * 获取碰撞箱（渲染和碰撞检测使用）。
     * 此方法返回的形状用于玩家碰撞、视线检测等。
     */
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return AABB;
    }

    /**
     * 当玩家放置方块时调用，用于决定初始状态。
     * 会根据周围已存在的同类型盲道计算初始连接形状。
     *
     * @param context 放置上下文，包含玩家朝向、点击位置等信息
     * @return 设置好形状和含水状态的 BlockState
     */
    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        BlockState state = this.defaultBlockState()
                .setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
        // 计算连接形状
        return state.setValue(SHAPE, calculateShape(state, context.getLevel(), context.getClickedPos()));
    }

    /**
     * 当邻块发生变化时调用（包括放置、破坏、更新等），
     * 用于同步更新当前方块的连接形状。
     * 只有水平方向的变化才会触发形状重算（上下方向不影响平面连接）。
     *
     * @param direction     发生变化的邻块相对于当前方块的方向
     * @param neighborState 邻块的新状态
     * @return 更新后的 BlockState
     */
    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState,
                                  LevelAccessor level, BlockPos currentPos, BlockPos neighborPos) {
        // 若含水，则需要计划水流动刻（保证水的正常行为）
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        // 只有水平方向变化才重新计算形状
        if (direction.getAxis().isHorizontal()) {
            return state.setValue(SHAPE, calculateShape(state, level, currentPos));
        }
        return super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
    }

    // ==========================================
    // 6. 核心算法 —— 计算连接形状
    // ==========================================

    /**
     * 检测四个水平方向（北、南、西、东）是否有同类型盲道砖，
     * 根据连接情况返回对应的 TactileShape。
     * 判定逻辑：
     *   - 仅南北方向有连接（东西无） → NORTH_SOUTH
     *   - 仅东西方向有连接（南北无） → EAST_WEST
     *   - 南北和东西同时有连接（即垂直交叉，包括三通、四通） → CROSS
     *   - 没有任何连接 → 默认为 NORTH_SOUTH（单独一块也显示为直线）
     * 注意：所有弯道（如北+东、南+西等）均统一归为 CROSS，
     *       不区分左拐右拐，符合「不区分方向」的需求。
     * @param state 当前方块状态（主要用于获取其他属性，此处未使用）
     * @param level 世界引用
     * @param pos   当前方块位置
     * @return 计算出的形状
     */
    private TactileShape calculateShape(BlockState state, BlockGetter level, BlockPos pos) {
        // 检测四个方向是否有同类型盲道
        boolean north = isTactilePaving(level.getBlockState(pos.relative(Direction.NORTH)));
        boolean south = isTactilePaving(level.getBlockState(pos.relative(Direction.SOUTH)));
        boolean west  = isTactilePaving(level.getBlockState(pos.relative(Direction.WEST)));
        boolean east  = isTactilePaving(level.getBlockState(pos.relative(Direction.EAST)));

        boolean hasNorthSouth = north || south;
        boolean hasWestEast   = west  || east;

        // 分支判断
        if (hasNorthSouth && !hasWestEast) {
            return TactileShape.NORTH_SOUTH;
        }
        if (hasWestEast && !hasNorthSouth) {
            return TactileShape.EAST_WEST;
        }
        if (!hasNorthSouth && !hasWestEast) {
            return TactileShape.NORTH_SOUTH;   // 无连接时默认直线
        }
        // 剩下情况：垂直交叉（北&西、北&东、南&西、南&东 或 三向、四向）
        return TactileShape.CROSS;
    }

    /**
     * 判断一个 BlockState 是否属于本盲道类型。
     * 使用 instanceof 确保可以识别所有继承自本类的子类（如果有）。
     *
     * @param state 要判断的状态
     * @return true 表示是盲道砖
     */
    private boolean isTactilePaving(BlockState state) {
        return state.getBlock() instanceof TactilePaving;
    }

    // ==========================================
    // 7. 状态注册
    // ==========================================

    /**
     * 注册方块的状态属性。
     * 必须在此方法中调用 builder.add(...) 注册所有用到的属性，
     * 否则游戏会报错。
     */
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SHAPE, WATERLOGGED);
    }

    // ==========================================
    // 8. 含水支持
    // ==========================================

    /**
     * 返回当前方块状态的流体状态（水或空）。
     * 当 WATERLOGGED = true 时，返回水源状态，否则返回空。
     */
    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    // ==========================================
    // 9. 使用示例（仅供开发者参考）
    // ==========================================
    /*
     * ========== 如何在您的 Mod 中使用这个方块 ==========
     *
     * 1. 在您的注册类中（例如 ModBlocks）创建 RegistryObject：
     *
     *    public static final RegistryObject<Block> TACTILE_PAVING =
     *        BLOCKS.register("tactile_paving",
     *            () -> new TactilePaving(BlockBehaviour.Properties.copy(Blocks.STONE)
     *                .strength(1.5F, 6.0F)
     *                .requiresCorrectToolForDrops()
     *            )
     *        );
     *
     * 2. 如果需要数据生成方块状态（自动生成 JSON），可在您的 BlockStateProvider 中：
     *
     *    public void registerStatesAndModels() {
     *        // 简单方式：为三种形状分别指定模型
     *        BlockStateBuilder builder = getVariantBuilder(ModBlocks.TACTILE_PAVING.get());
     *        builder.partialState().with(SHAPE, TactileShape.NORTH_SOUTH)
     *               .modelForState().modelFile(models().getExistingFile(modLoc("block/tactile_ns"))).addModel();
     *        builder.partialState().with(SHAPE, TactileShape.EAST_WEST)
     *               .modelForState().modelFile(models().getExistingFile(modLoc("block/tactile_ew"))).addModel();
     *        builder.partialState().with(SHAPE, TactileShape.CROSS)
     *               .modelForState().modelFile(models().getExistingFile(modLoc("block/tactile_cross"))).addModel();
     *    }
     *
     *    也可使用原版铁轨的方法，用模型旋转减少文件数。
     *
     * 3. 必须确保您的模型文件位于 resources/assets/yourmod/models/block/ 下，
     *    名称分别为：tactile_ns.json、tactile_ew.json、tactile_cross.json。
     *
     * 4. 若要使用含水功能，方块必须与水有交互（已在类中实现 SimpleWaterloggedBlock）。
     *    玩家使用水桶右键可切换含水状态。
     *
     * 5. 放置后，连接会自动更新。若需要手动触发更新，可破坏/放置相邻盲道。
     *
     * ========== 注意 ==========
     * - 此方块默认碰撞箱为完整方块，若需做成薄地毯，请修改 AABB 常量。
     * - 枚举值序列化名称必须与方块状态 JSON 中的 "shape" 值匹配（全小写、下划线）。
     */
}