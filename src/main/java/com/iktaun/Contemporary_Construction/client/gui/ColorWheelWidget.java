package com.iktaun.Contemporary_Construction.client.gui;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.awt.Color;
import java.util.function.Consumer;

/**
 * 色轮组件 - HSV 圆盘 + 亮度滑块
 * 布局：
 *   - 上方：正方形色轮（Hue 沿圆周变化，Saturation 从中心到边缘）
 *   - 下方：水平亮度滑块（Value）
 * 点击或拖拽可选择颜色，实时回调。
 */
public class ColorWheelWidget extends AbstractWidget {

    private int colorARGB;
    private float hue;          // 0-360
    private float saturation;   // 0-1
    private float brightness;   // 0-1

    private ResourceLocation wheelTextureLocation;
    private static final int TEXTURE_SIZE = 128;
    private int wheelRadius;

    private boolean draggingWheel = false;
    private boolean draggingBrightness = false;

    private final Consumer<Integer> onColorChanged;

    // 动态布局
    private int wheelX, wheelY, wheelSize;
    private int barX, barY, barW, barH;

    /**
     * @param x             左上角 X
     * @param y             左上角 Y
     * @param wheelSize     色轮边长（正方形）
     * @param initialColor  初始颜色（ARGB）
     * @param onColorChanged 颜色变化回调，参数为 ARGB
     */
    public ColorWheelWidget(int x, int y, int wheelSize, int initialColor, Consumer<Integer> onColorChanged) {
        super(x, y, wheelSize, wheelSize + 20, Component.empty());
        this.onColorChanged = onColorChanged;
        this.wheelSize = wheelSize;
        this.wheelRadius = wheelSize / 2;
        setColor(initialColor);
        generateWheelTexture();
    }

    /**
     * 预生成色轮纹理（一次性，之后直接用纹理绘制）
     */
    private void generateWheelTexture() {
        NativeImage nativeImage = new NativeImage(TEXTURE_SIZE, TEXTURE_SIZE, true);
        float center = TEXTURE_SIZE / 2f;
        float radius = TEXTURE_SIZE / 2f;

        for (int y = 0; y < TEXTURE_SIZE; y++) {
            for (int x = 0; x < TEXTURE_SIZE; x++) {
                float dx = x - center + 0.5f;
                float dy = y - center + 0.5f;
                float dist = (float) Math.sqrt(dx * dx + dy * dy);

                if (dist > radius) {
                    // 圆外：透明
                    nativeImage.setPixelRGBA(x, y, 0);
                } else {
                    float sat = dist / radius;
                    float angle = (float) Math.toDegrees(Math.atan2(dy, dx));
                    if (angle < 0) angle += 360;

                    int rgb = Color.HSBtoRGB(angle / 360f, sat, 1f);
                    int r = (rgb >> 16) & 0xFF;
                    int g = (rgb >> 8) & 0xFF;
                    int b = rgb & 0xFF;
                    // NativeImage 使用 ABGR 格式
                    int abgr = (0xFF << 24) | (b << 16) | (g << 8) | r;
                    nativeImage.setPixelRGBA(x, y, abgr);
                }
            }
        }

        DynamicTexture dynTex = new DynamicTexture(nativeImage);
        wheelTextureLocation = new ResourceLocation("contemporaryconstruction",
                "color_wheel_" + System.currentTimeMillis());
        Minecraft.getInstance().getTextureManager().register(wheelTextureLocation, dynTex);
    }

    /**
     * 从 ARGB 整数设置当前颜色，并同步 HSV
     */
    public void setColor(int argb) {
        this.colorARGB = argb;
        int r = (argb >> 16) & 0xFF;
        int g = (argb >> 8) & 0xFF;
        int b = argb & 0xFF;

        float[] hsb = Color.RGBtoHSB(r, g, b, null);
        this.hue = hsb[0] * 360f;
        this.saturation = hsb[1];
        this.brightness = hsb[2];
    }

    public int getColor() {
        return colorARGB;
    }

    private void updateColorFromHsv() {
        int rgb = Color.HSBtoRGB(hue / 360f, saturation, brightness);
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;
        colorARGB = (0xFF << 24) | (r << 16) | (g << 8) | b;
        if (onColorChanged != null) onColorChanged.accept(colorARGB);
    }

