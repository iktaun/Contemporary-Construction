package com.iktaun.Contemporary_Construction.client.gui;

import com.iktaun.Contemporary_Construction.blocks.Entity.SignpostText;
import com.iktaun.Contemporary_Construction.util.TextureCompositor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TextureExportScreen extends Screen {

    private final SignpostEditScreen parent;
    private final SignpostText text;
    private EditBox widthBox, heightBox, fileNameBox;
    private Button formatPngBtn, formatJpgBtn;
    private Button exportBtn, cancelBtn;
    private Button previewBtn;

    private int selectedWidth = 256;
    private int selectedHeight = 256;
    private String selectedFormat = "png";
    private BufferedImage previewImage = null;
    private boolean previewDirty = true;

    public TextureExportScreen(SignpostEditScreen parent) {
        super(Component.translatable("gui.contemporaryconstruction.export.title"));
        this.parent = parent;
        this.text = parent.getText();
    }

    @Override
    protected void init() {
        super.init();

        int centerX = this.width / 2;
        int centerY = this.height / 2;

        this.addRenderableWidget(new net.minecraft.client.gui.components.StringWidget(
                centerX - 80, centerY - 100, 160, 20,
                Component.translatable("gui.contemporaryconstruction.export.settings"), this.font));

        this.addRenderableWidget(new net.minecraft.client.gui.components.StringWidget(
                centerX - 100, centerY - 70, 40, 20,
                Component.translatable("gui.contemporaryconstruction.export.width"), this.font));
        widthBox = new EditBox(this.font, centerX - 60, centerY - 70, 60, 20, Component.literal(""));
        widthBox.setValue("256");
        widthBox.setResponder(s -> {
            try {
                selectedWidth = Integer.parseInt(s);
                previewDirty = true;
            } catch (NumberFormatException ignored) {}
        });
        this.addRenderableWidget(widthBox);

        this.addRenderableWidget(new net.minecraft.client.gui.components.StringWidget(
                centerX + 20, centerY - 70, 40, 20,
                Component.translatable("gui.contemporaryconstruction.export.height"), this.font));
        heightBox = new EditBox(this.font, centerX + 60, centerY - 70, 60, 20, Component.literal(""));
        heightBox.setValue("256");
        heightBox.setResponder(s -> {
            try {
                selectedHeight = Integer.parseInt(s);
                previewDirty = true;
            } catch (NumberFormatException ignored) {}
        });
        this.addRenderableWidget(heightBox);

        formatPngBtn = Button.builder(Component.translatable("gui.contemporaryconstruction.export.format_png"), b -> {
            selectedFormat = "png";
            previewDirty = true;
        }).bounds(centerX - 60, centerY - 40, 50, 20).build();
        formatJpgBtn = Button.builder(Component.translatable("gui.contemporaryconstruction.export.format_jpg"), b -> {
            selectedFormat = "jpg";
            previewDirty = true;
        }).bounds(centerX + 10, centerY - 40, 50, 20).build();
        this.addRenderableWidget(formatPngBtn);
        this.addRenderableWidget(formatJpgBtn);

        this.addRenderableWidget(new net.minecraft.client.gui.components.StringWidget(
                centerX - 100, centerY - 10, 40, 20,
                Component.translatable("gui.contemporaryconstruction.export.filename"), this.font));
        fileNameBox = new EditBox(this.font, centerX - 60, centerY - 10, 120, 20, Component.literal(""));
        fileNameBox.setValue(generateDefaultFileName());
        this.addRenderableWidget(fileNameBox);

        previewBtn = Button.builder(Component.translatable("gui.contemporaryconstruction.export.preview"), b -> generatePreview())
                .bounds(centerX - 60, centerY + 20, 50, 20).build();
        this.addRenderableWidget(previewBtn);

        exportBtn = Button.builder(Component.translatable("gui.contemporaryconstruction.export.export"), b -> doExport())
                .bounds(centerX + 20, centerY + 20, 50, 20).build();
        this.addRenderableWidget(exportBtn);

        cancelBtn = Button.builder(Component.translatable("gui.contemporaryconstruction.export.cancel"), b -> {
            Minecraft.getInstance().setScreen(parent);
        }).bounds(centerX - 100, centerY + 20, 50, 20).build();
        this.addRenderableWidget(cancelBtn);
    }

    private String generateDefaultFileName() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        return "路牌_" + timestamp;
    }

    private void generatePreview() {
        try {
            TextureCompositor compositor = new TextureCompositor(text, selectedWidth, selectedHeight);
            previewImage = compositor.compose();
            previewDirty = false;
        } catch (Exception e) {
            e.printStackTrace();
            previewImage = null;
        }
    }

    private void doExport() {
        if (previewDirty) generatePreview();
        if (previewImage == null) {
            Minecraft.getInstance().player.sendSystemMessage(Component.translatable("gui.contemporaryconstruction.export.preview_failed"));
            return;
        }
        String fileName = fileNameBox.getValue().trim();
        if (fileName.isEmpty()) fileName = generateDefaultFileName();
        File dir = new File(Minecraft.getInstance().gameDirectory, "config/contemporaryconstruction/exports");
        if (!dir.exists()) dir.mkdirs();
        File file = new File(dir, fileName + "." + selectedFormat);
        try {
            ImageIO.write(previewImage, selectedFormat, file);
            Minecraft.getInstance().player.sendSystemMessage(Component.translatable("gui.contemporaryconstruction.export.success", file.getAbsolutePath()));
            Minecraft.getInstance().setScreen(parent);
        } catch (IOException e) {
            e.printStackTrace();
            Minecraft.getInstance().player.sendSystemMessage(Component.translatable("gui.contemporaryconstruction.export.failed", e.getMessage()));
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        int previewSize = 128;
        int x = this.width - previewSize - 20;
        int y = this.height - previewSize - 20;
        guiGraphics.fill(x - 2, y - 2, x + previewSize + 2, y + previewSize + 2, 0xFFFFFFFF);

        if (previewImage != null) {
            guiGraphics.fill(x, y, x + previewSize, y + previewSize, 0xFF8888FF);
            guiGraphics.drawCenteredString(this.font, Component.translatable("gui.contemporaryconstruction.export.preview_hint"), x + previewSize/2, y + previewSize/2 - 4, 0xFFFFFF);
        } else {
            guiGraphics.fill(x, y, x + previewSize, y + previewSize, 0xFF333333);
            guiGraphics.drawCenteredString(this.font, Component.translatable("gui.contemporaryconstruction.export.preview_hint"), x + previewSize/2, y + previewSize/2 - 4, 0xCCCCCC);
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}