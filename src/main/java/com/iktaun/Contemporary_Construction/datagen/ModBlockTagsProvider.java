package com.iktaun.Contemporary_Construction.datagen;

import com.iktaun.Contemporary_Construction.blocks.Modblocks;

import com.iktaun.Contemporary_Construction.blocks.custom.ModblocksTags;
import com.iktaun.Contemporary_Construction.contemporaryconstruction;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {

    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> LookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, LookupProvider, contemporaryconstruction.MOD_ID,existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(Modblocks.BITUMEN_BLOCK.get())
                .add(Modblocks.YELLOW_DIRECTION_SIGNAL_BITUMEN_BLOCK.get())
                .add(Modblocks.WHITE_DIRECTION_SIGNAL_BITUMEN_BLOCK.get())
                .add(Modblocks.WHITE_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get())
                .add(Modblocks.WHITE_STRAIGHT_SIGNAL_BITUMEN_BLOCK_NO_CONNECTION.get())
                .add(Modblocks.WHITE_SLANT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get())
                .add(Modblocks.YELLOW_SLANT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get())
                .add(Modblocks.WHITE_DOUBLE_SHORT_SLANT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get())
                .add(Modblocks.YELLOW_DOUBLE_SHORT_SLANT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get())
                .add(Modblocks.WHITE_SHORT_SLANT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get())
                .add(Modblocks.YELLOW_SHORT_SLANT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get())
                .add(Modblocks.YELLOW_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get())
                .add(Modblocks.YELLOW_STRAIGHT_SIGNAL_BITUMEN_BLOCK_NO_CONNECTION.get())
                .add(Modblocks.WHITE_DOUBLE_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get())
                .add(Modblocks.YELLOW_DOUBLE_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get())
                .add(Modblocks.WHITE_CHARACTER_LI_SIGNAL_BITUMEN_BLOCK.get())
                .add(Modblocks.WHITE_CHARACTER_RANG_SIGNAL_BITUMEN_BLOCK.get())
                .add(Modblocks.WHITE_CHARACTER_XING_SIGNAL_BITUMEN_BLOCK.get())
                .add(Modblocks.WHITE_CHARACTER_JIN_SIGNAL_BITUMEN_BLOCK.get())
                .add(Modblocks.WHITE_CHARACTER_REN_SIGNAL_BITUMEN_BLOCK.get())
                .add(Modblocks.WHITE_CHARACTER_ZHI_SIGNAL_BITUMEN_BLOCK.get())
                .add(Modblocks.WHITE_CHARACTER_TONG_SIGNAL_BITUMEN_BLOCK.get())
                .add(Modblocks.WHITE_CHARACTER_ZHUAN_SIGNAL_BITUMEN_BLOCK.get())
                .add(Modblocks.WHITE_QUARTER_CIRCLE_SIGNAL_BITUMEN_BLOCK.get())
                .add(Modblocks.WHITE_STRAIGHT_QUARTER_CIRCLE_SIGNAL_BITUMEN_BLOCK.get())
                .add(Modblocks.YELLOW_QUARTER_CIRCLE_SIGNAL_BITUMEN_BLOCK.get())
                .add(Modblocks.YELLOW_STRAIGHT_QUARTER_CIRCLE_SIGNAL_BITUMEN_BLOCK.get())
                .add(Modblocks.WHITE_RIGHT_DIRECTION_SIGNAL_BITUMEN_BLOCK.get())
                .add(Modblocks.WHITE_RIGHT_SIGNAL_BITUMEN_BLOCK.get())
                .add(Modblocks.WHITE_RIGHT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get())
                .add(Modblocks.WHITE_LEFT_DIRECTION_SIGNAL_BITUMEN_BLOCK.get())
                .add(Modblocks.WHITE_LEFT_RIGHT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get())
                .add(Modblocks.WHITE_LEFT_SIGNAL_BITUMEN_BLOCK.get())
                .add(Modblocks.WHITE_LEFT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get())
                .add(Modblocks.YELLOW_LEFT_DIRECTION_SIGNAL_BITUMEN_BLOCK.get())
                .add(Modblocks.YELLOW_LEFT_RIGHT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get())
                .add(Modblocks.YELLOW_LEFT_SIGNAL_BITUMEN_BLOCK.get())
                .add(Modblocks.YELLOW_LEFT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get())
                .add(Modblocks.YELLOW_RIGHT_DIRECTION_SIGNAL_BITUMEN_BLOCK.get())
                .add(Modblocks.YELLOW_RIGHT_SIGNAL_BITUMEN_BLOCK.get())
                .add(Modblocks.YELLOW_RIGHT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get())
                //.add(Modblocks.STEEL_COLUMN.get())
                //.add(Modblocks.THICK_STEEL_COLUMN.get())
                //.add(Modblocks.STEEL_CROSSBAR.get());
                .add(Modblocks.ROAD_FENCE.get())
                .add(Modblocks.SPEED_BUMP.get())
                .add(Modblocks.RED_CRUSH_COLUMN.get())
                .add(Modblocks.YELLOW_CRUSH_COLUMN.get())
                .add(Modblocks.CONCRETE_BARRIER.get())
                .add(Modblocks.YELLOW_CONCRETE_BARRIER.get())
                .add(Modblocks.STREET_LIGHT_POLE_WHITE.get())
                .add(Modblocks.STREET_LIGHT_POLE_BLUE_BASE.get())
                .add(Modblocks.TACTILE_PAVING.get())
                .add(Modblocks.STREET_LIGHT_BLOCK_A.get())
                .add(Modblocks.SIGNPOST.get())
                .add(Modblocks.BARRIER_RED.get())
                .add(Modblocks.BARRIER_CYAN.get())
                .add(Modblocks.BARRIER_BLUE.get())
                .add(Modblocks.SIGNPOST_POLE.get());

        tag(ModblocksTags.ROAD_FENCE)
                .add(Modblocks.ROAD_FENCE.get());

        tag(ModblocksTags.ROAD_FENCE_BARRIER)
                .add(Modblocks.BARRIER_RED.get())
                .add(Modblocks.BARRIER_CYAN.get())
                .add(Modblocks.BARRIER_BLUE.get());

        tag(ModblocksTags.SIGNPOST_POLE)
                .add(Modblocks.SIGNPOST_POLE.get());

        tag(ModblocksTags.EDITABLE_WITH_BRUSH)
                .add(Modblocks.SIGNPOST.get());


    }
}
