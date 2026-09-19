package com.iktaun.Contemporary_Construction.client.gui;

import com.iktaun.Contemporary_Construction.api.IEditableWithBrush;
import com.iktaun.Contemporary_Construction.blocks.Entity.ImageLayer;
import com.iktaun.Contemporary_Construction.blocks.Entity.ShapeElementLayer;
import com.iktaun.Contemporary_Construction.blocks.Entity.SignpostText;
import com.iktaun.Contemporary_Construction.blocks.Entity.TextLayer;
import com.iktaun.Contemporary_Construction.client.gui.shape.ShapeElementManager;
import com.iktaun.Contemporary_Construction.network.ModMessages;
import com.iktaun.Contemporary_Construction.network.SignpostUpdatePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.DyeColor;

import java.util.*;

public class SignpostEditScreen extends Screen {

    private final IEditableWithBrush editable;
    private final BlockPos pos;
    private SignpostText text;
    private SignpostText initialText;
    private Set<Integer> selectedIndices = new HashSet<>();

    // 左侧图层列表
    private final List<EditBox> layerEditBoxes = new ArrayList<>();
    private int listLeft, listTop, listWidth, listHeight;
    private int itemHeight = 18;
    private int gap = 3;

    // 右侧控制区
    private int ctrlLeft, ctrlTop, ctrlWidth, ctrlHeight;
    private Button upBtn, downBtn, deleteBtn;
    private Button addTextBtn, addImageBtn, addShapeBtn;
    private Button savePresetBtn, loadPresetBtn;
    private Button glowBtn;
    private List<Button> colorButtons = new ArrayList<>();

    // 变换控件
    private EditBox offsetXBox, offsetYBox, offsetZBox;
    private EditBox rotateXBox, rotateYBox, rotationBox;
    private EditBox scaleXBox, scaleYBox;
    private Button offsetXDown, offsetXUp, offsetYDown, offsetYUp, offsetZDown, offsetZUp;
    private Button rotateXDown, rotateXUp, rotateYDown, rotateYUp, rotationDown, rotationUp;
    private Button scaleXDown, scaleXUp, scaleYDown, scaleYUp;

    private Button doneBtn, cancelBtn;

    private DyeColor selectedColor = DyeColor.WHITE;
    private boolean isUpdatingControls = false;

    // 撤销/重做系统
    private final Stack<SignpostText> undoStack = new Stack<>();
    private final Stack<SignpostText> redoStack = new Stack<>();
    private boolean isUndoRedo = false;

    // 复制/粘贴（多图层）
    private static final List<Object> clipboardLayers = new ArrayList<>();
    private static final List<Integer> clipboardTypes = new ArrayList<>(); // 0=文字, 1=图片, 2=形状

    private static final DyeColor[] COLORS = {
            DyeColor.BLACK, DyeColor.RED, DyeColor.GREEN,
            DyeColor.BLUE, DyeColor.YELLOW, DyeColor.WHITE
    };

    public SignpostEditScreen(IEditableWithBrush editable, BlockPos pos) {
        super(Component.translatable("gui.contemporaryconstruction.edit.title"));
        this.editable = editable;
        this.pos = pos;
        this.text = editable.getText();
        if (this.text.getTotalLayers() == 0) {
            this.text = this.text.addTextLayer(new TextLayer(Component.empty(), DyeColor.WHITE, false));
        }
        if (this.text.getTotalLayers() > 0) {
            selectedIndices.add(0);
        }
        this.initialText = copySignpostText(this.text);
        pushUndoState();
    }

