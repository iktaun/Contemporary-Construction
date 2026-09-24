package com.iktaun.Contemporary_Construction.mixin;

import com.iktaun.Contemporary_Construction.font.CustomFontRegistry;
import com.mojang.blaze3d.font.GlyphProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.font.FontManager;
import net.minecraft.client.gui.font.FontSet;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 把自定义字体注册为独立的 FontSet，供路牌通过 withFont 引用。
 * 不修改默认字体集，所以游戏其他部分保持原版字体。
 */
@Mixin(FontManager.class)
public abstract class FontManagerMixin {
    private static final Logger LOGGER = LoggerFactory.getLogger("FontManagerMixin");

    @Shadow private Map<ResourceLocation, FontSet> fontSets;
    @Shadow private TextureManager textureManager;

    @Inject(
            method = "apply(Lnet/minecraft/client/gui/font/FontManager$Preparation;Lnet/minecraft/util/profiling/ProfilerFiller;)V",
            at = @At("TAIL")
    )
    private void onApplyTail(CallbackInfo ci) {
        LOGGER.info("[FontManagerMixin] 开始注册路牌专用字体");

        // 1. 加载所有自定义 TTF
        CustomFontRegistry.loadAll();

        // 2. 收集默认字体集的 provider，作为 fallback
        List<GlyphProvider> fallbackProviders = new ArrayList<>();
        FontSet defaultSet = this.fontSets.get(Minecraft.DEFAULT_FONT);
        if (defaultSet != null) {
            fallbackProviders.addAll(((FontSetAccessor) defaultSet).getProviders());
        }
        LOGGER.info("[FontManagerMixin] 默认字体 provider 数量: {}", fallbackProviders.size());

        // 3. 每个自定义字体 → 独立 FontSet
        for (CustomFontRegistry.FontEntry entry : CustomFontRegistry.getAllFonts()) {
            try {
                List<GlyphProvider> providers = new ArrayList<>();
                providers.add(entry.provider);        // 自定义优先
                providers.addAll(fallbackProviders);  // 缺字 fallback

                FontSet fontSet = new FontSet(this.textureManager, entry.fontId);
                fontSet.reload(providers);
                this.fontSets.put(entry.fontId, fontSet);

                LOGGER.info("[FontManagerMixin] 已注册字体集: {} (中文: {})",
                        entry.fontId, entry.supportsChinese ? "是" : "否");
            } catch (Exception e) {
                LOGGER.error("[FontManagerMixin] 注册字体失败: {}", entry.name, e);
            }
        }

        LOGGER.info("[FontManagerMixin] 完成，共 {} 个自定义字体",
                CustomFontRegistry.getAllFonts().size());
        // ⚠️ 不注入默认字体集，游戏其他部分保持原版
    }
}