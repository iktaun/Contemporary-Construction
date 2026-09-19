package com.iktaun.Contemporary_Construction.blocks.custom.road_block;

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
 * 道路标线方块（StraightLineBlock）
 * ================================
 * 功能：仿照原版铁轨（RailBlock）的平面连接逻辑，自动根据周围同类型方块调整自身形状。
 * 形状状态支持 11 种精确形态：
 *   - 2 种直线：南北、东西
 *   - 4 种弯道：东北、西北、东南、西南
 *   - 4 种 T 形：朝东、朝西、朝南、朝北（即缺一个方向的三通）
 *   - 1 种十字：四向全连接
 * 特点：
 *   - 不支持上下坡（无 ASCENDING 形态）
 *   - 支持含水（Waterlogged）
 *   - 碰撞箱为完整方块（可轻松改为薄片）
 *   - 放置或邻块变化时自动重算形状
 * 适用场景：路面引导线、车道分割线、停车场标线、人行道指引等需要精确指示方向的标线。
 *
 * @author 您的Mod开发团队
 * @version 1.0
 */
public class StraightLineBlock extends Block implements SimpleWaterloggedBlock {

    // ==========================================
    // 1. 自定义枚举 —— 定义 11 种连接形状
    // ==========================================
    /**
     * 必须实现 StringRepresentable，以便 Minecraft 能正确将枚举值序列化为
     * 方块状态字符串（存储在存档中）。
     * 所有值使用小写+下划线命名，与 blockstates JSON 中的 "shape" 值完全匹配。
     */
    public enum StraightLineConnect implements StringRepresentable {
        // ---------- 直线（2种） ----------
        NORTH_SOUTH("north_south"),   // 南北方向直线
        EAST_WEST("east_west"),       // 东西方向直线

        // ---------- 弯道（4种） ----------
        NORTH_WEST("north_west"),     // 西北拐角（北+西）
        NORTH_EAST("north_east"),     // 东北拐角（北+东）
        SOUTH_WEST("south_west"),     // 西南拐角（南+西）
        SOUTH_EAST("south_east"),     // 东南拐角（南+东）

        // ---------- T 形三通（4种） ----------
        NORTH_EAST_SOUTH("north_east_south"),   // 缺西 → 朝东的 T 形（北+东+南）
        NORTH_WEST_SOUTH("north_west_south"),   // 缺东 → 朝西的 T 形（北+西+南）
        EAST_SOUTH_WEST("east_south_west"),     // 缺北 → 朝南的 T 形（东+南+西）
        EAST_NORTH_WEST("east_north_west"),     // 缺南 → 朝北的 T 形（东+北+西）

        // ---------- 十字（1种） ----------
        CROSS("cross");               // 四向全连接

        private final String serializedName;

        StraightLineConnect(String serializedName) {
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
    /**
     * 连接形状属性，类型为自定义枚举 StraightLineConnect。
     * 该属性决定了方块显示哪种标线形态。
     */
    public static final EnumProperty<StraightLineConnect> SHAPE =
            EnumProperty.create("shape", StraightLineConnect.class);

    /**
     * 是否含水（来自 SimpleWaterloggedBlock 接口）。
     * true 表示方块内含有水源，false 表示无水。
     */
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    // ==========================================
    // 3. 碰撞箱（视觉/物理形状）
    // ==========================================
    /**
     * 完整方块碰撞箱（16×16×16 像素）。
     * 若想做成薄片（如标线漆仅 1 像素高），可改为：
     *   Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D)
     */
    protected static final VoxelShape AABB = Block.box(0, 0, 0, 16, 16, 16);

    // ==========================================
    // 4. 构造函数
    // ==========================================
    public StraightLineBlock(BlockBehaviour.Properties properties) {
        super(properties);
        // 注册默认状态：南北直线，不含水
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(SHAPE, StraightLineConnect.NORTH_SOUTH)
                .setValue(WATERLOGGED, Boolean.FALSE)
        );
    }

    // ==========================================
    // 5. 重写方法
    // ==========================================

    /**
     * 获取碰撞箱（渲染和碰撞检测使用）。
     * 此方法返回的形状用于玩家碰撞、视线检测、投影等。
     *
     * @param state   当前方块状态
     * @param level   世界引用
     * @param pos     方块位置
     * @param context 碰撞上下文（包含碰撞实体等信息）
     * @return 方块的 VoxelShape（碰撞箱）
     */
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return AABB;
    }