    @Override
    protected void init() {
        super.init();

        float margin = 0.03f;
        float listRatio = 0.35f;
        float gapRatio = 0.05f;
        float ctrlRatio = 1f - listRatio - gapRatio - 2 * margin;

        int leftMargin = (int) (this.width * margin);
        int gapPx = (int) (this.width * gapRatio);

        listLeft = leftMargin;
        listWidth = (int) (this.width * listRatio);
        int titleHeight = 22;
        int statHeight = 14;
        listTop = titleHeight + statHeight + 12;
        int bottomOffset = (int) (this.height * 0.04) + 30;
        listHeight = this.height - listTop - bottomOffset;

        ctrlLeft = listLeft + listWidth + gapPx;
        ctrlWidth = (int) (this.width * ctrlRatio);
        ctrlTop = listTop;
        ctrlHeight = listHeight;

        for (EditBox box : layerEditBoxes) {
            this.removeWidget(box);
        }
        layerEditBoxes.clear();
        rebuildLayerList();

        int currentY = ctrlTop;
        int rowH = 22;
        int btnH = 18;
        int vGap = 3;

        // ---------- 第一行：添加 + 操作 ----------
        int addW = 44;
        addTextBtn = Button.builder(Component.translatable("gui.contemporaryconstruction.edit.text"), b -> addLayer(SignpostText.LayerType.TEXT))
                .bounds(ctrlLeft, currentY, addW, btnH).build();
        addImageBtn = Button.builder(Component.translatable("gui.contemporaryconstruction.edit.image"), b -> {
            Minecraft.getInstance().setScreen(new ImageFileSelectorScreen(this));
        }).bounds(ctrlLeft + addW + 3, currentY, addW, btnH).build();
        addShapeBtn = Button.builder(Component.translatable("gui.contemporaryconstruction.edit.shape"), b -> {
            ShapeElementManager.reload();
            if (ShapeElementManager.hasElements()) {
                Minecraft.getInstance().setScreen(new ShapeElementSelectorScreen(this));
            } else {
                Minecraft.getInstance().player.sendSystemMessage(
                        Component.translatable("message.contemporaryconstruction.no_shapes")
                );
            }
        }).bounds(ctrlLeft + 2 * (addW + 3), currentY, addW, btnH).build();
        this.addRenderableWidget(addTextBtn);
        this.addRenderableWidget(addImageBtn);
        this.addRenderableWidget(addShapeBtn);

        int opRight = ctrlLeft + ctrlWidth - 4;
        deleteBtn = Button.builder(Component.literal("✕"), b -> deleteLayer())
                .bounds(opRight - 36, currentY, 34, btnH).build();
        downBtn = Button.builder(Component.literal("↓"), b -> moveLayer(+1))
                .bounds(opRight - 74, currentY, 34, btnH).build();
        upBtn = Button.builder(Component.literal("↑"), b -> moveLayer(-1))
                .bounds(opRight - 112, currentY, 34, btnH).build();
        this.addRenderableWidget(upBtn);
        this.addRenderableWidget(downBtn);
        this.addRenderableWidget(deleteBtn);

        Button exportBtn = Button.builder(Component.translatable("gui.contemporaryconstruction.edit.export"), b -> {
            Minecraft.getInstance().setScreen(new TextureExportScreen(this));
        }).bounds(ctrlLeft + 3 * (addW + 3) + 4, currentY, 44, btnH).build();
        this.addRenderableWidget(exportBtn);

        currentY += rowH + vGap;

        // ---------- 第二行：预设 + 颜色 + 发光 ----------
        int presetW = 46;
        savePresetBtn = Button.builder(Component.translatable("gui.contemporaryconstruction.edit.preset.save"), b -> {
            Minecraft.getInstance().setScreen(new PresetSaveScreen(this));
        }).bounds(ctrlLeft, currentY, presetW, btnH).build();
        loadPresetBtn = Button.builder(Component.translatable("gui.contemporaryconstruction.edit.preset.load"), b -> {
            Minecraft.getInstance().setScreen(new PresetListScreen(this));
        }).bounds(ctrlLeft + presetW + 3, currentY, presetW, btnH).build();
        this.addRenderableWidget(savePresetBtn);
        this.addRenderableWidget(loadPresetBtn);

        int colorStart = ctrlLeft + 2 * (presetW + 3) + 4;
        int colorSize = 18;
        int colorGap = 2;
        colorButtons.clear();
        for (int i = 0; i < COLORS.length; i++) {
            DyeColor color = COLORS[i];
            int x = colorStart + i * (colorSize + colorGap);
            Button cb = Button.builder(
                    Component.literal("■").withStyle(s -> s.withColor(color.getTextColor())),
                    b -> { selectedColor = color; applyColorToSelected(color); }
            ).bounds(x, currentY, colorSize, btnH).build();
            this.addRenderableWidget(cb);
            colorButtons.add(cb);
        }

        int glowX = ctrlLeft + ctrlWidth - 60;
        glowBtn = Button.builder(Component.translatable("gui.contemporaryconstruction.edit.glow"), b -> toggleGlow())
                .bounds(glowX, currentY, 55, btnH).build();
        this.addRenderableWidget(glowBtn);

        currentY += rowH + vGap + 2;

        // ---------- 第三行：偏移 X / Y / Z ----------
        int thirdCols = 3;
        int colW = (ctrlWidth - (thirdCols - 1) * 6) / thirdCols;
        int labelW = 12;
        int btnW = 16;
        int editW = colW - labelW - 2 * btnW - 8;
        if (editW < 20) editW = 20;

        int xStart = ctrlLeft;
        this.addRenderableWidget(new net.minecraft.client.gui.components.StringWidget(
                xStart, currentY + 2, labelW, btnH, Component.translatable("gui.contemporaryconstruction.edit.offset_x"), this.font));
        offsetXDown = Button.builder(Component.literal("-"), b -> adjustTransform("offsetX", -1))
                .bounds(xStart + labelW + 2, currentY, btnW, btnH).build();
        offsetXBox = new EditBox(this.font, xStart + labelW + 2 + btnW + 2, currentY, editW, btnH, Component.literal("0"));
        offsetXBox.setValue("0");
        offsetXBox.setResponder(s -> applyTransformFromBox("offsetX", s));
        offsetXUp = Button.builder(Component.literal("+"), b -> adjustTransform("offsetX", 1))
                .bounds(xStart + labelW + 2 + btnW + 2 + editW + 2, currentY, btnW, btnH).build();
        this.addRenderableWidget(offsetXDown);
        this.addRenderableWidget(offsetXBox);
        this.addRenderableWidget(offsetXUp);

        int yStart = xStart + colW + 6;
        this.addRenderableWidget(new net.minecraft.client.gui.components.StringWidget(
                yStart, currentY + 2, labelW, btnH, Component.translatable("gui.contemporaryconstruction.edit.offset_y"), this.font));
        offsetYDown = Button.builder(Component.literal("-"), b -> adjustTransform("offsetY", -1))
                .bounds(yStart + labelW + 2, currentY, btnW, btnH).build();
        offsetYBox = new EditBox(this.font, yStart + labelW + 2 + btnW + 2, currentY, editW, btnH, Component.literal("0"));
        offsetYBox.setValue("0");
        offsetYBox.setResponder(s -> applyTransformFromBox("offsetY", s));
        offsetYUp = Button.builder(Component.literal("+"), b -> adjustTransform("offsetY", 1))
                .bounds(yStart + labelW + 2 + btnW + 2 + editW + 2, currentY, btnW, btnH).build();
        this.addRenderableWidget(offsetYDown);
        this.addRenderableWidget(offsetYBox);
        this.addRenderableWidget(offsetYUp);

        int zStart = yStart + colW + 6;
        this.addRenderableWidget(new net.minecraft.client.gui.components.StringWidget(
                zStart, currentY + 2, labelW, btnH, Component.translatable("gui.contemporaryconstruction.edit.offset_z"), this.font));
        offsetZDown = Button.builder(Component.literal("-"), b -> adjustTransform("offsetZ", -1))
                .bounds(zStart + labelW + 2, currentY, btnW, btnH).build();
        offsetZBox = new EditBox(this.font, zStart + labelW + 2 + btnW + 2, currentY, editW, btnH, Component.literal("0"));
        offsetZBox.setValue("0");
        offsetZBox.setResponder(s -> applyTransformFromBox("offsetZ", s));
        offsetZUp = Button.builder(Component.literal("+"), b -> adjustTransform("offsetZ", 1))
                .bounds(zStart + labelW + 2 + btnW + 2 + editW + 2, currentY, btnW, btnH).build();
        this.addRenderableWidget(offsetZDown);
        this.addRenderableWidget(offsetZBox);
        this.addRenderableWidget(offsetZUp);

        currentY += rowH + vGap;

        // ---------- 第四行：旋转 X / Y / Z ----------
        int colW4 = (ctrlWidth - 2 * 6) / 3;
        int labelW4 = 24;
        int editW4 = colW4 - labelW4 - 2 * btnW - 8;
        if (editW4 < 20) editW4 = 20;

        int row4Y = currentY;

        // RotX
        int rxStart = ctrlLeft;
        this.addRenderableWidget(new net.minecraft.client.gui.components.StringWidget(
                rxStart, row4Y + 2, labelW4, btnH, Component.translatable("gui.contemporaryconstruction.edit.rotate_x"), this.font));
        rotateXDown = Button.builder(Component.literal("-"), b -> adjustTransform("rotateX", -5))
                .bounds(rxStart + labelW4 + 2, row4Y, btnW, btnH).build();
        rotateXBox = new EditBox(this.font, rxStart + labelW4 + 2 + btnW + 2, row4Y, editW4, btnH, Component.literal("0"));
        rotateXBox.setValue("0");
        rotateXBox.setResponder(s -> applyTransformFromBox("rotateX", s));
        rotateXUp = Button.builder(Component.literal("+"), b -> adjustTransform("rotateX", 5))
                .bounds(rxStart + labelW4 + 2 + btnW + 2 + editW4 + 2, row4Y, btnW, btnH).build();
        this.addRenderableWidget(rotateXDown);
        this.addRenderableWidget(rotateXBox);
        this.addRenderableWidget(rotateXUp);

        // RotY
        int ryStart = rxStart + colW4 + 6;
        this.addRenderableWidget(new net.minecraft.client.gui.components.StringWidget(
                ryStart, row4Y + 2, 24, btnH, Component.translatable("gui.contemporaryconstruction.edit.rotate_y"), this.font));
        rotateYDown = Button.builder(Component.literal("-"), b -> adjustTransform("rotateY", -5))
                .bounds(ryStart + 26, row4Y, btnW, btnH).build();
        rotateYBox = new EditBox(this.font, ryStart + 26 + btnW + 2, row4Y, editW4, btnH, Component.literal("0"));
        rotateYBox.setValue("0");
        rotateYBox.setResponder(s -> applyTransformFromBox("rotateY", s));
        rotateYUp = Button.builder(Component.literal("+"), b -> adjustTransform("rotateY", 5))
                .bounds(ryStart + 26 + btnW + 2 + editW4 + 2, row4Y, btnW, btnH).build();
        this.addRenderableWidget(rotateYDown);
        this.addRenderableWidget(rotateYBox);
        this.addRenderableWidget(rotateYUp);

        // RotZ
        int rzStart = ryStart + colW4 + 6;
        this.addRenderableWidget(new net.minecraft.client.gui.components.StringWidget(
                rzStart, row4Y + 2, labelW4, btnH, Component.translatable("gui.contemporaryconstruction.edit.rotate_z"), this.font));
        rotationDown = Button.builder(Component.literal("-"), b -> adjustTransform("rotation", -5))
                .bounds(rzStart + labelW4 + 2, row4Y, btnW, btnH).build();
        rotationBox = new EditBox(this.font, rzStart + labelW4 + 2 + btnW + 2, row4Y, editW4, btnH, Component.literal("0"));
        rotationBox.setValue("0");
        rotationBox.setResponder(s -> applyTransformFromBox("rotation", s));
        rotationUp = Button.builder(Component.literal("+"), b -> adjustTransform("rotation", 5))
                .bounds(rzStart + labelW4 + 2 + btnW + 2 + editW4 + 2, row4Y, btnW, btnH).build();
        this.addRenderableWidget(rotationDown);
        this.addRenderableWidget(rotationBox);
        this.addRenderableWidget(rotationUp);

        currentY += rowH + vGap;

        // ---------- 第五行：缩放 X / Y ----------
        int colW5 = (ctrlWidth - 6) / 2;
        int labelW5 = 24;
        int editW5 = colW5 - labelW5 - 2 * btnW - 8;
        if (editW5 < 20) editW5 = 20;

        int row5Y = currentY;

        // SX
        int sxStart = ctrlLeft;
        this.addRenderableWidget(new net.minecraft.client.gui.components.StringWidget(
                sxStart, row5Y + 2, 24, btnH, Component.translatable("gui.contemporaryconstruction.edit.scale_x"), this.font));
        scaleXDown = Button.builder(Component.literal("-"), b -> adjustTransform("scaleX", -0.1f))
                .bounds(sxStart + 26, row5Y, btnW, btnH).build();
        scaleXBox = new EditBox(this.font, sxStart + 26 + btnW + 2, row5Y, editW5, btnH, Component.literal("1.0"));
        scaleXBox.setValue("1.0");
        scaleXBox.setResponder(s -> applyTransformFromBox("scaleX", s));
        scaleXUp = Button.builder(Component.literal("+"), b -> adjustTransform("scaleX", 0.1f))
                .bounds(sxStart + 26 + btnW + 2 + editW5 + 2, row5Y, btnW, btnH).build();
        this.addRenderableWidget(scaleXDown);
        this.addRenderableWidget(scaleXBox);
        this.addRenderableWidget(scaleXUp);

        // SY
        int syStart = sxStart + colW5 + 6;
        this.addRenderableWidget(new net.minecraft.client.gui.components.StringWidget(
                syStart, row5Y + 2, 24, btnH, Component.translatable("gui.contemporaryconstruction.edit.scale_y"), this.font));
        scaleYDown = Button.builder(Component.literal("-"), b -> adjustTransform("scaleY", -0.1f))
                .bounds(syStart + 26, row5Y, btnW, btnH).build();
        scaleYBox = new EditBox(this.font, syStart + 26 + btnW + 2, row5Y, editW5, btnH, Component.literal("1.0"));
        scaleYBox.setValue("1.0");
        scaleYBox.setResponder(s -> applyTransformFromBox("scaleY", s));
        scaleYUp = Button.builder(Component.literal("+"), b -> adjustTransform("scaleY", 0.1f))
                .bounds(syStart + 26 + btnW + 2 + editW5 + 2, row5Y, btnW, btnH).build();
        this.addRenderableWidget(scaleYDown);
        this.addRenderableWidget(scaleYBox);
        this.addRenderableWidget(scaleYUp);

        // ---------- 底部按钮 ----------
        int bottomY = this.height - (int) (this.height * 0.035) - btnH;
        doneBtn = Button.builder(CommonComponents.GUI_DONE, b -> this.onDone())
                .bounds(this.width - 120, bottomY, 100, btnH).build();
        cancelBtn = Button.builder(CommonComponents.GUI_CANCEL, b -> this.onClose())
                .bounds(this.width - 230, bottomY, 100, btnH).build();
        this.addRenderableWidget(doneBtn);
        this.addRenderableWidget(cancelBtn);

        updateControls();
    }

