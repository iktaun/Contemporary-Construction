package com.iktaun.Contemporary_Construction.datagen;

import com.iktaun.Contemporary_Construction.contemporaryconstruction;
import com.iktaun.Contemporary_Construction.blocks.Modblocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, CompletableFuture<TagLookup<Block>> pBlockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, pBlockTags, contemporaryconstruction.MOD_ID, existingFileHelper);
    }


    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(ItemTags.STONE_BRICKS)
                .add(Modblocks.BITUMEN_BLOCK.get().asItem())
                .add(Modblocks.WHITE_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get().asItem())
                .add(Modblocks.WHITE_STRAIGHT_SIGNAL_BITUMEN_BLOCK_NO_CONNECTION.get().asItem())
                .add(Modblocks.WHITE_SLANT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get().asItem())
                .add(Modblocks.YELLOW_SLANT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get().asItem())
                .add(Modblocks.WHITE_SHORT_SLANT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get().asItem())
                .add(Modblocks.YELLOW_SHORT_SLANT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get().asItem())
                .add(Modblocks.YELLOW_DOUBLE_SHORT_SLANT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get().asItem())
                .add(Modblocks.WHITE_DOUBLE_SHORT_SLANT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get().asItem())
                .add(Modblocks.YELLOW_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get().asItem())
                .add(Modblocks.YELLOW_STRAIGHT_SIGNAL_BITUMEN_BLOCK_NO_CONNECTION.get().asItem())
                .add(Modblocks.WHITE_DOUBLE_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get().asItem())
                .add(Modblocks.YELLOW_DOUBLE_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get().asItem())
                .add(Modblocks.WHITE_CHARACTER_LI_SIGNAL_BITUMEN_BLOCK.get().asItem())
                .add(Modblocks.WHITE_CHARACTER_RANG_SIGNAL_BITUMEN_BLOCK.get().asItem())
                .add(Modblocks.WHITE_CHARACTER_XING_SIGNAL_BITUMEN_BLOCK.get().asItem())
                .add(Modblocks.WHITE_CHARACTER_JIN_SIGNAL_BITUMEN_BLOCK.get().asItem())
                .add(Modblocks.WHITE_CHARACTER_REN_SIGNAL_BITUMEN_BLOCK.get().asItem())
                .add(Modblocks.WHITE_CHARACTER_ZHI_SIGNAL_BITUMEN_BLOCK.get().asItem())
                .add(Modblocks.WHITE_CHARACTER_TONG_SIGNAL_BITUMEN_BLOCK.get().asItem())
                .add(Modblocks.WHITE_CHARACTER_ZHUAN_SIGNAL_BITUMEN_BLOCK.get().asItem())
                .add(Modblocks.WHITE_CHARACTER_YON_SIGNAL_BITUMEN_BLOCK.get().asItem())
                .add(Modblocks.WHITE_DIRECTION_SIGNAL_BITUMEN_BLOCK.get().asItem())
                .add(Modblocks.YELLOW_DIRECTION_SIGNAL_BITUMEN_BLOCK.get().asItem())
                .add(Modblocks.WHITE_RIGHT_DIRECTION_SIGNAL_BITUMEN_BLOCK.get().asItem())
                .add(Modblocks.WHITE_RIGHT_SIGNAL_BITUMEN_BLOCK.get().asItem())
                .add(Modblocks.WHITE_RIGHT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get().asItem())
                .add(Modblocks.WHITE_LEFT_DIRECTION_SIGNAL_BITUMEN_BLOCK.get().asItem())
                .add(Modblocks.WHITE_LEFT_RIGHT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get().asItem())
                .add(Modblocks.WHITE_LEFT_SIGNAL_BITUMEN_BLOCK.get().asItem())
                .add(Modblocks.WHITE_LEFT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get().asItem())
                .add(Modblocks.YELLOW_LEFT_DIRECTION_SIGNAL_BITUMEN_BLOCK.get().asItem())
                .add(Modblocks.YELLOW_LEFT_RIGHT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get().asItem())
                .add(Modblocks.YELLOW_LEFT_SIGNAL_BITUMEN_BLOCK.get().asItem())
                .add(Modblocks.YELLOW_LEFT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get().asItem())
                .add(Modblocks.YELLOW_RIGHT_DIRECTION_SIGNAL_BITUMEN_BLOCK.get().asItem())
                .add(Modblocks.YELLOW_RIGHT_SIGNAL_BITUMEN_BLOCK.get().asItem())
                .add(Modblocks.YELLOW_RIGHT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get().asItem())
                //.add(Modblocks.STEEL_COLUMN.get().asItem())
                //.add(Modblocks.THICK_STEEL_COLUMN.get().asItem())
                //.add(Modblocks.STEEL_CROSSBAR.get().asItem());
                .add(Modblocks.BARRIER_RED.get().asItem())
                .add(Modblocks.BARRIER_BLUE.get().asItem())
                .add(Modblocks.BARRIER_CYAN.get().asItem())
                .add(Modblocks.ROAD_FENCE.get().asItem())
                .add(Modblocks.SPEED_BUMP.get().asItem())
                .add(Modblocks.RED_CRUSH_COLUMN.get().asItem())
                .add(Modblocks.YELLOW_CRUSH_COLUMN.get().asItem())
                .add(Modblocks.CONCRETE_BARRIER.get().asItem())
                .add(Modblocks.YELLOW_CONCRETE_BARRIER.get().asItem())
                .add(Modblocks.STREET_LIGHT_POLE_BLUE_BASE.get().asItem())
                .add(Modblocks.STREET_LIGHT_POLE_WHITE.get().asItem())
                .add(Modblocks.TACTILE_PAVING.get().asItem())
                .add(Modblocks.STREET_LIGHT_BLOCK_A.get().asItem())
                .add(Modblocks.SIGNPOST_POLE.get().asItem())
                .add(Modblocks.SIGNPOST.get().asItem());
    }
}
