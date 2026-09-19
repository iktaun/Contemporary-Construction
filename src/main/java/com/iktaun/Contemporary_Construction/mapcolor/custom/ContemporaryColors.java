package com.iktaun.Contemporary_Construction.mapcolor.custom;

/**
 * 预定义的现代化颜色
 * 专门为现代化建设模组设计
 */
public class ContemporaryColors {
    // 沥青系列颜色
    public static final CustomColor ASPHALT_BLACK = new CustomColor(50, "asphalt_black", 20, 20, 20);
    public static final CustomColor ASPHALT_DARK_GRAY = new CustomColor(51, "asphalt_dark_gray", 40, 40, 40);
    public static final CustomColor ASPHALT_GRAY = new CustomColor(52, "asphalt_gray", 60, 60, 60);
    public static final CustomColor ASPHALT_LIGHT_GRAY = new CustomColor(53, "asphalt_light_gray", 100, 100, 100);

    // 交通信号颜色（高可见度）
    public static final CustomColor TRAFFIC_WHITE = new CustomColor(54, "traffic_white", 255, 255, 240);
    public static final CustomColor TRAFFIC_YELLOW = new CustomColor(55, "traffic_yellow", 255, 200, 0);
    public static final CustomColor TRAFFIC_RED = new CustomColor(56, "traffic_red", 220, 0, 0);
    public static final CustomColor TRAFFIC_GREEN = new CustomColor(57, "traffic_green", 0, 180, 0);
    public static final CustomColor TRAFFIC_BLUE = new CustomColor(58, "traffic_blue", 0, 120, 220);

    // 道路标记颜色
    public static final CustomColor ROAD_MARKING_WHITE = new CustomColor(59, "road_marking_white", 240, 240, 220);
    public static final CustomColor ROAD_MARKING_YELLOW = new CustomColor(60, "road_marking_yellow", 255, 180, 0);
    public static final CustomColor ROAD_MARKING_BLUE = new CustomColor(61, "road_marking_blue", 30, 90, 180);

    // 建筑材料颜色
    public static final CustomColor CONCRETE_LIGHT = new CustomColor(62, "concrete_light", 200, 200, 200);
    public static final CustomColor CONCRETE_DARK = new CustomColor(63, "concrete_dark", 100, 100, 100);
    public static final CustomColor BRICK_RED = new CustomColor(64, "brick_red", 180, 80, 60);
    public static final CustomColor BRICK_BROWN = new CustomColor(65, "brick_brown", 140, 100, 80);

    // 玻璃/金属颜色
    public static final CustomColor GLASS_CLEAR = new CustomColor(66, "glass_clear", 220, 240, 255, 128);
    public static final CustomColor GLASS_TINTED = new CustomColor(67, "glass_tinted", 100, 120, 140, 200);
    public static final CustomColor METAL_STEEL = new CustomColor(68, "metal_steel", 150, 150, 160);
    public static final CustomColor METAL_ALUMINUM = new CustomColor(69, "metal_aluminum", 200, 200, 210);

    // 现代灯光颜色
    public static final CustomColor LED_WHITE = new CustomColor(70, "led_white", 255, 255, 255);
    public static final CustomColor LED_WARM = new CustomColor(71, "led_warm", 255, 240, 200);
    public static final CustomColor LED_COOL = new CustomColor(72, "led_cool", 200, 220, 255);
    public static final CustomColor NEON_RED = new CustomColor(73, "neon_red", 255, 0, 0);
    public static final CustomColor NEON_BLUE = new CustomColor(74, "neon_blue", 0, 200, 255);
    public static final CustomColor NEON_GREEN = new CustomColor(75, "neon_green", 0, 255, 100);

    // 自然/环境颜色
    public static final CustomColor GRASS_MODERN = new CustomColor(76, "grass_modern", 80, 180, 80);
    public static final CustomColor WATER_MODERN = new CustomColor(77, "water_modern", 60, 150, 220, 180);
    public static final CustomColor SKY_MODERN = new CustomColor(78, "sky_modern", 120, 180, 255);

    // 特殊效果颜色
    public static final CustomColor GLOW_WEAK = new CustomColor(79, "glow_weak", 255, 255, 200, 64);
    public static final CustomColor GLOW_MEDIUM = new CustomColor(80, "glow_medium", 255, 255, 180, 128);
    public static final CustomColor GLOW_STRONG = new CustomColor(81, "glow_strong", 255, 255, 150, 192);

