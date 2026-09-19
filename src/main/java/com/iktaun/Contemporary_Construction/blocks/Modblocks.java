package com.iktaun.Contemporary_Construction.blocks;


import com.iktaun.Contemporary_Construction.blocks.custom.barrier.Barrier;
import com.iktaun.Contemporary_Construction.blocks.custom.barrier.ConcreteBarrier;
import com.iktaun.Contemporary_Construction.blocks.custom.barrier.CrushBarrel;
import com.iktaun.Contemporary_Construction.blocks.custom.barrier.RoadFenceBlock;
import com.iktaun.Contemporary_Construction.blocks.custom.column.CrushColumn;
import com.iktaun.Contemporary_Construction.blocks.custom.column.SignpostPole;
import com.iktaun.Contemporary_Construction.blocks.custom.light.StreetLightBlockA;
import com.iktaun.Contemporary_Construction.blocks.custom.light.StreetLightPoleBaseBlock;
import com.iktaun.Contemporary_Construction.blocks.custom.light.StreetLightPoleBlock;
import com.iktaun.Contemporary_Construction.blocks.custom.other.Signpost;
import com.iktaun.Contemporary_Construction.blocks.custom.other.SpeedBump;
import com.iktaun.Contemporary_Construction.blocks.custom.other.TactilePaving;
import com.iktaun.Contemporary_Construction.blocks.custom.road_block.AllBitumenBlock;
import com.iktaun.Contemporary_Construction.blocks.custom.road_block.BitumenBlock;
import com.iktaun.Contemporary_Construction.blocks.custom.road_block.StraightLineBlock;
import com.iktaun.Contemporary_Construction.contemporaryconstruction;
import com.iktaun.Contemporary_Construction.items.Moditems;
import com.iktaun.Contemporary_Construction.mapcolor.custom.CustomColorAdapter;
import com.iktaun.Contemporary_Construction.mapcolor.custom.ContemporaryColors;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class Modblocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, contemporaryconstruction.MOD_ID);

    public static final RegistryObject<Block> BITUMEN_BLOCK =
            registerBlock("bitumen_block", () ->
                    new BitumenBlock(BlockBehaviour.Properties.of()
                            .mapColor(CustomColorAdapter.toMinecraftMapColor(ContemporaryColors.ASPHALT_BLACK))
                            .strength(0.25F)));
    public static final RegistryObject<Block> WHITE_STRAIGHT_SIGNAL_BITUMEN_BLOCK =
            registerBlock("white_straight_signal_bitumen_block", () ->
                    new StraightLineBlock(BlockBehaviour.Properties.of()
                            .mapColor(CustomColorAdapter.toMinecraftMapColor(ContemporaryColors.ROAD_MARKING_WHITE))
                            .strength(0.25F)));
    public static final RegistryObject<Block> WHITE_STRAIGHT_SIGNAL_BITUMEN_BLOCK_NO_CONNECTION =
            registerBlock("white_straight_signal_bitumen_block_no_connection", () ->
                    new AllBitumenBlock(BlockBehaviour.Properties.of()
                            .mapColor(CustomColorAdapter.toMinecraftMapColor(ContemporaryColors.ROAD_MARKING_WHITE))
                            .strength(0.25F)));
    public static final RegistryObject<Block> WHITE_SLANT_STRAIGHT_SIGNAL_BITUMEN_BLOCK =
            registerBlock("white_slant_straight_signal_bitumen_block", () ->
                    new AllBitumenBlock(BlockBehaviour.Properties.of()
                            .mapColor(CustomColorAdapter.toMinecraftMapColor(ContemporaryColors.ROAD_MARKING_WHITE))
                            .strength(0.25F)));
    public static final RegistryObject<Block> YELLOW_SLANT_STRAIGHT_SIGNAL_BITUMEN_BLOCK =
            registerBlock("yellow_slant_straight_signal_bitumen_block", () ->
                    new AllBitumenBlock(BlockBehaviour.Properties.of()
                            .mapColor(CustomColorAdapter.toMinecraftMapColor(ContemporaryColors.ROAD_MARKING_YELLOW))
                            .strength(0.25F)));
    public static final RegistryObject<Block> YELLOW_SHORT_SLANT_STRAIGHT_SIGNAL_BITUMEN_BLOCK =
            registerBlock("yellow_short_slant_straight_signal_bitumen_block", () ->
                    new AllBitumenBlock(BlockBehaviour.Properties.of()
                            .mapColor(CustomColorAdapter.toMinecraftMapColor(ContemporaryColors.ROAD_MARKING_YELLOW))
                            .strength(0.25F)));
    public static final RegistryObject<Block> YELLOW_DOUBLE_SHORT_SLANT_STRAIGHT_SIGNAL_BITUMEN_BLOCK =
            registerBlock("yellow_double_short_slant_straight_signal_bitumen_block", () ->
                    new AllBitumenBlock(BlockBehaviour.Properties.of()
                            .mapColor(CustomColorAdapter.toMinecraftMapColor(ContemporaryColors.ROAD_MARKING_YELLOW))
                            .strength(0.25F)));
    public static final RegistryObject<Block> WHITE_SHORT_SLANT_STRAIGHT_SIGNAL_BITUMEN_BLOCK =
            registerBlock("white_short_slant_straight_signal_bitumen_block", () ->
                    new AllBitumenBlock(BlockBehaviour.Properties.of()
                            .mapColor(CustomColorAdapter.toMinecraftMapColor(ContemporaryColors.ROAD_MARKING_WHITE))
                            .strength(0.25F)));
    public static final RegistryObject<Block> WHITE_DOUBLE_SHORT_SLANT_STRAIGHT_SIGNAL_BITUMEN_BLOCK =
            registerBlock("white_double_short_slant_straight_signal_bitumen_block", () ->
                    new AllBitumenBlock(BlockBehaviour.Properties.of()
                            .mapColor(CustomColorAdapter.toMinecraftMapColor(ContemporaryColors.ROAD_MARKING_WHITE))
                            .strength(0.25F)));
    public static final RegistryObject<Block> YELLOW_STRAIGHT_SIGNAL_BITUMEN_BLOCK =
            registerBlock("yellow_straight_signal_bitumen_block", () ->
                    new StraightLineBlock(BlockBehaviour.Properties.of()
                            .mapColor(CustomColorAdapter.toMinecraftMapColor(ContemporaryColors.ROAD_MARKING_YELLOW))
                            .strength(0.25F)));
    public static final RegistryObject<Block> YELLOW_STRAIGHT_SIGNAL_BITUMEN_BLOCK_NO_CONNECTION =
            registerBlock("yellow_straight_signal_bitumen_block_no_connection", () ->
                    new AllBitumenBlock(BlockBehaviour.Properties.of()
                            .mapColor(CustomColorAdapter.toMinecraftMapColor(ContemporaryColors.ROAD_MARKING_YELLOW))
                            .strength(0.25F)));
    public static final RegistryObject<Block> YELLOW_STRAIGHT_QUARTER_CIRCLE_SIGNAL_BITUMEN_BLOCK =
            registerBlock("yellow_straight_quarter_circle_signal_bitumen_block", () ->
                    new AllBitumenBlock(BlockBehaviour.Properties.of()
                            .mapColor(CustomColorAdapter.toMinecraftMapColor(ContemporaryColors.ROAD_MARKING_YELLOW))
                            .strength(0.25F)));
    public static final RegistryObject<Block> WHITE_STRAIGHT_QUARTER_CIRCLE_SIGNAL_BITUMEN_BLOCK =
            registerBlock("white_straight_quarter_circle_signal_bitumen_block", () ->
                    new AllBitumenBlock(BlockBehaviour.Properties.of()
                            .mapColor(CustomColorAdapter.toMinecraftMapColor(ContemporaryColors.ROAD_MARKING_WHITE))
                            .strength(0.25F)));
    public static final RegistryObject<Block> YELLOW_QUARTER_CIRCLE_SIGNAL_BITUMEN_BLOCK =
            registerBlock("yellow_quarter_circle_signal_bitumen_block", () ->
                    new AllBitumenBlock(BlockBehaviour.Properties.of()
                            .mapColor(CustomColorAdapter.toMinecraftMapColor(ContemporaryColors.ROAD_MARKING_YELLOW))
                            .strength(0.25F)));
    public static final RegistryObject<Block> WHITE_QUARTER_CIRCLE_SIGNAL_BITUMEN_BLOCK =
            registerBlock("white_quarter_circle_signal_bitumen_block", () ->
                    new AllBitumenBlock(BlockBehaviour.Properties.of()
                            .mapColor(CustomColorAdapter.toMinecraftMapColor(ContemporaryColors.ROAD_MARKING_WHITE))
                            .strength(0.25F)));
    public static final RegistryObject<Block> WHITE_DIRECTION_SIGNAL_BITUMEN_BLOCK =
            registerBlock("white_direction_signal_bitumen_block", () ->
                    new AllBitumenBlock(BlockBehaviour.Properties.of()
                            .mapColor(CustomColorAdapter.toMinecraftMapColor(ContemporaryColors.ROAD_MARKING_WHITE))
                            .strength(0.25F)));
    public static final RegistryObject<Block> YELLOW_DIRECTION_SIGNAL_BITUMEN_BLOCK =
            registerBlock("yellow_direction_signal_bitumen_block", () ->
                    new AllBitumenBlock(BlockBehaviour.Properties.of()
                            .mapColor(CustomColorAdapter.toMinecraftMapColor(ContemporaryColors.ROAD_MARKING_YELLOW))
                            .strength(0.25F)));
    public static final RegistryObject<Block> WHITE_DOUBLE_STRAIGHT_SIGNAL_BITUMEN_BLOCK =
            registerBlock("white_double_straight_signal_bitumen_block", () ->
                    new AllBitumenBlock(BlockBehaviour.Properties.of()
                            .mapColor(CustomColorAdapter.toMinecraftMapColor(ContemporaryColors.ROAD_MARKING_WHITE))
                            .strength(0.25F)));
    public static final RegistryObject<Block> YELLOW_DOUBLE_STRAIGHT_SIGNAL_BITUMEN_BLOCK =
            registerBlock("yellow_double_straight_signal_bitumen_block", () ->
                    new AllBitumenBlock(BlockBehaviour.Properties.of()
                            .mapColor(CustomColorAdapter.toMinecraftMapColor(ContemporaryColors.ROAD_MARKING_YELLOW))
                            .strength(0.25F)));
    public static final RegistryObject<Block> WHITE_CHARACTER_LI_SIGNAL_BITUMEN_BLOCK =
            registerBlock("white_character_li_signal_bitumen_block", () ->
                    new AllBitumenBlock(BlockBehaviour.Properties.of()
                            .mapColor(CustomColorAdapter.toMinecraftMapColor(ContemporaryColors.ROAD_MARKING_WHITE))
                            .strength(0.25F)));
    public static final RegistryObject<Block> WHITE_CHARACTER_RANG_SIGNAL_BITUMEN_BLOCK =
            registerBlock("white_character_rang_signal_bitumen_block", () ->
                    new AllBitumenBlock(BlockBehaviour.Properties.of()
                            .mapColor(CustomColorAdapter.toMinecraftMapColor(ContemporaryColors.ROAD_MARKING_WHITE))
                            .strength(0.25F)));
    public static final RegistryObject<Block> WHITE_CHARACTER_XING_SIGNAL_BITUMEN_BLOCK =
            registerBlock("white_character_xing_signal_bitumen_block", () ->
                    new AllBitumenBlock(BlockBehaviour.Properties.of()
                            .mapColor(CustomColorAdapter.toMinecraftMapColor(ContemporaryColors.ROAD_MARKING_WHITE))
                            .strength(0.25F)));
    public static final RegistryObject<Block> WHITE_CHARACTER_JIN_SIGNAL_BITUMEN_BLOCK =
            registerBlock("white_character_jin_signal_bitumen_block", () ->
                    new AllBitumenBlock(BlockBehaviour.Properties.of()
                            .mapColor(CustomColorAdapter.toMinecraftMapColor(ContemporaryColors.ROAD_MARKING_WHITE))
                            .strength(0.25F)));
    public static final RegistryObject<Block> WHITE_CHARACTER_REN_SIGNAL_BITUMEN_BLOCK =
            registerBlock("white_character_ren_signal_bitumen_block", () ->
                    new AllBitumenBlock(BlockBehaviour.Properties.of()
                            .mapColor(CustomColorAdapter.toMinecraftMapColor(ContemporaryColors.ROAD_MARKING_WHITE))
                            .strength(0.25F)));
    public static final RegistryObject<Block> WHITE_CHARACTER_ZHI_SIGNAL_BITUMEN_BLOCK =
            registerBlock("white_character_zhi_signal_bitumen_block", () ->
                    new AllBitumenBlock(BlockBehaviour.Properties.of()
                            .mapColor(CustomColorAdapter.toMinecraftMapColor(ContemporaryColors.ROAD_MARKING_WHITE))
                            .strength(0.25F)));
    public static final RegistryObject<Block> WHITE_CHARACTER_TONG_SIGNAL_BITUMEN_BLOCK =
            registerBlock("white_character_tong_signal_bitumen_block", () ->
                    new AllBitumenBlock(BlockBehaviour.Properties.of()
                            .mapColor(CustomColorAdapter.toMinecraftMapColor(ContemporaryColors.ROAD_MARKING_WHITE))
                            .strength(0.25F)));
    public static final RegistryObject<Block> WHITE_CHARACTER_ZHUAN_SIGNAL_BITUMEN_BLOCK =
            registerBlock("white_character_zhuan_signal_bitumen_block", () ->
                    new AllBitumenBlock(BlockBehaviour.Properties.of()
                            .mapColor(CustomColorAdapter.toMinecraftMapColor(ContemporaryColors.ROAD_MARKING_WHITE))
                            .strength(0.25F)));
    public static final RegistryObject<Block> WHITE_CHARACTER_YON_SIGNAL_BITUMEN_BLOCK =
            registerBlock("white_character_yon_signal_bitumen_block", () ->
                    new AllBitumenBlock(BlockBehaviour.Properties.of()
                            .mapColor(CustomColorAdapter.toMinecraftMapColor(ContemporaryColors.ROAD_MARKING_WHITE))
                            .strength(0.25F)));
    public static final RegistryObject<Block> WHITE_RIGHT_DIRECTION_SIGNAL_BITUMEN_BLOCK =
            registerBlock("white_right_direction_signal_bitumen_block", () ->
                    new AllBitumenBlock(BlockBehaviour.Properties.of()
                            .mapColor(CustomColorAdapter.toMinecraftMapColor(ContemporaryColors.ROAD_MARKING_WHITE))
                            .strength(0.25F)));

    public static final RegistryObject<Block> WHITE_RIGHT_SIGNAL_BITUMEN_BLOCK =
            registerBlock("white_right_signal_bitumen_block", () ->
                    new AllBitumenBlock(BlockBehaviour.Properties.of()
                            .mapColor(CustomColorAdapter.toMinecraftMapColor(ContemporaryColors.ROAD_MARKING_WHITE))
                            .strength(0.25F)));

    public static final RegistryObject<Block> WHITE_RIGHT_STRAIGHT_SIGNAL_BITUMEN_BLOCK =
            registerBlock("white_right_straight_signal_bitumen_block", () ->
                    new AllBitumenBlock(BlockBehaviour.Properties.of()
                            .mapColor(CustomColorAdapter.toMinecraftMapColor(ContemporaryColors.ROAD_MARKING_WHITE))
                            .strength(0.25F)));

    public static final RegistryObject<Block> WHITE_LEFT_DIRECTION_SIGNAL_BITUMEN_BLOCK =
            registerBlock("white_left_direction_signal_bitumen_block", () ->
                    new AllBitumenBlock(BlockBehaviour.Properties.of()
                            .mapColor(CustomColorAdapter.toMinecraftMapColor(ContemporaryColors.ROAD_MARKING_WHITE))
                            .strength(0.25F)));

    public static final RegistryObject<Block> WHITE_LEFT_RIGHT_STRAIGHT_SIGNAL_BITUMEN_BLOCK =
            registerBlock("white_left_right_straight_signal_bitumen_block", () ->
                    new AllBitumenBlock(BlockBehaviour.Properties.of()
                            .mapColor(CustomColorAdapter.toMinecraftMapColor(ContemporaryColors.ROAD_MARKING_WHITE))
                            .strength(0.25F)));

    public static final RegistryObject<Block> WHITE_LEFT_SIGNAL_BITUMEN_BLOCK =
            registerBlock("white_left_signal_bitumen_block", () ->
                    new AllBitumenBlock(BlockBehaviour.Properties.of()
                            .mapColor(CustomColorAdapter.toMinecraftMapColor(ContemporaryColors.ROAD_MARKING_WHITE))
                            .strength(0.25F)));

    public static final RegistryObject<Block> WHITE_LEFT_STRAIGHT_SIGNAL_BITUMEN_BLOCK =
            registerBlock("white_left_straight_signal_bitumen_block", () ->
                    new AllBitumenBlock(BlockBehaviour.Properties.of()
                            .mapColor(CustomColorAdapter.toMinecraftMapColor(ContemporaryColors.ROAD_MARKING_WHITE))
                            .strength(0.25F)));
    // ... 在现有代码之后添加以下黄色方向性方块注册 ...

    public static final RegistryObject<Block> YELLOW_LEFT_DIRECTION_SIGNAL_BITUMEN_BLOCK =
            registerBlock("yellow_left_direction_signal_bitumen_block", () ->
                    new AllBitumenBlock(BlockBehaviour.Properties.of()
                            .mapColor(CustomColorAdapter.toMinecraftMapColor(ContemporaryColors.ROAD_MARKING_YELLOW))
                            .strength(0.25F)));

    public static final RegistryObject<Block> YELLOW_LEFT_RIGHT_STRAIGHT_SIGNAL_BITUMEN_BLOCK =
            registerBlock("yellow_left_right_straight_signal_bitumen_block", () ->
                    new AllBitumenBlock(BlockBehaviour.Properties.of()
                            .mapColor(CustomColorAdapter.toMinecraftMapColor(ContemporaryColors.ROAD_MARKING_YELLOW))
                            .strength(0.25F)));

    public static final RegistryObject<Block> YELLOW_LEFT_SIGNAL_BITUMEN_BLOCK =
            registerBlock("yellow_left_signal_bitumen_block", () ->
                    new AllBitumenBlock(BlockBehaviour.Properties.of()
                            .mapColor(CustomColorAdapter.toMinecraftMapColor(ContemporaryColors.ROAD_MARKING_YELLOW))
                            .strength(0.25F)));

    public static final RegistryObject<Block> YELLOW_LEFT_STRAIGHT_SIGNAL_BITUMEN_BLOCK =
            registerBlock("yellow_left_straight_signal_bitumen_block", () ->
                    new AllBitumenBlock(BlockBehaviour.Properties.of()
                            .mapColor(CustomColorAdapter.toMinecraftMapColor(ContemporaryColors.ROAD_MARKING_YELLOW))
                            .strength(0.25F)));

    public static final RegistryObject<Block> YELLOW_RIGHT_DIRECTION_SIGNAL_BITUMEN_BLOCK =
            registerBlock("yellow_right_direction_signal_bitumen_block", () ->
                    new AllBitumenBlock(BlockBehaviour.Properties.of()
                            .mapColor(CustomColorAdapter.toMinecraftMapColor(ContemporaryColors.ROAD_MARKING_YELLOW))
                            .strength(0.25F)));

    public static final RegistryObject<Block> YELLOW_RIGHT_SIGNAL_BITUMEN_BLOCK =
            registerBlock("yellow_right_signal_bitumen_block", () ->
                    new AllBitumenBlock(BlockBehaviour.Properties.of()
                            .mapColor(CustomColorAdapter.toMinecraftMapColor(ContemporaryColors.ROAD_MARKING_YELLOW))
                            .strength(0.25F)));

    public static final RegistryObject<Block> YELLOW_RIGHT_STRAIGHT_SIGNAL_BITUMEN_BLOCK =
            registerBlock("yellow_right_straight_signal_bitumen_block", () ->
                    new AllBitumenBlock(BlockBehaviour.Properties.of()
                            .mapColor(CustomColorAdapter.toMinecraftMapColor(ContemporaryColors.ROAD_MARKING_YELLOW))
                            .strength(0.25F)));
    //public static final RegistryObject<Block> STEEL_CROSSBAR =
    //        registerBlock("steel_crossbar", () ->
    //                new Block(BlockBehaviour.Properties.of()
    //                        .mapColor(CustomColorAdapter.toMinecraftMapColor(ModernColors.METAL_STEEL))
    //                       .strength(0.25F)));
    //public static final RegistryObject<Block> STEEL_COLUMN =
    //        registerBlock("steel_column", () ->
    //               new Block(BlockBehaviour.Properties.of()
    //                        .mapColor(CustomColorAdapter.toMinecraftMapColor(ModernColors.METAL_STEEL))
    //                        .strength(0.25F))
    //        );
    //public static final RegistryObject<Block> THICK_STEEL_COLUMN =
    //        registerBlock("thick_steel_column",
    //                () -> new Block(Block.Properties.of()
    //                        .mapColor(CustomColorAdapter.toMinecraftMapColor(ModernColors.METAL_STEEL))
    //                        .strength(0.25F))
    //        );
    public static final RegistryObject<Block> ROAD_FENCE =
            registerBlock("road_fence", () ->
                    new RoadFenceBlock(BlockBehaviour.Properties.of()
                            .mapColor(CustomColorAdapter.toMinecraftMapColor(ContemporaryColors.GLOW_WEAK))
                            .strength(0.25F)
                            .noOcclusion()
                    )
            );
    public static final RegistryObject<Block> BARRIER_RED =
            registerBlock("barrier_red", () ->
                    new Barrier(BlockBehaviour.Properties.of()
                            .mapColor(CustomColorAdapter.toMinecraftMapColor(ContemporaryColors.GLOW_WEAK))
                            .strength(0.25F)
                            .noOcclusion())
            );
    public static final RegistryObject<Block> BARRIER_CYAN =
            registerBlock("barrier_cyan", () ->
                    new Barrier(BlockBehaviour.Properties.of()
                            .mapColor(CustomColorAdapter.toMinecraftMapColor(ContemporaryColors.GLOW_WEAK))
                            .strength(0.25F)
                            .noOcclusion())
            );
    public static final RegistryObject<Block> BARRIER_BLUE =
            registerBlock("barrier_blue", () ->
                    new Barrier(BlockBehaviour.Properties.of()
                            .mapColor(CustomColorAdapter.toMinecraftMapColor(ContemporaryColors.GLOW_WEAK))
                            .strength(0.25F)
                            .noOcclusion()
                    )
            );
    public static final RegistryObject<Block> SPEED_BUMP =
            registerBlock("speed_bump", () ->
                    new SpeedBump(BlockBehaviour.Properties.of()
                            .mapColor(CustomColorAdapter.toMinecraftMapColor(ContemporaryColors.ROAD_MARKING_YELLOW))
                            .strength(0.25F)
                            .isValidSpawn((state, getter, pos, type) -> false)
                    )
            );
    public static final RegistryObject<Block> CRUSH_BARREL =
            registerBlock("crush_barrel", () ->
                    new CrushBarrel(BlockBehaviour.Properties.of()
                            .mapColor(CustomColorAdapter.toMinecraftMapColor(ContemporaryColors.ROAD_MARKING_YELLOW))
                            .strength(0.75F)
                    )
            );
    public static final RegistryObject<Block> RED_CRUSH_COLUMN =
            registerBlock("red_crush_column", () ->
                    new CrushColumn(BlockBehaviour.Properties.of()
                            .mapColor(CustomColorAdapter.toMinecraftMapColor(ContemporaryColors.BRICK_RED))
                            .strength(0.75F)
                    )
            );
    public static final RegistryObject<Block> YELLOW_CRUSH_COLUMN =
            registerBlock("yellow_crush_column", () ->
                    new CrushColumn(BlockBehaviour.Properties.of()
                            .mapColor(CustomColorAdapter.toMinecraftMapColor(ContemporaryColors.ROAD_MARKING_YELLOW))
                            .strength(0.75F)
                    )
            );
    public static final RegistryObject<Block> CONCRETE_BARRIER =
            registerBlock("concrete_barrier", () ->
                    new ConcreteBarrier(BlockBehaviour.Properties.of()
                            .mapColor(CustomColorAdapter.toMinecraftMapColor(ContemporaryColors.CONCRETE_LIGHT))
                            .strength(0.75F)
                    )
            );
    public static final RegistryObject<Block> YELLOW_CONCRETE_BARRIER =
            registerBlock("yellow_concrete_barrier", () ->
                    new ConcreteBarrier(BlockBehaviour.Properties.of()
                            .mapColor(CustomColorAdapter.toMinecraftMapColor(ContemporaryColors.CONCRETE_LIGHT))
                            .strength(0.75F)
                    )
            );
    public static final RegistryObject<Block> TACTILE_PAVING =
            registerBlock("tactile_paving", () ->
                    new TactilePaving(BlockBehaviour.Properties.of()
                            .mapColor(CustomColorAdapter.toMinecraftMapColor(ContemporaryColors.ROAD_MARKING_YELLOW))
                            .strength(0.75F)
                    )
            );
    public static final RegistryObject<Block> STREET_LIGHT_POLE_BLUE_BASE =
            registerBlock("street_light_pole_blue_base", () ->
                    new StreetLightPoleBaseBlock(BlockBehaviour.Properties.of()
                            .mapColor(CustomColorAdapter.toMinecraftMapColor(ContemporaryColors.ROAD_MARKING_BLUE))
                            .strength(0.75F)
                    )
            );
    public static final RegistryObject<Block> STREET_LIGHT_POLE_WHITE =
            registerBlock("street_light_pole_white", () ->
                    new StreetLightPoleBlock(BlockBehaviour.Properties.of()
                            .mapColor(CustomColorAdapter.toMinecraftMapColor(ContemporaryColors.ROAD_MARKING_WHITE))
                            .strength(0.75F)
                    )
            );
    public static final RegistryObject<Block> STREET_LIGHT_BLOCK_A =
            registerBlock("street_light_block_a", () ->
                    new StreetLightBlockA(BlockBehaviour.Properties.of()
                            .mapColor(CustomColorAdapter.toMinecraftMapColor(ContemporaryColors.NEON_BLUE))
                            .strength(0.75F)
                    )
            );
    public static final RegistryObject<Block> SIGNPOST_POLE =
            registerBlock("sign_post_pole", () ->
                    new SignpostPole(BlockBehaviour.Properties.of()
                            .mapColor(CustomColorAdapter.toMinecraftMapColor(ContemporaryColors.GLOW_WEAK))
                            .strength(0.75F)
                    )
            );
    public static final RegistryObject<Block> SIGNPOST =
            registerBlock("sign_post_blank", () ->
                    new Signpost(BlockBehaviour.Properties.of()
                            .mapColor(CustomColorAdapter.toMinecraftMapColor(ContemporaryColors.GLOW_WEAK))
                            .strength(0.75F)
                    )
            );


    private static <T extends Block> void registerBlockItems(String name, RegistryObject<T> block) {
        Moditems.ITEMS.register(name, () -> new BlockItem(
                block.get(),
                new Item.Properties()));

    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> blocks = BLOCKS.register(name,block);
        registerBlockItems(name,blocks);
        return blocks;
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}

