package com.iktaun.Contemporary_Construction.blocks.Entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class BitumenBlockEntity extends AbstractEditableBlockEntity {

    public BitumenBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BITUMEN_BE.get(), pos, state);
    }
}