package com.iktaun.Contemporary_Construction.client.gui;

import com.iktaun.Contemporary_Construction.blocks.Entity.SignpostText;
import com.iktaun.Contemporary_Construction.preset.PresetData;
import com.iktaun.Contemporary_Construction.preset.PresetManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PresetListScreen extends Screen {

    private final SignpostEditScreen parent;
    private final List<String> presetNames = new ArrayList<>();
    private String selectedPreset = null;

    private int listLeft, listTop, listWidth, listHeight;
    private int itemHeight = 20;
    private int gap = 3;

    private Button loadBtn, deleteBtn, cancelBtn;
    private EditBox searchBox;

    public PresetListScreen(SignpostEditScreen parent) {
        super(Component.translatable("gui.contemporaryconstruction.preset.title"));
        this.parent = parent;
        refreshList();
    }

    private void refreshList() {
        presetNames.clear();
        presetNames.addAll(PresetManager.getPresetNames());
        presetNames.sort(String::compareToIgnoreCase);
        if (selectedPreset != null && !presetNames.contains(selectedPreset)) {
            selectedPreset = null;
        }
    }

    @Override
    protected void init() {
        super.init();

        int margin = 20;
        int topMargin = 50;
        int bottomMargin = 60;

        listLeft = margin;
        listTop = topMargin;
        listWidth = this.width - 2 * margin;
        listHeight = this.height - topMargin - bottomMargin;

        searchBox = new EditBox(this.font, listLeft, listTop - 24, 150, 18, Component.translatable("gui.contemporaryconstruction.preset.search"));
        searchBox.setResponder(s -> {
            refreshList();
        });
        this.addRenderableWidget(searchBox);

        int btnY = this.height - bottomMargin + 10;
        int btnW = 70;
        int btnH = 20;

        loadBtn = Button.builder(Component.translatable("gui.contemporaryconstruction.preset.load"), b -> loadPreset())
                .bounds(this.width - btnW - 10 - 80 - 10, btnY, btnW, btnH).build();
        deleteBtn = Button.builder(Component.translatable("gui.contemporaryconstruction.preset.delete"), b -> deletePreset())
                .bounds(this.width - btnW - 10, btnY, btnW, btnH).build();
        cancelBtn = Button.builder(Component.translatable("gui.contemporaryconstruction.preset.cancel"), b -> {
            Minecraft.getInstance().setScreen(parent);
        }).bounds(10, btnY, 70, btnH).build();

        this.addRenderableWidget(loadBtn);
        this.addRenderableWidget(deleteBtn);
        this.addRenderableWidget(cancelBtn);

        updateButtons();
    }

    private void updateButtons() {
        boolean hasSelected = selectedPreset != null && presetNames.contains(selectedPreset);
        loadBtn.active = hasSelected;
        deleteBtn.active = hasSelected;
    }

    private void loadPreset() {
        if (selectedPreset == null) return;
        PresetData data = PresetManager.loadPreset(selectedPreset);
        if (data == null) return;

        SignpostText newText = data.toSignpostText();
        parent.setText(newText);
        Minecraft.getInstance().setScreen(parent);
    }

    private void deletePreset() {
        if (selectedPreset == null) return;
        if (PresetManager.deletePreset(selectedPreset)) {
            refreshList();
            selectedPreset = null;
            updateButtons();
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int y = listTop;
        for (String name : presetNames) {
            if (mouseX >= listLeft && mouseX <= listLeft + listWidth &&
                    mouseY >= y && mouseY <= y + itemHeight) {
                selectedPreset = name;
                updateButtons();
                return true;
            }
            y += itemHeight + gap;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics);
        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 18, 0xFFFFFF);

        if (presetNames.isEmpty()) {
            guiGraphics.drawCenteredString(this.font,
                    Component.translatable("gui.contemporaryconstruction.preset.empty"),
                    this.width / 2, this.height / 2 - 4, 0x666666);
            super.render(guiGraphics, mouseX, mouseY, partialTick);
            return;
        }

        int y = listTop;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        for (String name : presetNames) {
            boolean isSelected = name.equals(selectedPreset);
            int bgColor = isSelected ? 0x44FFFFFF : 0x22000000;
            guiGraphics.fill(listLeft, y, listLeft + listWidth, y + itemHeight, bgColor);
            guiGraphics.drawString(this.font, Component.literal(name),
                    listLeft + 4, y + 4, isSelected ? 0xFFFFFF : 0xCCCCCC, false);

            long time = PresetManager.getPresetModifiedTime(name);
            if (time > 0) {
                String timeStr = sdf.format(new Date(time));
                guiGraphics.drawString(this.font, Component.literal(timeStr),
                        listLeft + listWidth - 150, y + 4, 0x666666, false);
            }

            if (isSelected) {
                guiGraphics.fill(listLeft, y, listLeft + 2, y + itemHeight, 0xFFFFAA00);
            }

            y += itemHeight + gap;
        }

        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}