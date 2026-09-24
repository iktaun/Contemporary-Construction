package com.iktaun.Contemporary_Construction.blocks.Entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;

import javax.annotation.Nullable;
import java.util.Objects;

public class TextLayer {

    private Component text;
    private int color;              // ARGB
    private boolean glowing;
    private boolean bold;
    private boolean italic;
    private float offsetX;
    private float offsetY;
    private float offsetZ;
    private float rotateX;
    private float rotateY;
    private float rotateZ;
    private float scaleX;
    private float scaleY;

    /** 字体 ID，null = MC 默认字体 */
    @Nullable
    private ResourceLocation fontId;

    public static final int DEFAULT_COLOR = 0xFFFFFFFF;

    // ----- 构造函数 -----
    public TextLayer() {
        this(Component.empty(), DEFAULT_COLOR, false, false, false,
                0, 0, 0, 0, 0, 0, 1.0f, 1.0f);
    }

    public TextLayer(Component text, int color, boolean glowing) {
        this(text, color, glowing, false, false,
                0, 0, 0, 0, 0, 0, 1.0f, 1.0f);
    }

    public TextLayer(Component text, int color, boolean glowing, float offsetX, float offsetY) {
        this(text, color, glowing, false, false,
                offsetX, offsetY, 0, 0, 0, 0, 1.0f, 1.0f);
    }

    public TextLayer(Component text, int color, boolean glowing,
                     float offsetX, float offsetY, float offsetZ,
                     float rotateX, float rotateY, float rotateZ,
                     float scaleX, float scaleY) {
        this(text, color, glowing, false, false,
                offsetX, offsetY, offsetZ, rotateX, rotateY, rotateZ, scaleX, scaleY);
    }

    public TextLayer(Component text, int color, boolean glowing,
                     boolean bold, boolean italic,
                     float offsetX, float offsetY, float offsetZ,
                     float rotateX, float rotateY, float rotateZ,
                     float scaleX, float scaleY) {
        this.text = text;
        this.color = color;
        this.glowing = glowing;
        this.bold = bold;
        this.italic = italic;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
        this.rotateX = rotateX;
        this.rotateY = rotateY;
        this.rotateZ = rotateZ;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.fontId = null;
    }

    public TextLayer(Component text, DyeColor dyeColor, boolean glowing) {
        this(text, dyeToArgb(dyeColor), glowing);
    }

    public TextLayer(Component text, DyeColor dyeColor, boolean glowing, float offsetX, float offsetY) {
        this(text, dyeToArgb(dyeColor), glowing, offsetX, offsetY);
    }

    // ===== Getters =====
    public Component getText() { return text; }
    public int getColor() { return color; }
    public int getColorARGB() { return color; }
    public boolean isGlowing() { return glowing; }
    public boolean isBold() { return bold; }
    public boolean isItalic() { return italic; }
    public float getOffsetX() { return offsetX; }
    public float getOffsetY() { return offsetY; }
    public float getOffsetZ() { return offsetZ; }
    public float getRotation() { return rotateZ; }
    public float getRotateX() { return rotateX; }
    public float getRotateY() { return rotateY; }
    public float getRotateZ() { return rotateZ; }
    public float getScaleX() { return scaleX; }
    public float getScaleY() { return scaleY; }

    @Nullable
    public ResourceLocation getFontId() { return fontId; }

    // ===== Setters =====
    public void setText(Component text) { this.text = text; }
    public void setColor(int color) { this.color = color; }
    public void setColorARGB(int color) { this.color = color; }
    public void setGlowing(boolean glowing) { this.glowing = glowing; }
    public void setBold(boolean bold) { this.bold = bold; }
    public void setItalic(boolean italic) { this.italic = italic; }
    public void setOffsetX(float offsetX) { this.offsetX = offsetX; }
    public void setOffsetY(float offsetY) { this.offsetY = offsetY; }
    public void setOffsetZ(float offsetZ) { this.offsetZ = offsetZ; }
    public void setRotation(float rotation) { this.rotateZ = rotation; }
    public void setRotateX(float rotateX) { this.rotateX = rotateX; }
    public void setRotateY(float rotateY) { this.rotateY = rotateY; }
    public void setRotateZ(float rotateZ) { this.rotateZ = rotateZ; }
    public void setScaleX(float scaleX) { this.scaleX = scaleX; }
    public void setScaleY(float scaleY) { this.scaleY = scaleY; }
    public void setFontId(@Nullable ResourceLocation fontId) { this.fontId = fontId; }

