package com.iktaun.Contemporary_Construction.mapcolor.custom;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.block.Block;

import java.util.HashMap;
import java.util.Map;

/**
 * 将自定义颜色系统与Minecraft集成的适配器
 * 这个类负责将自定义颜色映射到Minecraft的MapColor
 */
public class CustomColorAdapter {
    private static final Map<CustomColor, MapColor> CUSTOM_TO_MINECRAFT = new HashMap<>();
    private static final Map<Block, CustomColor> BLOCK_COLORS = new HashMap<>();

    // 初始化映射
    static {
        // 将自定义颜色映射到最接近的Minecraft MapColor
        // 注意：这只是近似映射，实际颜色可能会有所不同
        initColorMapping();
    }

    public static void initColorMapping() {
        // 沥青系列
        mapCustomToMinecraft(ContemporaryColors.ASPHALT_BLACK, MapColor.COLOR_BLACK);
        mapCustomToMinecraft(ContemporaryColors.ASPHALT_DARK_GRAY, MapColor.COLOR_GRAY);
        mapCustomToMinecraft(ContemporaryColors.ASPHALT_GRAY, MapColor.COLOR_LIGHT_GRAY);
        mapCustomToMinecraft(ContemporaryColors.ASPHALT_LIGHT_GRAY, MapColor.SNOW);

        // 交通信号颜色
        mapCustomToMinecraft(ContemporaryColors.TRAFFIC_WHITE, MapColor.SNOW);
        mapCustomToMinecraft(ContemporaryColors.TRAFFIC_YELLOW, MapColor.COLOR_YELLOW);
        mapCustomToMinecraft(ContemporaryColors.TRAFFIC_RED, MapColor.COLOR_RED);
        mapCustomToMinecraft(ContemporaryColors.TRAFFIC_GREEN, MapColor.COLOR_GREEN);
        mapCustomToMinecraft(ContemporaryColors.TRAFFIC_BLUE, MapColor.COLOR_BLUE);

        // 道路标记颜色
        mapCustomToMinecraft(ContemporaryColors.ROAD_MARKING_WHITE, MapColor.SNOW);
        mapCustomToMinecraft(ContemporaryColors.ROAD_MARKING_YELLOW, MapColor.COLOR_YELLOW);
        mapCustomToMinecraft(ContemporaryColors.ROAD_MARKING_BLUE, MapColor.COLOR_BLUE);

        // 建筑材料
        mapCustomToMinecraft(ContemporaryColors.CONCRETE_LIGHT, MapColor.COLOR_LIGHT_GRAY);
        mapCustomToMinecraft(ContemporaryColors.CONCRETE_DARK, MapColor.COLOR_GRAY);
        mapCustomToMinecraft(ContemporaryColors.BRICK_RED, MapColor.COLOR_RED);
        mapCustomToMinecraft(ContemporaryColors.BRICK_BROWN, MapColor.COLOR_BROWN);

        // 玻璃/金属颜色
        mapCustomToMinecraft(ContemporaryColors.GLASS_CLEAR, MapColor.NONE);
        mapCustomToMinecraft(ContemporaryColors.GLASS_TINTED, MapColor.COLOR_LIGHT_GRAY);
        mapCustomToMinecraft(ContemporaryColors.METAL_STEEL, MapColor.COLOR_GRAY);
        mapCustomToMinecraft(ContemporaryColors.METAL_ALUMINUM, MapColor.COLOR_LIGHT_GRAY);

        // 现代灯光颜色
        mapCustomToMinecraft(ContemporaryColors.LED_WHITE, MapColor.SNOW);
        mapCustomToMinecraft(ContemporaryColors.LED_WARM, MapColor.COLOR_YELLOW);
        mapCustomToMinecraft(ContemporaryColors.LED_COOL, MapColor.COLOR_LIGHT_BLUE);
        mapCustomToMinecraft(ContemporaryColors.NEON_RED, MapColor.COLOR_RED);
        mapCustomToMinecraft(ContemporaryColors.NEON_BLUE, MapColor.COLOR_BLUE);
        mapCustomToMinecraft(ContemporaryColors.NEON_GREEN, MapColor.COLOR_GREEN);

        // 自然/环境颜色
        mapCustomToMinecraft(ContemporaryColors.GRASS_MODERN, MapColor.COLOR_GREEN);
        mapCustomToMinecraft(ContemporaryColors.WATER_MODERN, MapColor.COLOR_LIGHT_BLUE);
        mapCustomToMinecraft(ContemporaryColors.SKY_MODERN, MapColor.COLOR_LIGHT_BLUE);

        // 特殊效果颜色
        mapCustomToMinecraft(ContemporaryColors.GLOW_WEAK, MapColor.COLOR_YELLOW);
        mapCustomToMinecraft(ContemporaryColors.GLOW_MEDIUM, MapColor.COLOR_YELLOW);
        mapCustomToMinecraft(ContemporaryColors.GLOW_STRONG, MapColor.COLOR_YELLOW);
    }

