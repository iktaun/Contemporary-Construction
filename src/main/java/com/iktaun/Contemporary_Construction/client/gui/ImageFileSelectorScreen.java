package com.iktaun.Contemporary_Construction.client.gui;

import com.iktaun.Contemporary_Construction.blocks.Entity.ImageLayer;
import com.iktaun.Contemporary_Construction.blocks.Entity.SignpostText;
import com.iktaun.Contemporary_Construction.util.ImageLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ImageFileSelectorScreen extends Screen {

    private final SignpostEditScreen parent;
    private final List<String> imageFiles = new ArrayList<>();
    private static final Path IMAGE_DIR = Paths.get(Minecraft.getInstance().gameDirectory.getPath(),
            "config", "contemporaryconstruction", "images");

    private final Map<String, ImageLoader.ImageResult> cache = new HashMap<>();
    private String selectedFile = null;
    private ImageLoader.ImageResult selectedResult = null;

    private EditBox scaleBox;
    private float selectedScale = 1.0f;

    private static final int BUTTON_HEIGHT = 20;
    private static final int LIST_WIDTH = 160;
    private static final int GAP = 6;

    private int listLeft, listTop, listHeight;
    private int previewLeft, previewTop, previewWidth, previewHeight;
    private int controlY;

    private final List<Button> fileButtons = new ArrayList<>();

    public ImageFileSelectorScreen(SignpostEditScreen parent) {
        super(Component.translatable("gui.contemporaryconstruction.image.title"));
        this.parent = parent;
        try {
            Files.createDirectories(IMAGE_DIR);
        } catch (IOException ignored) {}
        refreshFileList();
    }

    private void refreshFileList() {
        imageFiles.clear();
        try (var stream = Files.list(IMAGE_DIR)) {
            imageFiles.addAll(stream
                    .filter(p -> {
                        String name = p.getFileName().toString().toLowerCase();
                        return name.endsWith(".png") || name.endsWith(".jpg") || name.endsWith(".jpeg");
                    })
                    .map(Path::toString)
                    .collect(Collectors.toList()));
        } catch (IOException ignored) {}
        if (imageFiles.isEmpty()) imageFiles.add("");
        cache.keySet().removeIf(key -> !imageFiles.contains(key));
        if (selectedFile != null && !imageFiles.contains(selectedFile)) {
            selectedFile = null;
            selectedResult = null;
        }
    }

    @Override
    protected void init() {
        super.init();

        int topMargin = 40;
        int bottomMargin = 65;

        listLeft = 10;
        listTop = topMargin;
        listHeight = this.height - topMargin - bottomMargin;

        previewLeft = listLeft + LIST_WIDTH + 15;
        previewTop = topMargin;
        previewWidth = this.width - previewLeft - 15;
        previewHeight = this.height - topMargin - bottomMargin;

        controlY = this.height - bottomMargin + 5;

        for (Button btn : fileButtons) this.removeWidget(btn);
        fileButtons.clear();

        int y = listTop;
        for (String path : imageFiles) {
            if (path.isEmpty()) {
                Button btn = Button.builder(Component.translatable("gui.contemporaryconstruction.image.no_images"), b -> {})
                        .bounds(listLeft, y, LIST_WIDTH, BUTTON_HEIGHT).build();
                btn.active = false;
                this.addRenderableWidget(btn);
                fileButtons.add(btn);
                y += BUTTON_HEIGHT + GAP;
                continue;
            }

            String fileName = new File(path).getName();
            Button fileBtn = Button.builder(Component.literal(fileName), b -> selectFile(path))
                    .bounds(listLeft, y, LIST_WIDTH, BUTTON_HEIGHT).build();
            this.addRenderableWidget(fileBtn);
            fileButtons.add(fileBtn);
            y += BUTTON_HEIGHT + GAP;
        }

        this.addRenderableWidget(new net.minecraft.client.gui.components.StringWidget(
                previewLeft, controlY + 2, 36, BUTTON_HEIGHT,
                Component.translatable("gui.contemporaryconstruction.image.scale"), this.font));

        int boxX = previewLeft + 38;
        scaleBox = new EditBox(this.font, boxX, controlY, 50, BUTTON_HEIGHT, Component.literal(""));
        scaleBox.setValue("1.0");
        scaleBox.setResponder(s -> {
            try {
                selectedScale = Float.parseFloat(s);
                if (selectedScale <= 0) selectedScale = 0.1f;
                if (selectedScale > 10) selectedScale = 10f;
            } catch (NumberFormatException e) {
                selectedScale = 1.0f;
            }
        });
        this.addRenderableWidget(scaleBox);

        int quickX = boxX + 55;
        Button scaleHalfBtn = Button.builder(Component.literal("0.5"), b -> scaleBox.setValue("0.5"))
                .bounds(quickX, controlY, 32, BUTTON_HEIGHT).build();
        Button scaleOneBtn = Button.builder(Component.literal("1"), b -> scaleBox.setValue("1.0"))
                .bounds(quickX + 34, controlY, 28, BUTTON_HEIGHT).build();
        Button scaleTwoBtn = Button.builder(Component.literal("2"), b -> scaleBox.setValue("2.0"))
                .bounds(quickX + 64, controlY, 28, BUTTON_HEIGHT).build();
        this.addRenderableWidget(scaleHalfBtn);
        this.addRenderableWidget(scaleOneBtn);
        this.addRenderableWidget(scaleTwoBtn);

        int btnWidth = 70;
        int btnGap = 4;

        Button cancelBtn = Button.builder(Component.translatable("gui.contemporaryconstruction.image.cancel"), b -> {
            Minecraft.getInstance().setScreen(parent);
        }).bounds(this.width - btnWidth * 2 - btnGap - 10, controlY, btnWidth, BUTTON_HEIGHT).build();
        this.addRenderableWidget(cancelBtn);

        Button addBtn = Button.builder(Component.translatable("gui.contemporaryconstruction.image.add"), b -> {
            if (selectedResult != null) addSelectedImage();
        }).bounds(this.width - btnWidth - 10, controlY, btnWidth, BUTTON_HEIGHT).build();
        this.addRenderableWidget(addBtn);

        if (this.width < 480) {
            btnWidth = 60;
            cancelBtn.setWidth(btnWidth);
            addBtn.setWidth(btnWidth);
            cancelBtn.setX(this.width - btnWidth * 2 - btnGap - 8);
            addBtn.setX(this.width - btnWidth - 8);
        }
    }

    private void selectFile(String path) {
        selectedFile = path;
        if (cache.containsKey(path)) {
            selectedResult = cache.get(path);
            return;
        }
        try {
            selectedResult = ImageLoader.loadTextureFromFile(new File(path));
            cache.put(path, selectedResult);
        } catch (IOException e) {
            selectedResult = null;
            e.printStackTrace();
        }
    }

    private void addSelectedImage() {
        if (selectedResult == null) return;
        int scaledWidth = (int) (selectedResult.width * selectedScale);
        int scaledHeight = (int) (selectedResult.height * selectedScale);
        ImageLayer layer = new ImageLayer(selectedResult.texture, scaledWidth, scaledHeight, 0, 0, 1.0f, true);
        SignpostText current = parent.getText();
        current = current.addImageLayer(layer);
        parent.setText(current);
        Minecraft.getInstance().setScreen(parent);
    }

    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {}

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 18, 0xFFFFFF);

        guiGraphics.fill(previewLeft - 1, previewTop - 1,
                previewLeft + previewWidth + 1, previewTop + previewHeight + 1, 0xFF666666);
        guiGraphics.fill(previewLeft, previewTop,
                previewLeft + previewWidth, previewTop + previewHeight, 0xFF1A1A1A);

        if (selectedResult != null) {
            ResourceLocation texture = selectedResult.texture;
            int imgW = selectedResult.width;
            int imgH = selectedResult.height;
            float scaleX = (float) previewWidth / imgW;
            float scaleY = (float) previewHeight / imgH;
            float displayScale = Math.min(scaleX, scaleY) * 0.85f;
            int displayW = (int) (imgW * displayScale);
            int displayH = (int) (imgH * displayScale);
            int offsetX = (previewWidth - displayW) / 2;
            int offsetY = (previewHeight - displayH) / 2;

            guiGraphics.blit(texture, previewLeft + offsetX, previewTop + offsetY,
                    0, 0, displayW, displayH, displayW, displayH);

            String info = String.format(Component.translatable("gui.contemporaryconstruction.image.preview_info").getString(),
                    imgW, imgH, (int)(imgW * selectedScale), (int)(imgH * selectedScale), selectedScale);
            guiGraphics.drawString(this.font, Component.literal(info),
                    previewLeft + 6, previewTop + previewHeight - 14, 0xCCCCCC, false);
        } else {
            guiGraphics.drawCenteredString(this.font,
                    Component.translatable("gui.contemporaryconstruction.image.select_hint"),
                    previewLeft + previewWidth / 2, previewTop + previewHeight / 2 - 4, 0x666666);
        }

        guiGraphics.fill(listLeft + LIST_WIDTH + 10, listTop,
                listLeft + LIST_WIDTH + 12, listTop + listHeight, 0xFF444444);
        guiGraphics.drawString(this.font, Component.translatable("gui.contemporaryconstruction.image.file_list"),
                listLeft, listTop - 12, 0xAAAAAA, false);

        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}