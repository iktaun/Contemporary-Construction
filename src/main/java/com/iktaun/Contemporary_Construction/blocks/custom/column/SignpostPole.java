package com.iktaun.Contemporary_Construction.blocks.custom.column;

import com.iktaun.Contemporary_Construction.blocks.ContemporaryConstructionHorizontalFacingBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SignpostPole extends ContemporaryConstructionHorizontalFacingBlock {
    private final VoxelShape POLE = Block.box(6, 0, 6, 10, 16, 10);

    public SignpostPole(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return POLE;
    }
}