    /**
     * 注册自定义颜色到Minecraft MapColor的映射
     */
    public static void mapCustomToMinecraft(CustomColor customColor, MapColor minecraftColor) {
        CUSTOM_TO_MINECRAFT.put(customColor, minecraftColor);
    }

    /**
     * 将 CustomColor 转换为 Minecraft 的 MapColor
     */
    public static MapColor toMinecraftMapColor(CustomColor customColor) {
        if (customColor == null) {
            return MapColor.COLOR_GRAY;
        }

        MapColor mappedColor = CUSTOM_TO_MINECRAFT.get(customColor);
        return mappedColor != null ? mappedColor : MapColor.COLOR_GRAY;
    }

    /**
     * 根据颜色名称获取 Minecraft 的 MapColor
     */
    public static MapColor toMinecraftMapColor(String colorName) {
        CustomColor customColor = CustomColorRegistry.getInstance().getColor(colorName);
        return toMinecraftMapColor(customColor);
    }

    /**
     * 为方块注册自定义颜色
     */
    public static void registerBlockColor(Block block, CustomColor color) {
        BLOCK_COLORS.put(block, color);
    }

    /**
     * 获取方块对应的自定义颜色
     */
    public static CustomColor getBlockCustomColor(Block block) {
        return BLOCK_COLORS.get(block);
    }

    /**
     * 获取方块对应的Minecraft MapColor
     */
    public static MapColor getBlockMapColor(Block block) {
        CustomColor customColor = getBlockCustomColor(block);
        if (customColor != null) {
            MapColor mappedColor = CUSTOM_TO_MINECRAFT.get(customColor);
            return mappedColor != null ? mappedColor : MapColor.COLOR_GRAY;
        }
        return null;
    }

    /**
     * 在方块状态中获取自定义颜色
     */
    public static CustomColor getStateCustomColor(BlockState state, BlockGetter level, BlockPos pos) {
        Block block = state.getBlock();
        CustomColor color = getBlockCustomColor(block);

        if (color == null) {
            // 如果没有注册自定义颜色，根据方块位置计算一个
            return calculateDynamicColor(state, level, pos);
        }

        return color;
    }

    /**
     * 动态计算颜色（基于位置、光照等）
     */
    private static CustomColor calculateDynamicColor(BlockState state, BlockGetter level, BlockPos pos) {
        // 这里可以实现更复杂的颜色计算逻辑
        // 例如：根据光照、生物群系、时间等调整颜色

        // 简化版：返回一个默认颜色
        return ContemporaryColors.ASPHALT_GRAY;
    }

    /**
     * 获取所有已注册的方块颜色
     */
    public static Map<Block, CustomColor> getAllBlockColors() {
        return new HashMap<>(BLOCK_COLORS);
    }

    /**
     * 清除所有颜色映射
     */
    public static void clear() {
        CUSTOM_TO_MINECRAFT.clear();
        BLOCK_COLORS.clear();
    }
}