    // 注册所有现代化颜色
    public static void registerAll() {
        CustomColorRegistry registry = CustomColorRegistry.getInstance();

        // 沥青系列
        registry.registerColor(ASPHALT_BLACK);
        registry.registerColor(ASPHALT_DARK_GRAY);
        registry.registerColor(ASPHALT_GRAY);
        registry.registerColor(ASPHALT_LIGHT_GRAY);

        // 交通信号颜色
        registry.registerColor(TRAFFIC_WHITE);
        registry.registerColor(TRAFFIC_YELLOW);
        registry.registerColor(TRAFFIC_RED);
        registry.registerColor(TRAFFIC_GREEN);
        registry.registerColor(TRAFFIC_BLUE);

        // 道路标记颜色
        registry.registerColor(ROAD_MARKING_WHITE);
        registry.registerColor(ROAD_MARKING_YELLOW);
        registry.registerColor(ROAD_MARKING_BLUE);

        // 建筑材料颜色
        registry.registerColor(CONCRETE_LIGHT);
        registry.registerColor(CONCRETE_DARK);
        registry.registerColor(BRICK_RED);
        registry.registerColor(BRICK_BROWN);

        // 玻璃/金属颜色
        registry.registerColor(GLASS_CLEAR);
        registry.registerColor(GLASS_TINTED);
        registry.registerColor(METAL_STEEL);
        registry.registerColor(METAL_ALUMINUM);

        // 现代灯光颜色
        registry.registerColor(LED_WHITE);
        registry.registerColor(LED_WARM);
        registry.registerColor(LED_COOL);
        registry.registerColor(NEON_RED);
        registry.registerColor(NEON_BLUE);
        registry.registerColor(NEON_GREEN);

        // 自然/环境颜色
        registry.registerColor(GRASS_MODERN);
        registry.registerColor(WATER_MODERN);
        registry.registerColor(SKY_MODERN);

        // 特殊效果颜色
        registry.registerColor(GLOW_WEAK);
        registry.registerColor(GLOW_MEDIUM);
        registry.registerColor(GLOW_STRONG);

        // 创建颜色分组
        registry.addToGroup("asphalt", ASPHALT_BLACK);
        registry.addToGroup("asphalt", ASPHALT_DARK_GRAY);
        registry.addToGroup("asphalt", ASPHALT_GRAY);
        registry.addToGroup("asphalt", ASPHALT_LIGHT_GRAY);

        registry.addToGroup("traffic", TRAFFIC_WHITE);
        registry.addToGroup("traffic", TRAFFIC_YELLOW);
        registry.addToGroup("traffic", TRAFFIC_RED);
        registry.addToGroup("traffic", TRAFFIC_GREEN);
        registry.addToGroup("traffic", TRAFFIC_BLUE);

        registry.addToGroup("road_marking", ROAD_MARKING_WHITE);
        registry.addToGroup("road_marking", ROAD_MARKING_YELLOW);
        registry.addToGroup("road_marking", ROAD_MARKING_BLUE);

        registry.addToGroup("construction", CONCRETE_LIGHT);
        registry.addToGroup("construction", CONCRETE_DARK);
        registry.addToGroup("construction", BRICK_RED);
        registry.addToGroup("construction", BRICK_BROWN);

        registry.addToGroup("modern_lights", LED_WHITE);
        registry.addToGroup("modern_lights", LED_WARM);
        registry.addToGroup("modern_lights", LED_COOL);
        registry.addToGroup("modern_lights", NEON_RED);
        registry.addToGroup("modern_lights", NEON_BLUE);
        registry.addToGroup("modern_lights", NEON_GREEN);
    }

    /**
     * 获取颜色ID映射表
     */
    public static int getColorId(String colorName) {
        CustomColor color = CustomColorRegistry.getInstance().getColor(colorName);
        return color != null ? color.getId() : -1;
    }

    /**
     * 根据ID获取颜色名称
     */
    public static String getColorName(int colorId) {
        CustomColor color = CustomColorRegistry.getInstance().getColor(colorId);
        return color != null ? color.getName() : "unknown";
    }
}