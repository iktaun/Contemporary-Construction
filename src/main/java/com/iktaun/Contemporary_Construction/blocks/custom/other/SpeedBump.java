package com.iktaun.Contemporary_Construction.blocks.custom.other;

import com.iktaun.Contemporary_Construction.blocks.ContemporaryConstructionHorizontalFacingBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * 道路减速带方块（Speed Bump）
 * - 碰撞箱为条状薄片（高1.5像素，宽8像素，长16像素）
 * - 踩踏时强制减速 5%（水平速度保留 95%）
 * - 支持水平朝向（旋转）
 */
public class SpeedBump extends ContemporaryConstructionHorizontalFacingBlock {

    // ---- 两种方向的碰撞箱（条状） ----
    // 南北朝向（Z轴窄条）：X全宽，Z方向从4~12（即宽度8像素）
    public static final VoxelShape SHAPE_NS = Block.box(0, 0, 4, 16, 1.5, 12);
    // 东西朝向（X轴窄条）：Z全宽，X方向从4~12
    public static final VoxelShape SHAPE_EW = Block.box(4, 0, 0, 12, 1.5, 16);

    public SpeedBump(BlockBehaviour.Properties settings) {
        super(settings);
    }

    // ---- 视觉形状（与碰撞形状保持一致） ----
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return getBumpShape(state);
    }

    // ---- 碰撞形状（物理引擎使用） ----
    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return getBumpShape(state);
    }

    // ---- 辅助方法：根据朝向返回对应形状 ----
    private VoxelShape getBumpShape(BlockState state) {
        Direction facing = state.getValue(FACING); // 父类已定义 FACING
        // 南北朝向（Facing.NORTH / SOUTH）使用 NS 形状，东西使用 EW 形状
        if (facing == Direction.NORTH || facing == Direction.SOUTH) {
            return SHAPE_NS;
        } else { // EAST / WEST
            return SHAPE_EW;
        }
    }

    // ---- ★ 核心减速逻辑（踩踏回调） ----
    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        // 只有在地面上且非旁观者才减速
        if (!entity.onGround()|| entity.isSpectator()) {
            super.stepOn(level, pos, state, entity);
            return;
        }

        // 减速系数：0.95 = 保留95%速度（即减速5%），你可以根据需要调整
        double slowFactor = 0.95D;
        entity.setDeltaMovement(
                entity.getDeltaMovement().x * slowFactor,
                entity.getDeltaMovement().y,         // 垂直速度不变，避免跳跃粘滞
                entity.getDeltaMovement().z * slowFactor
        );

        // 可选：给玩家添加“缓慢”效果（视觉提示），但建议用上面的纯物理方式
        // if (entity instanceof Player player) {
        //     player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 10, 0, false, false));
        // }

        super.stepOn(level, pos, state, entity);
    }

    // ---- 辅助行为：让AI寻路系统认为这个方块可通行 ----
    @Override
    public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType type) {
        return true;
    }
    // ---- 删除所有与气泡柱相关的方法（tick, updateShape, onPlace） ----
    // 它们在这里没有任何作用，且可能带来副作用
}