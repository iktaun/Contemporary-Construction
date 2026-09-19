package com.iktaun.Contemporary_Construction.blocks.custom;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public final class ModblocksTags {
    private static final String MOD_ID = "contemporaryconstruction";

    public static final TagKey<Block> ROAD_FENCE = create("road_fence");
    public static final TagKey<Block> ROAD_FENCE_BARRIER = create("road_fence_barrier");
    public static final TagKey<Block> SIGNPOST_POLE = create("signpost_pole");
    public static final TagKey<Block> EDITABLE_WITH_BRUSH = create("editable_with_brush");

    private ModblocksTags(){}

    private static TagKey<Block> create(String path) {
        return TagKey.create(Registries.BLOCK, new ResourceLocation(MOD_ID, path));
    }
}
