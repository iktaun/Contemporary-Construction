package com.iktaun.Contemporary_Construction.font;

import com.iktaun.Contemporary_Construction.contemporaryconstruction;
import com.mojang.blaze3d.font.GlyphProvider;
import com.mojang.blaze3d.font.TrueTypeGlyphProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import org.lwjgl.stb.STBTTFontinfo;
import org.lwjgl.stb.STBTruetype;
import org.lwjgl.system.MemoryUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 路牌专用字体注册表。
 * 支持两类字体：
 *  - 内置（BUILTIN）：打包在 mod jar 内，路径 assets/contemporaryconstruction/fonts/
 *  - 自定义（CUSTOM）：玩家放在 config/contemporaryconstruction/fonts/
 * 同名时两者都保留，UI 上会区分显示。
 */
public class CustomFontRegistry {
    private static final Logger LOGGER = LoggerFactory.getLogger("CustomFontRegistry");
    private static final Path CUSTOM_FONT_DIR = Paths.get(
            Minecraft.getInstance().gameDirectory.getPath(),
            "config", "contemporaryconstruction", "fonts"
    );

    public enum Source { BUILTIN, CUSTOM }

    public static class FontEntry {
        public final String name;
        public final ResourceLocation fontId;
        public final GlyphProvider provider;
        public final boolean supportsChinese;
        public final Source source;

        public FontEntry(String name, ResourceLocation fontId, GlyphProvider provider,
                         boolean supportsChinese, Source source) {
            this.name = name;
            this.fontId = fontId;
            this.provider = provider;
            this.supportsChinese = supportsChinese;
            this.source = source;
        }
    }

    private static final Map<String, FontEntry> BUILTIN_FONTS = new LinkedHashMap<>();
    private static final Map<String, FontEntry> CUSTOM_FONTS = new LinkedHashMap<>();

    // ============================================================
    // 加载入口
    // ============================================================
    public static void loadAll() {
        // 释放旧的
        for (FontEntry e : BUILTIN_FONTS.values()) try { e.provider.close(); } catch (Exception ignored) {}
        for (FontEntry e : CUSTOM_FONTS.values())  try { e.provider.close(); } catch (Exception ignored) {}
        BUILTIN_FONTS.clear();
        CUSTOM_FONTS.clear();

        // 先内置、后外部
        loadBuiltinFonts();
        loadCustomFonts();

        LOGGER.info("[CustomFontRegistry] 内置 {} 个, 自定义 {} 个",
                BUILTIN_FONTS.size(), CUSTOM_FONTS.size());
    }

    // ============================================================
    // 内置字体（来自 mod 资源包）
    // ============================================================
    private static void loadBuiltinFonts() {
        var rm = Minecraft.getInstance().getResourceManager();
        Map<ResourceLocation, Resource> resources;
        try {
            // 扫描所有 namespace 为 our mod、路径以 .ttf 结尾、位于 fonts/ 目录下的资源
            resources = rm.listResources("fonts", loc -> {
                if (!loc.getNamespace().equals(contemporaryconstruction.MOD_ID)) return false;
                String path = loc.getPath().toLowerCase();
                return path.endsWith(".ttf") || path.endsWith(".otf")||path.endsWith(".ttc");
            });
        } catch (Exception e) {
            LOGGER.error("[CustomFontRegistry] 扫描内置字体目录失败", e);
            return;
        }

        for (var entry : resources.entrySet()) {
            ResourceLocation loc = entry.getKey();   // 形如 contemporaryconstruction:fonts/xxx.ttf
            String path = loc.getPath();             // fonts/xxx.ttf
            String fileName = path.substring("fonts/".length());
            String name = stripExt(fileName);
            String safeName = sanitize(name);
            ResourceLocation fontId = new ResourceLocation(
                    contemporaryconstruction.MOD_ID, "font/builtin_" + safeName);

            try (InputStream is = entry.getValue().open()) {
                byte[] bytes = is.readAllBytes();
                FontEntry fe = createEntry(name, fontId, bytes, Source.BUILTIN);
                if (fe != null) {
                    BUILTIN_FONTS.put(name, fe);
                    LOGGER.info("[CustomFontRegistry] 内置字体: {} → {} (中文: {})",
                            name, fontId, fe.supportsChinese ? "是" : "否");
                }
            } catch (Exception e) {
                LOGGER.error("[CustomFontRegistry] 加载内置字体失败: {}", loc, e);
            }
        }
    }

