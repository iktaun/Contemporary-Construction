package com.iktaun.Contemporary_Construction.blocks.Entity;

import com.iktaun.Contemporary_Construction.api.IEditableWithBrush;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.UUID;

public abstract class AbstractEditableBlockEntity extends BlockEntity implements IEditableWithBrush {

    protected SignpostText text = new SignpostText();
    @Nullable
    protected UUID editorUUID = null;
    protected boolean waxed = false;

    public AbstractEditableBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    // ===== IEditableWithBrush 接口实现 =====
    @Override
    public SignpostText getText() {
        return text;
    }

    @Override
    public void setText(SignpostText text) {
        this.text = text;
        setChangedAndSync();
    }

    @Override
    public UUID getEditorUUID() {
        return editorUUID;
    }

    @Override
    public void setEditorUUID(UUID uuid) {
        this.editorUUID = uuid;
        setChanged();
    }

    @Override
    public boolean isWaxed() {
        return waxed;
    }

    @Override
    public void setWaxed(boolean waxed) {
        this.waxed = waxed;
        setChangedAndSync();
    }

    @Override
    public BlockPos getBlockPos() {
        return this.worldPosition;
    }

    @Override
    public void setChangedAndSync() {
        setChanged();
        if (level != null && !level.isClientSide()) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    @Override
    public boolean playerIsTooFarAwayToEdit(UUID playerUUID) {
        if (level == null) return true;
        var player = level.getPlayerByUUID(playerUUID);
        return player == null || player.distanceToSqr(worldPosition.getX(), worldPosition.getY(), worldPosition.getZ()) > 64.0D;
    }

    @Override
    public BlockEntity asBlockEntity() {
        return this;
    }

    // ===== NBT 序列化 =====
    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("text", this.text.toNbt());
        tag.putBoolean("waxed", this.waxed);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("text")) {
            this.text = SignpostText.fromNbt(tag.getCompound("text"));
        } else {
            this.text = new SignpostText();
        }
        this.waxed = tag.getBoolean("waxed");
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        saveAdditional(tag);
        return tag;
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public net.minecraft.world.phys.AABB getRenderBoundingBox() {
        return new net.minecraft.world.phys.AABB(worldPosition, worldPosition.offset(1, 1, 1));
    }
}