package com.iktaun.Contemporary_Construction.client.gui;

import com.iktaun.Contemporary_Construction.blocks.Entity.ImageLayer;
import com.iktaun.Contemporary_Construction.blocks.Entity.ShapeElementLayer;
import com.iktaun.Contemporary_Construction.blocks.Entity.SignpostBlockEntity;
import com.iktaun.Contemporary_Construction.blocks.Entity.SignpostText;
import com.iktaun.Contemporary_Construction.blocks.Entity.TextLayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.List;

public class BlockPreviewWidget extends AbstractWidget {

    private final SignpostBlockEntity signEntity;

    public BlockPreviewWidget(int x, int y, int width, int height, SignpostBlockEntity entity) {
        super(x, y, width, height, Component.empty());
        this.signEntity = entity;
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        int x = this.getX();
        int y = this.getY();
        int w = this.width;
        int h = this.height;

        BlockState state = signEntity.getBlockState();
        Direction facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
        float rotation = switch (facing) {
            case NORTH -> 0;
            case EAST -> 90;
            case SOUTH -> 180;
            case WEST -> 270;
            default -> 0;
        };

        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(x + w / 2.0, y + h / 2.0, 0);
        guiGraphics.pose().mulPose(com.mojang.math.Axis.ZP.rotationDegrees(rotation));
        guiGraphics.pose().translate(-w / 2.0, -h / 2.0, 0);

        // 面板背景
        int woodLight = 0xFFC4A882;
        int woodDark = 0xFF8B6B4D;
        for (int i = 0; i < w; i += 8) {
            int color = (i / 8 % 2 == 0) ? woodLight : woodDark;
            guiGraphics.fill(x + i, y, x + Math.min(i + 8, w), y + h, color);
        }
        guiGraphics.fill(x, y, x + w, y + 2, 0xFF6B4D2B);
        guiGraphics.fill(x, y + h - 2, x + w, y + h, 0xFF6B4D2B);
        guiGraphics.fill(x, y, x + 2, y + h, 0xFF6B4D2B);
        guiGraphics.fill(x + w - 2, y, x + w, y + h, 0xFF6B4D2B);
        guiGraphics.pose().popPose();

        // 获取数据
        SignpostText textData = signEntity.getText();
        if (textData == null || textData.isEmpty()) {
            guiGraphics.drawCenteredString(Minecraft.getInstance().font,
                    Component.literal("空路牌"), x + w / 2, y + h / 2 - 4, 0xCCCCCC);
            return;
        }

        // 图片图层预览（简化版，应用变换）
        for (ImageLayer imgLayer : textData.getImageLayers()) {
            if (!imgLayer.isVisible() || !imgLayer.hasTexture()) continue;
            int px = x + 4 + (int) imgLayer.getOffsetX();
            int py = y + 4 + (int) imgLayer.getOffsetY();
            int pw = Math.min((int)(imgLayer.getWidth() * imgLayer.getScaleX() * imgLayer.getScale()), w - 8);
            int ph = Math.min((int)(imgLayer.getHeight() * imgLayer.getScaleY() * imgLayer.getScale()), h - 8);
            guiGraphics.fill(px, py, px + pw, py + ph, 0x88BBFF);
            guiGraphics.drawString(Minecraft.getInstance().font, "📷", px + pw / 2 - 4, py + ph / 2 - 4, 0xFFFFFF, false);
        }

        // 形状图层预览（应用变换）
        for (ShapeElementLayer shapeLayer : textData.getShapeLayers()) {
            if (!shapeLayer.isVisible() || !shapeLayer.hasTexture()) continue;
            int px = x + 4 + (int) shapeLayer.getOffsetX();
            int py = y + 4 + (int) shapeLayer.getOffsetY();
            int pw = Math.min((int)(shapeLayer.getWidth() * shapeLayer.getScaleX() * shapeLayer.getScale()), w - 8);
            int ph = Math.min((int)(shapeLayer.getHeight() * shapeLayer.getScaleY() * shapeLayer.getScale()), h - 8);
            guiGraphics.fill(px, py, px + pw, py + ph, 0xFFFF88BB);
        }

        // 文字图层预览（应用变换）
        List<TextLayer> layers = textData.getTextLayers();
        int layerCount = layers.size();
        if (layerCount == 0) {
            if (!textData.getImageLayers().isEmpty()) {
                guiGraphics.drawCenteredString(Minecraft.getInstance().font,
                        Component.literal("📷 " + textData.getImageLayers().size() + " 张图片"),
                        x + w / 2, y + h / 2 - 4, 0xCCCCCC);
            }
            return;
        }

        float lineHeight = Minecraft.getInstance().font.lineHeight;
        float totalHeight = layerCount * lineHeight;
        float startY = y + (h - totalHeight) / 2;

        for (int i = 0; i < layerCount; i++) {
            TextLayer layer = layers.get(i);
            if (layer == null || layer.isEmpty()) continue;

            Component line = layer.getText();
            int color = layer.getColor().getTextColor();
            boolean glowing = layer.isGlowing();

            float lineWidth = Minecraft.getInstance().font.width(line);
            // 应用变换：偏移 + 旋转 + 缩放（在2D预览中简化，仅展示位置变化）
            float xPos = x + (w - lineWidth) / 2 + layer.getOffsetX();
            float yPos = startY + i * lineHeight + layer.getOffsetY();

            int finalColor = glowing ? (color | 0xFFAA00) : color;
            guiGraphics.drawString(Minecraft.getInstance().font,
                    line, (int) xPos, (int) yPos, finalColor, false);
        }

        // 发光光晕
        boolean anyGlowing = layers.stream().anyMatch(TextLayer::isGlowing) ||
                textData.getImageLayers().stream().anyMatch(ImageLayer::isVisible) ||
                textData.getShapeLayers().stream().anyMatch(ShapeElementLayer::isGlowing);
        if (anyGlowing) {
            guiGraphics.fill(x - 4, y - 4, x + w + 4, y + h + 4, 0x33FFFF00);
            guiGraphics.fill(x - 2, y - 2, x + w + 2, y + h + 2, 0x22FFFF00);
        }
    }

    public void updatePreview() {}
    public void destroy() {}

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output) {}
}