package com.iktaun.Contemporary_Construction.datagen;

import com.iktaun.Contemporary_Construction.blocks.Modblocks;
import com.iktaun.Contemporary_Construction.contemporaryconstruction;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, contemporaryconstruction.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        // 普通沥青块
        simpleBlockWithItem(Modblocks.BITUMEN_BLOCK.get(),
                models().getExistingFile(modLoc("block/road_block/bitumen_block")));
        simpleBlockWithItem(Modblocks.RED_CRUSH_COLUMN.get(),
                models().getExistingFile(modLoc("block/column/formal/red_crush_column")));
        simpleBlockWithItem(Modblocks.YELLOW_CRUSH_COLUMN.get(),
                models().getExistingFile(modLoc("block/column/formal/yellow_crush_column")));
        simpleBlockWithItem(Modblocks.STREET_LIGHT_POLE_WHITE.get(),
                models().getExistingFile(modLoc("block/column/formal/street_light_pole_white")));
        simpleBlockWithItem(Modblocks.CRUSH_BARREL.get(),
                models().getExistingFile(modLoc("block/barrier/crush_barrel")));
        simpleBlockWithItem(Modblocks.SIGNPOST_POLE.get(),
                models().getExistingFile(modLoc("block/column/formal/sign_post_pole")));

        registerTwoDirectionalWhiteRoadBlock(Modblocks.WHITE_STRAIGHT_SIGNAL_BITUMEN_BLOCK_NO_CONNECTION.get(),
                "white_straight_signal_bitumen_block_no_connection");
        registerTwoDirectionalYellowRoadBlock(Modblocks.YELLOW_STRAIGHT_SIGNAL_BITUMEN_BLOCK_NO_CONNECTION.get(),
                "yellow_straight_signal_bitumen_block_no_connection");
        registerTwoDirectionalWhiteRoadBlock(Modblocks.WHITE_DOUBLE_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get(),
                "white_double_straight_signal_bitumen_block");
        registerTwoDirectionalYellowRoadBlock(Modblocks.YELLOW_DOUBLE_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get(),
                "yellow_double_straight_signal_bitumen_block");
        registerTwoDirectionalWhiteRoadBlock(Modblocks.WHITE_SLANT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get(),
                "white_slant_straight_signal_bitumen_block");
        registerTwoDirectionalYellowRoadBlock(Modblocks.YELLOW_SLANT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get(),
                "yellow_slant_straight_signal_bitumen_block");
        registerTwoDirectionalYellowRoadBlock(Modblocks.YELLOW_DOUBLE_SHORT_SLANT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get(),
                "yellow_double_short_slant_straight_signal_bitumen_block");
        registerTwoDirectionalWhiteRoadBlock(Modblocks.WHITE_DOUBLE_SHORT_SLANT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get(),
                "white_double_short_slant_straight_signal_bitumen_block");


        registerFourDirectionalWhiteRoadBlock(Modblocks.WHITE_CHARACTER_LI_SIGNAL_BITUMEN_BLOCK.get(),
                "white_character_li_signal_bitumen_block");
        registerFourDirectionalWhiteRoadBlock(Modblocks.WHITE_CHARACTER_RANG_SIGNAL_BITUMEN_BLOCK.get(),
                "white_character_rang_signal_bitumen_block");
        registerFourDirectionalWhiteRoadBlock(Modblocks.WHITE_CHARACTER_XING_SIGNAL_BITUMEN_BLOCK.get(),
                "white_character_xing_signal_bitumen_block");
        registerFourDirectionalWhiteRoadBlock(Modblocks.WHITE_CHARACTER_JIN_SIGNAL_BITUMEN_BLOCK.get(),
                "white_character_jin_signal_bitumen_block");
        registerFourDirectionalWhiteRoadBlock(Modblocks.WHITE_CHARACTER_REN_SIGNAL_BITUMEN_BLOCK.get(),
                "white_character_ren_signal_bitumen_block");
        registerFourDirectionalWhiteRoadBlock(Modblocks.WHITE_CHARACTER_ZHI_SIGNAL_BITUMEN_BLOCK.get(),
                "white_character_zhi_signal_bitumen_block");
        registerFourDirectionalWhiteRoadBlock(Modblocks.WHITE_CHARACTER_TONG_SIGNAL_BITUMEN_BLOCK.get(),
                "white_character_tong_signal_bitumen_block");
        registerFourDirectionalWhiteRoadBlock(Modblocks.WHITE_CHARACTER_ZHUAN_SIGNAL_BITUMEN_BLOCK.get(),
                "white_character_zhuan_signal_bitumen_block");
        registerFourDirectionalWhiteRoadBlock(Modblocks.WHITE_CHARACTER_YON_SIGNAL_BITUMEN_BLOCK.get(),
                "white_character_yon_signal_bitumen_block");
        registerFourDirectionalYellowRoadBlock(Modblocks.YELLOW_SHORT_SLANT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get(),
                "yellow_short_slant_straight_signal_bitumen_block");
        registerFourDirectionalWhiteRoadBlock(Modblocks.WHITE_SHORT_SLANT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get(),
                "white_short_slant_straight_signal_bitumen_block");
        registerFourDirectionalYellowRoadBlock(Modblocks.YELLOW_DIRECTION_SIGNAL_BITUMEN_BLOCK.get(),
                "yellow_direction_signal_bitumen_block");
        registerFourDirectionalWhiteRoadBlock(Modblocks.WHITE_DIRECTION_SIGNAL_BITUMEN_BLOCK.get(),
                "white_direction_signal_bitumen_block");
        registerFourDirectionalYellowRoadBlock(Modblocks.YELLOW_STRAIGHT_QUARTER_CIRCLE_SIGNAL_BITUMEN_BLOCK.get(),
                "yellow_straight_quarter_circle_signal_bitumen_block");
        registerFourDirectionalWhiteRoadBlock(Modblocks.WHITE_STRAIGHT_QUARTER_CIRCLE_SIGNAL_BITUMEN_BLOCK.get(),
                "white_straight_quarter_circle_signal_bitumen_block");
        registerFourDirectionalYellowRoadBlock(Modblocks.YELLOW_QUARTER_CIRCLE_SIGNAL_BITUMEN_BLOCK.get(),
                "yellow_quarter_circle_signal_bitumen_block");
        registerFourDirectionalWhiteRoadBlock(Modblocks.WHITE_QUARTER_CIRCLE_SIGNAL_BITUMEN_BLOCK.get(),
                "white_quarter_circle_signal_bitumen_block");
        registerFourDirectionalWhiteRoadBlock(Modblocks.WHITE_RIGHT_DIRECTION_SIGNAL_BITUMEN_BLOCK.get(),
                "white_right_direction_signal_bitumen_block");
        registerFourDirectionalWhiteRoadBlock(Modblocks.WHITE_RIGHT_SIGNAL_BITUMEN_BLOCK.get(),
                "white_right_signal_bitumen_block");
        registerFourDirectionalWhiteRoadBlock(Modblocks.WHITE_RIGHT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get(),
                "white_right_straight_signal_bitumen_block");
        registerFourDirectionalWhiteRoadBlock(Modblocks.WHITE_LEFT_DIRECTION_SIGNAL_BITUMEN_BLOCK.get(),
                "white_left_direction_signal_bitumen_block");
        registerFourDirectionalWhiteRoadBlock(Modblocks.WHITE_LEFT_RIGHT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get(),
                "white_left_right_straight_signal_bitumen_block");
        registerFourDirectionalWhiteRoadBlock(Modblocks.WHITE_LEFT_SIGNAL_BITUMEN_BLOCK.get(),
                "white_left_signal_bitumen_block");
        registerFourDirectionalWhiteRoadBlock(Modblocks.WHITE_LEFT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get(),
                "white_left_straight_signal_bitumen_block");
        registerFourDirectionalYellowRoadBlock(Modblocks.YELLOW_LEFT_DIRECTION_SIGNAL_BITUMEN_BLOCK.get(),
                "yellow_left_direction_signal_bitumen_block");
        registerFourDirectionalYellowRoadBlock(Modblocks.YELLOW_LEFT_RIGHT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get(),
                "yellow_left_right_straight_signal_bitumen_block");
        registerFourDirectionalYellowRoadBlock(Modblocks.YELLOW_LEFT_SIGNAL_BITUMEN_BLOCK.get(),
                "yellow_left_signal_bitumen_block");
        registerFourDirectionalYellowRoadBlock(Modblocks.YELLOW_LEFT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get(),
                "yellow_left_straight_signal_bitumen_block");
        registerFourDirectionalYellowRoadBlock(Modblocks.YELLOW_RIGHT_DIRECTION_SIGNAL_BITUMEN_BLOCK.get(),
                "yellow_right_direction_signal_bitumen_block");
        registerFourDirectionalYellowRoadBlock(Modblocks.YELLOW_RIGHT_SIGNAL_BITUMEN_BLOCK.get(),
                "yellow_right_signal_bitumen_block");
        registerFourDirectionalYellowRoadBlock(Modblocks.YELLOW_RIGHT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get(),
                "yellow_right_straight_signal_bitumen_block");
        registerFourDirectionalBarrierBlock(Modblocks.BARRIER_CYAN.get(),
                "barrier_cyan");
        registerFourDirectionalBarrierBlock(Modblocks.BARRIER_RED.get(),
                "barrier_red");
        registerFourDirectionalBarrierBlock(Modblocks.BARRIER_BLUE.get(),
                "barrier_blue");
        registerTwoDirectionalOtherBlock(Modblocks.SPEED_BUMP.get(),
                "speed_bump");

        registerTwoDirectionalBarrierBlock(Modblocks.CONCRETE_BARRIER.get(),
                "concrete_barrier");
        registerTwoDirectionalBarrierBlock(Modblocks.YELLOW_CONCRETE_BARRIER.get(),
                "yellow_concrete_barrier");
    }
    private void registerTwoDirectionalYellowRoadBlock(Block block, String modelName) {
        ModelFile model = models().getExistingFile(modLoc("block/road_block/sign/yellow/" + modelName));
        getVariantBuilder(block)
                .partialState().with(BlockStateProperties.HORIZONTAL_FACING, net.minecraft.core.Direction.NORTH)
                .modelForState().modelFile(model).addModel()
                .partialState().with(BlockStateProperties.HORIZONTAL_FACING, net.minecraft.core.Direction.SOUTH)
                .modelForState().modelFile(model).addModel()
                .partialState().with(BlockStateProperties.HORIZONTAL_FACING, net.minecraft.core.Direction.EAST)
                .modelForState().modelFile(model).rotationY(90).addModel()
                .partialState().with(BlockStateProperties.HORIZONTAL_FACING, net.minecraft.core.Direction.WEST)
                .modelForState().modelFile(model).rotationY(90).addModel();
        simpleBlockItem(block, model);
    }
    private void registerTwoDirectionalWhiteRoadBlock(Block block, String modelName) {
        ModelFile model = models().getExistingFile(modLoc("block/road_block/sign/white/" + modelName));
        getVariantBuilder(block)
                .partialState().with(BlockStateProperties.HORIZONTAL_FACING, net.minecraft.core.Direction.NORTH)
                .modelForState().modelFile(model).addModel()
                .partialState().with(BlockStateProperties.HORIZONTAL_FACING, net.minecraft.core.Direction.SOUTH)
                .modelForState().modelFile(model).addModel()
                .partialState().with(BlockStateProperties.HORIZONTAL_FACING, net.minecraft.core.Direction.EAST)
                .modelForState().modelFile(model).rotationY(90).addModel()
                .partialState().with(BlockStateProperties.HORIZONTAL_FACING, net.minecraft.core.Direction.WEST)
                .modelForState().modelFile(model).rotationY(90).addModel();
        simpleBlockItem(block, model);
    }

    private void registerFourDirectionalWhiteRoadBlock(Block block, String modelName) {
        ModelFile model = models().getExistingFile(modLoc("block/road_block/sign/white/" + modelName));
        getVariantBuilder(block)
                .partialState().with(BlockStateProperties.HORIZONTAL_FACING, net.minecraft.core.Direction.NORTH)
                .modelForState().modelFile(model).addModel()
                .partialState().with(BlockStateProperties.HORIZONTAL_FACING, net.minecraft.core.Direction.EAST)
                .modelForState().modelFile(model).rotationY(90).addModel()
                .partialState().with(BlockStateProperties.HORIZONTAL_FACING, net.minecraft.core.Direction.SOUTH)
                .modelForState().modelFile(model).rotationY(180).addModel()
                .partialState().with(BlockStateProperties.HORIZONTAL_FACING, net.minecraft.core.Direction.WEST)
                .modelForState().modelFile(model).rotationY(270).addModel();
        simpleBlockItem(block, model);
    }
    private void registerFourDirectionalYellowRoadBlock(Block block, String modelName) {
        ModelFile model = models().getExistingFile(modLoc("block/road_block/sign/yellow/" + modelName));
        getVariantBuilder(block)
                .partialState().with(BlockStateProperties.HORIZONTAL_FACING, net.minecraft.core.Direction.NORTH)
                .modelForState().modelFile(model).addModel()
                .partialState().with(BlockStateProperties.HORIZONTAL_FACING, net.minecraft.core.Direction.EAST)
                .modelForState().modelFile(model).rotationY(90).addModel()
                .partialState().with(BlockStateProperties.HORIZONTAL_FACING, net.minecraft.core.Direction.SOUTH)
                .modelForState().modelFile(model).rotationY(180).addModel()
                .partialState().with(BlockStateProperties.HORIZONTAL_FACING, net.minecraft.core.Direction.WEST)
                .modelForState().modelFile(model).rotationY(270).addModel();
        simpleBlockItem(block, model);
    }
    private void registerTwoDirectionalOtherBlock(Block block, String modelName) {
        ModelFile model = models().getExistingFile(modLoc("block/other/" + modelName));
        getVariantBuilder(block)
                .partialState().with(BlockStateProperties.HORIZONTAL_FACING, net.minecraft.core.Direction.NORTH)
                .modelForState().modelFile(model).addModel()
                .partialState().with(BlockStateProperties.HORIZONTAL_FACING, net.minecraft.core.Direction.SOUTH)
                .modelForState().modelFile(model).addModel()
                .partialState().with(BlockStateProperties.HORIZONTAL_FACING, net.minecraft.core.Direction.EAST)
                .modelForState().modelFile(model).rotationY(90).addModel()
                .partialState().with(BlockStateProperties.HORIZONTAL_FACING, net.minecraft.core.Direction.WEST)
                .modelForState().modelFile(model).rotationY(90).addModel();
        simpleBlockItem(block, model);
    }
    private void registerTwoDirectionalBarrierBlock(Block block, String modelName) {
        ModelFile model = models().getExistingFile(modLoc("block/barrier/" + modelName));
        getVariantBuilder(block)
                .partialState().with(BlockStateProperties.HORIZONTAL_FACING, net.minecraft.core.Direction.NORTH)
                .modelForState().modelFile(model).addModel()
                .partialState().with(BlockStateProperties.HORIZONTAL_FACING, net.minecraft.core.Direction.SOUTH)
                .modelForState().modelFile(model).addModel()
                .partialState().with(BlockStateProperties.HORIZONTAL_FACING, net.minecraft.core.Direction.EAST)
                .modelForState().modelFile(model).rotationY(90).addModel()
                .partialState().with(BlockStateProperties.HORIZONTAL_FACING, net.minecraft.core.Direction.WEST)
                .modelForState().modelFile(model).rotationY(90).addModel();
        simpleBlockItem(block, model);
    }

    private void registerFourDirectionalBarrierBlock(Block block, String modelName) {
        ModelFile model = models().getExistingFile(modLoc("block/barrier/" + modelName));
        getVariantBuilder(block)
                .partialState().with(BlockStateProperties.HORIZONTAL_FACING, net.minecraft.core.Direction.NORTH)
                .modelForState().modelFile(model).addModel()
                .partialState().with(BlockStateProperties.HORIZONTAL_FACING, net.minecraft.core.Direction.EAST)
                .modelForState().modelFile(model).rotationY(90).addModel()
                .partialState().with(BlockStateProperties.HORIZONTAL_FACING, net.minecraft.core.Direction.SOUTH)
                .modelForState().modelFile(model).rotationY(180).addModel()
                .partialState().with(BlockStateProperties.HORIZONTAL_FACING, net.minecraft.core.Direction.WEST)
                .modelForState().modelFile(model).rotationY(270).addModel();
        simpleBlockItem(block, model);
    }

    private void registerFourDirectionalColumnBlock(Block block, String modelName) {
        ModelFile model = models().getExistingFile(modLoc("block/column/formal/" + modelName));
        getVariantBuilder(block)
                .partialState().with(BlockStateProperties.HORIZONTAL_FACING, net.minecraft.core.Direction.NORTH)
                .modelForState().modelFile(model).addModel()
                .partialState().with(BlockStateProperties.HORIZONTAL_FACING, net.minecraft.core.Direction.EAST)
                .modelForState().modelFile(model).rotationY(90).addModel()
                .partialState().with(BlockStateProperties.HORIZONTAL_FACING, net.minecraft.core.Direction.SOUTH)
                .modelForState().modelFile(model).rotationY(180).addModel()
                .partialState().with(BlockStateProperties.HORIZONTAL_FACING, net.minecraft.core.Direction.WEST)
                .modelForState().modelFile(model).rotationY(270).addModel();
        simpleBlockItem(block, model);
    }
    private void registerFourDirectionalLightBlock(Block block, String modelName) {
        ModelFile model = models().getExistingFile(modLoc("block/light/" + modelName));
        getVariantBuilder(block)
                .partialState().with(BlockStateProperties.HORIZONTAL_FACING, net.minecraft.core.Direction.NORTH)
                .modelForState().modelFile(model).addModel()
                .partialState().with(BlockStateProperties.HORIZONTAL_FACING, net.minecraft.core.Direction.EAST)
                .modelForState().modelFile(model).rotationY(90).addModel()
                .partialState().with(BlockStateProperties.HORIZONTAL_FACING, net.minecraft.core.Direction.SOUTH)
                .modelForState().modelFile(model).rotationY(180).addModel()
                .partialState().with(BlockStateProperties.HORIZONTAL_FACING, net.minecraft.core.Direction.WEST)
                .modelForState().modelFile(model).rotationY(270).addModel();
        simpleBlockItem(block, model);
    }
}