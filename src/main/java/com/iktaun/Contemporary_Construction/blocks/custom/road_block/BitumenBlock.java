package com.iktaun.Contemporary_Construction.blocks.custom.road_block;

import com.iktaun.Contemporary_Construction.blocks.Entity.BitumenBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class BitumenBlock extends AllBitumenBlock implements EntityBlock {

    public BitumenBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new BitumenBlockEntity(pos, state);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof BitumenBlockEntity) {
                // 清理工作（如有需要）
            }
            super.onRemove(state, level, pos, newState, isMoving);
        }
    }
}