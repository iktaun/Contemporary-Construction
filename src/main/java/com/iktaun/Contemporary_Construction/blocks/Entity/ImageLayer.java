package com.iktaun.Contemporary_Construction.blocks.Entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

public class ImageLayer {

    private ResourceLocation textureLocation;
    private int width;
    private int height;
    private float offsetX;
    private float offsetY;
    private float offsetZ;
    private float scale;
    private boolean visible;
    private float rotateX;
    private float rotateY;
    private float rotateZ;
    private float scaleX;
    private float scaleY;
    private int color;    // 新增：ARGB 着色

    public static final int DEFAULT_COLOR = 0xFFFFFFFF; // 白色不透明（不着色）

    // ----- 构造函数 -----
    public ImageLayer() {
        this(null, 64, 64, 0, 0, 0, 1.0f, true, 0, 0, 0, 1.0f, 1.0f, DEFAULT_COLOR);
    }

    public ImageLayer(ResourceLocation texture, int width, int height, float offsetX, float offsetY, float scale, boolean visible) {
        this(texture, width, height, offsetX, offsetY, 0, scale, visible, 0, 0, 0, 1.0f, 1.0f, DEFAULT_COLOR);
    }

    public ImageLayer(ResourceLocation texture, int width, int height,
                      float offsetX, float offsetY, float offsetZ,
                      float scale, boolean visible,
                      float rotateX, float rotateY, float rotateZ,
                      float scaleX, float scaleY, int color) {
        this.textureLocation = texture;
        this.width = width;
        this.height = height;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
        this.scale = scale;
        this.visible = visible;
        this.rotateX = rotateX;
        this.rotateY = rotateY;
        this.rotateZ = rotateZ;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.color = color;
    }

    // ----- Getters -----
    public ResourceLocation getTextureLocation() { return textureLocation; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public float getOffsetX() { return offsetX; }
    public float getOffsetY() { return offsetY; }
    public float getOffsetZ() { return offsetZ; }
    public float getScale() { return scale; }
    public boolean isVisible() { return visible; }
    public float getRotation() { return rotateZ; }
    public float getRotateX() { return rotateX; }
    public float getRotateY() { return rotateY; }
    public float getRotateZ() { return rotateZ; }
    public float getScaleX() { return scaleX; }
    public float getScaleY() { return scaleY; }
    public int getColor() { return color; }
    public int getColorARGB() { return color; }

    // ----- Setters -----
    public void setTextureLocation(ResourceLocation texture) { this.textureLocation = texture; }
    public void setWidth(int width) { this.width = width; }
    public void setHeight(int height) { this.height = height; }
    public void setOffsetX(float offsetX) { this.offsetX = offsetX; }
    public void setOffsetY(float offsetY) { this.offsetY = offsetY; }
    public void setOffsetZ(float offsetZ) { this.offsetZ = offsetZ; }
    public void setScale(float scale) { this.scale = scale; }
    public void setVisible(boolean visible) { this.visible = visible; }
    public void setRotation(float rotation) { this.rotateZ = rotation; }
    public void setRotateX(float rotateX) { this.rotateX = rotateX; }
    public void setRotateY(float rotateY) { this.rotateY = rotateY; }
    public void setRotateZ(float rotateZ) { this.rotateZ = rotateZ; }
    public void setScaleX(float scaleX) { this.scaleX = scaleX; }
    public void setScaleY(float scaleY) { this.scaleY = scaleY; }
    public void setColor(int color) { this.color = color; }
    public void setColorARGB(int color) { this.color = color; }

    // ----- 便捷方法 -----
    public void move(float dx, float dy) {
        this.offsetX += dx;
        this.offsetY += dy;
    }

    public void rotate(float degrees) {
        this.rotateZ += degrees;
    }

    public boolean hasTexture() { return textureLocation != null; }
    public boolean isEmpty() { return textureLocation == null; }

    public ImageLayer copy() {
        return new ImageLayer(textureLocation, width, height,
                offsetX, offsetY, offsetZ, scale, visible,
                rotateX, rotateY, rotateZ, scaleX, scaleY, color);
    }

    // ----- NBT 序列化 -----
    public CompoundTag toNbt() {
        CompoundTag tag = new CompoundTag();
        if (textureLocation != null) tag.putString("texture", textureLocation.toString());
        tag.putInt("width", width);
        tag.putInt("height", height);
        tag.putFloat("offsetX", offsetX);
        tag.putFloat("offsetY", offsetY);
        tag.putFloat("offsetZ", offsetZ);
        tag.putFloat("scale", scale);
        tag.putBoolean("visible", visible);
        tag.putFloat("rotateX", rotateX);
        tag.putFloat("rotateY", rotateY);
        tag.putFloat("rotateZ", rotateZ);
        tag.putFloat("scaleX", scaleX);
        tag.putFloat("scaleY", scaleY);
        tag.putInt("colorARGB", color);
        return tag;
    }

    public static ImageLayer fromNbt(CompoundTag tag) {
        ResourceLocation texture = tag.contains("texture") ? new ResourceLocation(tag.getString("texture")) : null;
        int width = tag.getInt("width");
        int height = tag.getInt("height");
        float offsetX = tag.getFloat("offsetX");
        float offsetY = tag.getFloat("offsetY");
        float offsetZ = tag.getFloat("offsetZ");
        float scale = tag.getFloat("scale");
        boolean visible = tag.getBoolean("visible");
        float rotateX = tag.getFloat("rotateX");
        float rotateY = tag.getFloat("rotateY");
        float rotateZ = tag.getFloat("rotateZ");
        float scaleX = tag.getFloat("scaleX");
        float scaleY = tag.getFloat("scaleY");
        int color = tag.contains("colorARGB") ? tag.getInt("colorARGB") : DEFAULT_COLOR;
        return new ImageLayer(texture, width, height,
                offsetX, offsetY, offsetZ, scale, visible,
                rotateX, rotateY, rotateZ, scaleX, scaleY, color);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ImageLayer that)) return false;
        return width == that.width &&
                height == that.height &&
                Float.compare(that.offsetX, offsetX) == 0 &&
                Float.compare(that.offsetY, offsetY) == 0 &&
                Float.compare(that.offsetZ, offsetZ) == 0 &&
                Float.compare(that.scale, scale) == 0 &&
                visible == that.visible &&
                Float.compare(that.rotateX, rotateX) == 0 &&
                Float.compare(that.rotateY, rotateY) == 0 &&
                Float.compare(that.rotateZ, rotateZ) == 0 &&
                Float.compare(that.scaleX, scaleX) == 0 &&
                Float.compare(that.scaleY, scaleY) == 0 &&
                color == that.color &&
                Objects.equals(textureLocation, that.textureLocation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(textureLocation, width, height, offsetX, offsetY, offsetZ,
                scale, visible, rotateX, rotateY, rotateZ, scaleX, scaleY, color);
    }
}