    // ============================================================
    // 撤销/重做系统
    // ============================================================
    private void pushUndoState() {
        if (isUndoRedo) return;
        SignpostText copy = copySignpostText(text);
        undoStack.push(copy);
        if (undoStack.size() > 50) {
            undoStack.remove(0);
        }
        redoStack.clear();
    }

    private SignpostText copySignpostText(SignpostText original) {
        List<TextLayer> textLayers = new ArrayList<>();
        for (TextLayer l : original.getTextLayers()) textLayers.add(l.copy());
        List<ImageLayer> imageLayers = new ArrayList<>();
        for (ImageLayer l : original.getImageLayers()) imageLayers.add(l.copy());
        List<ShapeElementLayer> shapeLayers = new ArrayList<>();
        for (ShapeElementLayer l : original.getShapeLayers()) shapeLayers.add(l.copy());
        return new SignpostText(textLayers, imageLayers, shapeLayers);
    }

    private void undo() {
        if (undoStack.size() <= 1) return;
        redoStack.push(copySignpostText(text));
        undoStack.pop();
        SignpostText previous = undoStack.peek();
        isUndoRedo = true;
        applySignpostText(previous);
        isUndoRedo = false;
    }

    private void redo() {
        if (redoStack.isEmpty()) return;
        undoStack.push(copySignpostText(text));
        SignpostText next = redoStack.pop();
        isUndoRedo = true;
        applySignpostText(next);
        isUndoRedo = false;
    }

