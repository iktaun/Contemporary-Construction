// RoadFenceBlock.java
// ============================================================
// 本文件说明（请仔细阅读）：
// 1. 本类继承自 CrossCollisionBlock，保留了原版栅栏的连接逻辑（四个方向的状态）。
// 2. 碰撞箱固定为细柱（2×15.5×2 像素），不与连接状态联动。
// 3. 视觉上通过资源包（blockstates + models）实现“柱子永远在，横杆随状态显隐”。
// 4. 所有资源文件（JSON / OBJ / 贴图）的路径和写法都在下方的注释中详细给出。
// ============================================================

package com.iktaun.Contemporary_Construction.blocks.custom.barrier;

import com.iktaun.Contemporary_Construction.blocks.custom.ModblocksTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.LeadItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CrossCollisionBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

// ============================================================
// 资源文件配置指南（配合本类使用）：
//
// 一、blockstates 文件（实现连接视觉）
// 路径：assets/contemporary_construction/blockstates/road_fence.json
// 内容（multipart 写法）：
// {
//   "multipart": [
//     { "apply": { "model": "contemporary_construction:block/road_fence_post.obj" } },
//     { "when": { "north": "true" }, "apply": { "model": "contemporary_construction:block/road_fence_side" } },
//     { "when": { "east": "true" }, "apply": { "model": "contemporary_construction:block/road_fence_side", "y": 90 } },
//     { "when": { "south": "true" }, "apply": { "model": "contemporary_construction:block/road_fence_side", "y": 180 } },
//     { "when": { "west": "true" }, "apply": { "model": "contemporary_construction:block/road_fence_side", "y": 270 } }
//   ]
// }
// 解释：柱子（OBJ）无条件渲染；四个横杆（JSON）仅在对应状态为 true 时出现，并旋转方向。
//
// 二、柱子 OBJ 模型（你的独立建模）
// - 桥接文件：assets/contemporary_construction/models/block/road_fence_post.obj.json
//   { "loader": "forge:obj",
//   "model": "contemporary_construction:models/block/road_fence_post.obj",
//   "flip-v": true,
//   "textures": { "particle": "contemporary_construction:block/road_fence" }
//   }
// - 实际 OBJ：assets/contemporary_construction/models/block/road_fence_post.obj
// - MTL 文件：assets/contemporary_construction/models/block/road_fence_post.mtl（内部贴图路径写：map_Kd ../textures/block/road_fence.png）
//
// 三、横杆 JSON 模型（轻量化，无需 OBJ）
// 路径：assets/contemporary_construction/models/block/road_fence_side.json
// 内容（16×2×1 像素的横杆，居中偏上）：
// {
//   "parent": "block/block",
//   "textures": { "particle": "contemporary_construction:block/road_fence", "texture": "contemporary_construction:block/road_fence" },
//   "elements": [ { "from": [0, 7.5, 7.5], "to": [16, 9.5, 8.5], "faces": { "north": { "uv": [0,0,16,2], "texture": "#texture" }, "south": { "uv": [0,0,16,2], "texture": "#texture" }, "up": { "uv": [0,7.5,16,8.5], "texture": "#texture" }, "down": { "uv": [0,7.5,16,8.5], "texture": "#texture" } } } ]
// }
// ============================================================

/**
 * 自定义道路栅栏方块（RoadFenceBlock）
 *
 * <p>设计目标：
 * - 逻辑上继承原版栅栏的十字连接状态（NORTH/EAST/SOUTH/WEST），
 *   使其能根据邻居方块动态决定是否“连接”。
 * - 视觉上完全替换为“独立 OBJ 柱子 + 条件显示的 JSON 横杆”，
 *   横杆仅在对应方向连接时为 true 时出现，实现原版风格的连接动画。
 * - 碰撞箱固定为细柱，不随连接状态变化，确保交互手感统一。
 *
 * <p>父类 CrossCollisionBlock 构造器需要 6 个参数，我们传入原版栅栏的数值，
 * 但由于完全重写了 getShape / getCollisionShape / getOcclusionShape / getVisualShape，
 * 这些数值实际上不会影响任何碰撞或渲染，仅用于满足构造器签名要求。
 */
public class RoadFenceBlock extends CrossCollisionBlock {

