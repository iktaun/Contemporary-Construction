package com.iktaun.Contemporary_Construction.mapcolor.custom;

/**
 * 完全独立的自定义颜色类
 * 不依赖于Minecraft的任何类
 */
public class CustomColor {
    private final int id;           // 颜色ID (0-255)
    private final String name;      // 颜色名称
    private final int rgb;          // RGB颜色值
    private final int argb;         // ARGB颜色值（带透明度）

    // 预定义的透明度常量
    public static final int ALPHA_OPAQUE = 0xFF000000;
    public static final int ALPHA_TRANSLUCENT = 0x80000000;
    public static final int ALPHA_SEMI_TRANSPARENT = 0x40000000;
    public static final int ALPHA_TRANSPARENT = 0x00000000;

    /**
     * 创建自定义颜色
     * @param id 颜色ID (0-255)
     * @param name 颜色名称
     * @param r 红色 (0-255)
     * @param g 绿色 (0-255)
     * @param b 蓝色 (0-255)
     * @param alpha 透明度 (0-255, 0=透明, 255=不透明)
     */
    public CustomColor(int id, String name, int r, int g, int b, int alpha) {
        if (id < 0 || id > 255) {
            throw new IllegalArgumentException("Color ID must be between 0 and 255");
        }
        if (r < 0 || r > 255 || g < 0 || g > 255 || b < 0 || b > 255 || alpha < 0 || alpha > 255) {
            throw new IllegalArgumentException("RGB and alpha values must be between 0 and 255");
        }

        this.id = id;
        this.name = name;
        this.rgb = ((r & 0xFF) << 16) | ((g & 0xFF) << 8) | (b & 0xFF);
        this.argb = ((alpha & 0xFF) << 24) | this.rgb;
    }

    /**
     * 创建不透明的自定义颜色
     */
    public CustomColor(int id, String name, int r, int g, int b) {
        this(id, name, r, g, b, 255);
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public int getRGB() { return rgb; }
    public int getARGB() { return argb; }
    public int getRed() { return (rgb >> 16) & 0xFF; }
    public int getGreen() { return (rgb >> 8) & 0xFF; }
    public int getBlue() { return rgb & 0xFF; }
    public int getAlpha() { return (argb >> 24) & 0xFF; }

    // 颜色操作方法
    public CustomColor withAlpha(int alpha) {
        return new CustomColor(id, name, getRed(), getGreen(), getBlue(), alpha);
    }

    public CustomColor darker(float factor) {
        int r = Math.max(0, (int)(getRed() * factor));
        int g = Math.max(0, (int)(getGreen() * factor));
        int b = Math.max(0, (int)(getBlue() * factor));
        return new CustomColor(id, name + "_darker", r, g, b, getAlpha());
    }

    public CustomColor brighter(float factor) {
        int r = Math.min(255, (int)(getRed() * factor));
        int g = Math.min(255, (int)(getGreen() * factor));
        int b = Math.min(255, (int)(getBlue() * factor));
        return new CustomColor(id, name + "_brighter", r, g, b, getAlpha());
    }

    // 转换为十六进制字符串
    public String toHexString() {
        return String.format("#%08X", argb);
    }

    public String toRGBHexString() {
        return String.format("#%06X", rgb);
    }

    @Override
    public String toString() {
        return String.format("CustomColor{id=%d, name='%s', rgba=[%d,%d,%d,%d]}",
                id, name, getRed(), getGreen(), getBlue(), getAlpha());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        CustomColor that = (CustomColor) obj;
        return id == that.id && rgb == that.rgb && argb == that.argb;
    }

    @Override
    public int hashCode() {
        return 31 * id + argb;
    }
}