    private void applySignpostText(SignpostText newText) {
        this.text = newText;
        this.editable.setText(this.text);
        selectedIndices.clear();
        if (this.text.getTotalLayers() > 0) {
            selectedIndices.add(0);
        }
        updateControls();
    }

    // ============================================================
    // 复制/粘贴（多图层）
    // ============================================================
    private void copyLayer() {
        if (selectedIndices.isEmpty()) return;
        clipboardLayers.clear();
        clipboardTypes.clear();
        List<Integer> sorted = new ArrayList<>(selectedIndices);
        sorted.sort(Comparator.naturalOrder());
        for (int idx : sorted) {
            Object layer = text.getLayer(idx);
            if (layer instanceof TextLayer) {
                clipboardLayers.add(((TextLayer) layer).copy());
                clipboardTypes.add(0);
            } else if (layer instanceof ImageLayer) {
                clipboardLayers.add(((ImageLayer) layer).copy());
                clipboardTypes.add(1);
            } else if (layer instanceof ShapeElementLayer) {
                clipboardLayers.add(((ShapeElementLayer) layer).copy());
                clipboardTypes.add(2);
            }
        }
    }

    private void pasteLayer() {
        if (clipboardLayers.isEmpty()) return;
        pushUndoState();

        int addedText = 0, addedImage = 0, addedShape = 0;
        for (int i = 0; i < clipboardLayers.size(); i++) {
            Object layer = clipboardLayers.get(i);
            int type = clipboardTypes.get(i);
            switch (type) {
                case 0:
                    text = text.addTextLayer(((TextLayer) layer).copy());
                    addedText++;
                    break;
                case 1:
                    text = text.addImageLayer(((ImageLayer) layer).copy());
                    addedImage++;
                    break;
                case 2:
                    text = text.addShapeLayer(((ShapeElementLayer) layer).copy());
                    addedShape++;
                    break;
            }
        }

        selectedIndices.clear();
        int textCount = text.getTextLayers().size();
        int imageCount = text.getImageLayers().size();
        int shapeCount = text.getShapeLayers().size();

        for (int i = textCount - addedText; i < textCount; i++) {
            selectedIndices.add(i);
        }
        for (int i = 0; i < addedImage; i++) {
            selectedIndices.add(textCount + imageCount - addedImage + i);
        }
        for (int i = 0; i < addedShape; i++) {
            selectedIndices.add(textCount + imageCount + shapeCount - addedShape + i);
        }

        editable.setText(text);
        updateControls();
        pushUndoState();
    }

    // ============================================================
    // 图层列表构建
    // ============================================================
    private void rebuildLayerList() {
        for (EditBox box : layerEditBoxes) {
            this.removeWidget(box);
        }
        layerEditBoxes.clear();

        int total = text.getTotalLayers();
        if (total == 0) {
            EditBox emptyBox = new EditBox(this.font, listLeft, listTop, listWidth, itemHeight, Component.empty());
            emptyBox.setValue(Component.translatable("gui.contemporaryconstruction.edit.empty").getString());
            emptyBox.setEditable(false);
            this.addRenderableWidget(emptyBox);
            layerEditBoxes.add(emptyBox);
            return;
        }

        int index = 0;
        int textCount = text.getTextLayers().size();

        for (int i = 0; i < textCount; i++) {
            TextLayer layer = text.getTextLayers().get(i);
            int y = listTop + index * (itemHeight + gap);
            EditBox box = new EditBox(this.font, listLeft, y, listWidth, itemHeight, Component.empty());
            box.setValue(layer.getText().getString());
            final int finalIndex = i;
            box.setResponder(newText -> {
                if (finalIndex < text.getTextLayers().size()) {
                    pushUndoState();
                    TextLayer updated = text.getTextLayers().get(finalIndex).copy();
                    updated.setText(Component.literal(newText));
                    text = text.updateTextLayer(finalIndex, updated);
                    editable.setText(text);
                    pushUndoState();
                }
            });
            if (selectedIndices.contains(index)) {
                box.setFocused(true);
                box.setTextColor(0xFFFFAA);
            } else {
                box.setTextColor(0xFFFFFF);
            }
            this.addRenderableWidget(box);
            layerEditBoxes.add(box);
            index++;
        }

        for (ImageLayer layer : text.getImageLayers()) {
            int y = listTop + index * (itemHeight + gap);
            EditBox box = new EditBox(this.font, listLeft, y, listWidth, itemHeight, Component.empty());
            box.setValue(Component.translatable("gui.contemporaryconstruction.edit.image").getString() + ": " + layer.getWidth() + "×" + layer.getHeight());
            box.setEditable(false);
            if (selectedIndices.contains(index)) {
                box.setTextColor(0xFFFFAA);
            } else {
                box.setTextColor(0xFFFFFF);
            }
            this.addRenderableWidget(box);
            layerEditBoxes.add(box);
            index++;
        }

        for (ShapeElementLayer layer : text.getShapeLayers()) {
            int y = listTop + index * (itemHeight + gap);
            EditBox box = new EditBox(this.font, listLeft, y, listWidth, itemHeight, Component.empty());
            box.setValue(Component.translatable("gui.contemporaryconstruction.edit.shape").getString() + ": " + layer.getName() + " " + layer.getWidth() + "×" + layer.getHeight());
            box.setEditable(false);
            if (selectedIndices.contains(index)) {
                box.setTextColor(0xFFFFAA);
            } else {
                box.setTextColor(0xFFFFFF);
            }
            this.addRenderableWidget(box);
            layerEditBoxes.add(box);
            index++;
        }
    }

    // ============================================================
    // 控件更新
    // ============================================================
    private void updateControls() {
        updateButtons();
        updateTransformControls();
        updateGlowButton();
        rebuildLayerList();
    }

    private void updateButtons() {
        if (deleteBtn == null || upBtn == null || downBtn == null) return;
        int total = text.getTotalLayers();
        boolean hasSelected = !selectedIndices.isEmpty();
        deleteBtn.active = hasSelected && !(selectedIndices.size() == 1 && text.getTextLayers().size() == 1 && selectedIndices.contains(0));
        upBtn.active = selectedIndices.size() == 1 && selectedIndices.iterator().next() > 0;
        downBtn.active = selectedIndices.size() == 1 && selectedIndices.iterator().next() < total - 1;
    }

