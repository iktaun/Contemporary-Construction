package com.iktaun.Contemporary_Construction.api;

import com.iktaun.Contemporary_Construction.blocks.Entity.SignpostText;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.UUID;

/**
 * 可被刷子编辑的方块实体接口
 */
public interface IEditableWithBrush {

    SignpostText getText();

    void setText(SignpostText text);

    UUID getEditorUUID();

    void setEditorUUID(UUID uuid);

    boolean isWaxed();

    void setWaxed(boolean waxed);

    BlockPos getBlockPos();

    void setChangedAndSync();

    boolean playerIsTooFarAwayToEdit(UUID playerUUID);

    BlockEntity asBlockEntity();

    default void markDirtyAndUpdate() {
        setChangedAndSync();
    }
}