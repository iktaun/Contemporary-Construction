package com.iktaun.Contemporary_Construction.client.gui;

import com.iktaun.Contemporary_Construction.blocks.Entity.SignpostText;
import com.iktaun.Contemporary_Construction.preset.PresetManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class PresetSaveScreen extends Screen {

    private final SignpostEditScreen parent;
    private final SignpostText text;
    private EditBox nameBox;
    private Button saveBtn, cancelBtn;
    private String errorMessage = null;

    public PresetSaveScreen(SignpostEditScreen parent) {
        super(Component.translatable("gui.contemporaryconstruction.preset.save.title"));
        this.parent = parent;
        this.text = parent.getText();
    }

    @Override
    protected void init() {
        super.init();

        int centerX = this.width / 2;
        int centerY = this.height / 2;

        this.addRenderableWidget(new net.minecraft.client.gui.components.StringWidget(
                centerX - 80, centerY - 60, 160, 20,
                Component.translatable("gui.contemporaryconstruction.preset.save.name"), this.font));

        nameBox = new EditBox(this.font, centerX - 80, centerY - 35, 160, 20, Component.literal(""));
        nameBox.setMaxLength(50);
        nameBox.setValue("");
        this.addRenderableWidget(nameBox);

        saveBtn = Button.builder(Component.translatable("gui.contemporaryconstruction.preset.save.save"), b -> doSave())
                .bounds(centerX + 10, centerY + 10, 70, 20).build();
        cancelBtn = Button.builder(Component.translatable("gui.contemporaryconstruction.preset.save.cancel"), b -> {
            Minecraft.getInstance().setScreen(parent);
        }).bounds(centerX - 80, centerY + 10, 70, 20).build();

        this.addRenderableWidget(saveBtn);
        this.addRenderableWidget(cancelBtn);

        setFocused(nameBox);
        nameBox.setFocused(true);
    }

    private void doSave() {
        String name = nameBox.getValue().trim();
        if (name.isEmpty()) {
            errorMessage = Component.translatable("gui.contemporaryconstruction.preset.save.empty_name").getString();
            return;
        }

        if (!name.matches("^[a-zA-Z0-9_\\-\\u4e00-\\u9fa5]+$")) {
            errorMessage = Component.translatable("gui.contemporaryconstruction.preset.save.invalid_name").getString();
            return;
        }

        if (PresetManager.presetExists(name)) {
            errorMessage = Component.translatable("gui.contemporaryconstruction.preset.save.exists").getString();
            return;
        }

        if (PresetManager.savePreset(name, text)) {
            Minecraft.getInstance().setScreen(parent);
            Minecraft.getInstance().player.sendSystemMessage(
                    Component.translatable("gui.contemporaryconstruction.preset.save.success", name)
            );
        } else {
            errorMessage = Component.translatable("gui.contemporaryconstruction.preset.save.failed").getString();
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics);
        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 20, 0xFFFFFF);

        int total = text.getTotalLayers();
        String info = Component.translatable("gui.contemporaryconstruction.preset.save.info",
                total,
                text.getTextLayers().size(),
                text.getImageLayers().size(),
                text.getShapeLayers().size()).getString();
        guiGraphics.drawCenteredString(this.font, Component.literal(info), this.width / 2, this.height / 2 - 60, 0xAAAAAA);

        if (errorMessage != null) {
            guiGraphics.drawCenteredString(this.font,
                    Component.literal("§c" + errorMessage),
                    this.width / 2, this.height / 2 + 50, 0xFFFFFF);
        }

        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 257) {
            doSave();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}