    private void updateGlowButton() {
        if (glowBtn == null) return;
        boolean anyText = false;
        boolean allSame = true;
        Boolean firstGlow = null;
        for (int idx : selectedIndices) {
            Object layer = text.getLayer(idx);
            if (layer instanceof TextLayer) {
                anyText = true;
                boolean glow = ((TextLayer) layer).isGlowing();
                if (firstGlow == null) {
                    firstGlow = glow;
                } else if (firstGlow != glow) {
                    allSame = false;
                }
            }
        }
        if (anyText && allSame && firstGlow != null) {
            glowBtn.setMessage(Component.translatable(firstGlow ? "gui.contemporaryconstruction.edit.glow" : "gui.contemporaryconstruction.edit.no_glow"));
            glowBtn.active = true;
        } else {
            glowBtn.setMessage(Component.translatable("gui.contemporaryconstruction.edit.glow"));
            glowBtn.active = false;
        }
    }

    private void toggleGlow() {
        if (selectedIndices.isEmpty()) return;
        pushUndoState();
        boolean allGlow = true;
        boolean first = true;
        for (int idx : selectedIndices) {
            Object layer = text.getLayer(idx);
            if (layer instanceof TextLayer) {
                if (first) {
                    allGlow = ((TextLayer) layer).isGlowing();
                    first = false;
                } else {
                    if (allGlow != ((TextLayer) layer).isGlowing()) {
                        allGlow = false;
                    }
                }
            }
        }
        boolean newGlow = !allGlow;
        for (int idx : selectedIndices) {
            Object layer = text.getLayer(idx);
            if (layer instanceof TextLayer) {
                TextLayer copy = ((TextLayer) layer).copy();
                copy.setGlowing(newGlow);
                text = text.updateTextLayer(idx, copy);
            }
        }
        editable.setText(text);
        updateControls();
        pushUndoState();
    }

    private void applyColorToSelected(DyeColor color) {
        if (selectedIndices.isEmpty()) return;
        pushUndoState();
        for (int idx : selectedIndices) {
            Object layer = text.getLayer(idx);
            if (layer instanceof TextLayer) {
                TextLayer copy = ((TextLayer) layer).copy();
                copy.setColor(color);
                text = text.updateTextLayer(idx, copy);
            }
        }
        editable.setText(text);
        updateControls();
        pushUndoState();
    }

    // ============================================================
    // 图层操作
    // ============================================================
    private void addLayer(SignpostText.LayerType type) {
        if (type == SignpostText.LayerType.TEXT) {
            pushUndoState();
            // 新图层位置全部为 0，不自动偏移
            text = text.addTextLayer(new TextLayer(Component.empty(), DyeColor.WHITE, false,
                    0, 0, 0, 0, 0, 0, 1.0f, 1.0f));
            editable.setText(text);
            selectedIndices.clear();
            selectedIndices.add(text.getTotalLayers() - 1);
            updateControls();
            pushUndoState();
        }
    }

    private void deleteLayer() {
        if (selectedIndices.isEmpty()) return;
        if (selectedIndices.size() == 1 && text.getTextLayers().size() == 1 && selectedIndices.contains(0)) {
            return;
        }
        pushUndoState();
        List<Integer> sorted = new ArrayList<>(selectedIndices);
        sorted.sort(Collections.reverseOrder());
        for (int idx : sorted) {
            SignpostText.LayerType type = text.getLayerType(idx);
            if (type == SignpostText.LayerType.TEXT) {
                if (text.getTextLayers().size() <= 1) continue;
                text = text.removeTextLayer(idx);
            } else if (type == SignpostText.LayerType.IMAGE) {
                int textCount = text.getTextLayers().size();
                text = text.removeImageLayer(idx - textCount);
            } else {
                int textCount = text.getTextLayers().size();
                int imageCount = text.getImageLayers().size();
                text = text.removeShapeLayer(idx - textCount - imageCount);
            }
        }
        selectedIndices.clear();
        if (text.getTotalLayers() > 0) {
            selectedIndices.add(text.getTotalLayers() - 1);
        }
        editable.setText(text);
        updateControls();
        pushUndoState();
    }

    private void moveLayer(int direction) {
        if (selectedIndices.size() != 1) return;
        int idx = selectedIndices.iterator().next();
        int newIdx = idx + direction;
        if (newIdx < 0 || newIdx >= text.getTotalLayers()) return;
        SignpostText.LayerType type = text.getLayerType(idx);
        if (type == SignpostText.LayerType.TEXT) {
            int textCount = text.getTextLayers().size();
            if (newIdx < textCount) {
                pushUndoState();
                text = text.moveTextLayer(idx, newIdx);
                selectedIndices.clear();
                selectedIndices.add(newIdx);
                editable.setText(text);
                updateControls();
                pushUndoState();
            }
        }
    }

    // ============================================================
    // 键盘移动图层（含 Z 轴）
    // ============================================================
    private void moveSelectedLayer(float dx, float dy, float dz) {
        if (selectedIndices.isEmpty()) return;
        pushUndoState();
        for (int idx : selectedIndices) {
            Object layer = text.getLayer(idx);
            int textCount = text.getTextLayers().size();
            int imageCount = text.getImageLayers().size();
            if (idx < textCount) {
                TextLayer copy = ((TextLayer) layer).copy();
                copy.move(dx, dy);
                copy.setOffsetZ(copy.getOffsetZ() + dz);
                text = text.updateTextLayer(idx, copy);
            } else if (idx < textCount + imageCount) {
                int imgIdx = idx - textCount;
                ImageLayer copy = ((ImageLayer) layer).copy();
                copy.move(dx, dy);
                copy.setOffsetZ(copy.getOffsetZ() + dz);
                text = text.updateImageLayer(imgIdx, copy);
            } else {
                int shapeIdx = idx - textCount - imageCount;
                ShapeElementLayer copy = ((ShapeElementLayer) layer).copy();
                copy.move(dx, dy);
                copy.setOffsetZ(copy.getOffsetZ() + dz);
                text = text.updateShapeLayer(shapeIdx, copy);
            }
        }
        editable.setText(text);
        updateTransformControls();
        pushUndoState();
    }

    // ============================================================
    // 公共方法
    // ============================================================
    public SignpostText getText() {
        return text;
    }

    public void setText(SignpostText newText) {
        this.text = newText;
        this.editable.setText(this.text);
        selectedIndices.clear();
        if (this.text.getTotalLayers() > 0) {
            selectedIndices.add(0);
        }
        updateControls();
    }

