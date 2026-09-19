package com.iktaun.Contemporary_Construction.blocks.custom.light;

import com.iktaun.Contemporary_Construction.blocks.ContemporaryConstructionHorizontalFacingBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class StreetLightPoleBlock extends ContemporaryConstructionHorizontalFacingBlock {
    public StreetLightPoleBlock(Properties settings) {
        super(settings);
    }
    public static final VoxelShape SHAPE = Block.box(6, 0, 6, 10, 16, 10);

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}
