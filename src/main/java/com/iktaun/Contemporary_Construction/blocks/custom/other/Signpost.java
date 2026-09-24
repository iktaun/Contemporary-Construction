package com.iktaun.Contemporary_Construction.blocks.custom.other;

import com.iktaun.Contemporary_Construction.blocks.Entity.SignpostBlockEntity;
import com.iktaun.Contemporary_Construction.blocks.custom.ModblocksTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class Signpost extends BaseEntityBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty DOWN = BooleanProperty.create("down");

    private static final VoxelShape SHAPE_A = Block.box(7, 0, -11.5, 9, 9, 27.5);
    private static final VoxelShape SHAPE_B = Block.box(-11.5, 0, 7, 27.5, 9, 9);

    public Signpost(Properties properties) {
        super(properties);
        this.registerDefaultState(
                this.stateDefinition.any()
                        .setValue(FACING, Direction.NORTH)
                        .setValue(DOWN, false)
        );
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SignpostBlockEntity(pos, state);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;  // 支持实体渲染器
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, DOWN);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        boolean hasPole = level.getBlockState(pos.below()).is(ModblocksTags.SIGNPOST_POLE);
        Direction facing = context.getHorizontalDirection().getOpposite();
        return this.defaultBlockState()
                .setValue(FACING, facing)
                .setValue(DOWN, hasPole);
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos,
                                Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
        if (neighborPos.equals(pos.below())) {
            boolean hasPole = level.getBlockState(pos.below()).is(ModblocksTags.SIGNPOST_POLE);
            boolean currentDown = state.getValue(DOWN);
            if (hasPole != currentDown) {
                level.setBlock(pos, state.setValue(DOWN, hasPole), Block.UPDATE_ALL);
            }
        }
        super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        Direction facing = state.getValue(FACING);
        switch (facing) {
            case EAST: return SHAPE_A;
            case NORTH: return SHAPE_B;
            case WEST: return SHAPE_A;
            default: return SHAPE_B;
        }
    }
}