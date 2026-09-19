package com.iktaun.Contemporary_Construction.datagen;

import com.iktaun.Contemporary_Construction.contemporaryconstruction;
import com.iktaun.Contemporary_Construction.blocks.Modblocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelsProvider extends ItemModelProvider {
    public ModItemModelsProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, contemporaryconstruction.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // 为所有方块物品生成模型（基于提供的JSON文件）
        // 这些模型都是简单的父模型引用，指向对应的方块模型

        // 普通沥青块
        roadblockItem(Modblocks.BITUMEN_BLOCK);

        // 白色系列
        roadsignwhiteblockItem(Modblocks.WHITE_STRAIGHT_SIGNAL_BITUMEN_BLOCK);
        roadsignwhiteblockItem(Modblocks.WHITE_STRAIGHT_SIGNAL_BITUMEN_BLOCK_NO_CONNECTION);
        roadsignwhiteblockItem(Modblocks.WHITE_DIRECTION_SIGNAL_BITUMEN_BLOCK);
        roadsignwhiteblockItem(Modblocks.WHITE_SLANT_STRAIGHT_SIGNAL_BITUMEN_BLOCK);
        roadsignwhiteblockItem(Modblocks.WHITE_SHORT_SLANT_STRAIGHT_SIGNAL_BITUMEN_BLOCK);
        roadsignwhiteblockItem(Modblocks.WHITE_DOUBLE_SHORT_SLANT_STRAIGHT_SIGNAL_BITUMEN_BLOCK);
        roadsignwhiteblockItem(Modblocks.WHITE_DOUBLE_STRAIGHT_SIGNAL_BITUMEN_BLOCK);
        roadsignwhiteblockItem(Modblocks.WHITE_CHARACTER_LI_SIGNAL_BITUMEN_BLOCK);
        roadsignwhiteblockItem(Modblocks.WHITE_CHARACTER_RANG_SIGNAL_BITUMEN_BLOCK);
        roadsignwhiteblockItem(Modblocks.WHITE_CHARACTER_XING_SIGNAL_BITUMEN_BLOCK);
        roadsignwhiteblockItem(Modblocks.WHITE_CHARACTER_JIN_SIGNAL_BITUMEN_BLOCK);
        roadsignwhiteblockItem(Modblocks.WHITE_CHARACTER_REN_SIGNAL_BITUMEN_BLOCK);
        roadsignwhiteblockItem(Modblocks.WHITE_CHARACTER_ZHI_SIGNAL_BITUMEN_BLOCK);
        roadsignwhiteblockItem(Modblocks.WHITE_CHARACTER_TONG_SIGNAL_BITUMEN_BLOCK);
        roadsignwhiteblockItem(Modblocks.WHITE_CHARACTER_ZHUAN_SIGNAL_BITUMEN_BLOCK);
        roadsignwhiteblockItem(Modblocks.WHITE_CHARACTER_YON_SIGNAL_BITUMEN_BLOCK);
        roadsignwhiteblockItem(Modblocks.WHITE_STRAIGHT_QUARTER_CIRCLE_SIGNAL_BITUMEN_BLOCK);
        roadsignwhiteblockItem(Modblocks.WHITE_QUARTER_CIRCLE_SIGNAL_BITUMEN_BLOCK);
        roadsignwhiteblockItem(Modblocks.WHITE_RIGHT_DIRECTION_SIGNAL_BITUMEN_BLOCK);
        roadsignwhiteblockItem(Modblocks.WHITE_RIGHT_SIGNAL_BITUMEN_BLOCK);
        roadsignwhiteblockItem(Modblocks.WHITE_RIGHT_STRAIGHT_SIGNAL_BITUMEN_BLOCK);
        roadsignwhiteblockItem(Modblocks.WHITE_LEFT_DIRECTION_SIGNAL_BITUMEN_BLOCK);
        roadsignwhiteblockItem(Modblocks.WHITE_LEFT_RIGHT_STRAIGHT_SIGNAL_BITUMEN_BLOCK);
        roadsignwhiteblockItem(Modblocks.WHITE_LEFT_SIGNAL_BITUMEN_BLOCK);
        roadsignwhiteblockItem(Modblocks.WHITE_LEFT_STRAIGHT_SIGNAL_BITUMEN_BLOCK);

        // 黄色系列
        roadsignyellowblockItem(Modblocks.YELLOW_STRAIGHT_SIGNAL_BITUMEN_BLOCK);
        roadsignyellowblockItem(Modblocks.YELLOW_STRAIGHT_SIGNAL_BITUMEN_BLOCK_NO_CONNECTION);
        roadsignyellowblockItem(Modblocks.YELLOW_SLANT_STRAIGHT_SIGNAL_BITUMEN_BLOCK);
        roadsignyellowblockItem(Modblocks.YELLOW_SHORT_SLANT_STRAIGHT_SIGNAL_BITUMEN_BLOCK);
        roadsignyellowblockItem(Modblocks.YELLOW_DOUBLE_SHORT_SLANT_STRAIGHT_SIGNAL_BITUMEN_BLOCK);
        roadsignyellowblockItem(Modblocks.YELLOW_DOUBLE_STRAIGHT_SIGNAL_BITUMEN_BLOCK);
        roadsignyellowblockItem(Modblocks.YELLOW_DIRECTION_SIGNAL_BITUMEN_BLOCK);
        roadsignyellowblockItem(Modblocks.YELLOW_STRAIGHT_QUARTER_CIRCLE_SIGNAL_BITUMEN_BLOCK);
        roadsignyellowblockItem(Modblocks.YELLOW_QUARTER_CIRCLE_SIGNAL_BITUMEN_BLOCK);
        roadsignyellowblockItem(Modblocks.YELLOW_LEFT_DIRECTION_SIGNAL_BITUMEN_BLOCK);
        roadsignyellowblockItem(Modblocks.YELLOW_LEFT_RIGHT_STRAIGHT_SIGNAL_BITUMEN_BLOCK);
        roadsignyellowblockItem(Modblocks.YELLOW_LEFT_SIGNAL_BITUMEN_BLOCK);
        roadsignyellowblockItem(Modblocks.YELLOW_LEFT_STRAIGHT_SIGNAL_BITUMEN_BLOCK);
        roadsignyellowblockItem(Modblocks.YELLOW_RIGHT_DIRECTION_SIGNAL_BITUMEN_BLOCK);
        roadsignyellowblockItem(Modblocks.YELLOW_RIGHT_SIGNAL_BITUMEN_BLOCK);
        roadsignyellowblockItem(Modblocks.YELLOW_RIGHT_STRAIGHT_SIGNAL_BITUMEN_BLOCK);

        //blockItem(Modblocks.STEEL_COLUMN);
        //blockItem(Modblocks.THICK_STEEL_COLUMN);
        //blockItem(Modblocks.STEEL_CROSSBAR);

        barrierblockItem(Modblocks.BARRIER_BLUE);
        barrierblockItem(Modblocks.BARRIER_RED);
        barrierblockItem(Modblocks.BARRIER_CYAN);
        barrierblockItem(Modblocks.ROAD_FENCE);
        barrierblockItem(Modblocks.CONCRETE_BARRIER);
        barrierblockItem(Modblocks.YELLOW_CONCRETE_BARRIER);
        barrierblockItem(Modblocks.CRUSH_BARREL);

        otherblockItem(Modblocks.SPEED_BUMP);
        otherblockItem(Modblocks.TACTILE_PAVING);
        otherblockItem(Modblocks.SIGNPOST);

        formalcolumnblockItem(Modblocks.RED_CRUSH_COLUMN);
        formalcolumnblockItem(Modblocks.YELLOW_CRUSH_COLUMN);
        formalcolumnblockItem(Modblocks.STREET_LIGHT_POLE_BLUE_BASE);
        formalcolumnblockItem(Modblocks.STREET_LIGHT_POLE_WHITE);
        formalcolumnblockItem(Modblocks.SIGNPOST_POLE);

        lightblockItem(Modblocks.STREET_LIGHT_BLOCK_A);
    }

    private void roadblockItem(RegistryObject<Block> block) {
        String blockName = block.getId().getPath();
        withExistingParent(blockName, modLoc("block/road_block/" + blockName));
    }
    private void barrierblockItem(RegistryObject<Block> block) {
        String blockName = block.getId().getPath();
        withExistingParent(blockName, modLoc("block/barrier/" + blockName));
    }
    private void roadsignwhiteblockItem(RegistryObject<Block> block) {
        String blockName = block.getId().getPath();
        withExistingParent(blockName, modLoc("block/road_block/sign/white/" + blockName));
    }
    private void roadsignyellowblockItem(RegistryObject<Block> block) {
        String blockName = block.getId().getPath();
        withExistingParent(blockName, modLoc("block/road_block/sign/yellow/" + blockName));
    }
    private void otherblockItem(RegistryObject<Block> block) {
        String blockName = block.getId().getPath();
        withExistingParent(blockName, modLoc("block/other/" + blockName));
    }
    private void formalcolumnblockItem(RegistryObject<Block> block) {
        String blockName = block.getId().getPath();
        withExistingParent(blockName, modLoc("block/column/formal/" + blockName));
    }
    private void thickcolumnblockItem(RegistryObject<Block> block) {
        String blockName = block.getId().getPath();
        withExistingParent(blockName, modLoc("block/column/thick/" + blockName));
    }
    private void lightblockItem(RegistryObject<Block> block) {
        String blockName = block.getId().getPath();
        withExistingParent(blockName, modLoc("block/light/" + blockName));
    }
}
