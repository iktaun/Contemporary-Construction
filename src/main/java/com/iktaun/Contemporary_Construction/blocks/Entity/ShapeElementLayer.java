package com.iktaun.Contemporary_Construction.blocks.Entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;

import java.util.Objects;

public class ShapeElementLayer {

    private String name;
    private ResourceLocation texture;
    private int width;
    private int height;
    private float offsetX;
    private float offsetY;
    private float offsetZ;
    private float scale;
    private DyeColor color;
    private boolean glowing;
    private boolean visible;
    private float rotateX;
    private float rotateY;
    private float rotateZ;
    private float scaleX;
    private float scaleY;

    // ----- 构造函数 -----
    public ShapeElementLayer() {
        this("shape", null, 32, 32, 0, 0, 0, 1.0f, DyeColor.WHITE, false, true,
                0, 0, 0, 1.0f, 1.0f);
    }

    public ShapeElementLayer(String name, ResourceLocation texture, int width, int height,
                             float offsetX, float offsetY, float scale,
                             DyeColor color, boolean glowing, boolean visible) {
        this(name, texture, width, height, offsetX, offsetY, 0, scale, color, glowing, visible,
                0, 0, 0, 1.0f, 1.0f);
    }

    public ShapeElementLayer(String name, ResourceLocation texture, int width, int height,
                             float offsetX, float offsetY, float offsetZ, float scale,
                             DyeColor color, boolean glowing, boolean visible,
                             float rotateX, float rotateY, float rotateZ,
                             float scaleX, float scaleY) {
        this.name = name;
        this.texture = texture;
        this.width = width;
        this.height = height;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
        this.scale = scale;
        this.color = color;
        this.glowing = glowing;
        this.visible = visible;
        this.rotateX = rotateX;
        this.rotateY = rotateY;
        this.rotateZ = rotateZ;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    // ----- Getters -----
    public String getName() { return name; }
    public ResourceLocation getTexture() { return texture; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public float getOffsetX() { return offsetX; }
    public float getOffsetY() { return offsetY; }
    public float getOffsetZ() { return offsetZ; }
    public float getScale() { return scale; }
    public DyeColor getColor() { return color; }
    public boolean isGlowing() { return glowing; }
    public boolean isVisible() { return visible; }
    public float getRotation() { return rotateZ; }
    public float getRotateX() { return rotateX; }
    public float getRotateY() { return rotateY; }
    public float getRotateZ() { return rotateZ; }
    public float getScaleX() { return scaleX; }
    public float getScaleY() { return scaleY; }

    // ----- Setters -----
    public void setName(String name) { this.name = name; }
    public void setTexture(ResourceLocation texture) { this.texture = texture; }
    public void setWidth(int width) { this.width = width; }
    public void setHeight(int height) { this.height = height; }
    public void setOffsetX(float offsetX) { this.offsetX = offsetX; }
    public void setOffsetY(float offsetY) { this.offsetY = offsetY; }
    public void setOffsetZ(float offsetZ) { this.offsetZ = offsetZ; }
    public void setColor(DyeColor color) { this.color = color; }
    public void setGlowing(boolean glowing) { this.glowing = glowing; }
    public void setVisible(boolean visible) { this.visible = visible; }
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
        this.scale = scale;
    }

    public boolean hasTexture() { return texture != null; }

    public ShapeElementLayer copy() {
        return new ShapeElementLayer(name, texture, width, height,
                offsetX, offsetY, offsetZ, scale, color, glowing, visible,
                rotateX, rotateY, rotateZ, scaleX, scaleY);
    }

    // ----- NBT 序列化 -----
    public CompoundTag toNbt() {
        CompoundTag tag = new CompoundTag();
        tag.putString("name", name);
        if (texture != null) tag.putString("texture", texture.toString());
        tag.putInt("width", width);
        tag.putInt("height", height);
        tag.putFloat("offsetX", offsetX);
        tag.putFloat("offsetY", offsetY);
        tag.putFloat("offsetZ", offsetZ);
        tag.putFloat("scale", scale);
        tag.putString("color", color.getName());
        tag.putBoolean("glowing", glowing);
        tag.putBoolean("visible", visible);
        tag.putFloat("rotateX", rotateX);
        tag.putFloat("rotateY", rotateY);
        tag.putFloat("rotateZ", rotateZ);
        tag.putFloat("scaleX", scaleX);
        tag.putFloat("scaleY", scaleY);
        return tag;
    }

    public static ShapeElementLayer fromNbt(CompoundTag tag) {
        String name = tag.getString("name");
        ResourceLocation texture = tag.contains("texture") ? new ResourceLocation(tag.getString("texture")) : null;
        int width = tag.getInt("width");
        int height = tag.getInt("height");
        float offsetX = tag.getFloat("offsetX");
        float offsetY = tag.getFloat("offsetY");
        float offsetZ = tag.getFloat("offsetZ");
        float scale = tag.getFloat("scale");
        DyeColor color = DyeColor.byName(tag.getString("color"), DyeColor.WHITE);
        boolean glowing = tag.getBoolean("glowing");
        boolean visible = tag.getBoolean("visible");
        float rotateX = tag.getFloat("rotateX");
        float rotateY = tag.getFloat("rotateY");
        float rotateZ = tag.getFloat("rotateZ");
        float scaleX = tag.getFloat("scaleX");
        float scaleY = tag.getFloat("scaleY");
        return new ShapeElementLayer(name, texture, width, height,
                offsetX, offsetY, offsetZ, scale, color, glowing, visible,
                rotateX, rotateY, rotateZ, scaleX, scaleY);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShapeElementLayer that)) return false;
        return width == that.width &&
                height == that.height &&
                Float.compare(that.offsetX, offsetX) == 0 &&
                Float.compare(that.offsetY, offsetY) == 0 &&
                Float.compare(that.offsetZ, offsetZ) == 0 &&
                Float.compare(that.scale, scale) == 0 &&
                glowing == that.glowing &&
                visible == that.visible &&
                Float.compare(that.rotateX, rotateX) == 0 &&
                Float.compare(that.rotateY, rotateY) == 0 &&
                Float.compare(that.rotateZ, rotateZ) == 0 &&
                Float.compare(that.scaleX, scaleX) == 0 &&
                Float.compare(that.scaleY, scaleY) == 0 &&
                Objects.equals(name, that.name) &&
                Objects.equals(texture, that.texture) &&
                color == that.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, texture, width, height, offsetX, offsetY, offsetZ,
                scale, color, glowing, visible, rotateX, rotateY, rotateZ, scaleX, scaleY);
    }
}