    @Override
    public void renderWidget(GuiGraphics g, int mouseX, int mouseY, float partialTick) {
        int x = this.getX();
        int y = this.getY();
        wheelX = x;
        wheelY = y;

        // 色轮纹理
        if (wheelTextureLocation != null) {
            g.blit(wheelTextureLocation,
                    wheelX, wheelY, wheelSize, wheelSize,
                    0, 0, TEXTURE_SIZE, TEXTURE_SIZE,
                    TEXTURE_SIZE, TEXTURE_SIZE);
        }

        // 选中位置指示器（黑圈 + 白圈）
        float cos = (float) Math.cos(Math.toRadians(hue));
        float sin = (float) Math.sin(Math.toRadians(hue));
        int selX = wheelX + wheelRadius + (int) (cos * saturation * wheelRadius);
        int selY = wheelY + wheelRadius + (int) (sin * saturation * wheelRadius);
        drawCircleOutline(g, selX, selY, 5, 0xFF000000);
        drawCircleOutline(g, selX, selY, 3, 0xFFFFFFFF);

        // 亮度滑块
        barX = x;
        barY = y + wheelSize + 4;
        barW = wheelSize;
        barH = 12;

        int satRgb = Color.HSBtoRGB(hue / 360f, saturation, 1f);
        int satR = (satRgb >> 16) & 0xFF;
        int satG = (satRgb >> 8) & 0xFF;
        int satB = satRgb & 0xFF;
        for (int i = 0; i < barW; i++) {
            float t = (float) i / barW;
            int r = (int) (satR * t);
            int gg = (int) (satG * t);
            int bb = (int) (satB * t);
            g.fill(barX + i, barY, barX + i + 1, barY + barH, 0xFF000000 | (r << 16) | (gg << 8) | bb);
        }

        // 滑块边框
        g.fill(barX - 1, barY - 1, barX + barW + 1, barY, 0xFF000000);
        g.fill(barX - 1, barY + barH, barX + barW + 1, barY + barH + 1, 0xFF000000);
        g.fill(barX - 1, barY, barX, barY + barH, 0xFF000000);
        g.fill(barX + barW, barY, barX + barW + 1, barY + barH, 0xFF000000);

        // 亮度指示器
        int selBx = barX + (int) (brightness * barW);
        g.fill(selBx - 2, barY - 2, selBx + 2, barY + barH + 2, 0xFFFFFFFF);
    }

    private void drawCircleOutline(GuiGraphics g, int cx, int cy, int radius, int color) {
        for (int angle = 0; angle < 360; angle += 4) {
            float rad = (float) Math.toRadians(angle);
            int x = cx + (int) (Math.cos(rad) * radius);
            int y = cy + (int) (Math.sin(rad) * radius);
            g.fill(x, y, x + 1, y + 1, color);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button != 0) return false;

        // 色轮
        if (mouseX >= wheelX && mouseX <= wheelX + wheelSize
                && mouseY >= wheelY && mouseY <= wheelY + wheelSize) {
            draggingWheel = true;
            updateFromMouseWheel(mouseX, mouseY);
            return true;
        }

        // 亮度滑块
        if (mouseX >= barX && mouseX <= barX + barW
                && mouseY >= barY && mouseY <= barY + barH) {
            draggingBrightness = true;
            updateFromMouseBrightness(mouseX);
            return true;
        }

        return false;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (draggingWheel) {
            updateFromMouseWheel(mouseX, mouseY);
            return true;
        }
        if (draggingBrightness) {
            updateFromMouseBrightness(mouseX);
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        draggingWheel = false;
        draggingBrightness = false;
        return super.mouseReleased(mouseX, mouseY, button);
    }

    private void updateFromMouseWheel(double mouseX, double mouseY) {
        float cx = wheelX + wheelRadius;
        float cy = wheelY + wheelRadius;
        float dx = (float) mouseX - cx;
        float dy = (float) mouseY - cy;
        float dist = (float) Math.sqrt(dx * dx + dy * dy);

        if (dist > wheelRadius) dist = wheelRadius;

        float angle = (float) Math.toDegrees(Math.atan2(dy, dx));
        if (angle < 0) angle += 360;

        this.hue = angle;
        this.saturation = dist / wheelRadius;
        updateColorFromHsv();
    }

    private void updateFromMouseBrightness(double mouseX) {
        float t = (float) ((mouseX - barX) / (double) barW);
        if (t < 0) t = 0;
        if (t > 1) t = 1;
        this.brightness = t;
        updateColorFromHsv();
    }

    /**
     * 从 16 进制字符串（如 "#FF5500" 或 "FF5500"）设置颜色
     */
    public void setColorFromHex(String hex) {
        if (hex == null) return;
        hex = hex.trim();
        if (hex.startsWith("#")) hex = hex.substring(1);
        if (hex.length() != 6) return;
        try {
            int rgb = Integer.parseInt(hex, 16);
            setColor(0xFF000000 | rgb);
        } catch (NumberFormatException ignored) {}
    }

    /**
     * 当前颜色的 16 进制字符串（不含 # 和 alpha）
     */
    public String getHexColor() {
        int r = (colorARGB >> 16) & 0xFF;
        int g = (colorARGB >> 8) & 0xFF;
        int b = colorARGB & 0xFF;
        return String.format("%02X%02X%02X", r, g, b);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output) {}
}