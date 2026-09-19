package com.iktaun.Contemporary_Construction.blocks.Entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class SignpostBlockEntity extends AbstractEditableBlockEntity {

    public SignpostBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SIGNPOST_BE.get(), pos, state);
    }
}