    // ===== 便捷方法 =====
    public void move(float dx, float dy) {
        this.offsetX += dx;
        this.offsetY += dy;
    }

    public void rotate(float degrees) { this.rotateZ += degrees; }

    public void setScale(float scale) {
        this.scaleX = scale;
        this.scaleY = scale;
    }

    public boolean isEmpty() { return text.getString().isEmpty(); }

    /**
     * 返回带 bold/italic/font 的 Component，用于渲染。
     */
    public Component getStyledText() {
        Style style = Style.EMPTY;
        if (bold) style = style.withBold(true);
        if (italic) style = style.withItalic(true);
        if (fontId != null) style = style.withFont(fontId);   // ★ 关键
        return text.copy().withStyle(style);
    }

    public TextLayer copy() {
        TextLayer copy = new TextLayer(text, color, glowing, bold, italic,
                offsetX, offsetY, offsetZ, rotateX, rotateY, rotateZ, scaleX, scaleY);
        copy.fontId = this.fontId;
        return copy;
    }

    // ----- NBT -----
    public CompoundTag toNbt() {
        CompoundTag tag = new CompoundTag();
        tag.putString("text", Component.Serializer.toJson(text));
        tag.putInt("colorARGB", color);
        tag.putBoolean("glowing", glowing);
        tag.putBoolean("bold", bold);
        tag.putBoolean("italic", italic);
        tag.putFloat("offsetX", offsetX);
        tag.putFloat("offsetY", offsetY);
        tag.putFloat("offsetZ", offsetZ);
        tag.putFloat("rotateX", rotateX);
        tag.putFloat("rotateY", rotateY);
        tag.putFloat("rotateZ", rotateZ);
        tag.putFloat("scaleX", scaleX);
        tag.putFloat("scaleY", scaleY);
        if (fontId != null) tag.putString("fontId", fontId.toString());
        return tag;
    }

    public static TextLayer fromNbt(CompoundTag tag) {
        Component text = Component.Serializer.fromJson(tag.getString("text"));
        int color = parseColorFromTag(tag);
        boolean glowing = tag.getBoolean("glowing");
        boolean bold = tag.getBoolean("bold");
        boolean italic = tag.getBoolean("italic");
        float offsetX = tag.getFloat("offsetX");
        float offsetY = tag.getFloat("offsetY");
        float offsetZ = tag.getFloat("offsetZ");
        float rotateX = tag.getFloat("rotateX");
        float rotateY = tag.getFloat("rotateY");
        float rotateZ = tag.getFloat("rotateZ");
        float scaleX = tag.getFloat("scaleX");
        float scaleY = tag.getFloat("scaleY");
        TextLayer layer = new TextLayer(text, color, glowing, bold, italic,
                offsetX, offsetY, offsetZ, rotateX, rotateY, rotateZ, scaleX, scaleY);
        if (tag.contains("fontId")) {
            try {
                layer.fontId = new ResourceLocation(tag.getString("fontId"));
            } catch (Exception ignored) {}
        }
        return layer;
    }

    public static int parseColorFromTag(CompoundTag tag) {
        if (tag.contains("colorARGB")) return tag.getInt("colorARGB");
        if (tag.contains("color")) {
            DyeColor dye = DyeColor.byName(tag.getString("color"), DyeColor.WHITE);
            return dyeToArgb(dye);
        }
        return DEFAULT_COLOR;
    }

    public static int dyeToArgb(DyeColor dye) {
        return 0xFF000000 | dye.getTextColor();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TextLayer that)) return false;
        return color == that.color &&
                glowing == that.glowing &&
                bold == that.bold &&
                italic == that.italic &&
                Float.compare(that.offsetX, offsetX) == 0 &&
                Float.compare(that.offsetY, offsetY) == 0 &&
                Float.compare(that.offsetZ, offsetZ) == 0 &&
                Float.compare(that.rotateX, rotateX) == 0 &&
                Float.compare(that.rotateY, rotateY) == 0 &&
                Float.compare(that.rotateZ, rotateZ) == 0 &&
                Float.compare(that.scaleX, scaleX) == 0 &&
                Float.compare(that.scaleY, scaleY) == 0 &&
                Objects.equals(text, that.text) &&
                Objects.equals(fontId, that.fontId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, color, glowing, bold, italic,
                offsetX, offsetY, offsetZ, rotateX, rotateY, rotateZ, scaleX, scaleY, fontId);
    }
}