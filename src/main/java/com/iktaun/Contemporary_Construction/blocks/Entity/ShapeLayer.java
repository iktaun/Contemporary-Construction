package com.iktaun.Contemporary_Construction.blocks.Entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.DyeColor;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

public class ShapeLayer {

    public enum ShapeType {
        RECTANGLE("矩形", null),
        ELLIPSE("椭圆", null),
        CIRCLE("圆形", null),
        ARROW_UP("上箭头", null),
        ARROW_DOWN("下箭头", null),
        ARROW_LEFT("左箭头", null),
        ARROW_RIGHT("右箭头", null),
        SLASH("斜杠 /", null),
        BACKSLASH("反斜杠 \\", null),
        CROSS("叉号 ✕", null),
        CHECK("对勾 ✓", null),
        STAR("星号 ★", null),
        HEART("心形 ♥", null),
        DIAMOND("菱形 ♦", null),
        TRIANGLE_UP("上三角", null),
        TRIANGLE_DOWN("下三角", null),
        TRIANGLE_LEFT("左三角", null),
        TRIANGLE_RIGHT("右三角", null);

        private final String displayName;
        private final ResourceLocation texture;

        ShapeType(String displayName, ResourceLocation texture) {
            this.displayName = displayName;
            this.texture = texture;
        }

        public String getDisplayName() { return displayName; }
        public ResourceLocation getTexture() { return texture; }

        public static ShapeType fromName(String name) {
            for (ShapeType type : values()) {
                if (type.displayName.equals(name) || type.name().equalsIgnoreCase(name)) {
                    return type;
                }
            }
            return RECTANGLE;
        }
    }

    private ShapeType shapeType;
    private int width;
    private int height;
    private float offsetX;
    private float offsetY;
    private DyeColor color;
    private boolean glowing;
    private boolean visible;

    public ShapeLayer() {
        this(ShapeType.RECTANGLE, 30, 20, 0, 0, DyeColor.WHITE, false, true);
    }

    public ShapeLayer(ShapeType shapeType, int width, int height, float offsetX, float offsetY,
                      DyeColor color, boolean glowing, boolean visible) {
        this.shapeType = shapeType;
        this.width = width;
        this.height = height;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.color = color;
        this.glowing = glowing;
        this.visible = visible;
    }

    // ----- Getters -----
    public ShapeType getShapeType() { return shapeType; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public float getOffsetX() { return offsetX; }
    public float getOffsetY() { return offsetY; }
    public DyeColor getColor() { return color; }
    public boolean isGlowing() { return glowing; }
    public boolean isVisible() { return visible; }

    // ----- Setters -----
    public void setShapeType(ShapeType shapeType) { this.shapeType = shapeType; }
    public void setWidth(int width) { this.width = width; }
    public void setHeight(int height) { this.height = height; }
    public void setOffsetX(float offsetX) { this.offsetX = offsetX; }
    public void setOffsetY(float offsetY) { this.offsetY = offsetY; }
    public void setColor(DyeColor color) { this.color = color; }
    public void setGlowing(boolean glowing) { this.glowing = glowing; }
    public void setVisible(boolean visible) { this.visible = visible; }

    // ----- 移动方法（阶段七使用）-----
    public void move(float dx, float dy) {
        this.offsetX += dx;
        this.offsetY += dy;
    }

    public ShapeLayer copy() {
        return new ShapeLayer(shapeType, width, height, offsetX, offsetY, color, glowing, visible);
    }

    // ----- NBT序列化 -----
    public CompoundTag toNbt() {
        CompoundTag tag = new CompoundTag();
        tag.putString("shapeType", shapeType.name());
        tag.putInt("width", width);
        tag.putInt("height", height);
        tag.putFloat("offsetX", offsetX);
        tag.putFloat("offsetY", offsetY);
        tag.putString("color", color.getName());
        tag.putBoolean("glowing", glowing);
        tag.putBoolean("visible", visible);
        return tag;
    }

    public static ShapeLayer fromNbt(CompoundTag tag) {
        ShapeType shapeType = ShapeType.fromName(tag.getString("shapeType"));
        int width = tag.getInt("width");
        int height = tag.getInt("height");
        float offsetX = tag.getFloat("offsetX");
        float offsetY = tag.getFloat("offsetY");
        DyeColor color = DyeColor.byName(tag.getString("color"), DyeColor.WHITE);
        boolean glowing = tag.getBoolean("glowing");
        boolean visible = tag.getBoolean("visible");
        return new ShapeLayer(shapeType, width, height, offsetX, offsetY, color, glowing, visible);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShapeLayer that)) return false;
        return width == that.width &&
                height == that.height &&
                Float.compare(that.offsetX, offsetX) == 0 &&
                Float.compare(that.offsetY, offsetY) == 0 &&
                glowing == that.glowing &&
                visible == that.visible &&
                shapeType == that.shapeType &&
                color == that.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(shapeType, width, height, offsetX, offsetY, color, glowing, visible);
    }
}