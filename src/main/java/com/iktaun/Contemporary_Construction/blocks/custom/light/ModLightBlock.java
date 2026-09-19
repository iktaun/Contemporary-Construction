package com.iktaun.Contemporary_Construction.blocks.custom.light; // 保持你的包名

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

/**
 * 智能街灯方块 —— 夜晚自动亮起（亮度15），白天自动熄灭。
 * 右键可手动切换（仅管理员/创造模式）。
 */
public class ModLightBlock extends Block {

    // 使用原版自带的 LIT 属性（true=亮, false=灭）
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public ModLightBlock(Properties properties) {
        super(properties);
        // 默认不亮
        this.registerDefaultState(this.stateDefinition.any().setValue(LIT, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }

    // ---------- 核心：发光强度 ----------
    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        return state.getValue(LIT) ? 15 : 0; // 亮时15，灭时0
    }

    // ---------- 放置时开始计时 ----------
    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, level, pos, oldState, isMoving);
        if (!level.isClientSide) {
            // 20 tick（1秒）后首次检测
            level.scheduleTick(pos, this, 20);
        }
    }

    // ---------- 定时检测时间 ----------
    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, net.minecraft.util.RandomSource random) {
        if (level.isClientSide) return;

        // 获取一天中的时刻（0~24000 tick）
        long dayTime = level.getDayTime() % 24000L;

        // 判定规则：夜晚（12500 ~ 23500）亮灯，其余灭灯
        boolean shouldBeLit = (dayTime >= 12500 && dayTime < 23500);

        boolean isCurrentlyLit = state.getValue(LIT);

        if (shouldBeLit != isCurrentlyLit) {
            // 状态变化时，更新方块并刷新光照
            BlockState newState = state.setValue(LIT, shouldBeLit);
            level.setBlock(pos, newState, 2); // 2 = 更新并同步客户端
            // 注：setBlock 会自动调用 getLightEmission，光照引擎会重新计算
        }

        // 每 100 tick（5秒）检测一次（性能友好）
        level.scheduleTick(pos, this, 100);
    }

    // ---------- 右键手动切换（方便测试，仅管理员/创造） ----------
    /*@Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide && player.canUseGameMasterBlocks()) {
            boolean current = state.getValue(LIT);
            level.setBlock(pos, state.setValue(LIT, !current), 2);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.CONSUME;
    }

    // ---------- （可选）隐形：如果你想要像原版光源方块那样看不见 ----------
    // 如果想让它有实体模型（比如路灯柱），把下面两个方法注释掉或删除
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.empty(); // 无碰撞箱
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.INVISIBLE; // 完全透明，不渲染任何纹理
     */
}