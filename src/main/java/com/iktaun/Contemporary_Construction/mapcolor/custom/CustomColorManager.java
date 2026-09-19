package com.iktaun.Contemporary_Construction.mapcolor.custom;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/**
 * 自定义颜色系统的管理器
 * 负责初始化和集成
 */
public class CustomColorManager {
    private static boolean initialized = false;

    /**
     * 初始化整个自定义颜色系统
     */
    public static void init() {
        if (initialized) {
            return;
        }

        System.out.println("Initializing Custom Color System...");

        // 1. 初始化颜色注册表
        CustomColorRegistry registry = CustomColorRegistry.getInstance();

        // 2. 注册现代化颜色
        ContemporaryColors.registerAll();

        // 3. 初始化颜色适配器
        CustomColorAdapter.initColorMapping();

        // 4. 设置事件监听
        setupEventListeners();

        // 5. 输出调试信息
        printDebugInfo();

        initialized = true;
        System.out.println("Custom Color System initialized successfully!");
    }

    /**
     * 设置事件监听器
     */
    private static void setupEventListeners() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener((FMLCommonSetupEvent event) -> {
            event.enqueueWork(() -> {
                // 在游戏加载完成后执行的颜色相关初始化
                System.out.println("Custom Color System: Common setup completed");
            });
        });
    }

    /**
     * 为方块注册自定义颜色
     */
    public static void registerBlockColor(Block block, String colorName) {
        CustomColor color = CustomColorRegistry.getInstance().getColor(colorName);
        if (color != null) {
            CustomColorAdapter.registerBlockColor(block, color);
            System.out.println("Registered color " + colorName + " for block " + block);
        } else {
            System.err.println("Color not found: " + colorName);
        }
    }

    /**
     * 为方块注册自定义颜色（使用RGB值）
     */
    public static void registerBlockColor(Block block, String colorName, int r, int g, int b) {
        CustomColorRegistry registry = CustomColorRegistry.getInstance();
        CustomColor color = registry.registerColor(colorName, r, g, b);
        CustomColorAdapter.registerBlockColor(block, color);

        System.out.println("Registered new color " + colorName + " (" + r + "," + g + "," + b + ") for block " + block);
    }

    /**
     * 获取方块的自定义颜色
     */
    public static CustomColor getBlockColor(Block block) {
        return CustomColorAdapter.getBlockCustomColor(block);
    }

    /**
     * 获取颜色总数
     */
    public static int getColorCount() {
        return CustomColorRegistry.getInstance().getAllColors().size();
    }

    /**
     * 导出颜色配置
     */
    public static String exportColorConfig() {
        StringBuilder sb = new StringBuilder();
        sb.append("Custom Color Configuration\n");
        sb.append("===========================\n");

        for (CustomColor color : CustomColorRegistry.getInstance().getAllColors()) {
            sb.append(String.format("ID: %3d | Name: %-20s | Color: %s\n",
                    color.getId(), color.getName(), color.toHexString()));
        }

        return sb.toString();
    }

    /**
     * 打印调试信息
     */
    private static void printDebugInfo() {
        System.out.println("=== Custom Color System Debug Info ===");
        System.out.println("Total colors registered: " + getColorCount());
        System.out.println("Color groups: " +
                String.join(", ", CustomColorRegistry.getInstance().getAllGroupNames()));
        System.out.println("======================================");
    }

    /**
     * 检查是否已初始化
     */
    public static boolean isInitialized() {
        return initialized;
    }
}