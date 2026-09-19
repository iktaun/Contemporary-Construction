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
            "arrow_up", "arrow_down", "arrow_left", "arrow_right",
            "arrow_turn_left", "arrow_turn_right",
            "slash", "backslash",
            "cross", "check", "star", "heart", "diamond",
            "triangle_up", "triangle_down", "triangle_left", "triangle_right",
            "circle", "square", "round_rect", "hexagon"
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