package com.iktaun.Contemporary_Construction.util;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

public class FontManager {

    private static final Path CUSTOM_FONT_DIR = Paths.get(
            Minecraft.getInstance().gameDirectory.getPath(),
            "config", "contemporaryconstruction", "fonts"
    );

    private static final Map<String, Font> FONT_CACHE = new LinkedHashMap<>();
    private static final Map<String, String> FONT_SOURCE = new LinkedHashMap<>();

    static {
        reload();
    }

    public static void reload() {
        FONT_CACHE.clear();
        FONT_SOURCE.clear();

        // 1. 添加 Minecraft 原版字体
        FONT_CACHE.put("Minecraft", null);
        FONT_SOURCE.put("Minecraft", "Minecraft 原版");

        // 2. 加载系统字体（验证可用性）
        loadSystemFonts();

        // 3. 加载内置字体
        loadBuiltinFonts();

        // 4. 加载自定义字体（玩家导入）
        loadCustomFonts();

        // 注册所有非 null 字体到 AWT 环境
        for (Font font : FONT_CACHE.values()) {
            if (font != null) {
                try {
                    GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);
                } catch (Exception e) {
                    System.out.println("[FontManager] 注册字体失败: " + font.getFamily() + " - " + e.getMessage());
                }
            }
        }

        // 打印最终字体列表
        System.out.println("[FontManager] 已加载字体: " + FONT_CACHE.keySet());
    }

    /**
     * 加载系统字体（只加载真正可用的字体）
     */
    private static void loadSystemFonts() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] families = ge.getAvailableFontFamilyNames();
        for (String name : families) {
            if (!FONT_CACHE.containsKey(name)) {
                Font testFont = new Font(name, Font.PLAIN, 12);
                // 验证字体是否真正可用
                if (testFont.getFamily().equals(name) && testFont.canDisplay('A')) {
                    FONT_CACHE.put(name, testFont);
                    FONT_SOURCE.put(name, "系统字体");
                } else {
                    System.out.println("[FontManager] 跳过无效系统字体: " + name);
                }
            }
        }
    }

    /**
     * 加载内置字体（从模组资源中读取）
     */
    private static void loadBuiltinFonts() {
        var resourceManager = Minecraft.getInstance().getResourceManager();
        // 你可以在这里添加实际存在的字体文件名
        String[] builtinFonts = {};
        for (String fileName : builtinFonts) {
            ResourceLocation location = new ResourceLocation(
                    "contemporaryconstruction",
                    "fonts/" + fileName
            );
            try {
                var resource = resourceManager.getResource(location);
                if (resource.isPresent()) {
                    try (InputStream is = resource.get().open()) {
                        Font font = Font.createFont(Font.TRUETYPE_FONT, is);
                        String name = font.getFamily();
                        FONT_CACHE.put(name, font);
                        FONT_SOURCE.put(name, "内置: " + fileName);
                        System.out.println("[FontManager] 加载内置字体: " + fileName + " → " + name);
                    }
                } else {
                    System.out.println("[FontManager] 内置字体不存在: " + location);
                }
            } catch (Exception e) {
                System.out.println("[FontManager] 加载内置字体失败: " + fileName + " - " + e.getMessage());
            }
        }
    }

    /**
     * 加载自定义字体（玩家导入）
     */
    private static void loadCustomFonts() {
        try {
            Files.createDirectories(CUSTOM_FONT_DIR);
            System.out.println("[FontManager] 扫描自定义字体目录: " + CUSTOM_FONT_DIR);

            try (var stream = Files.list(CUSTOM_FONT_DIR)) {
                stream.filter(p -> {
                    String name = p.getFileName().toString().toLowerCase();
                    return name.endsWith(".ttf") || name.endsWith(".otf");
                }).forEach(path -> {
                    try {
                        System.out.println("[FontManager] 正在加载: " + path.getFileName());

                        // 加载字体
                        Font font = Font.createFont(Font.TRUETYPE_FONT, path.toFile());

                        // 获取字体家族名称
                        String familyName = font.getFamily();
                        System.out.println("[FontManager] 字体家族名称: " + familyName);

                        // 验证字体是否有效（能否渲染基本字符）
                        if (font.canDisplay('A')) {
                            FONT_CACHE.put(familyName, font);
                            FONT_SOURCE.put(familyName, "自定义: " + path.getFileName().toString());
                            System.out.println("[FontManager] 成功加载自定义字体: " + path.getFileName() + " → " + familyName);
                        } else {
                            System.out.println("[FontManager] 自定义字体无法渲染基本字符，跳过: " + path.getFileName());
                        }

                    } catch (FontFormatException e) {
                        System.out.println("[FontManager] 字体格式错误（非 TrueType/OpenType）: " + path.getFileName());
                        e.printStackTrace();
                    } catch (IOException e) {
                        System.out.println("[FontManager] 字体文件读取失败: " + path.getFileName());
                        e.printStackTrace();
                    } catch (Exception e) {
                        System.out.println("[FontManager] 未知错误加载字体: " + path.getFileName());
                        e.printStackTrace();
                    }
                });
            }
        } catch (IOException e) {
            System.out.println("[FontManager] 扫描字体目录失败");
            e.printStackTrace();
        }
    }

    /**
     * 获取字体
     */
    public static Font getFont(String name, int size) {
        if (name == null || name.equals("Minecraft")) return null;
        Font base = FONT_CACHE.get(name);
        if (base == null) {
            System.out.println("[FontManager] 字体不存在: " + name + " (可用字体: " + FONT_CACHE.keySet() + ")");
            return null;
        }
        try {
            return base.deriveFont((float) size);
        } catch (Exception e) {
            System.out.println("[FontManager] 派生字体失败: " + name + " - " + e.getMessage());
            return null;
        }
    }

    /**
     * 获取可用字体名称列表
     */
    public static List<String> getAvailableFontNames() {
        return new ArrayList<>(FONT_CACHE.keySet());
    }

    public static String getFontSource(String fontName) {
        return FONT_SOURCE.getOrDefault(fontName, "");
    }

    public static String getFontDisplayName(String fontName) {
        String source = getFontSource(fontName);
        if (source.isEmpty()) return fontName;
        return fontName + " (" + source + ")";
    }

    public static boolean isMinecraftFont(String name) {
        return "Minecraft".equals(name);
    }

    public static boolean hasCustomFonts() {
        for (String name : FONT_CACHE.keySet()) {
            if (!name.equals("Minecraft") && FONT_CACHE.get(name) != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * 调试：打印所有可用字体
     */
    public static void dumpAvailableFonts() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        System.out.println("=== 系统可用字体 ===");
        for (String name : ge.getAvailableFontFamilyNames()) {
            System.out.println("  " + name);
        }
        System.out.println("=== 模组加载字体 ===");
        for (String name : FONT_CACHE.keySet()) {
            System.out.println("  " + name + " (" + FONT_SOURCE.getOrDefault(name, "未知") + ")");
        }
    }
}