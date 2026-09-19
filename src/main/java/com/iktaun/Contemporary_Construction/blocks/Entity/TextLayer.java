package com.iktaun.Contemporary_Construction.blocks.Entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.DyeColor;

import java.util.Objects;

public class TextLayer {

    private Component text;
    private DyeColor color;
    private boolean glowing;
    private float offsetX;
    private float offsetY;
    private float offsetZ;
    private float rotateX;
    private float rotateY;
    private float rotateZ;
    private float scaleX;
    private float scaleY;

    // ----- 构造函数 -----
    public TextLayer() {
        this(Component.empty(), DyeColor.WHITE, false, 0, 0, 0, 0, 0, 0, 1.0f, 1.0f);
    }

    public TextLayer(Component text, DyeColor color, boolean glowing) {
        this(text, color, glowing, 0, 0, 0, 0, 0, 0, 1.0f, 1.0f);
    }

    public TextLayer(Component text, DyeColor color, boolean glowing, float offsetX, float offsetY) {
        this(text, color, glowing, offsetX, offsetY, 0, 0, 0, 0, 1.0f, 1.0f);
    }

    public TextLayer(Component text, DyeColor color, boolean glowing,
                     float offsetX, float offsetY, float offsetZ,
                     float rotateX, float rotateY, float rotateZ,
                     float scaleX, float scaleY) {
        this.text = text;
        this.color = color;
        this.glowing = glowing;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
        this.rotateX = rotateX;
        this.rotateY = rotateY;
        this.rotateZ = rotateZ;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    // ----- Getters -----
    public Component getText() { return text; }
    public DyeColor getColor() { return color; }
    public boolean isGlowing() { return glowing; }
    public float getOffsetX() { return offsetX; }
    public float getOffsetY() { return offsetY; }
    public float getOffsetZ() { return offsetZ; }
    public float getRotation() { return rotateZ; }
    public float getRotateX() { return rotateX; }
    public float getRotateY() { return rotateY; }
    public float getRotateZ() { return rotateZ; }
    public float getScaleX() { return scaleX; }
    public float getScaleY() { return scaleY; }

    // ----- Setters -----
    public void setText(Component text) { this.text = text; }
    public void setColor(DyeColor color) { this.color = color; }
    public void setGlowing(boolean glowing) { this.glowing = glowing; }
    public void setOffsetX(float offsetX) { this.offsetX = offsetX; }
    public void setOffsetY(float offsetY) { this.offsetY = offsetY; }
    public void setOffsetZ(float offsetZ) { this.offsetZ = offsetZ; }
    public void setRotation(float rotation) { this.rotateZ = rotation; }
    public void setRotateX(float rotateX) { this.rotateX = rotateX; }
    public void setRotateY(float rotateY) { this.rotateY = rotateY; }
    public void setRotateZ(float rotateZ) { this.rotateZ = rotateZ; }
    public void setScaleX(float scaleX) { this.scaleX = scaleX; }
    public void setScaleY(float scaleY) { this.scaleY = scaleY; }

    // ----- 便捷方法 -----
    public void move(float dx, float dy) {
        this.offsetX += dx;
        this.offsetY += dy;
    }

    public void rotate(float degrees) {
        this.rotateZ += degrees;
    }

    public void setScale(float scale) {
        this.scaleX = scale;
        this.scaleY = scale;
    }

    public boolean isEmpty() {
        return text.getString().isEmpty();
    }

    public TextLayer copy() {
        return new TextLayer(text, color, glowing, offsetX, offsetY, offsetZ,
                rotateX, rotateY, rotateZ, scaleX, scaleY);
    }

    // ----- NBT 序列化 -----
    public CompoundTag toNbt() {
        CompoundTag tag = new CompoundTag();
        tag.putString("text", Component.Serializer.toJson(text));
        tag.putString("color", color.getName());
        tag.putBoolean("glowing", glowing);
        tag.putFloat("offsetX", offsetX);
        tag.putFloat("offsetY", offsetY);
        tag.putFloat("offsetZ", offsetZ);
        tag.putFloat("rotateX", rotateX);
        tag.putFloat("rotateY", rotateY);
        tag.putFloat("rotateZ", rotateZ);
        tag.putFloat("scaleX", scaleX);
        tag.putFloat("scaleY", scaleY);
        return tag;
    }

    public static TextLayer fromNbt(CompoundTag tag) {
        Component text = Component.Serializer.fromJson(tag.getString("text"));
        DyeColor color = DyeColor.byName(tag.getString("color"), DyeColor.WHITE);
        boolean glowing = tag.getBoolean("glowing");
        float offsetX = tag.getFloat("offsetX");
        float offsetY = tag.getFloat("offsetY");
        float offsetZ = tag.getFloat("offsetZ");
        float rotateX = tag.getFloat("rotateX");
        float rotateY = tag.getFloat("rotateY");
        float rotateZ = tag.getFloat("rotateZ");
        float scaleX = tag.getFloat("scaleX");
        float scaleY = tag.getFloat("scaleY");
        return new TextLayer(text, color, glowing, offsetX, offsetY, offsetZ,
                rotateX, rotateY, rotateZ, scaleX, scaleY);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TextLayer that)) return false;
        return glowing == that.glowing &&
                Float.compare(that.offsetX, offsetX) == 0 &&
                Float.compare(that.offsetY, offsetY) == 0 &&
                Float.compare(that.offsetZ, offsetZ) == 0 &&
                Float.compare(that.rotateX, rotateX) == 0 &&
                Float.compare(that.rotateY, rotateY) == 0 &&
                Float.compare(that.rotateZ, rotateZ) == 0 &&
                Float.compare(that.scaleX, scaleX) == 0 &&
                Float.compare(that.scaleY, scaleY) == 0 &&
                Objects.equals(text, that.text) &&
                color == that.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, color, glowing, offsetX, offsetY, offsetZ,
                rotateX, rotateY, rotateZ, scaleX, scaleY);
    }
}