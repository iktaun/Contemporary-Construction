package com.iktaun.Contemporary_Construction.datagen;

import com.iktaun.Contemporary_Construction.blocks.Modblocks;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    public ModBlockLootTableProvider() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }


    @Override
    protected void generate() {

        this.dropSelf(Modblocks.BITUMEN_BLOCK.get());
        this.dropSelf(Modblocks.WHITE_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get());
        this.dropSelf(Modblocks.WHITE_STRAIGHT_SIGNAL_BITUMEN_BLOCK_NO_CONNECTION.get());
        this.dropSelf(Modblocks.WHITE_SLANT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get());
        this.dropSelf(Modblocks.WHITE_DIRECTION_SIGNAL_BITUMEN_BLOCK.get());
        this.dropSelf(Modblocks.YELLOW_DIRECTION_SIGNAL_BITUMEN_BLOCK.get());
        this.dropSelf(Modblocks.WHITE_SLANT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get());
        this.dropSelf(Modblocks.WHITE_DOUBLE_SHORT_SLANT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get());
        this.dropSelf(Modblocks.WHITE_SHORT_SLANT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get());
        this.dropSelf(Modblocks.YELLOW_SLANT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get());
        this.dropSelf(Modblocks.YELLOW_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get());
        this.dropSelf(Modblocks.YELLOW_STRAIGHT_SIGNAL_BITUMEN_BLOCK_NO_CONNECTION.get());
        this.dropSelf(Modblocks.YELLOW_SHORT_SLANT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get());
        this.dropSelf(Modblocks.YELLOW_DOUBLE_SHORT_SLANT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get());
        this.dropSelf(Modblocks.WHITE_DOUBLE_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get());
        this.dropSelf(Modblocks.YELLOW_DOUBLE_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get());
        this.dropSelf(Modblocks.WHITE_CHARACTER_LI_SIGNAL_BITUMEN_BLOCK.get());
        this.dropSelf(Modblocks.WHITE_CHARACTER_RANG_SIGNAL_BITUMEN_BLOCK.get());
        this.dropSelf(Modblocks.WHITE_CHARACTER_XING_SIGNAL_BITUMEN_BLOCK.get());
        this.dropSelf(Modblocks.WHITE_CHARACTER_JIN_SIGNAL_BITUMEN_BLOCK.get());
        this.dropSelf(Modblocks.WHITE_CHARACTER_REN_SIGNAL_BITUMEN_BLOCK.get());
        this.dropSelf(Modblocks.WHITE_CHARACTER_ZHI_SIGNAL_BITUMEN_BLOCK.get());
        this.dropSelf(Modblocks.WHITE_CHARACTER_TONG_SIGNAL_BITUMEN_BLOCK.get());
        this.dropSelf(Modblocks.WHITE_CHARACTER_ZHUAN_SIGNAL_BITUMEN_BLOCK.get());
        this.dropSelf(Modblocks.WHITE_CHARACTER_YON_SIGNAL_BITUMEN_BLOCK.get());
        this.dropSelf(Modblocks.YELLOW_STRAIGHT_QUARTER_CIRCLE_SIGNAL_BITUMEN_BLOCK.get());
        this.dropSelf(Modblocks.WHITE_STRAIGHT_QUARTER_CIRCLE_SIGNAL_BITUMEN_BLOCK.get());
        this.dropSelf(Modblocks.YELLOW_QUARTER_CIRCLE_SIGNAL_BITUMEN_BLOCK.get());
        this.dropSelf(Modblocks.WHITE_QUARTER_CIRCLE_SIGNAL_BITUMEN_BLOCK.get());
        this.dropSelf(Modblocks.WHITE_RIGHT_DIRECTION_SIGNAL_BITUMEN_BLOCK.get());
        this.dropSelf(Modblocks.WHITE_RIGHT_SIGNAL_BITUMEN_BLOCK.get());
        this.dropSelf(Modblocks.WHITE_RIGHT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get());
        this.dropSelf(Modblocks.WHITE_LEFT_DIRECTION_SIGNAL_BITUMEN_BLOCK.get());
        this.dropSelf(Modblocks.WHITE_LEFT_RIGHT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get());
        this.dropSelf(Modblocks.WHITE_LEFT_SIGNAL_BITUMEN_BLOCK.get());
        this.dropSelf(Modblocks.WHITE_LEFT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get());
        this.dropSelf(Modblocks.YELLOW_LEFT_DIRECTION_SIGNAL_BITUMEN_BLOCK.get());
        this.dropSelf(Modblocks.YELLOW_LEFT_RIGHT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get());
        this.dropSelf(Modblocks.YELLOW_LEFT_SIGNAL_BITUMEN_BLOCK.get());
        this.dropSelf(Modblocks.YELLOW_LEFT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get());
        this.dropSelf(Modblocks.YELLOW_RIGHT_DIRECTION_SIGNAL_BITUMEN_BLOCK.get());
        this.dropSelf(Modblocks.YELLOW_RIGHT_SIGNAL_BITUMEN_BLOCK.get());
        this.dropSelf(Modblocks.YELLOW_RIGHT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get());
        //this.dropSelf(Modblocks.STEEL_CROSSBAR.get());
        //this.dropSelf(Modblocks.STEEL_COLUMN.get());
        //this.dropSelf(Modblocks.THICK_STEEL_COLUMN.get());
        this.dropSelf(Modblocks.BARRIER_BLUE.get());
        this.dropSelf(Modblocks.BARRIER_CYAN.get());
        this.dropSelf(Modblocks.BARRIER_RED.get());
        this.dropSelf(Modblocks.ROAD_FENCE.get());
        this.dropSelf(Modblocks.SPEED_BUMP.get());
        this.dropSelf(Modblocks.CRUSH_BARREL.get());
        this.dropSelf(Modblocks.RED_CRUSH_COLUMN.get());
        this.dropSelf(Modblocks.YELLOW_CRUSH_COLUMN.get());
        this.dropSelf(Modblocks.CONCRETE_BARRIER.get());
        this.dropSelf(Modblocks.YELLOW_CONCRETE_BARRIER.get());
        this.dropSelf(Modblocks.TACTILE_PAVING.get());
        this.dropSelf(Modblocks.STREET_LIGHT_POLE_WHITE.get());
        this.dropSelf(Modblocks.STREET_LIGHT_POLE_BLUE_BASE.get());
        this.dropSelf(Modblocks.STREET_LIGHT_BLOCK_A.get());
        this.dropSelf(Modblocks.SIGNPOST.get());
        this.dropSelf(Modblocks.SIGNPOST_POLE.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return Modblocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
