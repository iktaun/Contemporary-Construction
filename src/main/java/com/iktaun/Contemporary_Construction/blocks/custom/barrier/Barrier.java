package com.iktaun.Contemporary_Construction.blocks.custom.barrier;

import com.iktaun.Contemporary_Construction.blocks.ContemporaryConstructionHorizontalFacingBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class Barrier extends ContemporaryConstructionHorizontalFacingBlock {
    public Barrier(Properties settings) {
        super(settings);
    }
    public static final VoxelShape SHAPE_A = Block.box(0, 5, 7.25, 16, 18.5, 8.75);
    public static final VoxelShape SHAPE_B = Block.box(7.25, 5, 0, 8.75, 18.5, 16);

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        Direction facing = state.getValue(FACING);
        switch (facing) {
            case EAST: return SHAPE_B;
            case SOUTH: return SHAPE_A;
            case WEST: return SHAPE_B;
            default: return SHAPE_A; // NORTH
        }
    }
}