    /**
     * 当玩家放置方块时调用，用于决定初始状态。
     * 会根据周围已存在的同类型方块计算初始连接形状。
     *
     * @param context 放置上下文，包含玩家朝向、点击位置、世界等信息
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
     * @param state         当前方块状态
     * @param direction     发生变化的邻块相对于当前方块的方向
     * @param neighborState 邻块的新状态
     * @param level         世界引用
     * @param currentPos    当前方块位置
     * @param neighborPos   邻块位置
     * @return 更新后的 BlockState
     */
    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState,
                                  LevelAccessor level, BlockPos currentPos, BlockPos neighborPos) {
        // 若含水，则需要计划水流动刻（保证水的正常行为，如流动或蒸发）
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
    // 6. 核心算法 —— 计算连接形状（铁轨式精确匹配）
    // ==========================================

    /**
     * 检测四个水平方向（北、南、西、东）是否有同类型方块，
     * 根据连接情况返回对应的 StraightLineConnect。
     *
     * 判定逻辑（完全仿照原版 RailBlock 的 getRailDirection）：
     *   - 0 个连接 → 默认 NORTH_SOUTH（单独一块显示为南北直线）
     *   - 2 个连接：
     *       • 南北成对 → NORTH_SOUTH
     *       • 东西成对 → EAST_WEST
     *       • 北+东 → NORTH_EAST（东北弯道）
     *       • 北+西 → NORTH_WEST（西北弯道）
     *       • 南+东 → SOUTH_EAST（东南弯道）
     *       • 南+西 → SOUTH_WEST（西南弯道）
     *   - 3 个连接（T 形三通）：
     *       • 缺北 → EAST_SOUTH_WEST（朝南 T 形）
     *       • 缺南 → EAST_NORTH_WEST（朝北 T 形）
     *       • 缺西 → NORTH_EAST_SOUTH（朝东 T 形）
     *       • 缺东 → NORTH_WEST_SOUTH（朝西 T 形）
     *   - 4 个连接 → CROSS（十字交叉）
     *
     * @param state 当前方块状态（主要用于获取其他属性，此处未使用）
     * @param level 世界引用
     * @param pos   当前方块位置
     * @return 计算出的形状枚举值
     */
    private StraightLineConnect calculateShape(BlockState state, BlockGetter level, BlockPos pos) {
        // 检测四个方向是否存在同类型方块
        boolean north = isSameBlock(level.getBlockState(pos.relative(Direction.NORTH)));
        boolean south = isSameBlock(level.getBlockState(pos.relative(Direction.SOUTH)));
        boolean west  = isSameBlock(level.getBlockState(pos.relative(Direction.WEST)));
        boolean east  = isSameBlock(level.getBlockState(pos.relative(Direction.EAST)));

        // 统计连接数量
        int connections = (north ? 1 : 0) + (south ? 1 : 0) + (west ? 1 : 0) + (east ? 1 : 0);

        // 无连接 → 默认南北直线
        if (connections == 0) {
            return StraightLineConnect.NORTH_SOUTH;
        }

        if (connections == 1) {
            // 只有单个方向连接，显示为直线（沿着连接方向）
            if (north || south) return StraightLineConnect.NORTH_SOUTH;
            if (west || east)   return StraightLineConnect.EAST_WEST;
        }

        // 两个连接 → 直线或弯道
        if (connections == 2) {
            if (north && south) return StraightLineConnect.NORTH_SOUTH;
            if (west && east)   return StraightLineConnect.EAST_WEST;
            if (north && east)  return StraightLineConnect.NORTH_EAST;
            if (north && west)  return StraightLineConnect.NORTH_WEST;
            if (south && east)  return StraightLineConnect.SOUTH_EAST;
            if (south && west)  return StraightLineConnect.SOUTH_WEST;
        }

        // 三个连接 → T 形三通
        if (connections == 3) {
            if (!north) return StraightLineConnect.EAST_SOUTH_WEST;   // 缺北 → T 朝南
            if (!south) return StraightLineConnect.EAST_NORTH_WEST;   // 缺南 → T 朝北
            if (!west)  return StraightLineConnect.NORTH_EAST_SOUTH;  // 缺西 → T 朝东
            if (!east)  return StraightLineConnect.NORTH_WEST_SOUTH;  // 缺东 → T 朝西
        }

        // 四个连接 → 十字交叉
        return StraightLineConnect.CROSS;
    }

    // ==========================================
    // 7. 辅助方法
    // ==========================================

    /**
     * 判断一个 BlockState 是否属于本道路标线类型。
     * 使用 instanceof 确保可以识别所有继承自本类的子类（如果有）。
     *
     * @param state 要判断的状态
     * @return true 表示是道路标线方块
     */
    private boolean isSameBlock(BlockState state) {
        return state.getBlock() instanceof StraightLineBlock;
    }

    // ==========================================
    // 8. 状态注册
    // ==========================================

    /**
     * 注册方块的状态属性。
     * 必须在此方法中调用 builder.add(...) 注册所有用到的属性，
     * 否则游戏会因未知属性而报错。
     *
     * @param builder 状态定义构建器
     */
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SHAPE, WATERLOGGED);
    }

    // ==========================================
    // 9. 含水支持
    // ==========================================

    /**
     * 返回当前方块状态的流体状态（水或空）。
     * 当 WATERLOGGED = true 时，返回水源状态，否则返回空。
     *
     * @param state 当前方块状态
     * @return 对应的 FluidState
     */
    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    // ==========================================
    // 10. 使用示例（仅供开发者参考）
    // ==========================================
    /*
     * ========== 如何在您的 Mod 中使用这个方块 ==========
     *
     * 1. 在您的注册类中（例如 ModBlocks）创建 RegistryObject：
     *
     *    public static final RegistryObject<Block> STRAIGHT_LINE_BLOCK =
     *        BLOCKS.register("straight_line_block",
     *            () -> new StraightLineBlock(BlockBehaviour.Properties.copy(Blocks.STONE)
     *                .strength(1.5F, 6.0F)
     *                .requiresCorrectToolForDrops()
     *            )
     *        );
     *
     * 2. 如果需要数据生成方块状态（自动生成 JSON），可在您的 BlockStateProvider 中：
     *
     *    public void registerStatesAndModels() {
     *        // 为 11 种形状分别指定模型（或使用旋转复用）
     *        BlockStateBuilder builder = getVariantBuilder(ModBlocks.STRAIGHT_LINE_BLOCK.get());
     *        for (StraightLineConnect shape : StraightLineConnect.values()) {
     *            builder.partialState().with(SHAPE, shape)
     *                   .modelForState()
     *                   .modelFile(models().getExistingFile(modLoc("block/straight_line_" + shape.getSerializedName())))
     *                   .addModel();
     *        }
     *    }
     *
     * 3. 模型文件命名建议：
     *    - straight_line_north_south.json
     *    - straight_line_east_west.json
     *    - straight_line_north_west.json
     *    - straight_line_north_east.json
     *    - straight_line_south_west.json
     *    - straight_line_south_east.json
     *    - straight_line_north_east_south.json
     *    - straight_line_north_west_south.json
     *    - straight_line_east_south_west.json
     *    - straight_line_east_north_west.json
     *    - straight_line_cross.json
     *
     *    或使用 3 个基础模型 + 旋转复用（节省文件数）：
     *    - 直线模型（旋转 0°/90°）
     *    - 弯道模型（旋转 0°/90°/180°/270°）
     *    - T 形模型（旋转 0°/90°/180°/270°）
     *    - 十字模型（无需旋转）
     *
     * 4. 若要使用含水功能，玩家使用水桶右键可切换含水状态。
     *
     * 5. 放置后，连接会自动更新。若需要手动触发更新，可破坏/放置相邻道路标线。
     *
     * ========== 注意 ==========
     * - 此方块默认碰撞箱为完整方块，若需做成薄漆层，请修改 AABB 常量。
     * - 枚举值序列化名称必须与方块状态 JSON 中的 "shape" 值完全匹配（全小写、下划线）。
     * - 确保 blockstates JSON 中为每个 shape 值定义了对应的模型映射。
     * - 若只需要 3 种形状（直线/十字），可删除多余的枚举值并简化算法。
     */
}