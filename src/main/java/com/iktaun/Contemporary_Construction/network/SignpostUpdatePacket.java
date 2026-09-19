package com.iktaun.Contemporary_Construction.network;

import com.iktaun.Contemporary_Construction.api.IEditableWithBrush;
import com.iktaun.Contemporary_Construction.blocks.Entity.ImageLayer;
import com.iktaun.Contemporary_Construction.blocks.Entity.ShapeElementLayer;
import com.iktaun.Contemporary_Construction.blocks.Entity.SignpostText;
import com.iktaun.Contemporary_Construction.blocks.Entity.TextLayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class SignpostUpdatePacket {
    private final BlockPos pos;
    private final SignpostText text;

    public SignpostUpdatePacket(BlockPos pos, SignpostText text) {
        this.pos = pos;
        this.text = text;
    }

    public static void encode(SignpostUpdatePacket packet, FriendlyByteBuf buf) {
        buf.writeBlockPos(packet.pos);

        // ===== 文字图层 =====
        List<TextLayer> textLayers = packet.text.getTextLayers();
        buf.writeVarInt(textLayers.size());
        for (TextLayer layer : textLayers) {
            buf.writeComponent(layer.getText());
            buf.writeEnum(layer.getColor());
            buf.writeBoolean(layer.isGlowing());
            buf.writeFloat(layer.getOffsetX());
            buf.writeFloat(layer.getOffsetY());
            buf.writeFloat(layer.getOffsetZ());
            buf.writeFloat(layer.getRotateX());
            buf.writeFloat(layer.getRotateY());
            buf.writeFloat(layer.getRotateZ());
            buf.writeFloat(layer.getScaleX());
            buf.writeFloat(layer.getScaleY());
        }

        // ===== 图片图层 =====
        List<ImageLayer> imageLayers = packet.text.getImageLayers();
        buf.writeVarInt(imageLayers.size());
        for (ImageLayer layer : imageLayers) {
            buf.writeUtf(layer.getTextureLocation() != null ? layer.getTextureLocation().toString() : "");
            buf.writeInt(layer.getWidth());
            buf.writeInt(layer.getHeight());
            buf.writeFloat(layer.getOffsetX());
            buf.writeFloat(layer.getOffsetY());
            buf.writeFloat(layer.getOffsetZ());
            buf.writeFloat(layer.getScale());
            buf.writeBoolean(layer.isVisible());
            buf.writeFloat(layer.getRotateX());
            buf.writeFloat(layer.getRotateY());
            buf.writeFloat(layer.getRotateZ());
            buf.writeFloat(layer.getScaleX());
            buf.writeFloat(layer.getScaleY());
        }

        // ===== 形状图层 =====
        List<ShapeElementLayer> shapeLayers = packet.text.getShapeLayers();
        buf.writeVarInt(shapeLayers.size());
        for (ShapeElementLayer layer : shapeLayers) {
            buf.writeUtf(layer.getName());
            buf.writeUtf(layer.getTexture() != null ? layer.getTexture().toString() : "");
            buf.writeInt(layer.getWidth());
            buf.writeInt(layer.getHeight());
            buf.writeFloat(layer.getOffsetX());
            buf.writeFloat(layer.getOffsetY());
            buf.writeFloat(layer.getOffsetZ());
            buf.writeFloat(layer.getScale());
            buf.writeUtf(layer.getColor().getName());
            buf.writeBoolean(layer.isGlowing());
            buf.writeBoolean(layer.isVisible());
            buf.writeFloat(layer.getRotateX());
            buf.writeFloat(layer.getRotateY());
            buf.writeFloat(layer.getRotateZ());
            buf.writeFloat(layer.getScaleX());
            buf.writeFloat(layer.getScaleY());
        }
    }

    public static SignpostUpdatePacket decode(FriendlyByteBuf buf) {
        BlockPos pos = buf.readBlockPos();

        // ===== 文字图层 =====
        int textCount = buf.readVarInt();
        List<TextLayer> textLayers = new ArrayList<>();
        for (int i = 0; i < textCount; i++) {
            Component text = buf.readComponent();
            DyeColor color = buf.readEnum(DyeColor.class);
            boolean glowing = buf.readBoolean();
            float offsetX = buf.readFloat();
            float offsetY = buf.readFloat();
            float offsetZ = buf.readFloat();
            float rotateX = buf.readFloat();
            float rotateY = buf.readFloat();
            float rotateZ = buf.readFloat();
            float scaleX = buf.readFloat();
            float scaleY = buf.readFloat();
            TextLayer layer = new TextLayer(text, color, glowing,
                    offsetX, offsetY, offsetZ, rotateX, rotateY, rotateZ, scaleX, scaleY);
            textLayers.add(layer);
        }

        // ===== 图片图层 =====
        int imageCount = buf.readVarInt();
        List<ImageLayer> imageLayers = new ArrayList<>();
        for (int i = 0; i < imageCount; i++) {
            String textureStr = buf.readUtf();
            var texture = textureStr.isEmpty() ? null : new net.minecraft.resources.ResourceLocation(textureStr);
            int width = buf.readInt();
            int height = buf.readInt();
            float offsetX = buf.readFloat();
            float offsetY = buf.readFloat();
            float offsetZ = buf.readFloat();
            float scale = buf.readFloat();
            boolean visible = buf.readBoolean();
            float rotateX = buf.readFloat();
            float rotateY = buf.readFloat();
            float rotateZ = buf.readFloat();
            float scaleX = buf.readFloat();
            float scaleY = buf.readFloat();
            imageLayers.add(new ImageLayer(texture, width, height,
                    offsetX, offsetY, offsetZ, scale, visible,
                    rotateX, rotateY, rotateZ, scaleX, scaleY));
        }

        // ===== 形状图层 =====
        int shapeCount = buf.readVarInt();
        List<ShapeElementLayer> shapeLayers = new ArrayList<>();
        for (int i = 0; i < shapeCount; i++) {
            String name = buf.readUtf();
            String textureStr = buf.readUtf();
            var texture = textureStr.isEmpty() ? null : new net.minecraft.resources.ResourceLocation(textureStr);
            int width = buf.readInt();
            int height = buf.readInt();
            float offsetX = buf.readFloat();
            float offsetY = buf.readFloat();
            float offsetZ = buf.readFloat();
            float scale = buf.readFloat();
            DyeColor color = DyeColor.byName(buf.readUtf(), DyeColor.WHITE);
            boolean glowing = buf.readBoolean();
            boolean visible = buf.readBoolean();
            float rotateX = buf.readFloat();
            float rotateY = buf.readFloat();
            float rotateZ = buf.readFloat();
            float scaleX = buf.readFloat();
            float scaleY = buf.readFloat();
            shapeLayers.add(new ShapeElementLayer(name, texture, width, height,
                    offsetX, offsetY, offsetZ, scale, color, glowing, visible,
                    rotateX, rotateY, rotateZ, scaleX, scaleY));
        }

        return new SignpostUpdatePacket(pos, new SignpostText(textLayers, imageLayers, shapeLayers));
    }

    public static void handle(SignpostUpdatePacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player == null) return;
            Level level = player.level();
            BlockEntity be = level.getBlockEntity(packet.pos);
            if (be instanceof IEditableWithBrush editable) {
                if (editable.getEditorUUID() != null && !editable.getEditorUUID().equals(player.getUUID())) {
                    return;
                }
                editable.setText(packet.text);
                editable.setEditorUUID(null);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}