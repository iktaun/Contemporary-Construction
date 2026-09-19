package com.iktaun.Contemporary_Construction.client.gui;

import com.iktaun.Contemporary_Construction.blocks.Entity.ShapeElementLayer;
import com.iktaun.Contemporary_Construction.blocks.Entity.SignpostText;
import com.iktaun.Contemporary_Construction.client.gui.shape.ShapeElementData;
import com.iktaun.Contemporary_Construction.client.gui.shape.ShapeElementManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.DyeColor;

import java.util.List;

public class ShapeElementSelectorScreen extends Screen {

    private final SignpostEditScreen parent;
    private final List<ShapeElementData> elements;
    private ShapeElementData selectedElement = null;
    private EditBox widthBox, heightBox;
    private DyeColor selectedColor = DyeColor.WHITE;

    private static final DyeColor[] COLORS = {
            DyeColor.WHITE, DyeColor.RED, DyeColor.GREEN,
            DyeColor.BLUE, DyeColor.YELLOW, DyeColor.ORANGE,
            DyeColor.PURPLE, DyeColor.PINK, DyeColor.CYAN
    };
    private static final int ITEMS_PER_ROW = 5;
    private static final int ITEM_SIZE = 50;
    private static final int GAP = 8;

    public ShapeElementSelectorScreen(SignpostEditScreen parent) {
        super(Component.translatable("gui.contemporaryconstruction.shape.title"));
        this.parent = parent;
        ShapeElementManager.reload();
        this.elements = ShapeElementManager.getAllElements();
    }

    @Override
    protected void init() {
        int cx = this.width / 2;

        this.addRenderableWidget(new net.minecraft.client.gui.components.StringWidget(
                20, 20, 40, 20, Component.translatable("gui.contemporaryconstruction.shape.width"), this.font));
        widthBox = new EditBox(this.font, 60, 18, 50, 20, Component.literal(""));
        widthBox.setValue("32");
        this.addRenderableWidget(widthBox);

        this.addRenderableWidget(new net.minecraft.client.gui.components.StringWidget(
                130, 20, 40, 20, Component.translatable("gui.contemporaryconstruction.shape.height"), this.font));
        heightBox = new EditBox(this.font, 170, 18, 50, 20, Component.literal(""));
        heightBox.setValue("32");
        this.addRenderableWidget(heightBox);

        for (int i = 0; i < COLORS.length; i++) {
            DyeColor color = COLORS[i];
            int x = 240 + i * 28;
            Button btn = Button.builder(
                    Component.literal("■").withStyle(s -> s.withColor(color.getTextColor())),
                    b -> selectedColor = color
            ).bounds(x, 18, 24, 20).build();
            this.addRenderableWidget(btn);
        }

        Button cancelBtn = Button.builder(Component.translatable("gui.contemporaryconstruction.shape.cancel"), b -> {
            Minecraft.getInstance().setScreen(parent);
        }).bounds(this.width - 160, this.height - 30, 70, 20).build();
        this.addRenderableWidget(cancelBtn);

        Button addBtn = Button.builder(Component.translatable("gui.contemporaryconstruction.shape.add"), b -> {
            if (selectedElement != null) addShape();
        }).bounds(this.width - 85, this.height - 30, 70, 20).build();
        this.addRenderableWidget(addBtn);

        if (elements.isEmpty()) {
            Button hintBtn = Button.builder(
                    Component.translatable("gui.contemporaryconstruction.shape.no_shapes"),
                    b -> {}
            ).bounds(20, 50, 300, 20).build();
            hintBtn.active = false;
            this.addRenderableWidget(hintBtn);
        }
    }

    private void addShape() {
        if (selectedElement == null) return;

        int w, h;
        try {
            w = Integer.parseInt(widthBox.getValue().trim());
            h = Integer.parseInt(heightBox.getValue().trim());
            if (w <= 0 || h <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            w = 32;
            h = 32;
        }

        ShapeElementLayer layer = new ShapeElementLayer(
                selectedElement.name,
                selectedElement.texture,
                w, h,
                0, 0, 1.0f,
                selectedColor,
                false, true
        );

        SignpostText current = parent.getText();
        current = current.addShapeLayer(layer);
        parent.setText(current);
        Minecraft.getInstance().setScreen(parent);
    }

    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {}

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 20, 0xFFFFFF);

        guiGraphics.drawString(this.font,
                Component.translatable("gui.contemporaryconstruction.shape.current_color").withStyle(s -> s.withColor(selectedColor.getTextColor())),
                20, 48, 0xFFFFFF, false);

        int startX = 20;
        int startY = 75;
        int cols = ITEMS_PER_ROW;

        for (int i = 0; i < elements.size(); i++) {
            ShapeElementData data = elements.get(i);
            int row = i / cols;
            int col = i % cols;
            int x = startX + col * (ITEM_SIZE + GAP);
            int y = startY + row * (ITEM_SIZE + GAP + 16);

            boolean isSelected = data == selectedElement;
            int borderColor = isSelected ? 0xFFFFAA00 : 0xFF666666;
            guiGraphics.fill(x - 2, y - 2, x + ITEM_SIZE + 2, y + ITEM_SIZE + 2, borderColor);
            guiGraphics.fill(x, y, x + ITEM_SIZE, y + ITEM_SIZE, 0xFF333333);

            guiGraphics.drawString(this.font,
                    Component.literal(data.getDisplayName()),
                    x, y + ITEM_SIZE + 2, 0xAAAAAA, false);

            String mark = data.source == ShapeElementData.Source.BUILTIN ? "★" : "📁";
            guiGraphics.drawString(this.font, Component.literal(mark),
                    x + ITEM_SIZE - 12, y + 2, 0xFFFFFF, false);
        }

        if (selectedElement != null) {
            String info = "选中: " + selectedElement.getDisplayName() +
                    " (" + selectedElement.width + "x" + selectedElement.height + ")";
            guiGraphics.drawString(this.font, Component.literal(info),
                    20, this.height - 55, 0xCCCCCC, false);
        }

        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int startX = 20;
        int startY = 75;
        int cols = ITEMS_PER_ROW;

        for (int i = 0; i < elements.size(); i++) {
            int row = i / cols;
            int col = i % cols;
            int x = startX + col * (ITEM_SIZE + GAP);
            int y = startY + row * (ITEM_SIZE + GAP + 16);

            if (mouseX >= x && mouseX <= x + ITEM_SIZE &&
                    mouseY >= y && mouseY <= y + ITEM_SIZE) {
                selectedElement = elements.get(i);
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}