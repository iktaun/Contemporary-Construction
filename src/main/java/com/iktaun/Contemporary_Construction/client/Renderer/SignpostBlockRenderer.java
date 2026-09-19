package com.iktaun.Contemporary_Construction.client.Renderer;

import com.iktaun.Contemporary_Construction.api.IEditableWithBrush;
import com.iktaun.Contemporary_Construction.blocks.Entity.ImageLayer;
import com.iktaun.Contemporary_Construction.blocks.Entity.ShapeElementLayer;
import com.iktaun.Contemporary_Construction.blocks.Entity.SignpostText;
import com.iktaun.Contemporary_Construction.blocks.Entity.TextLayer;
import com.iktaun.Contemporary_Construction.items.Moditems;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.List;

public class SignpostBlockRenderer implements BlockEntityRenderer<BlockEntity> {

    private final Font mcFont;

    public SignpostBlockRenderer(BlockEntityRendererProvider.Context context) {
        this.mcFont = context.getFont();
    }

    @Override
    public void render(BlockEntity entity, float partialTick, PoseStack poseStack,
                       MultiBufferSource buffer, int packedLight, int packedOverlay) {

        if (!(entity instanceof IEditableWithBrush editable)) return;

        Player player = Minecraft.getInstance().player;
        if (player == null) return;

        SignpostText textData = editable.getText();
        if (textData == null || textData.isEmpty()) return;

        poseStack.pushPose();

        // 移动到方块中心
        poseStack.translate(0.5, 0.5, 0.5);

        // 朝向旋转
        BlockState state = entity.getBlockState();
        Direction facing = Direction.NORTH;
        if (state.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
            facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
        }
        float rotation = switch (facing) {
            case NORTH -> 0;
            case EAST -> 90;
            case SOUTH -> 180;
            case WEST -> 270;
            default -> 0;
        };
        poseStack.mulPose(Axis.YP.rotationDegrees(rotation));

        // 缩放到像素单位
        poseStack.scale(1 / 16f, -1 / 16f, 1 / 16f);
        poseStack.translate(8, 8, 0);
        poseStack.translate(0, 0, -0.02);

        // ----- 图片图层 -----
        for (ImageLayer imgLayer : textData.getImageLayers()) {
            if (!imgLayer.isVisible() || !imgLayer.hasTexture()) continue;
            renderImageLayer(imgLayer, poseStack, buffer, packedLight);
        }

        // ----- 形状图层 -----
        for (ShapeElementLayer shapeLayer : textData.getShapeLayers()) {
            if (!shapeLayer.isVisible() || !shapeLayer.hasTexture()) continue;
            renderShapeLayer(shapeLayer, poseStack, buffer, packedLight);
        }

        // ----- 文字图层（使用Minecraft原版字体） -----
        List<TextLayer> textLayers = textData.getTextLayers();
        int layerCount = textLayers.size();
        if (layerCount > 0) {
            float lineHeight = this.mcFont.lineHeight;
            float totalHeight = layerCount * lineHeight;
            float startY = -totalHeight / 2;

            for (int i = 0; i < layerCount; i++) {
                TextLayer layer = textLayers.get(i);
                if (layer.isEmpty()) continue;

                poseStack.pushPose();
                poseStack.translate(layer.getOffsetX(), layer.getOffsetY(), layer.getOffsetZ());
                poseStack.mulPose(Axis.ZP.rotationDegrees(layer.getRotateZ()));
                poseStack.mulPose(Axis.YP.rotationDegrees(layer.getRotateY()));
                poseStack.mulPose(Axis.XP.rotationDegrees(layer.getRotateX()));
                poseStack.scale(layer.getScaleX(), layer.getScaleY(), 1);

                int color = layer.getColor().getTextColor();
                int light = 15728880; // 最大亮度
                Component text = layer.getText();
                float width = this.mcFont.width(text);
                float x = -width / 2;
                float y = 0;

                this.mcFont.drawInBatch(
                        text, x, y, color, false,
                        poseStack.last().pose(), buffer,
                        Font.DisplayMode.NORMAL, 0, light
                );
                poseStack.popPose();
            }
        }

        poseStack.popPose();
    }

    private void renderImageLayer(ImageLayer layer, PoseStack poseStack,
                                  MultiBufferSource buffer, int packedLight) {
        if (!layer.hasTexture()) return;
        poseStack.pushPose();
        poseStack.translate(layer.getOffsetX(), layer.getOffsetY(), layer.getOffsetZ());
        poseStack.mulPose(Axis.ZP.rotationDegrees(layer.getRotateZ()));
        poseStack.mulPose(Axis.YP.rotationDegrees(layer.getRotateY()));
        poseStack.mulPose(Axis.XP.rotationDegrees(layer.getRotateX()));
        float finalScaleX = layer.getScale() * layer.getScaleX();
        float finalScaleY = layer.getScale() * layer.getScaleY();
        poseStack.scale(finalScaleX, finalScaleY, 1);

        VertexConsumer consumer = buffer.getBuffer(RenderType.text(layer.getTextureLocation()));

        float w = layer.getWidth() / 2f;
        float h = layer.getHeight() / 2f;
        float[] vertices = { -w, -h, 0, -w, h, 0, w, h, 0, w, -h, 0 };
        float[] uvs = { 0, 0, 0, 1, 1, 1, 1, 0 };

        for (int i = 0; i < 4; i++) {
            consumer.vertex(poseStack.last().pose(), vertices[i * 3], vertices[i * 3 + 1], vertices[i * 3 + 2])
                    .color(255, 255, 255, 255)
                    .uv(uvs[i * 2], uvs[i * 2 + 1])
                    .uv2(packedLight)
                    .endVertex();
        }
        poseStack.popPose();
    }

    private void renderShapeLayer(ShapeElementLayer layer, PoseStack poseStack,
                                  MultiBufferSource buffer, int packedLight) {
        if (!layer.hasTexture()) return;
        poseStack.pushPose();
        poseStack.translate(layer.getOffsetX(), layer.getOffsetY(), layer.getOffsetZ());
        poseStack.mulPose(Axis.ZP.rotationDegrees(layer.getRotateZ()));
        poseStack.mulPose(Axis.YP.rotationDegrees(layer.getRotateY()));
        poseStack.mulPose(Axis.XP.rotationDegrees(layer.getRotateX()));
        float finalScaleX = layer.getScale() * layer.getScaleX();
        float finalScaleY = layer.getScale() * layer.getScaleY();
        poseStack.scale(finalScaleX, finalScaleY, 1);

        int color = layer.getColor().getTextColor();
        float r = ((color >> 16) & 0xFF) / 255f;
        float g = ((color >> 8) & 0xFF) / 255f;
        float b = (color & 0xFF) / 255f;
        float a = 1.0f;

        VertexConsumer consumer = buffer.getBuffer(RenderType.text(layer.getTexture()));

        float w = layer.getWidth() / 2f;
        float h = layer.getHeight() / 2f;
        float[] vertices = { -w, -h, 0, -w, h, 0, w, h, 0, w, -h, 0 };
        float[] uvs = { 0, 0, 0, 1, 1, 1, 1, 0 };

        for (int i = 0; i < 4; i++) {
            consumer.vertex(poseStack.last().pose(), vertices[i * 3], vertices[i * 3 + 1], vertices[i * 3 + 2])
                    .color(r, g, b, a)
                    .uv(uvs[i * 2], uvs[i * 2 + 1])
                    .uv2(packedLight)
                    .endVertex();
        }
        poseStack.popPose();
    }
}