    // ============================================================
    // 固定碰撞箱定义
    // 使用 Block.box(左, 下, 前, 右, 上, 后)，单位是像素（1/16 格）。
    // 当前值：左 7、下 0、前 7 → 右 9、上 15.5、后 9
    // 即：宽度 2 像素，高度 15.5 像素，深度 2 像素，居中放置。
    // 如果希望柱子更高（满格），可改为 Shapes.box(0.25, 0, 0.25, 0.75, 1, 0.75)。
    // 注意：此形状必须与你 OBJ 模型的实际尺寸大致匹配，否则会出现视觉穿模。
    // ============================================================
    private final VoxelShape HIGH_SHAPE = Block.box(7.2, 0, 7.2, 8.8, 22.6, 8.8);
    private static final VoxelShape ARM_NORTH = Block.box(7.25, 5, -0.05, 8.75, 18.5, 7.15);
    private static final VoxelShape ARM_SOUTH = Block.box(7.25, 5, 8.85, 8.75, 18.5, 16.05);
    private static final VoxelShape ARM_EAST  = Block.box(14.05, 5, 7.25, 15.55, 18.5, 14.45);
    private static final VoxelShape ARM_WEST  = Block.box(5.25, 5, 7.25, 6.75, 18.5, 14.45);




    /**
     * 构造器
     *
     * @param properties 方块行为属性（硬度、抗性、声音等），由注册时传入
     *
     * 父类 CrossCollisionBlock 的构造器要求传入 5 个 float 参数：
     * - 柱宽、柱高（碰撞箱算法的内部参数，此处不影响最终形状）
     * - 横杆起始高度、终止高度、总高（同样仅用于父类内部，我们不使用）
     * 我们直接沿用原版栅栏的数值：2.0F, 2.0F, 16.0F, 16.0F, 24.0F。
     */
    public RoadFenceBlock(BlockBehaviour.Properties properties) {
        super(2.0F, 2.0F, 16.0F, 16.0F, 24.0F, properties);
        // 注册默认方块状态：四个方向均不连接，且未被水淹没
        // 这些状态会在放置和邻居更新时由本类的逻辑自动修改
        this.registerDefaultState(
                this.stateDefinition.any()
                        .setValue(NORTH, false)
                        .setValue(EAST, false)
                        .setValue(SOUTH, false)
                        .setValue(WEST, false)
                        .setValue(WATERLOGGED, false)
        );
    }

    // ============================================================
    // 以下四个方法全部重写，返回统一的固定碰撞箱 HIGH_SHAPE。
    // 这确保了玩家碰撞、光标高亮、光照计算和渲染轮廓全部一致，
    // 彻底摆脱父类中基于连接状态动态生成复杂形状的机制。
    // ============================================================

