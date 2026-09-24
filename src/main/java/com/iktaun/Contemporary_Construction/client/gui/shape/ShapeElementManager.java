package com.iktaun.Contemporary_Construction.client.gui.shape;

import com.iktaun.Contemporary_Construction.contemporaryconstruction;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class ShapeElementManager {

    private static final Path CUSTOM_PATH = Paths.get(
            Minecraft.getInstance().gameDirectory.getPath(),
            "config", "contemporaryconstruction", "presets", "persetelement"
    );

    private static final Map<String, ShapeElementData> ELEMENTS = new LinkedHashMap<>();

    private static final String[] BUILTIN_SHAPES = {
            // ===== 箭头（Arrow）=====
            "arrow",           // 箭头 ↑

            // ===== 符号（Symbol）=====
            "slash",              // 斜杠 /
            "slant",
            "cross",              // 叉号 ✕
            "check",              // 对勾 ✓
            "star",               // 星号 ★

            // ===== 图形（Shape）=====
            "heart",              // 心形 ♥
            "diamond",            // 菱形 ♦
            "circle",             // 圆形 ●
            "hollow_circle",      // 空心圆 ○
            "square",             // 正方形 ■
            "round_rect",         // 圆角矩形 ▢
            "hexagon",            // 六边形 ⬡
            "octagon",            // 六边形 ⬡

            // ===== 三角形（Triangle）=====
            "triangle",        // 三角 ▲
    };

    public static void reload() {
        ELEMENTS.clear();
        loadBuiltinElements();
        loadCustomElements();
    }

    private static void loadBuiltinElements() {
        var resourceManager = Minecraft.getInstance().getResourceManager();
        for (String name : BUILTIN_SHAPES) {
            ResourceLocation location = new ResourceLocation(
                    contemporaryconstruction.MOD_ID,
                    "textures/persetelement/" + name + ".png"
            );
            try {
                Optional<Resource> resource = resourceManager.getResource(location);
                if (resource.isPresent()) {
                    try (InputStream is = resource.get().open()) {
                        BufferedImage img = ImageIO.read(is);
                        if (img != null) {
                            ELEMENTS.put(name, new ShapeElementData(
                                    name, location, img.getWidth(), img.getHeight(),
                                    ShapeElementData.Source.BUILTIN
                            ));
                        }
                    }
                }
            } catch (IOException ignored) {}
        }
    }

    private static void loadCustomElements() {
        try {
            Files.createDirectories(CUSTOM_PATH);
            try (var stream = Files.list(CUSTOM_PATH)) {
                stream.filter(p -> {
                    String name = p.getFileName().toString().toLowerCase();
                    return name.endsWith(".png") || name.endsWith(".jpg") || name.endsWith(".jpeg");
                }).forEach(path -> {
                    String name = path.getFileName().toString();
                    int dot = name.lastIndexOf('.');
                    if (dot > 0) name = name.substring(0, dot);
                    try {
                        BufferedImage img = ImageIO.read(path.toFile());
                        if (img != null) {
                            ResourceLocation location = new ResourceLocation(
                                    contemporaryconstruction.MOD_ID,
                                    "custom_shape_" + System.currentTimeMillis() + "_" + name
                            );
                            ELEMENTS.put(name, new ShapeElementData(
                                    name, location, img.getWidth(), img.getHeight(),
                                    ShapeElementData.Source.CUSTOM
                            ));
                        }
                    } catch (IOException ignored) {}
                });
            }
        } catch (IOException ignored) {}
    }

    public static List<ShapeElementData> getAllElements() {
        return new ArrayList<>(ELEMENTS.values());
    }

    public static ShapeElementData getElement(String name) {
        return ELEMENTS.get(name);
    }

    public static boolean hasElements() {
        return !ELEMENTS.isEmpty();
    }
}