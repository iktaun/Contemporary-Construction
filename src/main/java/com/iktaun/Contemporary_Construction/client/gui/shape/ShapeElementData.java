package com.iktaun.Contemporary_Construction.client.gui.shape;

import net.minecraft.resources.ResourceLocation;

public class ShapeElementData {
    public enum Source {
        BUILTIN,
        CUSTOM
    }

    public final String name;
    public final ResourceLocation texture;
    public final int width;
    public final int height;
    public final Source source;

    public ShapeElementData(String name, ResourceLocation texture, int width, int height, Source source) {
        this.name = name;
        this.texture = texture;
        this.width = width;
        this.height = height;
        this.source = source;
    }

    public String getDisplayName() {
        return name.replace('_', ' ');
    }

    public String getSourceDisplay() {
        return source == Source.BUILTIN ? "内置" : "自定义";
    }
}