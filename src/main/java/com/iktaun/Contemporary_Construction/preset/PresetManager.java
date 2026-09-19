package com.iktaun.Contemporary_Construction.preset;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.iktaun.Contemporary_Construction.blocks.Entity.SignpostText;
import net.minecraft.client.Minecraft;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class PresetManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(PresetManager.class);
    private static final Path PRESET_DIR = Paths.get(
            Minecraft.getInstance().gameDirectory.getPath(),
            "config", "contemporaryconstruction", "presets"
    );

    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    static {
        try {
            Files.createDirectories(PRESET_DIR);
        } catch (IOException e) {
            LOGGER.error("Failed to create preset directory", e);
        }
    }

    public static List<String> getPresetNames() {
        List<String> names = new ArrayList<>();
        try (var stream = Files.list(PRESET_DIR)) {
            stream.filter(p -> p.toString().endsWith(".json"))
                    .forEach(p -> {
                        String fileName = p.getFileName().toString();
                        names.add(fileName.substring(0, fileName.lastIndexOf('.')));
                    });
        } catch (IOException ignored) {}
        return names;
    }

    public static PresetData loadPreset(String name) {
        Path file = PRESET_DIR.resolve(name + ".json");
        if (!Files.exists(file)) return null;
        try (Reader reader = new InputStreamReader(Files.newInputStream(file), StandardCharsets.UTF_8)) {
            return GSON.fromJson(reader, PresetData.class);
        } catch (JsonSyntaxException | IOException e) {
            LOGGER.error("Failed to load preset '{}', deleting corrupted file", name, e);
            try {
                Files.deleteIfExists(file);
            } catch (IOException ex) {
                LOGGER.error("Failed to delete corrupted preset file", ex);
            }
            return null;
        }
    }

    public static boolean savePreset(String name, SignpostText text) {
        try {
            Path file = PRESET_DIR.resolve(name + ".json");
            PresetData data = new PresetData(name, text);
            String json = GSON.toJson(data);
            Files.writeString(file, json, StandardCharsets.UTF_8);
            return true;
        } catch (IOException e) {
            LOGGER.error("Failed to save preset", e);
            return false;
        }
    }

    public static boolean deletePreset(String name) {
        try {
            Path file = PRESET_DIR.resolve(name + ".json");
            return Files.deleteIfExists(file);
        } catch (IOException e) {
            LOGGER.error("Failed to delete preset", e);
            return false;
        }
    }

    public static boolean presetExists(String name) {
        return Files.exists(PRESET_DIR.resolve(name + ".json"));
    }

    public static long getPresetModifiedTime(String name) {
        try {
            Path file = PRESET_DIR.resolve(name + ".json");
            if (Files.exists(file)) {
                return Files.getLastModifiedTime(file).toMillis();
            }
        } catch (IOException ignored) {}
        return 0;
    }
}