    // ============================================================
    // 鼠标点击（支持 Ctrl 多选）
    // ============================================================
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (int i = 0; i < layerEditBoxes.size(); i++) {
            EditBox box = layerEditBoxes.get(i);
            if (box.isMouseOver(mouseX, mouseY)) {
                if (hasControlDown()) {
                    if (selectedIndices.contains(i)) {
                        selectedIndices.remove(i);
                    } else {
                        selectedIndices.add(i);
                    }
                } else {
                    selectedIndices.clear();
                    selectedIndices.add(i);
                }
                updateControls();
                break;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    // ============================================================
    // 键盘事件
    // ============================================================
    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        int total = text.getTotalLayers();

        if (keyCode == 90 && hasControlDown()) {
            undo();
            return true;
        }
        if (keyCode == 89 && hasControlDown()) {
            redo();
            return true;
        }
        if (keyCode == 67 && hasControlDown()) {
            copyLayer();
            return true;
        }
        if (keyCode == 86 && hasControlDown()) {
            pasteLayer();
            return true;
        }
        if (keyCode == 261) {
            deleteLayer();
            return true;
        }

        if (selectedIndices.size() == 1) {
            int idx = selectedIndices.iterator().next();
            if (keyCode == 265 && idx > 0) {
                selectedIndices.clear();
                selectedIndices.add(idx - 1);
                updateControls();
                return true;
            }
            if (keyCode == 264 && idx < total - 1) {
                selectedIndices.clear();
                selectedIndices.add(idx + 1);
                updateControls();
                return true;
            }
        }

        if (!selectedIndices.isEmpty()) {
            double step = 1.0;
            if (hasControlDown()) step = 0.1;
            else if (hasShiftDown()) step = 0.25;

            float dx = 0, dy = 0, dz = 0;
            if (keyCode == 263) dx = (float) -step;
            else if (keyCode == 262) dx = (float) step;
            else if (keyCode == 265) dy = (float) -step;
            else if (keyCode == 264) dy = (float) step;
            else if (keyCode == 73) dz = (float) -step;
            else if (keyCode == 79) dz = (float) step;
            else return super.keyPressed(keyCode, scanCode, modifiers);

            moveSelectedLayer(dx, dy, dz);
            updateTransformControls();
            return true;
        }

        if (keyCode == 257 && hasControlDown()) {
            addLayer(SignpostText.LayerType.TEXT);
            return true;
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void tick() {
        if (!this.isValid()) this.onDone();
    }

    private boolean isValid() {
        return this.minecraft != null &&
                this.minecraft.player != null &&
                !this.editable.asBlockEntity().isRemoved() &&
                !this.editable.playerIsTooFarAwayToEdit(this.minecraft.player.getUUID());
    }

    // ============================================================
    // 取消/完成
    // ============================================================
    @Override
    public void onClose() {
        if (this.initialText != null) {
            this.text = copySignpostText(this.initialText);
            this.editable.setText(this.text);
        }
        undoStack.clear();
        redoStack.clear();
        super.onClose();
    }

    private void onDone() {
        undoStack.clear();
        redoStack.clear();
        ModMessages.CHANNEL.sendToServer(new SignpostUpdatePacket(this.pos, this.text));
        this.minecraft.setScreen(null);
    }

    // ============================================================
    // 渲染（透明背景）
    // ============================================================
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        // 完全透明
    }

    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, int partialTick) {
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 6, 0xFFFFFF);

        int firstIdx = selectedIndices.isEmpty() ? 0 : selectedIndices.iterator().next();
        String info = Component.translatable("gui.contemporaryconstruction.edit.info",
                selectedIndices.isEmpty() ? 0 : firstIdx + 1,
                text.getTotalLayers(),
                text.getTextLayers().size(),
                text.getImageLayers().size(),
                text.getShapeLayers().size()).getString();
        guiGraphics.drawString(this.font, Component.literal(info), listLeft, listTop - 12, 0xAAAAAA, false);

        if (selectedIndices.size() > 1) {
            guiGraphics.drawString(this.font,
                    Component.literal("已选中 " + selectedIndices.size() + " 个图层"),
                    listLeft, listTop - 28, 0xFFFFAA, false);
        }

        guiGraphics.fill(ctrlLeft - 4, ctrlTop, ctrlLeft - 2, ctrlTop + ctrlHeight, 0x44FFFFFF);

        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    // ================================================================
    // 变换控制
    // ================================================================

    private void updateTransformControls() {
        if (isUpdatingControls) return;
        isUpdatingControls = true;
        try {
            if (selectedIndices.isEmpty()) {
                offsetXBox.setValue("0");
                offsetYBox.setValue("0");
                offsetZBox.setValue("0");
                rotateXBox.setValue("0");
                rotateYBox.setValue("0");
                rotationBox.setValue("0");
                scaleXBox.setValue("1.0");
                scaleYBox.setValue("1.0");
                setTransformBoxesEnabled(false);
                return;
            }

            boolean isMultiSelect = selectedIndices.size() > 1;
            setTransformBoxesEnabled(!isMultiSelect);

            int first = selectedIndices.iterator().next();
            Object layer = text.getLayer(first);
            if (layer instanceof TextLayer tl) {
                offsetXBox.setValue(fmt(tl.getOffsetX()));
                offsetYBox.setValue(fmt(tl.getOffsetY()));
                offsetZBox.setValue(fmt(tl.getOffsetZ()));
                rotateXBox.setValue(fmt(tl.getRotateX()));
                rotateYBox.setValue(fmt(tl.getRotateY()));
                rotationBox.setValue(fmt(tl.getRotateZ()));
                scaleXBox.setValue(fmt(tl.getScaleX()));
                scaleYBox.setValue(fmt(tl.getScaleY()));
            } else if (layer instanceof ImageLayer il) {
                offsetXBox.setValue(fmt(il.getOffsetX()));
                offsetYBox.setValue(fmt(il.getOffsetY()));
                offsetZBox.setValue(fmt(il.getOffsetZ()));
                rotateXBox.setValue(fmt(il.getRotateX()));
                rotateYBox.setValue(fmt(il.getRotateY()));
                rotationBox.setValue(fmt(il.getRotateZ()));
                scaleXBox.setValue(fmt(il.getScaleX()));
                scaleYBox.setValue(fmt(il.getScaleY()));
            } else if (layer instanceof ShapeElementLayer sel) {
                offsetXBox.setValue(fmt(sel.getOffsetX()));
                offsetYBox.setValue(fmt(sel.getOffsetY()));
                offsetZBox.setValue(fmt(sel.getOffsetZ()));
                rotateXBox.setValue(fmt(sel.getRotateX()));
                rotateYBox.setValue(fmt(sel.getRotateY()));
                rotationBox.setValue(fmt(sel.getRotateZ()));
                scaleXBox.setValue(fmt(sel.getScaleX()));
                scaleYBox.setValue(fmt(sel.getScaleY()));
            }
        } finally {
            isUpdatingControls = false;
        }
    }

    private void setTransformBoxesEnabled(boolean enabled) {
        if (offsetXBox != null) offsetXBox.setEditable(enabled);
        if (offsetYBox != null) offsetYBox.setEditable(enabled);
        if (offsetZBox != null) offsetZBox.setEditable(enabled);
        if (rotateXBox != null) rotateXBox.setEditable(enabled);
        if (rotateYBox != null) rotateYBox.setEditable(enabled);
        if (rotationBox != null) rotationBox.setEditable(enabled);
        if (scaleXBox != null) scaleXBox.setEditable(enabled);
        if (scaleYBox != null) scaleYBox.setEditable(enabled);
    }

    private void applyTransformFromBox(String type, String value) {
        if (selectedIndices.isEmpty()) return;
        // 多选时禁用输入框的直接输入，避免统一覆盖
        if (selectedIndices.size() > 1) return;
        try {
            float val = Float.parseFloat(value);
            if (type.equals("scaleX") || type.equals("scaleY")) {
                val = clamp(val, 0.1f, 3f);
            } else if (type.equals("rotation") || type.equals("rotateY") || type.equals("rotateX")) {
                val = normRot(val);
            }
            applyTransformToLayerAbsolute(type, val);
        } catch (NumberFormatException ignored) {}
    }

    private void adjustTransform(String type, float delta) {
        if (selectedIndices.isEmpty()) return;
        applyTransformToLayerDelta(type, delta);
    }

    /**
     * 绝对值模式：单选时直接设置为该值
     */
    private void applyTransformToLayerAbsolute(String type, float val) {
        if (selectedIndices.isEmpty()) return;
        pushUndoState();
        for (int idx : selectedIndices) {
            Object layer = text.getLayer(idx);
            int tc = text.getTextLayers().size();
            int ic = text.getImageLayers().size();
            switch (type) {
                case "offsetX":
                    if (layer instanceof TextLayer l) {
                        TextLayer c = l.copy(); c.setOffsetX(val); text = text.updateTextLayer(idx, c);
                    } else if (layer instanceof ImageLayer l) {
                        ImageLayer c = l.copy(); c.setOffsetX(val); text = text.updateImageLayer(idx - tc, c);
                    } else if (layer instanceof ShapeElementLayer l) {
                        ShapeElementLayer c = l.copy(); c.setOffsetX(val); text = text.updateShapeLayer(idx - tc - ic, c);
                    }
                    break;
                case "offsetY":
                    if (layer instanceof TextLayer l) {
                        TextLayer c = l.copy(); c.setOffsetY(val); text = text.updateTextLayer(idx, c);
                    } else if (layer instanceof ImageLayer l) {
                        ImageLayer c = l.copy(); c.setOffsetY(val); text = text.updateImageLayer(idx - tc, c);
                    } else if (layer instanceof ShapeElementLayer l) {
                        ShapeElementLayer c = l.copy(); c.setOffsetY(val); text = text.updateShapeLayer(idx - tc - ic, c);
                    }
                    break;
                case "offsetZ":
                    if (layer instanceof TextLayer l) {
                        TextLayer c = l.copy(); c.setOffsetZ(val); text = text.updateTextLayer(idx, c);
                    } else if (layer instanceof ImageLayer l) {
                        ImageLayer c = l.copy(); c.setOffsetZ(val); text = text.updateImageLayer(idx - tc, c);
                    } else if (layer instanceof ShapeElementLayer l) {
                        ShapeElementLayer c = l.copy(); c.setOffsetZ(val); text = text.updateShapeLayer(idx - tc - ic, c);
                    }
                    break;
                case "rotateX":
                    if (layer instanceof TextLayer l) {
                        TextLayer c = l.copy(); c.setRotateX(val); text = text.updateTextLayer(idx, c);
                    } else if (layer instanceof ImageLayer l) {
                        ImageLayer c = l.copy(); c.setRotateX(val); text = text.updateImageLayer(idx - tc, c);
                    } else if (layer instanceof ShapeElementLayer l) {
                        ShapeElementLayer c = l.copy(); c.setRotateX(val); text = text.updateShapeLayer(idx - tc - ic, c);
                    }
                    break;
                case "rotateY":
                    if (layer instanceof TextLayer l) {
                        TextLayer c = l.copy(); c.setRotateY(val); text = text.updateTextLayer(idx, c);
                    } else if (layer instanceof ImageLayer l) {
                        ImageLayer c = l.copy(); c.setRotateY(val); text = text.updateImageLayer(idx - tc, c);
                    } else if (layer instanceof ShapeElementLayer l) {
                        ShapeElementLayer c = l.copy(); c.setRotateY(val); text = text.updateShapeLayer(idx - tc - ic, c);
                    }
                    break;
                case "rotation":
                    if (layer instanceof TextLayer l) {
                        TextLayer c = l.copy(); c.setRotateZ(val); text = text.updateTextLayer(idx, c);
                    } else if (layer instanceof ImageLayer l) {
                        ImageLayer c = l.copy(); c.setRotateZ(val); text = text.updateImageLayer(idx - tc, c);
                    } else if (layer instanceof ShapeElementLayer l) {
                        ShapeElementLayer c = l.copy(); c.setRotateZ(val); text = text.updateShapeLayer(idx - tc - ic, c);
                    }
                    break;
                case "scaleX":
                    if (layer instanceof TextLayer l) {
                        TextLayer c = l.copy(); c.setScaleX(val); text = text.updateTextLayer(idx, c);
                    } else if (layer instanceof ImageLayer l) {
                        ImageLayer c = l.copy(); c.setScaleX(val); text = text.updateImageLayer(idx - tc, c);
                    } else if (layer instanceof ShapeElementLayer l) {
                        ShapeElementLayer c = l.copy(); c.setScaleX(val); text = text.updateShapeLayer(idx - tc - ic, c);
                    }
                    break;
                case "scaleY":
                    if (layer instanceof TextLayer l) {
                        TextLayer c = l.copy(); c.setScaleY(val); text = text.updateTextLayer(idx, c);
                    } else if (layer instanceof ImageLayer l) {
                        ImageLayer c = l.copy(); c.setScaleY(val); text = text.updateImageLayer(idx - tc, c);
                    } else if (layer instanceof ShapeElementLayer l) {
                        ShapeElementLayer c = l.copy(); c.setScaleY(val); text = text.updateShapeLayer(idx - tc - ic, c);
                    }
                    break;
            }
        }
        editable.setText(text);
        updateTransformControls();
        pushUndoState();
    }

    /**
     * 增量模式：每个图层在各自当前值上增加 delta
     */
    private void applyTransformToLayerDelta(String type, float delta) {
        if (selectedIndices.isEmpty()) return;
        pushUndoState();
        for (int idx : selectedIndices) {
            Object layer = text.getLayer(idx);
            int tc = text.getTextLayers().size();
            int ic = text.getImageLayers().size();
            float currentVal = 0;
            if (layer instanceof TextLayer l) {
                switch (type) {
                    case "offsetX": currentVal = l.getOffsetX(); break;
                    case "offsetY": currentVal = l.getOffsetY(); break;
                    case "offsetZ": currentVal = l.getOffsetZ(); break;
                    case "rotateX": currentVal = l.getRotateX(); break;
                    case "rotateY": currentVal = l.getRotateY(); break;
                    case "rotation": currentVal = l.getRotateZ(); break;
                    case "scaleX": currentVal = l.getScaleX(); break;
                    case "scaleY": currentVal = l.getScaleY(); break;
                }
            } else if (layer instanceof ImageLayer l) {
                switch (type) {
                    case "offsetX": currentVal = l.getOffsetX(); break;
                    case "offsetY": currentVal = l.getOffsetY(); break;
                    case "offsetZ": currentVal = l.getOffsetZ(); break;
                    case "rotateX": currentVal = l.getRotateX(); break;
                    case "rotateY": currentVal = l.getRotateY(); break;
                    case "rotation": currentVal = l.getRotateZ(); break;
                    case "scaleX": currentVal = l.getScaleX(); break;
                    case "scaleY": currentVal = l.getScaleY(); break;
                }
            } else if (layer instanceof ShapeElementLayer l) {
                switch (type) {
                    case "offsetX": currentVal = l.getOffsetX(); break;
                    case "offsetY": currentVal = l.getOffsetY(); break;
                    case "offsetZ": currentVal = l.getOffsetZ(); break;
                    case "rotateX": currentVal = l.getRotateX(); break;
                    case "rotateY": currentVal = l.getRotateY(); break;
                    case "rotation": currentVal = l.getRotateZ(); break;
                    case "scaleX": currentVal = l.getScaleX(); break;
                    case "scaleY": currentVal = l.getScaleY(); break;
                }
            }
            float newVal = currentVal + delta;
            if (type.equals("scaleX") || type.equals("scaleY")) {
                newVal = clamp(newVal, 0.1f, 3f);
            } else if (type.equals("rotation") || type.equals("rotateY") || type.equals("rotateX")) {
                newVal = normRot(newVal);
            }
            switch (type) {
                case "offsetX":
                    if (layer instanceof TextLayer l) {
                        TextLayer c = l.copy(); c.setOffsetX(newVal); text = text.updateTextLayer(idx, c);
                    } else if (layer instanceof ImageLayer l) {
                        ImageLayer c = l.copy(); c.setOffsetX(newVal); text = text.updateImageLayer(idx - tc, c);
                    } else if (layer instanceof ShapeElementLayer l) {
                        ShapeElementLayer c = l.copy(); c.setOffsetX(newVal); text = text.updateShapeLayer(idx - tc - ic, c);
                    }
                    break;
                case "offsetY":
                    if (layer instanceof TextLayer l) {
                        TextLayer c = l.copy(); c.setOffsetY(newVal); text = text.updateTextLayer(idx, c);
                    } else if (layer instanceof ImageLayer l) {
                        ImageLayer c = l.copy(); c.setOffsetY(newVal); text = text.updateImageLayer(idx - tc, c);
                    } else if (layer instanceof ShapeElementLayer l) {
                        ShapeElementLayer c = l.copy(); c.setOffsetY(newVal); text = text.updateShapeLayer(idx - tc - ic, c);
                    }
                    break;
                case "offsetZ":
                    if (layer instanceof TextLayer l) {
                        TextLayer c = l.copy(); c.setOffsetZ(newVal); text = text.updateTextLayer(idx, c);
                    } else if (layer instanceof ImageLayer l) {
                        ImageLayer c = l.copy(); c.setOffsetZ(newVal); text = text.updateImageLayer(idx - tc, c);
                    } else if (layer instanceof ShapeElementLayer l) {
                        ShapeElementLayer c = l.copy(); c.setOffsetZ(newVal); text = text.updateShapeLayer(idx - tc - ic, c);
                    }
                    break;
                case "rotateX":
                    if (layer instanceof TextLayer l) {
                        TextLayer c = l.copy(); c.setRotateX(newVal); text = text.updateTextLayer(idx, c);
                    } else if (layer instanceof ImageLayer l) {
                        ImageLayer c = l.copy(); c.setRotateX(newVal); text = text.updateImageLayer(idx - tc, c);
                    } else if (layer instanceof ShapeElementLayer l) {
                        ShapeElementLayer c = l.copy(); c.setRotateX(newVal); text = text.updateShapeLayer(idx - tc - ic, c);
                    }
                    break;
                case "rotateY":
                    if (layer instanceof TextLayer l) {
                        TextLayer c = l.copy(); c.setRotateY(newVal); text = text.updateTextLayer(idx, c);
                    } else if (layer instanceof ImageLayer l) {
                        ImageLayer c = l.copy(); c.setRotateY(newVal); text = text.updateImageLayer(idx - tc, c);
                    } else if (layer instanceof ShapeElementLayer l) {
                        ShapeElementLayer c = l.copy(); c.setRotateY(newVal); text = text.updateShapeLayer(idx - tc - ic, c);
                    }
                    break;
                case "rotation":
                    if (layer instanceof TextLayer l) {
                        TextLayer c = l.copy(); c.setRotateZ(newVal); text = text.updateTextLayer(idx, c);
                    } else if (layer instanceof ImageLayer l) {
                        ImageLayer c = l.copy(); c.setRotateZ(newVal); text = text.updateImageLayer(idx - tc, c);
                    } else if (layer instanceof ShapeElementLayer l) {
                        ShapeElementLayer c = l.copy(); c.setRotateZ(newVal); text = text.updateShapeLayer(idx - tc - ic, c);
                    }
                    break;
                case "scaleX":
                    if (layer instanceof TextLayer l) {
                        TextLayer c = l.copy(); c.setScaleX(newVal); text = text.updateTextLayer(idx, c);
                    } else if (layer instanceof ImageLayer l) {
                        ImageLayer c = l.copy(); c.setScaleX(newVal); text = text.updateImageLayer(idx - tc, c);
                    } else if (layer instanceof ShapeElementLayer l) {
                        ShapeElementLayer c = l.copy(); c.setScaleX(newVal); text = text.updateShapeLayer(idx - tc - ic, c);
                    }
                    break;
                case "scaleY":
                    if (layer instanceof TextLayer l) {
                        TextLayer c = l.copy(); c.setScaleY(newVal); text = text.updateTextLayer(idx, c);
                    } else if (layer instanceof ImageLayer l) {
                        ImageLayer c = l.copy(); c.setScaleY(newVal); text = text.updateImageLayer(idx - tc, c);
                    } else if (layer instanceof ShapeElementLayer l) {
                        ShapeElementLayer c = l.copy(); c.setScaleY(newVal); text = text.updateShapeLayer(idx - tc - ic, c);
                    }
                    break;
            }
        }
        editable.setText(text);
        updateTransformControls();
        pushUndoState();
    }

    // ===== 辅助方法 =====
    private float clamp(float v, float min, float max) {
        return Math.max(min, Math.min(max, v));
    }
    private float normRot(float v) {
        v = ((v % 360) + 360) % 360;
        return v > 180 ? v - 360 : v;
    }
    private String fmt(float v) {
        return v == (int) v ? Integer.toString((int) v) : String.format("%.2f", v);
    }
}