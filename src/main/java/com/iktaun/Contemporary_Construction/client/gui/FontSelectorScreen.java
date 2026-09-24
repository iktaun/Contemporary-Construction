package com.iktaun.Contemporary_Construction.client.gui;

import com.iktaun.Contemporary_Construction.font.CustomFontRegistry;
import com.iktaun.Contemporary_Construction.font.CustomFontRegistry.Source;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class FontSelectorScreen extends Screen {

    private final Screen parent;
    private final Consumer<ResourceLocation> onSelected;
    private final List<CustomFontRegistry.FontEntry> entries = new ArrayList<>();
    private int selectedIndex = -1;

    private int listLeft, listTop, listWidth;
    private static final int ITEM_HEIGHT = 22;
    private static final int GAP = 3;

    public FontSelectorScreen(Screen parent, Consumer<ResourceLocation> onSelected) {
        super(Component.translatable("gui.contemporaryconstruction.font.title"));
        this.parent = parent;
        this.onSelected = onSelected;
        // 内置在前，自定义在后
        entries.addAll(CustomFontRegistry.getBuiltinFonts());
        entries.addAll(CustomFontRegistry.getCustomFonts());
    }

    @Override
    protected void init() {
        listLeft = 20;
        listTop = 50;
        listWidth = this.width - 40;

        // 原版字体按钮
        Button vanillaBtn = Button.builder(
                Component.translatable("gui.contemporaryconstruction.font.vanilla"),
                b -> {
                    if (onSelected != null) onSelected.accept(null);
                    Minecraft.getInstance().setScreen(parent);
                }
        ).bounds(this.width / 2 - 100, 26, 200, 20).build();
        this.addRenderableWidget(vanillaBtn);

        Button cancelBtn = Button.builder(
                Component.translatable("gui.contemporaryconstruction.font.cancel"),
                b -> Minecraft.getInstance().setScreen(parent)
        ).bounds(this.width / 2 - 40, this.height - 30, 80, 20).build();
        this.addRenderableWidget(cancelBtn);
    }

    @Override
    public boolean mouseClicked(double mx, double my, int button) {
        int y = listTop;
        for (int i = 0; i < entries.size(); i++) {
            if (mx >= listLeft && mx <= listLeft + listWidth
                    && my >= y && my <= y + ITEM_HEIGHT) {
                selectedIndex = i;
                if (onSelected != null) {
                    onSelected.accept(entries.get(i).fontId);
                }
                Minecraft.getInstance().setScreen(parent);
                return true;
            }
            y += ITEM_HEIGHT + GAP;
        }
        return super.mouseClicked(mx, my, button);
    }

    @Override
    public void render(GuiGraphics g, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(g);
        g.drawCenteredString(this.font, this.title, this.width / 2, 8, 0xFFFFFF);

        if (entries.isEmpty()) {
            g.drawCenteredString(this.font,
                    Component.translatable("gui.contemporaryconstruction.font.empty"),
                    this.width / 2, this.height / 2, 0xAAAAAA);
            super.render(g, mouseX, mouseY, partialTick);
            return;
        }

        int y = listTop;
        for (int i = 0; i < entries.size(); i++) {
            CustomFontRegistry.FontEntry entry = entries.get(i);
            boolean hover = mouseX >= listLeft && mouseX <= listLeft + listWidth
                    && mouseY >= y && mouseY <= y + ITEM_HEIGHT;
            boolean selected = i == selectedIndex;

            int bg = selected ? 0x44FFAA00 : (hover ? 0x44FFFFFF : 0x22000000);
            g.fill(listLeft, y, listLeft + listWidth, y + ITEM_HEIGHT, bg);

            // 用该字体本身渲染名字，加来源标记
            String mark = entry.source == Source.BUILTIN ? "★ " : "📁 ";
            String chinese = entry.supportsChinese ? " [中]" : "";
            Component preview = Component.literal(mark + entry.name + chinese)
                    .withStyle(style -> style.withFont(entry.fontId));
            g.drawString(this.font, preview, listLeft + 8, y + 7, 0xFFFFFF, false);

            y += ITEM_HEIGHT + GAP;
        }

        super.render(g, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean isPauseScreen() { return false; }
}