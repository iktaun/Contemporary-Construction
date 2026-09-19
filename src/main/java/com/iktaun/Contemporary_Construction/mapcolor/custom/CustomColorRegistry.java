package com.iktaun.Contemporary_Construction.mapcolor.custom;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自定义颜色注册表
 * 管理所有自定义颜色，确保ID唯一性
 */
public class CustomColorRegistry {
    // 单例实例
    private static final CustomColorRegistry INSTANCE = new CustomColorRegistry();

    // 颜色存储
    private final Map<Integer, CustomColor> colorsById = new ConcurrentHashMap<>();
    private final Map<String, CustomColor> colorsByName = new ConcurrentHashMap<>();
    private final AtomicInteger nextId = new AtomicInteger(0);

    // 颜色分组
    private final Map<String, List<CustomColor>> colorGroups = new ConcurrentHashMap<>();

    // 私有构造函数（单例模式）
    private CustomColorRegistry() {
        // 初始化基础颜色
        initializeBasicColors();
    }

    public static CustomColorRegistry getInstance() {
        return INSTANCE;
    }

    /**
     * 初始化基础颜色
     */
    private void initializeBasicColors() {
        // 黑色系
        registerColor(new CustomColor(0, "black", 0, 0, 0));
        registerColor(new CustomColor(1, "dark_gray", 64, 64, 64));
        registerColor(new CustomColor(2, "gray", 128, 128, 128));
        registerColor(new CustomColor(3, "light_gray", 192, 192, 192));
        registerColor(new CustomColor(4, "white", 255, 255, 255));

        // 红色系
        registerColor(new CustomColor(5, "dark_red", 128, 0, 0));
        registerColor(new CustomColor(6, "red", 255, 0, 0));
        registerColor(new CustomColor(7, "light_red", 255, 128, 128));

        // 绿色系
        registerColor(new CustomColor(8, "dark_green", 0, 128, 0));
        registerColor(new CustomColor(9, "green", 0, 255, 0));
        registerColor(new CustomColor(10, "light_green", 128, 255, 128));

        // 蓝色系
        registerColor(new CustomColor(11, "dark_blue", 0, 0, 128));
        registerColor(new CustomColor(12, "blue", 0, 0, 255));
        registerColor(new CustomColor(13, "light_blue", 128, 128, 255));

        // 黄色系
        registerColor(new CustomColor(14, "dark_yellow", 128, 128, 0));
        registerColor(new CustomColor(15, "yellow", 255, 255, 0));
        registerColor(new CustomColor(16, "light_yellow", 255, 255, 128));

        // 特殊颜色组
        addToGroup("basic", getColor(0));
        addToGroup("basic", getColor(2));
        addToGroup("basic", getColor(4));

        // 设置下一个可用ID
        nextId.set(17);
    }

    /**
     * 注册新颜色（自动分配ID）
     */
    public CustomColor registerColor(String name, int r, int g, int b, int alpha) {
        int id = nextId.getAndIncrement();
        if (id > 255) {
            throw new IllegalStateException("Maximum number of colors (256) reached");
        }

        CustomColor color = new CustomColor(id, name, r, g, b, alpha);
        registerColor(color);
        return color;
    }

    /**
     * 注册新颜色（自动分配ID，不透明）
     */
    public CustomColor registerColor(String name, int r, int g, int b) {
        return registerColor(name, r, g, b, 255);
    }

    /**
     * 注册预定义的颜色对象
     */
    public void registerColor(CustomColor color) {
        if (colorsById.containsKey(color.getId())) {
            throw new IllegalArgumentException("Color ID " + color.getId() + " already registered");
        }
        if (colorsByName.containsKey(color.getName().toLowerCase())) {
            throw new IllegalArgumentException("Color name '" + color.getName() + "' already registered");
        }

        colorsById.put(color.getId(), color);
        colorsByName.put(color.getName().toLowerCase(), color);
    }

    /**
     * 根据ID获取颜色
     */
    public CustomColor getColor(int id) {
        return colorsById.get(id);
    }

    /**
     * 根据名称获取颜色
     */
    public CustomColor getColor(String name) {
        return colorsByName.get(name.toLowerCase());
    }

    /**
     * 获取所有已注册的颜色
     */
    public Collection<CustomColor> getAllColors() {
        return Collections.unmodifiableCollection(colorsById.values());
    }

    /**
     * 获取颜色ID列表
     */
    public Set<Integer> getAllColorIds() {
        return Collections.unmodifiableSet(colorsById.keySet());
    }

    /**
     * 获取颜色名称列表
     */
    public Set<String> getAllColorNames() {
        return Collections.unmodifiableSet(colorsByName.keySet());
    }

    /**
     * 添加颜色到分组
     */
    public void addToGroup(String groupName, CustomColor color) {
        colorGroups.computeIfAbsent(groupName, k -> new ArrayList<>()).add(color);
    }

    /**
     * 获取分组中的颜色
     */
    public List<CustomColor> getGroup(String groupName) {
        List<CustomColor> group = colorGroups.get(groupName);
        return group != null ? Collections.unmodifiableList(group) : Collections.emptyList();
    }

    /**
     * 获取所有分组名称
     */
    public Set<String> getAllGroupNames() {
        return Collections.unmodifiableSet(colorGroups.keySet());
    }

    /**
     * 根据RGB值查找最接近的颜色
     */
    public CustomColor findNearestColor(int targetR, int targetG, int targetB) {
        CustomColor nearest = null;
        double minDistance = Double.MAX_VALUE;

        for (CustomColor color : colorsById.values()) {
            double distance = colorDistance(
                    targetR, targetG, targetB,
                    color.getRed(), color.getGreen(), color.getBlue()
            );

            if (distance < minDistance) {
                minDistance = distance;
                nearest = color;
            }
        }

        return nearest;
    }

    /**
     * 计算颜色之间的距离（欧几里得距离）
     */
    private double colorDistance(int r1, int g1, int b1, int r2, int g2, int b2) {
        return Math.sqrt(
                Math.pow(r1 - r2, 2) +
                        Math.pow(g1 - g2, 2) +
                        Math.pow(b1 - b2, 2)
        );
    }

    /**
     * 生成调色板（基于起始颜色的渐变色）
     */
    public List<CustomColor> generatePalette(CustomColor baseColor, int count) {
        List<CustomColor> palette = new ArrayList<>();
        palette.add(baseColor);

        for (int i = 1; i < count; i++) {
            float factor = 1.0f - (i * 0.1f); // 逐渐变暗
            CustomColor shade = baseColor.darker(factor);

            // 创建新的颜色ID
            String shadeName = baseColor.getName() + "_shade_" + i;
            CustomColor registeredShade = registerColor(shadeName,
                    shade.getRed(), shade.getGreen(), shade.getBlue(), shade.getAlpha());

            palette.add(registeredShade);
        }

        return palette;
    }

    /**
     * 清空所有颜色（用于测试）
     */
    public void clear() {
        colorsById.clear();
        colorsByName.clear();
        colorGroups.clear();
        nextId.set(0);
        initializeBasicColors();
    }
}