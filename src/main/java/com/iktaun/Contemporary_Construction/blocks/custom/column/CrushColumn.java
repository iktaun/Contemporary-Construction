package com.iktaun.Contemporary_Construction.blocks.custom.column;

import com.iktaun.Contemporary_Construction.blocks.ContemporaryConstructionHorizontalFacingBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CrushColumn extends ContemporaryConstructionHorizontalFacingBlock {
    public CrushColumn(BlockBehaviour.Properties settings) {
        super(settings);
    }
    public static final VoxelShape SHAPE = Block.box(7, 0, 7, 9, 15.5, 9);

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}
