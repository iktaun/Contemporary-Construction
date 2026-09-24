package com.iktaun.Contemporary_Construction.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.function.Consumer;

public class ColorPickerScreen extends Screen {

    private final Screen parent;
    private final Consumer<Integer> onColorPicked;
    private int currentColor;

    private ColorWheelWidget colorWheel;
    private EditBox hexBox;
    private boolean isUpdatingHex = false;
    private String errorMessage = null;

    public ColorPickerScreen(Screen parent, int initialColor, Consumer<Integer> onColorPicked) {
        super(Component.translatable("gui.contemporaryconstruction.color.title"));
        this.parent = parent;
        this.currentColor = initialColor;
        this.onColorPicked = onColorPicked;
    }

    @Override
    protected void init() {
        super.init();

        int centerX = this.width / 2;
        int wheelSize = Math.min(160, Math.min(this.width - 120, this.height - 180));
        if (wheelSize < 80) wheelSize = 80;

        int wheelX = centerX - wheelSize / 2;
        int wheelY = 60;

        // 色轮
        colorWheel = new ColorWheelWidget(wheelX, wheelY, wheelSize, currentColor, this::onWheelColorChanged);
        this.addRenderableWidget(colorWheel);

        int belowWheelY = wheelY + wheelSize + 24;

        // Hex 标签
        this.addRenderableWidget(new StringWidget(
                wheelX, belowWheelY + 2, 30, 20,
                Component.literal("Hex:"), this.font));

        // Hex 输入框
        hexBox = new EditBox(this.font, wheelX + 32, belowWheelY, 80, 20, Component.literal(""));
        hexBox.setMaxLength(6);
        hexBox.setValue(colorWheel.getHexColor());
        hexBox.setResponder(this::onHexChanged);
        this.addRenderableWidget(hexBox);

        // # 提示符
        this.addRenderableWidget(new StringWidget(
                wheelX + 116, belowWheelY + 2, 20, 20,
                Component.literal("#"), this.font));

        int btnY = belowWheelY + 34;
        int btnW = 70;
        int btnH = 20;

        Button okBtn = Button.builder(Component.translatable("gui.contemporaryconstruction.color.ok"), b -> {
            if (onColorPicked != null) onColorPicked.accept(currentColor);
            Minecraft.getInstance().setScreen(parent);
        }).bounds(centerX - btnW - 6, btnY, btnW, btnH).build();

        Button cancelBtn = Button.builder(Component.translatable("gui.contemporaryconstruction.color.cancel"), b -> {
            Minecraft.getInstance().setScreen(parent);
        }).bounds(centerX + 6, btnY, btnW, btnH).build();

        this.addRenderableWidget(okBtn);
        this.addRenderableWidget(cancelBtn);
    }

    private void onWheelColorChanged(int argb) {
        this.currentColor = argb;
        this.errorMessage = null;
        if (!isUpdatingHex) {
            isUpdatingHex = true;
            try {
                hexBox.setValue(colorWheel.getHexColor());
            } finally {
                isUpdatingHex = false;
            }
        }
    }

    private void onHexChanged(String hex) {
        if (isUpdatingHex) return;
        String clean = hex.trim();
        if (clean.startsWith("#")) clean = clean.substring(1);
        if (clean.isEmpty()) return;

        // 只允许 16 进制字符
        if (!clean.matches("^[0-9a-fA-F]+$")) {
            errorMessage = Component.translatable("gui.contemporaryconstruction.color.invalid").getString();
            return;
        }

        isUpdatingHex = true;
        try {
            colorWheel.setColorFromHex(clean);
            currentColor = colorWheel.getColor();
            errorMessage = null;
        } finally {
            isUpdatingHex = false;
        }
    }

    @Override
    public void render(GuiGraphics g, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(g);
        g.drawCenteredString(this.font, this.title, this.width / 2, 20, 0xFFFFFF);

        // 颜色预览方块
        int previewSize = 28;
        int previewX = this.width / 2 - previewSize / 2;
        int previewY = 34;
        // 边框 + 棋盘格（透明背景显示）
        g.fill(previewX - 1, previewY - 1, previewX + previewSize + 1, previewY + previewSize + 1, 0xFF000000);
        g.fill(previewX, previewY, previewX + previewSize / 2, previewY + previewSize / 2, 0xFFCCCCCC);
        g.fill(previewX + previewSize / 2, previewY, previewX + previewSize, previewY + previewSize / 2, 0xFF888888);
        g.fill(previewX, previewY + previewSize / 2, previewX + previewSize / 2, previewY + previewSize, 0xFF888888);
        g.fill(previewX + previewSize / 2, previewY + previewSize / 2, previewX + previewSize, previewY + previewSize, 0xFFCCCCCC);
        // 颜色叠加
        g.fill(previewX, previewY, previewX + previewSize, previewY + previewSize, currentColor);

        // 错误提示
        if (errorMessage != null) {
            g.drawCenteredString(this.font, Component.literal("§c" + errorMessage),
                    this.width / 2, this.height - 30, 0xFFFFFF);
        }

        super.render(g, mouseX, mouseY, partialTick);
    }

    @Override
    public void onClose() {
        // 直接关闭时视为取消
        super.onClose();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}