    // ============================================================
    // 外部自定义字体（config 目录）
    // ============================================================
    private static void loadCustomFonts() {
        try {
            Files.createDirectories(CUSTOM_FONT_DIR);
            try (var stream = Files.list(CUSTOM_FONT_DIR)) {
                stream.filter(CustomFontRegistry::isFontFile)
                        .forEach(CustomFontRegistry::registerCustom);
            }
        } catch (IOException e) {
            LOGGER.error("[CustomFontRegistry] 扫描自定义字体目录失败", e);
        }
    }

    private static void registerCustom(Path path) {
        try {
            String fileName = path.getFileName().toString();
            String name = stripExt(fileName);
            String safeName = sanitize(name);
            ResourceLocation fontId = new ResourceLocation(
                    contemporaryconstruction.MOD_ID, "font/custom_" + safeName);

            byte[] bytes = Files.readAllBytes(path);
            FontEntry fe = createEntry(name, fontId, bytes, Source.CUSTOM);
            if (fe != null) {
                CUSTOM_FONTS.put(name, fe);
                LOGGER.info("[CustomFontRegistry] 自定义字体: {} → {} (中文: {})",
                        name, fontId, fe.supportsChinese ? "是" : "否");
            }
        } catch (Exception e) {
            LOGGER.error("[CustomFontRegistry] 加载自定义字体失败: {}", path.getFileName(), e);
        }
    }

    // ============================================================
    // 通用：从字节流创建 FontEntry
    // ============================================================
    @Nullable
    private static FontEntry createEntry(String name, ResourceLocation fontId,
                                         byte[] bytes, Source source) {
        ByteBuffer buffer = MemoryUtil.memAlloc(bytes.length);
        buffer.put(bytes);
        buffer.flip();

        STBTTFontinfo info = STBTTFontinfo.malloc();
        if (!STBTruetype.stbtt_InitFont(info, buffer)) {
            MemoryUtil.memFree(buffer);
            info.free();
            LOGGER.warn("[CustomFontRegistry] 无效的 TTF: {}", name);
            return null;
        }

        TrueTypeGlyphProvider provider = new TrueTypeGlyphProvider(
                buffer, info, 11.0f, 4.0f, 0f, 0f, ""
        );
        boolean supportsChinese = provider.getGlyph('汉') != null;
        return new FontEntry(name, fontId, provider, supportsChinese, source);
    }

    // ============================================================
    // 查询接口
    // ============================================================
    /** 合并后的显示名列表（UI 用） */
    public static List<String> getFontNames() {
        List<String> names = new ArrayList<>();
        for (FontEntry e : BUILTIN_FONTS.values()) names.add(e.name);
        for (FontEntry e : CUSTOM_FONTS.values())  names.add(e.name);
        return names;
    }

    /**
     * 按显示名查找。先在内置里找，找不到再找自定义。
     * 如果同名（内置和外部同名），优先返回内置——但 UI 里两个名字都可见，
     * 由 FontSelectorScreen 通过 source 区分显示，这里只作为兜底 API。
     */
    @Nullable
    public static FontEntry getFont(String name) {
        FontEntry fe = BUILTIN_FONTS.get(name);
        if (fe == null) fe = CUSTOM_FONTS.get(name);
        return fe;
    }

    /** 按来源+名字查找，供 FontSelectorScreen 精确使用 */
    @Nullable
    public static FontEntry getFont(String name, Source source) {
        return source == Source.BUILTIN ? BUILTIN_FONTS.get(name) : CUSTOM_FONTS.get(name);
    }

    public static List<FontEntry> getAllFonts() {
        List<FontEntry> all = new ArrayList<>();
        all.addAll(BUILTIN_FONTS.values());
        all.addAll(CUSTOM_FONTS.values());
        return all;
    }

    public static List<FontEntry> getBuiltinFonts() { return new ArrayList<>(BUILTIN_FONTS.values()); }
    public static List<FontEntry> getCustomFonts()  { return new ArrayList<>(CUSTOM_FONTS.values()); }

    // ============================================================
    // 工具
    // ============================================================
    private static boolean isFontFile(Path p) {
        String n = p.getFileName().toString().toLowerCase();
        return n.endsWith(".ttf") || n.endsWith(".otf")|| n.endsWith(".ttc");
    }

    private static String stripExt(String fileName) {
        int dot = fileName.lastIndexOf('.');
        return dot > 0 ? fileName.substring(0, dot) : fileName;
    }

    private static String sanitize(String s) {
        return s.toLowerCase().replaceAll("[^a-z0-9_\\-]", "_");
    }
}