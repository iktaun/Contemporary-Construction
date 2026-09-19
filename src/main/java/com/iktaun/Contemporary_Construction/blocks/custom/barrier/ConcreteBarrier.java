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

public class ConcreteBarrier extends ContemporaryConstructionHorizontalFacingBlock {
    public static final VoxelShape SHAPE_A = Block.box(0, -0.3806018829345703, 5.913416862487793, 16, 13.61939811706543, 10.078416862487792);
    public static final VoxelShape SHAPE_B = Block.box(5.9175, -0.3806018829345703, -0.004083137512207458, 10.0825, 13.61939811706543, 15.995916862487793);

    public ConcreteBarrier(Properties pProperties) {
        super(pProperties);
    }

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
