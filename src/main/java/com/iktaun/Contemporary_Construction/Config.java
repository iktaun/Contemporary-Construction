package com.iktaun.Contemporary_Construction;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = contemporaryconstruction.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.BooleanValue LOG_DIRT_BLOCK = BUILDER
            .comment("Whether to log the dirt block on common setup")
            .define("logDirtBlock", true);

    private static final ForgeConfigSpec.IntValue MAGIC_NUMBER = BUILDER
            .comment("A magic number")
            .defineInRange("magicNumber", 42, 0, Integer.MAX_VALUE);

    public static final ForgeConfigSpec.ConfigValue<String> MAGIC_NUMBER_INTRODUCTION = BUILDER
            .comment("What you want the introduction message to be for the magic number")
            .define("magicNumberIntroduction", "The magic number is... ");

    // a list of strings that are treated as resource locations for items
    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> ITEM_STRINGS = BUILDER
            .comment("A list of items to log on common setup.")
            .defineListAllowEmpty("items", List.of("minecraft:iron_ingot"), Config::validateItemName);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    // 配置值字段 - 直接初始化为默认值
    public static boolean logDirtBlock = true;
    public static int magicNumber = 42;
    public static String magicNumberIntroduction = "The magic number is... ";
    public static Set<Item> items;

    // 使用安全的ResourceLocation解析
    private static boolean validateItemName(final Object obj)
    {
        if (obj instanceof final String itemName) {
            try {
                // 使用try-catch避免解析失败
                ResourceLocation location = new ResourceLocation(itemName);
                return ForgeRegistries.ITEMS.containsKey(location);
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent.Loading event)
    {
        // 加载配置值，处理可能的异常
        try {
            if (LOG_DIRT_BLOCK != null) logDirtBlock = LOG_DIRT_BLOCK.get();
            if (MAGIC_NUMBER != null) magicNumber = MAGIC_NUMBER.get();
            if (MAGIC_NUMBER_INTRODUCTION != null) magicNumberIntroduction = MAGIC_NUMBER_INTRODUCTION.get();

            // 转换物品列表
            if (ITEM_STRINGS != null) {
                items = ITEM_STRINGS.get().stream()
                        .map(itemName -> {
                            try {
                                ResourceLocation location = new ResourceLocation(itemName);
                                return ForgeRegistries.ITEMS.getValue(location);
                            } catch (Exception e) {
                                return null;
                            }
                        })
                        .filter(item -> item != null)
                        .collect(Collectors.toSet());
            }
        } catch (Exception e) {
            // 如果配置加载失败，使用默认值并记录错误
            System.err.println("Error loading config: " + e.getMessage());
            e.printStackTrace();

            // 确保items不为null
            if (items == null) {
                items = Set.of();
            }
        }
    }

    // 静态初始化块确保items有默认值
    static {
        try {
            items = Set.of(ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft:iron_ingot")));
        } catch (Exception e) {
            items = Set.of();
        }
    }
}