package com.iktaun.Contemporary_Construction.blocks.custom.light;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class StreetLightBlockA extends ModLightBlock {

    // 1. 定义朝向属性（水平方向：北东南西）
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    // 自定义碰撞箱（注意：这个形状是固定朝向北/南的长条）

    public StreetLightBlockA(Properties pProperties) {
        super(pProperties);
        // 2. 注册默认状态：朝北 + 未点亮
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(LIT, false)
                .setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        // 3. 把 FACING 加入状态定义（注意：父类已经有 LIT，这里要一起加上）
        builder.add(FACING);
        super.createBlockStateDefinition(builder); // 父类会加 LIT
    }

    // 4. 玩家放置时，根据玩家朝向设置方块朝向
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        // 获取玩家水平朝向（南/北/东/西），并取反（因为玩家朝北时，方块应该朝南正对玩家）
        Direction direction = context.getHorizontalDirection().getOpposite();
        return this.defaultBlockState().setValue(FACING, direction);
    }

    // 5. 碰撞箱（保持不变，但注意这个形状是固定朝向的）
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        // 获取当前朝向
        Direction facing = state.getValue(FACING);
        // 基础形状（假设默认朝北）
        VoxelShape baseShape = Block.box(-5, 0, 5, 22, 16, 9.5);
        // 根据朝向旋转（需要手动计算偏移，或者用 VoxelShapes 的旋转辅助方法）
        // 推荐使用 net.minecraft.world.phys.shapes.Shapes 的旋转方法，但比较复杂。
        // 简单做法：根据朝向返回预先定义好的不同形状。
        switch (facing) {
            case NORTH:  return Block.box(6.5, 0, -5, 9.5, 16, 22);
            case EAST: return Block.box(-5, 0, 6.5, 22, 16, 9.5); // 与 NORTH 相同，但其实是旋转 180°
            case SOUTH:  return Block.box(-9.5, 0, -5, -6.5, 16, 22);
            default:    return Block.box(-5, 0, 5, 22, 16, 9.5); // NORTH
        }
    }
}