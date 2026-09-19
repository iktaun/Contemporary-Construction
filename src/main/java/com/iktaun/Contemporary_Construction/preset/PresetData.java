package com.iktaun.Contemporary_Construction.preset;

import com.iktaun.Contemporary_Construction.blocks.Entity.ImageLayer;
import com.iktaun.Contemporary_Construction.blocks.Entity.ShapeElementLayer;
import com.iktaun.Contemporary_Construction.blocks.Entity.SignpostText;
import com.iktaun.Contemporary_Construction.blocks.Entity.TextLayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;

import java.util.ArrayList;
import java.util.List;

public class PresetData {

    private String name;
    private String author;
    private long createdTime;
    private String description;
    private List<TextLayerData> textLayers = new ArrayList<>();
    private List<ImageLayerData> imageLayers = new ArrayList<>();
    private List<ShapeLayerData> shapeLayers = new ArrayList<>();

    public PresetData() {}

    public PresetData(String name, SignpostText text) {
        this.name = name;
        this.author = System.getProperty("user.name", "Unknown");
        this.createdTime = System.currentTimeMillis();
        this.description = "";

        for (TextLayer layer : text.getTextLayers()) {
            textLayers.add(new TextLayerData(layer));
        }
        for (ImageLayer layer : text.getImageLayers()) {
            imageLayers.add(new ImageLayerData(layer));
        }
        for (ShapeElementLayer layer : text.getShapeLayers()) {
            shapeLayers.add(new ShapeLayerData(layer));
        }
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public long getCreatedTime() { return createdTime; }
    public void setCreatedTime(long createdTime) { this.createdTime = createdTime; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public List<TextLayerData> getTextLayers() { return textLayers; }
    public List<ImageLayerData> getImageLayers() { return imageLayers; }
    public List<ShapeLayerData> getShapeLayers() { return shapeLayers; }

    public int getTotalLayers() {
        return textLayers.size() + imageLayers.size() + shapeLayers.size();
    }

    public SignpostText toSignpostText() {
        List<TextLayer> textList = new ArrayList<>();
        List<ImageLayer> imageList = new ArrayList<>();
        List<ShapeElementLayer> shapeList = new ArrayList<>();

        for (TextLayerData d : textLayers) {
            textList.add(d.toTextLayer());
        }
        for (ImageLayerData d : imageLayers) {
            imageList.add(d.toImageLayer());
        }
        for (ShapeLayerData d : shapeLayers) {
            shapeList.add(d.toShapeLayer());
        }

        return new SignpostText(textList, imageList, shapeList);
    }

    // ===== 内部数据类 =====
    public static class TextLayerData {
        public String text;
        public String color;
        public boolean glowing;
        public float offsetX;
        public float offsetY;
        public float offsetZ;
        public float rotateX;
        public float rotateY;
        public float rotateZ;
        public float scaleX;
        public float scaleY;

        public TextLayerData() {}

        public TextLayerData(TextLayer layer) {
            this.text = layer.getText().getString();
            this.color = layer.getColor().getName();
            this.glowing = layer.isGlowing();
            this.offsetX = layer.getOffsetX();
            this.offsetY = layer.getOffsetY();
            this.offsetZ = layer.getOffsetZ();
            this.rotateX = layer.getRotateX();
            this.rotateY = layer.getRotateY();
            this.rotateZ = layer.getRotateZ();
            this.scaleX = layer.getScaleX();
            this.scaleY = layer.getScaleY();
        }

        public TextLayer toTextLayer() {
            Component comp = Component.literal(text);
            DyeColor dye = DyeColor.byName(color, DyeColor.WHITE);
            return new TextLayer(comp, dye, glowing,
                    offsetX, offsetY, offsetZ, rotateX, rotateY, rotateZ, scaleX, scaleY);
        }
    }

    public static class ImageLayerData {
        public String texture;
        public int width;
        public int height;
        public float offsetX;
        public float offsetY;
        public float offsetZ;
        public float scale;
        public boolean visible;
        public float rotateX;
        public float rotateY;
        public float rotateZ;
        public float scaleX;
        public float scaleY;

        public ImageLayerData() {}

        public ImageLayerData(ImageLayer layer) {
            this.texture = layer.getTextureLocation() != null ? layer.getTextureLocation().toString() : "";
            this.width = layer.getWidth();
            this.height = layer.getHeight();
            this.offsetX = layer.getOffsetX();
            this.offsetY = layer.getOffsetY();
            this.offsetZ = layer.getOffsetZ();
            this.scale = layer.getScale();
            this.visible = layer.isVisible();
            this.rotateX = layer.getRotateX();
            this.rotateY = layer.getRotateY();
            this.rotateZ = layer.getRotateZ();
            this.scaleX = layer.getScaleX();
            this.scaleY = layer.getScaleY();
        }

        public ImageLayer toImageLayer() {
            ResourceLocation loc = texture.isEmpty() ? null : new ResourceLocation(texture);
            return new ImageLayer(loc, width, height,
                    offsetX, offsetY, offsetZ, scale, visible,
                    rotateX, rotateY, rotateZ, scaleX, scaleY);
        }
    }

    public static class ShapeLayerData {
        public String name;
        public String texture;
        public int width;
        public int height;
        public float offsetX;
        public float offsetY;
        public float offsetZ;
        public float scale;
        public String color;
        public boolean glowing;
        public boolean visible;
        public float rotateX;
        public float rotateY;
        public float rotateZ;
        public float scaleX;
        public float scaleY;

        public ShapeLayerData() {}

        public ShapeLayerData(ShapeElementLayer layer) {
            this.name = layer.getName();
            this.texture = layer.getTexture() != null ? layer.getTexture().toString() : "";
            this.width = layer.getWidth();
            this.height = layer.getHeight();
            this.offsetX = layer.getOffsetX();
            this.offsetY = layer.getOffsetY();
            this.offsetZ = layer.getOffsetZ();
            this.scale = layer.getScale();
            this.color = layer.getColor().getName();
            this.glowing = layer.isGlowing();
            this.visible = layer.isVisible();
            this.rotateX = layer.getRotateX();
            this.rotateY = layer.getRotateY();
            this.rotateZ = layer.getRotateZ();
            this.scaleX = layer.getScaleX();
            this.scaleY = layer.getScaleY();
        }

        public ShapeElementLayer toShapeLayer() {
            ResourceLocation loc = texture.isEmpty() ? null : new ResourceLocation(texture);
            DyeColor dye = DyeColor.byName(color, DyeColor.WHITE);
            return new ShapeElementLayer(name, loc, width, height,
                    offsetX, offsetY, offsetZ, scale, dye, glowing, visible,
                    rotateX, rotateY, rotateZ, scaleX, scaleY);
        }
    }
}