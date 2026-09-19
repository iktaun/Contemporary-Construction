package com.iktaun.Contemporary_Construction;

import com.iktaun.Contemporary_Construction.blocks.Modblocks;
import com.iktaun.Contemporary_Construction.items.Moditems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, contemporaryconstruction.MOD_ID);

    public static final RegistryObject<CreativeModeTab> ROAD_BLOCKS_TAB =
            CREATIVE_MODE_TABS.register("road_blocks_tab",() -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(Modblocks.BITUMEN_BLOCK.get()))
                    .title(Component.translatable("itemGroup.road_blocks"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(Modblocks.BITUMEN_BLOCK.get());

                        pOutput.accept(Modblocks.WHITE_STRAIGHT_SIGNAL_BITUMEN_BLOCK_NO_CONNECTION.get());
                        pOutput.accept(Modblocks.WHITE_DOUBLE_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.WHITE_CHARACTER_XING_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.WHITE_CHARACTER_LI_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.WHITE_CHARACTER_JIN_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.WHITE_CHARACTER_RANG_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.WHITE_CHARACTER_ZHI_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.WHITE_CHARACTER_TONG_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.WHITE_CHARACTER_ZHUAN_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.WHITE_CHARACTER_REN_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.WHITE_DOUBLE_SHORT_SLANT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.WHITE_SHORT_SLANT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.WHITE_CHARACTER_YON_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.WHITE_SLANT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.WHITE_DIRECTION_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.WHITE_QUARTER_CIRCLE_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.WHITE_STRAIGHT_QUARTER_CIRCLE_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.WHITE_RIGHT_DIRECTION_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.WHITE_RIGHT_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.WHITE_RIGHT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.WHITE_LEFT_DIRECTION_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.WHITE_LEFT_RIGHT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.WHITE_LEFT_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.WHITE_LEFT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get());

                        pOutput.accept(Modblocks.YELLOW_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.YELLOW_STRAIGHT_SIGNAL_BITUMEN_BLOCK_NO_CONNECTION.get());
                        pOutput.accept(Modblocks.YELLOW_DOUBLE_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get());

                        pOutput.accept(Modblocks.YELLOW_DOUBLE_SHORT_SLANT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.YELLOW_SHORT_SLANT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.YELLOW_SLANT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.YELLOW_DIRECTION_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.YELLOW_QUARTER_CIRCLE_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.YELLOW_STRAIGHT_QUARTER_CIRCLE_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.YELLOW_LEFT_DIRECTION_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.YELLOW_LEFT_RIGHT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.YELLOW_LEFT_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.YELLOW_LEFT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.YELLOW_RIGHT_DIRECTION_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.YELLOW_RIGHT_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.YELLOW_RIGHT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get());
                    }).build());
    public static final RegistryObject<CreativeModeTab> SIGNAL_NOTE_TAB =
            CREATIVE_MODE_TABS.register("signal_note_tab",() -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(Modblocks.WHITE_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get()))
                    .title(Component.translatable("itemGroup.signal_note"))
                    .displayItems((pParameters, pOutput) -> {
                        //pOutput.accept(Modblocks.THICK_STEEL_COLUMN.get());
                        //pOutput.accept(Modblocks.STEEL_COLUMN.get());

                        pOutput.accept(Modblocks.WHITE_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.WHITE_STRAIGHT_SIGNAL_BITUMEN_BLOCK_NO_CONNECTION.get());
                        pOutput.accept(Modblocks.WHITE_DOUBLE_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.WHITE_CHARACTER_XING_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.WHITE_CHARACTER_LI_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.WHITE_CHARACTER_JIN_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.WHITE_CHARACTER_RANG_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.WHITE_CHARACTER_ZHI_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.WHITE_CHARACTER_TONG_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.WHITE_CHARACTER_ZHUAN_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.WHITE_CHARACTER_REN_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.WHITE_DOUBLE_SHORT_SLANT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.WHITE_SHORT_SLANT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.WHITE_CHARACTER_YON_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.WHITE_SLANT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.WHITE_DIRECTION_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.WHITE_QUARTER_CIRCLE_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.WHITE_STRAIGHT_QUARTER_CIRCLE_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.WHITE_RIGHT_DIRECTION_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.WHITE_RIGHT_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.WHITE_RIGHT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.WHITE_LEFT_DIRECTION_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.WHITE_LEFT_RIGHT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.WHITE_LEFT_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.WHITE_LEFT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get());

                        pOutput.accept(Modblocks.YELLOW_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.YELLOW_STRAIGHT_SIGNAL_BITUMEN_BLOCK_NO_CONNECTION.get());
                        pOutput.accept(Modblocks.YELLOW_DOUBLE_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.YELLOW_DOUBLE_SHORT_SLANT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.YELLOW_SHORT_SLANT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.YELLOW_SLANT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.YELLOW_DIRECTION_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.YELLOW_QUARTER_CIRCLE_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.YELLOW_STRAIGHT_QUARTER_CIRCLE_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.YELLOW_LEFT_DIRECTION_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.YELLOW_LEFT_RIGHT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.YELLOW_LEFT_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.YELLOW_LEFT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.YELLOW_RIGHT_DIRECTION_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.YELLOW_RIGHT_SIGNAL_BITUMEN_BLOCK.get());
                        pOutput.accept(Modblocks.YELLOW_RIGHT_STRAIGHT_SIGNAL_BITUMEN_BLOCK.get());
                    }).build());

    //public static final RegistryObject<CreativeModeTab> COLUMN_TAB =
    //        CREATIVE_MODE_TABS.register("column_tab",() -> CreativeModeTab.builder()
    //                .icon(() -> new ItemStack(Modblocks.STEEL_COLUMN.get()))
    //                .title(Component.translatable("itemGroup.signal_note"))
    //                .displayItems((pParameters, pOutput) -> {
    //                    pOutput.accept(Modblocks.THICK_STEEL_COLUMN.get());
    //                    pOutput.accept(Modblocks.STEEL_COLUMN.get());
    //                    pOutput.accept(Modblocks.STEEL_CROSSBAR.get());
    //                }).build());

    public static final RegistryObject<CreativeModeTab> BARRIER_TAB =
            CREATIVE_MODE_TABS.register("barrier_tab",() -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(Modblocks.ROAD_FENCE.get()))
                    .title(Component.translatable("itemGroup.barrier"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(Modblocks.ROAD_FENCE.get());
                        pOutput.accept(Modblocks.BARRIER_BLUE.get());
                        pOutput.accept(Modblocks.BARRIER_RED.get());
                        pOutput.accept(Modblocks.BARRIER_CYAN.get());
                        pOutput.accept(Modblocks.CRUSH_BARREL.get());
                        pOutput.accept(Modblocks.CONCRETE_BARRIER.get());
                        pOutput.accept(Modblocks.YELLOW_CONCRETE_BARRIER.get());
                    }).build());

    public static final RegistryObject<CreativeModeTab> COLUMN_TAB =
            CREATIVE_MODE_TABS.register("column_tab",() -> CreativeModeTab.builder()
                    .icon(() ->new ItemStack(Modblocks.STREET_LIGHT_POLE_BLUE_BASE.get()))
                    .title(Component.translatable("itemGroup.column"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(Modblocks.STREET_LIGHT_POLE_BLUE_BASE.get());
                        pOutput.accept(Modblocks.STREET_LIGHT_POLE_WHITE.get());
                        pOutput.accept(Modblocks.RED_CRUSH_COLUMN.get());
                        pOutput.accept(Modblocks.YELLOW_CRUSH_COLUMN.get());
                        pOutput.accept(Modblocks.SIGNPOST_POLE.get());
                    }).build());

    public static final RegistryObject<CreativeModeTab> OTHER_TAB =
            CREATIVE_MODE_TABS.register("other_tab",() -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(Modblocks.SPEED_BUMP.get()))
                    .title(Component.translatable("itemGroup.other"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(Modblocks.SPEED_BUMP.get());
                        pOutput.accept(Modblocks.TACTILE_PAVING.get());
                        pOutput.accept(Modblocks.SIGNPOST.get());
                    }).build());

    public static final RegistryObject<CreativeModeTab> LIGHT_TAB =
            CREATIVE_MODE_TABS.register("light_tab",() -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(Modblocks.STREET_LIGHT_POLE_WHITE.get()))
                    .title(Component.translatable("itemGroup.light"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(Modblocks.STREET_LIGHT_POLE_BLUE_BASE.get());
                        pOutput.accept(Modblocks.STREET_LIGHT_POLE_WHITE.get());
                        pOutput.accept(Modblocks.STREET_LIGHT_BLOCK_A.get());
                    }).build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
