package com.iktaun.Contemporary_Construction.blocks.custom.column;

import com.iktaun.Contemporary_Construction.blocks.ContemporaryConstructionHorizontalFacingBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.stream.Stream;

public class SteelColumn extends ContemporaryConstructionHorizontalFacingBlock {

    private static final VoxelShape SHAPE = Stream.of(
            Block.box(7.58579, 0, 7, 8.41421, 16, 9),
            Block.box(7.58579, 0, 7, 8.41421, 16, 9),
            Block.box(7, 0, 7.58579, 9, 16, 8.41421),
            Block.box(7, 0, 7.58579, 9, 16, 8.41421)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public SteelColumn(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}