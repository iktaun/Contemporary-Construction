package com.iktaun.Contemporary_Construction.data;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;

/**
 * 单行文本数据（含偏移/旋转/缩放）
 * 所有坐标均为像素单位，相对于面板中心
 */
public class TextLine {
    private Component text;
    private float offsetX;
    private float offsetY;
    private float rotation;
    private float scale;
    private boolean shadow;

    public TextLine(Component text) {
        this(text, 0, 0, 0, 1.0f, false);
    }

    public TextLine(Component text, float offsetX, float offsetY, float rotation, float scale, boolean shadow) {
        this.text = text;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.rotation = rotation;
        this.scale = scale;
        this.shadow = shadow;
    }

    // --- Getters / Setters ---
    public Component getText() { return text; }
    public void setText(Component text) { this.text = text; }
    public float getOffsetX() { return offsetX; }
    public void setOffsetX(float offsetX) { this.offsetX = offsetX; }
    public float getOffsetY() { return offsetY; }
    public void setOffsetY(float offsetY) { this.offsetY = offsetY; }
    public float getRotation() { return rotation; }
    public void setRotation(float rotation) { this.rotation = rotation; }
    public float getScale() { return scale; }
    public void setScale(float scale) { this.scale = scale; }
    public boolean isShadow() { return shadow; }
    public void setShadow(boolean shadow) { this.shadow = shadow; }

    public boolean isEmpty() {
        return text == null || text.getString().isEmpty();
    }

    // --- 序列化 ---
    public CompoundTag toNbt() {
        CompoundTag tag = new CompoundTag();
        tag.putString("text", Component.Serializer.toJson(text));
        tag.putFloat("offsetX", offsetX);
        tag.putFloat("offsetY", offsetY);
        tag.putFloat("rotation", rotation);
        tag.putFloat("scale", scale);
        tag.putBoolean("shadow", shadow);
        return tag;
    }

    public static TextLine fromNbt(CompoundTag tag) {
        Component text = Component.Serializer.fromJson(tag.getString("text"));
        float offsetX = tag.getFloat("offsetX");
        float offsetY = tag.getFloat("offsetY");
        float rotation = tag.getFloat("rotation");
        float scale = tag.getFloat("scale");
        boolean shadow = tag.getBoolean("shadow");
        return new TextLine(text, offsetX, offsetY, rotation, scale, shadow);
    }

    public TextLine clone() {
        return new TextLine(text.copy(), offsetX, offsetY, rotation, scale, shadow);
    }
}