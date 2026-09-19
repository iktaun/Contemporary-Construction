package com.iktaun.Contemporary_Construction.blocks.custom.barrier;

import com.iktaun.Contemporary_Construction.blocks.ContemporaryConstructionHorizontalFacingBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CrushBarrel extends ContemporaryConstructionHorizontalFacingBlock {
    public CrushBarrel(Properties settings) {
    super(settings);
}
    public static final VoxelShape SHAPE = Block.box(1, 0, 1, 15, 16, 16);

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}
