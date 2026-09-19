package com.iktaun.Contemporary_Construction.blocks.Entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class SignpostText {

    private final List<TextLayer> textLayers;
    private final List<ImageLayer> imageLayers;
    private final List<ShapeElementLayer> shapeLayers;

    // ----- 构造函数 -----
    public SignpostText() {
        this(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    public SignpostText(List<TextLayer> textLayers, List<ImageLayer> imageLayers, List<ShapeElementLayer> shapeLayers) {
        this.textLayers = new ArrayList<>(textLayers);
        this.imageLayers = new ArrayList<>(imageLayers);
        this.shapeLayers = new ArrayList<>(shapeLayers);
    }

    // ===== 获取所有图层 =====
    public List<TextLayer> getTextLayers() { return Collections.unmodifiableList(textLayers); }
    public List<ImageLayer> getImageLayers() { return Collections.unmodifiableList(imageLayers); }
    public List<ShapeElementLayer> getShapeLayers() { return Collections.unmodifiableList(shapeLayers); }

    public int getTotalLayers() {
        return textLayers.size() + imageLayers.size() + shapeLayers.size();
    }

    public boolean isEmpty() {
        return textLayers.isEmpty() && imageLayers.isEmpty() && shapeLayers.isEmpty();
    }

    // ===== 获取任意图层（统一索引） =====
    public Object getLayer(int index) {
        int textCount = textLayers.size();
        if (index < textCount) return textLayers.get(index);
        index -= textCount;
        int imageCount = imageLayers.size();
        if (index < imageCount) return imageLayers.get(index);
        index -= imageCount;
        if (index < shapeLayers.size()) return shapeLayers.get(index);
        return null;
    }

    public LayerType getLayerType(int index) {
        int textCount = textLayers.size();
        if (index < textCount) return LayerType.TEXT;
        index -= textCount;
        int imageCount = imageLayers.size();
        if (index < imageCount) return LayerType.IMAGE;
        return LayerType.SHAPE;
    }

    public enum LayerType { TEXT, IMAGE, SHAPE }

    // ===== 文字图层操作 =====
    public SignpostText addTextLayer(TextLayer layer) {
        List<TextLayer> newList = new ArrayList<>(textLayers);
        newList.add(layer);
        return new SignpostText(newList, imageLayers, shapeLayers);
    }

    public SignpostText removeTextLayer(int index) {
        if (index < 0 || index >= textLayers.size()) return this;
        List<TextLayer> newList = new ArrayList<>(textLayers);
        newList.remove(index);
        return new SignpostText(newList, imageLayers, shapeLayers);
    }

    public SignpostText moveTextLayer(int from, int to) {
        if (from < 0 || from >= textLayers.size() || to < 0 || to >= textLayers.size() || from == to) return this;
        List<TextLayer> newList = new ArrayList<>(textLayers);
        TextLayer temp = newList.get(from);
        newList.remove(from);
        newList.add(to, temp);
        return new SignpostText(newList, imageLayers, shapeLayers);
    }

    public SignpostText updateTextLayer(int index, TextLayer layer) {
        if (index < 0 || index >= textLayers.size()) return this;
        List<TextLayer> newList = new ArrayList<>(textLayers);
        newList.set(index, layer);
        return new SignpostText(newList, imageLayers, shapeLayers);
    }

    // ===== 图片图层操作 =====
    public SignpostText addImageLayer(ImageLayer layer) {
        List<ImageLayer> newList = new ArrayList<>(imageLayers);
        newList.add(layer);
        return new SignpostText(textLayers, newList, shapeLayers);
    }

    public SignpostText removeImageLayer(int index) {
        if (index < 0 || index >= imageLayers.size()) return this;
        List<ImageLayer> newList = new ArrayList<>(imageLayers);
        newList.remove(index);
        return new SignpostText(textLayers, newList, shapeLayers);
    }

    public SignpostText updateImageLayer(int index, ImageLayer layer) {
        if (index < 0 || index >= imageLayers.size()) return this;
        List<ImageLayer> newList = new ArrayList<>(imageLayers);
        newList.set(index, layer);
        return new SignpostText(textLayers, newList, shapeLayers);
    }

    public SignpostText moveImageLayer(int from, int to) {
        if (from < 0 || from >= imageLayers.size() || to < 0 || to >= imageLayers.size() || from == to) return this;
        List<ImageLayer> newList = new ArrayList<>(imageLayers);
        ImageLayer temp = newList.get(from);
        newList.remove(from);
        newList.add(to, temp);
        return new SignpostText(textLayers, newList, shapeLayers);
    }

    // ===== 形状元素图层操作 =====
    public SignpostText addShapeLayer(ShapeElementLayer layer) {
        List<ShapeElementLayer> newList = new ArrayList<>(shapeLayers);
        newList.add(layer);
        return new SignpostText(textLayers, imageLayers, newList);
    }

    public SignpostText removeShapeLayer(int index) {
        if (index < 0 || index >= shapeLayers.size()) return this;
        List<ShapeElementLayer> newList = new ArrayList<>(shapeLayers);
        newList.remove(index);
        return new SignpostText(textLayers, imageLayers, newList);
    }

    public SignpostText updateShapeLayer(int index, ShapeElementLayer layer) {
        if (index < 0 || index >= shapeLayers.size()) return this;
        List<ShapeElementLayer> newList = new ArrayList<>(shapeLayers);
        newList.set(index, layer);
        return new SignpostText(textLayers, imageLayers, newList);
    }

    public SignpostText moveShapeLayer(int from, int to) {
        if (from < 0 || from >= shapeLayers.size() || to < 0 || to >= shapeLayers.size() || from == to) return this;
        List<ShapeElementLayer> newList = new ArrayList<>(shapeLayers);
        ShapeElementLayer temp = newList.get(from);
        newList.remove(from);
        newList.add(to, temp);
        return new SignpostText(textLayers, imageLayers, newList);
    }

    // ===== NBT序列化 =====
    public CompoundTag toNbt() {
        CompoundTag tag = new CompoundTag();

        ListTag textList = new ListTag();
        for (TextLayer layer : textLayers) textList.add(layer.toNbt());
        tag.put("textLayers", textList);

        ListTag imageList = new ListTag();
        for (ImageLayer layer : imageLayers) imageList.add(layer.toNbt());
        tag.put("imageLayers", imageList);

        ListTag shapeList = new ListTag();
        for (ShapeElementLayer layer : shapeLayers) shapeList.add(layer.toNbt());
        tag.put("shapeLayers", shapeList);

        return tag;
    }

    public static SignpostText fromNbt(CompoundTag tag) {
        List<TextLayer> textLayers = new ArrayList<>();
        List<ImageLayer> imageLayers = new ArrayList<>();
        List<ShapeElementLayer> shapeLayers = new ArrayList<>();

        if (tag.contains("textLayers")) {
            ListTag list = tag.getList("textLayers", Tag.TAG_COMPOUND);
            for (int i = 0; i < list.size(); i++) {
                textLayers.add(TextLayer.fromNbt(list.getCompound(i)));
            }
        }

        if (tag.contains("imageLayers")) {
            ListTag list = tag.getList("imageLayers", Tag.TAG_COMPOUND);
            for (int i = 0; i < list.size(); i++) {
                imageLayers.add(ImageLayer.fromNbt(list.getCompound(i)));
            }
        }

        if (tag.contains("shapeLayers")) {
            ListTag list = tag.getList("shapeLayers", Tag.TAG_COMPOUND);
            for (int i = 0; i < list.size(); i++) {
                shapeLayers.add(ShapeElementLayer.fromNbt(list.getCompound(i)));
            }
        }

        return new SignpostText(textLayers, imageLayers, shapeLayers);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SignpostText that)) return false;
        return Objects.equals(textLayers, that.textLayers) &&
                Objects.equals(imageLayers, that.imageLayers) &&
                Objects.equals(shapeLayers, that.shapeLayers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(textLayers, imageLayers, shapeLayers);
    }
}