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

public class ThickSteelColumn extends ContemporaryConstructionHorizontalFacingBlock {

    private static final VoxelShape SHAPE = Stream.of(
            Block.box(6.75736, 0, 5, 9.24264, 16, 11),
            Block.box(6.75736, 0, 5, 9.24264, 16, 11),
            Block.box(5, 0, 6.75736, 11, 16, 9.24264),
            Block.box(5, 0, 6.75736, 11, 16, 9.24264)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public ThickSteelColumn(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}