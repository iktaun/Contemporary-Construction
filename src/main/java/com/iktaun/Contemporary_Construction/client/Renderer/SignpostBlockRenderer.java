package com.iktaun.Contemporary_Construction.client.Renderer;

import com.iktaun.Contemporary_Construction.api.IEditableWithBrush;
import com.iktaun.Contemporary_Construction.blocks.Entity.ImageLayer;
import com.iktaun.Contemporary_Construction.blocks.Entity.ShapeElementLayer;
import com.iktaun.Contemporary_Construction.blocks.Entity.SignpostText;
import com.iktaun.Contemporary_Construction.blocks.Entity.TextLayer;
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
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.List;

public class SignpostBlockRenderer implements BlockEntityRenderer<BlockEntity> {

    /** 最大光照：使图层内容完全不受方块所在位置的光照影响 */
    private static final int FULL_BRIGHT = 15728880;

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

        poseStack.translate(0.5, 0.5, 0.5);

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

        poseStack.scale(1 / 16f, -1 / 16f, 1 / 16f);
        poseStack.translate(8, 8, 0);
        poseStack.translate(0, 0, -0.02);

        // 图片图层
        for (ImageLayer imgLayer : textData.getImageLayers()) {
            if (!imgLayer.isVisible() || !imgLayer.hasTexture()) continue;
            renderImageLayer(imgLayer, poseStack, buffer);
        }

        // 形状图层
        for (ShapeElementLayer shapeLayer : textData.getShapeLayers()) {
            if (!shapeLayer.isVisible() || !shapeLayer.hasTexture()) continue;
            renderShapeLayer(shapeLayer, poseStack, buffer);
        }

        // 文字图层
        List<TextLayer> textLayers = textData.getTextLayers();
        for (TextLayer layer : textLayers) {
            if (layer.isEmpty()) continue;
            renderTextLayer(layer, poseStack, buffer);
        }

        poseStack.popPose();
    }


    // ============================================================
    // 图片图层：使用 ARGB 分解后的 rgba 调制纹理，光照始终最大
    // ============================================================
    private void renderImageLayer(ImageLayer layer, PoseStack poseStack,
                                  MultiBufferSource buffer) {
        if (!layer.hasTexture()) return;

        poseStack.pushPose();
        poseStack.translate(layer.getOffsetX(), layer.getOffsetY(), layer.getOffsetZ());
        poseStack.mulPose(Axis.ZP.rotationDegrees(layer.getRotateZ()));
        poseStack.mulPose(Axis.YP.rotationDegrees(layer.getRotateY()));
        poseStack.mulPose(Axis.XP.rotationDegrees(layer.getRotateX()));
        float finalScaleX = layer.getScale() * layer.getScaleX();
        float finalScaleY = layer.getScale() * layer.getScaleY();
        poseStack.scale(finalScaleX, finalScaleY, 1);

        // 用户选定的 ARGB 颜色
        int argb = layer.getColorARGB();
        float a = ((argb >> 24) & 0xFF) / 255f;
        float r = ((argb >> 16) & 0xFF) / 255f;
        float g = ((argb >> 8) & 0xFF) / 255f;
        float b = (argb & 0xFF) / 255f;

        VertexConsumer consumer = buffer.getBuffer(RenderType.text(layer.getTextureLocation()));

        float w = layer.getWidth() / 2f;
        float h = layer.getHeight() / 2f;
        float[] vertices = { -w, -h, 0, -w, h, 0, w, h, 0, w, -h, 0 };
        float[] uvs = { 0, 0, 0, 1, 1, 1, 1, 0 };

        for (int i = 0; i < 4; i++) {
            consumer.vertex(poseStack.last().pose(),
                            vertices[i * 3], vertices[i * 3 + 1], vertices[i * 3 + 2])
                    .color(r, g, b, a)
                    .uv(uvs[i * 2], uvs[i * 2 + 1])
                    .uv2(FULL_BRIGHT)
                    .endVertex();
        }
        poseStack.popPose();
    }

    // ============================================================
    // 形状图层：ARGB 分解后的 rgba，发光时提亮 RGB（不改 alpha）
    // ============================================================
    private void renderShapeLayer(ShapeElementLayer layer, PoseStack poseStack,
                                  MultiBufferSource buffer) {
        if (!layer.hasTexture()) return;

        poseStack.pushPose();
        poseStack.translate(layer.getOffsetX(), layer.getOffsetY(), layer.getOffsetZ());
        poseStack.mulPose(Axis.ZP.rotationDegrees(layer.getRotateZ()));
        poseStack.mulPose(Axis.YP.rotationDegrees(layer.getRotateY()));
        poseStack.mulPose(Axis.XP.rotationDegrees(layer.getRotateX()));
        float finalScaleX = layer.getScale() * layer.getScaleX();
        float finalScaleY = layer.getScale() * layer.getScaleY();
        poseStack.scale(finalScaleX, finalScaleY, 1);

        int argb = layer.getColorARGB();
        float a = ((argb >> 24) & 0xFF) / 255f;
        float r = ((argb >> 16) & 0xFF) / 255f;
        float g = ((argb >> 8) & 0xFF) / 255f;
        float b = (argb & 0xFF) / 255f;

        // 发光：提高 RGB 亮度（alpha 不变）
        if (layer.isGlowing()) {
            r = Math.min(1.0f, r * 1.5f);
            g = Math.min(1.0f, g * 1.5f);
            b = Math.min(1.0f, b * 1.5f);
        }

        VertexConsumer consumer = buffer.getBuffer(RenderType.text(layer.getTexture()));

        float w = layer.getWidth() / 2f;
        float h = layer.getHeight() / 2f;
        float[] vertices = { -w, -h, 0, -w, h, 0, w, h, 0, w, -h, 0 };
        float[] uvs = { 0, 0, 0, 1, 1, 1, 1, 0 };

        for (int i = 0; i < 4; i++) {
            consumer.vertex(poseStack.last().pose(),
                            vertices[i * 3], vertices[i * 3 + 1], vertices[i * 3 + 2])
                    .color(r, g, b, a)
                    .uv(uvs[i * 2], uvs[i * 2 + 1])
                    .uv2(FULL_BRIGHT)
                    .endVertex();
        }
        poseStack.popPose();
    }

    // ============================================================
    // 文字图层：用 Minecraft 字体绘制，ARGB 直接写入，光照始终最大
    // ============================================================
    private void renderTextLayer(TextLayer layer, PoseStack poseStack,
                                 MultiBufferSource buffer) {
        poseStack.pushPose();
        poseStack.translate(layer.getOffsetX(), layer.getOffsetY(), layer.getOffsetZ());
        poseStack.mulPose(Axis.ZP.rotationDegrees(layer.getRotateZ()));
        poseStack.mulPose(Axis.YP.rotationDegrees(layer.getRotateY()));
        poseStack.mulPose(Axis.XP.rotationDegrees(layer.getRotateX()));
        poseStack.scale(layer.getScaleX(), layer.getScaleY(), 1);

        int argb = layer.getColorARGB();
        Component text = layer.getStyledText();
        float width = this.mcFont.width(text);
        float x = -width / 2;
        float y = 0;

        this.mcFont.drawInBatch(
                text, x, y, argb, false,
                poseStack.last().pose(), buffer,
                Font.DisplayMode.NORMAL, 0, FULL_BRIGHT
        );

        poseStack.popPose();
    }
}