    /**
     * 遮挡形状（用于光照计算，决定光线是否透过方块）
     */
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter level, BlockPos pos) {
        return this.HIGH_SHAPE;
    }

    /**
     * 视觉形状（用于调试轮廓和部分渲染辅助）
     */
    public VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return this.HIGH_SHAPE;
    }

    /**
     * 主要碰撞形状（玩家移动、实体碰撞、投射物判定等）
     */
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        VoxelShape shape = HIGH_SHAPE;
        if (state.getValue(NORTH)) {
            shape = Shapes.or(shape, ARM_NORTH);
        }
        if (state.getValue(EAST)) {
            shape = Shapes.or(shape, ARM_EAST);
        }
        if (state.getValue(SOUTH)) {
            shape = Shapes.or(shape, ARM_SOUTH);
        }
        if (state.getValue(WEST)) {
            shape = Shapes.or(shape, ARM_WEST);
        }
        return shape;
    }

    /**
     * 碰撞检测形状（与 getShape 保持相同，一般不需要区别）
     */
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return this.HIGH_SHAPE;
    }

    /**
     * 寻路判定：栅栏完全阻挡路径，始终返回 false
     */
    public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType type) {
        return false;
    }

    // ============================================================
    // 连接判定逻辑（核心）
    // 本类的 connectsTo 方法决定了栅栏在什么情况下会与邻居“连接”，
    // 即对应的方向状态（north/east/south/west）会被设为 true。
    // 连接后，blockstates 中的横杆部件便会显示。
    // ============================================================

    /**
     * 判断当前栅栏是否应与指定方向上的邻居方块连接。
     *
     * @param neighborState 邻居方块的 BlockState
     * @param isSideSolid   邻居的相邻面是否为完整固体（原版辅助参数）
     * @param direction     邻居相对于本方块的方向
     * @return true 表示应建立连接（对应方向状态设为 true）
     *
     * 当前连接策略（满足其一即连）：
     * 1. 邻居拥有 ROAD_FENCE 标签（即同类栅栏）—— 通过 isSameFence 判定。
     * 2. 邻居不是“连接例外方块”（如屏障、树叶），且相邻面为完整固体 ——
     *    这使得道路栅栏能连接到普通石头、泥土等方块，与原版栅栏行为一致。
     *    如果你不希望连到普通方块，可以删除后面的部分，只保留 flag。
     */
    public boolean connectsTo(BlockState neighborState, boolean isSideSolid, Direction direction) {
        boolean flag_A = this.isSameFence(neighborState);
        boolean flag_B = this.isSameBarrier(neighborState);
        // 注意：isExceptionForConnection 是父类方法，会排除屏障、树叶等特殊方块。
        return !isExceptionForConnection(neighborState) && isSideSolid || flag_A || flag_B;
    }

    /**
     * 判断两个栅栏是否属于“同一类型”（基于标签）。
     *
     * 当前实现：检查邻居是否具有 ModblocksTags.ROAD_FENCE 标签。
     * 这里使用了双重检查，但实质上等价于 pState.is(ROAD_FENCE)。
     * 保留此写法是为了便于未来扩展（例如区分木质/石质等子类型）。
     */
    private boolean isSameFence(BlockState state) {
        return state.is(ModblocksTags.ROAD_FENCE);
    }

    private boolean isSameBarrier(BlockState state) {
        return state.is(ModblocksTags.ROAD_FENCE_BARRIER);
    }

    // ============================================================
    // 玩家交互：拴绳功能（保留原版栅栏特性）
    // ============================================================

    /**
     * 右键交互：如果玩家手持拴绳，尝试将其绑定到本栅栏上。
     * 客户端返回 SUCCESS 以播放动画，服务端执行实际绑定逻辑。
     */
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hit) {
        if (level.isClientSide) {
            return player.getItemInHand(hand).is(Items.LEAD) ? InteractionResult.SUCCESS : InteractionResult.PASS;
        } else {
            return LeadItem.bindPlayerMobs(player, level, pos);
        }
    }

    // ============================================================
    // 状态更新与放置（完全复用父类逻辑，仅传入我们自己的 connectsTo）
    // 这两个方法确保了放置时和邻居变化时，四个方向状态会被正确计算。
    // ============================================================

    /**
     * 放置时计算初始状态：遍历四个水平方向，调用 connectsTo 判定是否连接。
     */
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockGetter level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        FluidState fluid = level.getFluidState(pos);
        BlockPos north = pos.north();
        BlockPos east = pos.east();
        BlockPos south = pos.south();
        BlockPos west = pos.west();
        BlockState stateNorth = level.getBlockState(north);
        BlockState stateEast = level.getBlockState(east);
        BlockState stateSouth = level.getBlockState(south);
        BlockState stateWest = level.getBlockState(west);

        return super.getStateForPlacement(context)
                .setValue(NORTH, this.connectsTo(stateNorth, stateNorth.isFaceSturdy(level, north, Direction.SOUTH), Direction.SOUTH))
                .setValue(EAST,  this.connectsTo(stateEast,  stateEast.isFaceSturdy(level, east,  Direction.WEST),  Direction.WEST))
                .setValue(SOUTH, this.connectsTo(stateSouth, stateSouth.isFaceSturdy(level, south, Direction.NORTH), Direction.NORTH))
                .setValue(WEST,  this.connectsTo(stateWest,  stateWest.isFaceSturdy(level, west,  Direction.EAST),  Direction.EAST))
                .setValue(WATERLOGGED, fluid.getType() == Fluids.WATER);
    }

    /**
     * 邻居变化时更新对应方向的状态。
     * 仅当变化方向为水平方向时才重新计算，垂直方向（上下）交给父类处理（如水更新）。
     */
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState,
                                  LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        if (facing.getAxis().getPlane() == Direction.Plane.HORIZONTAL) {
            return state.setValue(
                    PROPERTY_BY_DIRECTION.get(facing),
                    this.connectsTo(facingState,
                            facingState.isFaceSturdy(level, facingPos, facing.getOpposite()),
                            facing.getOpposite())
            );
        }
        return super.updateShape(state, facing, facingState, level, currentPos, facingPos);
    }

    // ============================================================
    // 状态属性注册（必须将使用的属性添加到构建器中，否则存档/网络会丢失）
    // ============================================================

    /**
     * 将四个水平连接属性和水淹属性注册进方块状态定义。
     * 这些属性定义在父类 CrossCollisionBlock 中，这里只是“声明”本方块拥有它们。
     */
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST, WATERLOGGED);
    }
}