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

import java.util.*;

public class SignpostEditScreen extends Screen {

    private final IEditableWithBrush editable;
    private final BlockPos pos;
    private SignpostText text;
    private SignpostText initialText;
    private Set<Integer> selectedIndices = new HashSet<>();

    // 图层列表
    private final List<EditBox> layerEditBoxes = new ArrayList<>();
    private int listLeft, listTop, listWidth, listHeight;
    private int itemHeight = 18;
    private int gap = 3;

    // 控制区
    private int ctrlLeft, ctrlTop, ctrlWidth, ctrlHeight;
    private Button upBtn, downBtn, deleteBtn;
    private Button addTextBtn, addImageBtn, addShapeBtn;
    private Button savePresetBtn, loadPresetBtn;
    private Button glowBtn;
    private Button colorBtn;
    private Button boldBtn;
    private Button italicBtn;
    private Button fontBtn;
    private int currentColorPreview = 0xFFFFFFFF;


    // 变换控件
    private EditBox offsetXBox, offsetYBox, offsetZBox;
    private EditBox rotateXBox, rotateYBox, rotationBox;
    private EditBox scaleXBox, scaleYBox;
    private Button offsetXDown, offsetXUp, offsetYDown, offsetYUp, offsetZDown, offsetZUp;
    private Button rotateXDown, rotateXUp, rotateYDown, rotateYUp, rotationDown, rotationUp;
    private Button scaleXDown, scaleXUp, scaleYDown, scaleYUp;

    private Button doneBtn, cancelBtn;
    private boolean isUpdatingControls = false;

    // 撤销/重做
    private final Stack<SignpostText> undoStack = new Stack<>();
    private final Stack<SignpostText> redoStack = new Stack<>();
    private boolean isUndoRedo = false;

    // 剪贴板
    private static final List<Object> clipboardLayers = new ArrayList<>();
    private static final List<Integer> clipboardTypes = new ArrayList<>();

    public SignpostEditScreen(IEditableWithBrush editable, BlockPos pos) {
        super(Component.translatable("gui.contemporaryconstruction.edit.title"));
        this.editable = editable;
        this.pos = pos;
        this.text = editable.getText();
        if (this.text.getTotalLayers() == 0) {
            this.text = this.text.addTextLayer(new TextLayer(Component.empty(), 0xFFFFFFFF, false));
        }
        if (this.text.getTotalLayers() > 0) selectedIndices.add(0);
        this.initialText = copySignpostText(this.text);
        pushUndoState();
    }

    @Override
    protected void init() {
        super.init();

        // ============================================================
        // 布局参数（注意：局部变量名不要和成员字段同名）
        // ============================================================
        int marginLeft = 12;
        int listW = (int) (this.width * 0.32f);              // ← 局部变量 listW
        int contentLeftPx = marginLeft + listW + 12;
        int contentWidthPx = this.width - contentLeftPx - marginLeft;
        int contentCenterX = contentLeftPx + contentWidthPx / 2;

        int btnH = 18;
        int rowGap = 4;
        int topY = 32;

        // ---- 左侧图层列表（用 this. 显式赋值给字段）----
        this.listLeft = marginLeft;
        this.listWidth = listW;                              // ← 用 this. + 局部变量 listW
        this.listTop = topY + 24;
        int bottomOffset = (3 + 1) * (btnH + rowGap) + 20;   // 底部 3 行 + 完成按钮 + 留白
        this.listHeight = this.height - this.listTop - bottomOffset;

        // ---- 右侧控制区（用 this. 显式赋值给字段）----
        this.ctrlLeft = contentLeftPx;
        this.ctrlWidth = contentWidthPx;
        this.ctrlTop = topY;

        // 清理旧控件
        for (EditBox box : layerEditBoxes) this.removeWidget(box);
        layerEditBoxes.clear();
        rebuildLayerList();

        // ============================================================
        // 【上部-第1行】图层列表操作：添加 + 列表操作 + 导出
        // ============================================================
        int y = topY;
        int addW = 56;

        addTextBtn = Button.builder(
                        Component.translatable("gui.contemporaryconstruction.edit.text"),
                        b -> addLayer(SignpostText.LayerType.TEXT))
                .bounds(this.ctrlLeft, y, addW, btnH).build();
        addImageBtn = Button.builder(
                        Component.translatable("gui.contemporaryconstruction.edit.image"),
                        b -> Minecraft.getInstance().setScreen(new ImageFileSelectorScreen(this)))
                .bounds(this.ctrlLeft + addW + 4, y, addW, btnH).build();
        addShapeBtn = Button.builder(
                Component.translatable("gui.contemporaryconstruction.edit.shape"),
                b -> {
                    ShapeElementManager.reload();
                    if (ShapeElementManager.hasElements()) {
                        Minecraft.getInstance().setScreen(new ShapeElementSelectorScreen(this));
                    } else if (Minecraft.getInstance().player != null) {
                        Minecraft.getInstance().player.sendSystemMessage(
                                Component.translatable("message.contemporaryconstruction.no_shapes"));
                    }
                }).bounds(this.ctrlLeft + 2 * (addW + 4), y, addW, btnH).build();
        this.addRenderableWidget(addTextBtn);
        this.addRenderableWidget(addImageBtn);
        this.addRenderableWidget(addShapeBtn);

        // 列表操作组（中）
        int opW = 28;
        int opStartX = this.ctrlLeft + 3 * (addW + 4) + 8;
        upBtn = Button.builder(Component.literal("↑"), b -> moveLayer(-1))
                .bounds(opStartX, y, opW, btnH).build();
        downBtn = Button.builder(Component.literal("↓"), b -> moveLayer(+1))
                .bounds(opStartX + opW + 4, y, opW, btnH).build();
        deleteBtn = Button.builder(Component.literal("✕"), b -> deleteLayer())
                .bounds(opStartX + 2 * (opW + 4), y, opW, btnH).build();
        this.addRenderableWidget(upBtn);
        this.addRenderableWidget(downBtn);
        this.addRenderableWidget(deleteBtn);

        // 导出组（右）
        Button exportBtn = Button.builder(
                        Component.translatable("gui.contemporaryconstruction.edit.export"),
                        b -> Minecraft.getInstance().setScreen(new TextureExportScreen(this)))
                .bounds(this.ctrlLeft + this.ctrlWidth - addW, y, addW, btnH).build();
        this.addRenderableWidget(exportBtn);

        // ============================================================
        // 【上部-第2行】样式工具：预设 + 颜色 + B + I + 字体 + 发光
        // ============================================================
        y += btnH + rowGap;

        int presetW = 56;
        int styleW = 28;
        int fontW = 56;
        int colorW = 56;
        int glowW = 56;

        int row2Width = presetW + 4 + presetW + 8
                + colorW + 4 + styleW + 4 + styleW + 4 + fontW + 4
                + glowW;
        int row2StartX = contentCenterX - row2Width / 2;

        int x = row2StartX;

        savePresetBtn = Button.builder(
                        Component.translatable("gui.contemporaryconstruction.edit.preset.save"),
                        b -> Minecraft.getInstance().setScreen(new PresetSaveScreen(this)))
                .bounds(x, y, presetW, btnH).build();
        x += presetW + 4;

        loadPresetBtn = Button.builder(
                        Component.translatable("gui.contemporaryconstruction.edit.preset.load"),
                        b -> Minecraft.getInstance().setScreen(new PresetListScreen(this)))
                .bounds(x, y, presetW, btnH).build();
        x += presetW + 8;

        colorBtn = Button.builder(
                        Component.translatable("gui.contemporaryconstruction.edit.color"),
                        b -> openColorPicker())
                .bounds(x, y, colorW, btnH).build();
        x += colorW + 4;

        boldBtn = Button.builder(Component.literal("§lB"), b -> toggleBold())
                .bounds(x, y, styleW, btnH).build();
        x += styleW + 4;

        italicBtn = Button.builder(Component.literal("§oI"), b -> toggleItalic())
                .bounds(x, y, styleW, btnH).build();
        x += styleW + 4;

        fontBtn = Button.builder(
                        Component.translatable("gui.contemporaryconstruction.edit.font"),
                        b -> openFontSelector())
                .bounds(x, y, fontW, btnH).build();
        x += fontW + 4;

        glowBtn = Button.builder(
                        Component.translatable("gui.contemporaryconstruction.edit.glow"),
                        b -> toggleGlow())
                .bounds(x, y, glowW, btnH).build();

        this.addRenderableWidget(savePresetBtn);
        this.addRenderableWidget(loadPresetBtn);
        this.addRenderableWidget(colorBtn);
        this.addRenderableWidget(boldBtn);
        this.addRenderableWidget(italicBtn);
        this.addRenderableWidget(fontBtn);
        this.addRenderableWidget(glowBtn);

        // ============================================================
        // 【中部】透明区 —— 什么都不放
        // ============================================================

        // ============================================================
        // 【下部】变换工具：偏移 / 旋转 / 缩放
        // ============================================================
        int bottomStartY = this.height - (3 + 1) * (btnH + rowGap) - 8;

        int labelW = 32;
        int editW = 42;
        int arrowW = 18;
        int colW = labelW + arrowW + editW + arrowW + 8;

        // ---------- 偏移 X / Y / Z ----------
        y = bottomStartY;
        int offsetRowWidth = 3 * colW + 2 * 8;
        int offsetStartX = contentCenterX - offsetRowWidth / 2;
        addTransformRow(offsetStartX, y, "X:", "offsetX", labelW, arrowW, editW, colW);
        addTransformRow(offsetStartX + colW + 8, y, "Y:", "offsetY", labelW, arrowW, editW, colW);
        addTransformRow(offsetStartX + 2 * (colW + 8), y, "Z:", "offsetZ", labelW, arrowW, editW, colW);
        y += btnH + rowGap;

        // ---------- 旋转 RX / RY / RZ ----------
        int rotRowWidth = 3 * colW + 2 * 8;
        int rotStartX = contentCenterX - rotRowWidth / 2;
        addTransformRow(rotStartX, y, "RX:", "rotateX", labelW, arrowW, editW, colW);
        addTransformRow(rotStartX + colW + 8, y, "RY:", "rotateY", labelW, arrowW, editW, colW);
        addTransformRow(rotStartX + 2 * (colW + 8), y, "RZ:", "rotation", labelW, arrowW, editW, colW);
        y += btnH + rowGap;

        // ---------- 缩放 SX / SY ----------
        int scaleRowWidth = 2 * colW + 8;
        int scaleStartX = contentCenterX - scaleRowWidth / 2;
        addTransformRow(scaleStartX, y, "SX:", "scaleX", labelW, arrowW, editW, colW);
        addTransformRow(scaleStartX + colW + 8, y, "SY:", "scaleY", labelW, arrowW, editW, colW);

        // ============================================================
        // 【底部】完成 / 取消（右下角）
        // ============================================================
        int doneY = this.height - btnH - 6;
        doneBtn = Button.builder(CommonComponents.GUI_DONE, b -> this.onDone())
                .bounds(this.width - 110, doneY, 100, btnH).build();
        cancelBtn = Button.builder(CommonComponents.GUI_CANCEL, b -> this.onClose())
                .bounds(this.width - 216, doneY, 100, btnH).build();
        this.addRenderableWidget(doneBtn);
        this.addRenderableWidget(cancelBtn);

        updateControls();
    }

// ============================================================
// 辅助方法：把三行变换控件的创建拆出去，让 init 看起来干净
// ============================================================

    /**
     * 通用变换行：标签 + [-] + 输入框 + [+]
     * 关键点：按 field 名把控件绑回成员字段，否则 updateTransformControls() 会 NPE
     */
    private void addTransformRow(int x, int y, String label, String field,
                                 int labelW, int arrowW, int editW, int colW) {
        // 标签
        this.addRenderableWidget(new net.minecraft.client.gui.components.StringWidget(
                x, y + 2, labelW, 18, Component.literal(label), this.font));

        // - 按钮
        Button minus = Button.builder(Component.literal("-"),
                        b -> adjustTransform(field, field.startsWith("scale") ? -0.1f : -1f))
                .bounds(x + labelW, y, arrowW, 18).build();
        this.addRenderableWidget(minus);

        // 输入框
        EditBox box = new EditBox(this.font,
                x + labelW + arrowW + 2, y, editW, 18, Component.literal(""));
        String defaultValue = field.startsWith("scale") ? "1.0" : "0";
        box.setValue(defaultValue);
        box.setResponder(s -> applyTransformFromBox(field, s));
        this.addRenderableWidget(box);

        // + 按钮
        Button plus = Button.builder(Component.literal("+"),
                        b -> adjustTransform(field, field.startsWith("scale") ? 0.1f : 1f))
                .bounds(x + labelW + arrowW + 2 + editW + 2, y, arrowW, 18).build();
        this.addRenderableWidget(plus);

        // ============================================================
        // ★ 按 field 名把控件绑回成员字段
        // ============================================================
        switch (field) {
            case "offsetX" -> { offsetXDown = minus; offsetXBox = box; offsetXUp = plus; }
            case "offsetY" -> { offsetYDown = minus; offsetYBox = box; offsetYUp = plus; }
            case "offsetZ" -> { offsetZDown = minus; offsetZBox = box; offsetZUp = plus; }
            case "rotateX" -> { rotateXDown = minus; rotateXBox = box; rotateXUp = plus; }
            case "rotateY" -> { rotateYDown = minus; rotateYBox = box; rotateYUp = plus; }
            case "rotation" -> { rotationDown = minus; rotationBox = box; rotationUp = plus; }
            case "scaleX" -> { scaleXDown = minus; scaleXBox = box; scaleXUp = plus; }
            case "scaleY" -> { scaleYDown = minus; scaleYBox = box; scaleYUp = plus; }
        }
    }

    // ===== 颜色选择器入口 =====
    private void openColorPicker() {
        int initialColor = currentColorPreview;
        Minecraft.getInstance().setScreen(new ColorPickerScreen(this, initialColor, this::applyColorARGB));
    }

    // ===== 应用颜色（对所有选中图层） =====
    private void applyColorARGB(int argb) {
        if (selectedIndices.isEmpty()) return;
        pushUndoState();
        for (int idx : selectedIndices) {
            Object layer = text.getLayer(idx);
            int tc = text.getTextLayers().size();
            int ic = text.getImageLayers().size();
            if (layer instanceof TextLayer l) {
                TextLayer c = l.copy();
                c.setColorARGB(argb);
                text = text.updateTextLayer(idx, c);
            } else if (layer instanceof ImageLayer l) {
                ImageLayer c = l.copy();
                c.setColorARGB(argb);
                text = text.updateImageLayer(idx - tc, c);
            } else if (layer instanceof ShapeElementLayer l) {
                ShapeElementLayer c = l.copy();
                c.setColorARGB(argb);
                text = text.updateShapeLayer(idx - tc - ic, c);
            }
        }
        editable.setText(text);
        updateControls();
        pushUndoState();
    }

    // ============================================================
    // 撤销/重做
    // ============================================================
    private void pushUndoState() {
        if (isUndoRedo) return;
        SignpostText copy = copySignpostText(text);
        undoStack.push(copy);
        if (undoStack.size() > 50) undoStack.remove(0);
        redoStack.clear();
    }

    private SignpostText copySignpostText(SignpostText original) {
        List<TextLayer> tl = new ArrayList<>();
        for (TextLayer l : original.getTextLayers()) tl.add(l.copy());
        List<ImageLayer> il = new ArrayList<>();
        for (ImageLayer l : original.getImageLayers()) il.add(l.copy());
        List<ShapeElementLayer> sl = new ArrayList<>();
        for (ShapeElementLayer l : original.getShapeLayers()) sl.add(l.copy());
        return new SignpostText(tl, il, sl);
    }

    private void undo() {
        if (undoStack.size() <= 1) return;
        redoStack.push(copySignpostText(text));
        undoStack.pop();
        isUndoRedo = true;
        applySignpostText(undoStack.peek());
        isUndoRedo = false;
    }

    private void redo() {
        if (redoStack.isEmpty()) return;
        undoStack.push(copySignpostText(text));
        isUndoRedo = true;
        applySignpostText(redoStack.pop());
        isUndoRedo = false;
    }

    private void applySignpostText(SignpostText newText) {
        this.text = newText;
        this.editable.setText(this.text);
        selectedIndices.clear();
        if (this.text.getTotalLayers() > 0) selectedIndices.add(0);
        updateControls();
    }

    // ============================================================
    // 复制/粘贴
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
                case 0: text = text.addTextLayer(((TextLayer) layer).copy()); addedText++; break;
                case 1: text = text.addImageLayer(((ImageLayer) layer).copy()); addedImage++; break;
                case 2: text = text.addShapeLayer(((ShapeElementLayer) layer).copy()); addedShape++; break;
            }
        }
        selectedIndices.clear();
        int tc = text.getTextLayers().size();
        int ic = text.getImageLayers().size();
        int sc = text.getShapeLayers().size();
        for (int i = tc - addedText; i < tc; i++) selectedIndices.add(i);
        for (int i = 0; i < addedImage; i++) selectedIndices.add(tc + ic - addedImage + i);
        for (int i = 0; i < addedShape; i++) selectedIndices.add(tc + ic + sc - addedShape + i);
        editable.setText(text);
        updateControls();
        pushUndoState();
    }

    // ============================================================
    // 图层列表
    // ============================================================
    private void rebuildLayerList() {
        for (EditBox box : layerEditBoxes) this.removeWidget(box);
        layerEditBoxes.clear();

        if (text.getTotalLayers() == 0) {
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
            } else box.setTextColor(0xFFFFFF);
            this.addRenderableWidget(box);
            layerEditBoxes.add(box);
            index++;
        }

        for (ImageLayer layer : text.getImageLayers()) {
            int y = listTop + index * (itemHeight + gap);
            EditBox box = new EditBox(this.font, listLeft, y, listWidth, itemHeight, Component.empty());
            box.setValue(Component.translatable("gui.contemporaryconstruction.edit.image").getString() + ": " + layer.getWidth() + "×" + layer.getHeight());
            box.setEditable(false);
            if (selectedIndices.contains(index)) box.setTextColor(0xFFFFAA);
            else box.setTextColor(0xFFFFFF);
            this.addRenderableWidget(box);
            layerEditBoxes.add(box);
            index++;
        }

        for (ShapeElementLayer layer : text.getShapeLayers()) {
            int y = listTop + index * (itemHeight + gap);
            EditBox box = new EditBox(this.font, listLeft, y, listWidth, itemHeight, Component.empty());
            box.setValue(Component.translatable("gui.contemporaryconstruction.edit.shape").getString() + ": " + layer.getName() + " " + layer.getWidth() + "×" + layer.getHeight());
            box.setEditable(false);
            if (selectedIndices.contains(index)) box.setTextColor(0xFFFFAA);
            else box.setTextColor(0xFFFFFF);
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
        updateStyleButtons();
        updateColorPreview();
        rebuildLayerList();
    }
    
    private void updateColorPreview() {
        if (selectedIndices.isEmpty()) {
            currentColorPreview = 0xFFFFFFFF;
            return;
        }
        int first = selectedIndices.iterator().next();
        Object layer = text.getLayer(first);
        if (layer instanceof TextLayer tl) currentColorPreview = tl.getColorARGB();
        else if (layer instanceof ImageLayer il) currentColorPreview = il.getColorARGB();
        else if (layer instanceof ShapeElementLayer sl) currentColorPreview = sl.getColorARGB();
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
            if (layer instanceof TextLayer || layer instanceof ShapeElementLayer) {
                boolean glow = (layer instanceof TextLayer tl) ? tl.isGlowing() : ((ShapeElementLayer) layer).isGlowing();
                anyText = true;
                if (firstGlow == null) firstGlow = glow;
                else if (firstGlow != glow) allSame = false;
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

    /**
     * 切换加粗状态（对所有选中的文字图层）
     */
    private void toggleBold() {
        if (selectedIndices.isEmpty()) return;
        pushUndoState();
        boolean allBold = true;
        boolean first = true;
        for (int idx : selectedIndices) {
            Object layer = text.getLayer(idx);
            if (layer instanceof TextLayer tl) {
                if (first) { allBold = tl.isBold(); first = false; }
                else if (allBold != tl.isBold()) allBold = false;
            }
        }
        boolean newBold = !allBold;
        for (int idx : selectedIndices) {
            Object layer = text.getLayer(idx);
            if (layer instanceof TextLayer l) {
                TextLayer c = l.copy();
                c.setBold(newBold);
                text = text.updateTextLayer(idx, c);
            }
        }
        editable.setText(text);
        updateControls();
        pushUndoState();
    }

    /**
     * 切换斜体状态（对所有选中的文字图层）
     */
    private void toggleItalic() {
        if (selectedIndices.isEmpty()) return;
        pushUndoState();
        boolean allItalic = true;
        boolean first = true;
        for (int idx : selectedIndices) {
            Object layer = text.getLayer(idx);
            if (layer instanceof TextLayer tl) {
                if (first) { allItalic = tl.isItalic(); first = false; }
                else if (allItalic != tl.isItalic()) allItalic = false;
            }
        }
        boolean newItalic = !allItalic;
        for (int idx : selectedIndices) {
            Object layer = text.getLayer(idx);
            if (layer instanceof TextLayer l) {
                TextLayer c = l.copy();
                c.setItalic(newItalic);
                text = text.updateTextLayer(idx, c);
            }
        }
        editable.setText(text);
        updateControls();
        pushUndoState();
    }

    /**
     * 更新 B/I 按钮的显示状态（视觉反馈）
     */
    private void updateStyleButtons() {
        if (boldBtn == null || italicBtn == null) return;
        // 只对文字图层有作用
        Boolean boldState = null;
        Boolean italicState = null;
        for (int idx : selectedIndices) {
            Object layer = text.getLayer(idx);
            if (layer instanceof TextLayer tl) {
                if (boldState == null) boldState = tl.isBold();
                else if (boldState != tl.isBold()) boldState = false;
                if (italicState == null) italicState = tl.isItalic();
                else if (italicState != tl.isItalic()) italicState = false;
            }
        }
        boolean isText = boldState != null;
        boldBtn.active = isText;
        italicBtn.active = isText;
        boldBtn.setMessage(Component.literal(boldState != null && boldState ? "§l§nB" : "§lB"));
        italicBtn.setMessage(Component.literal(italicState != null && italicState ? "§o§nI" : "§oI"));
    }

    private void toggleGlow() {
        if (selectedIndices.isEmpty()) return;
        pushUndoState();
        boolean allGlow = true;
        boolean first = true;
        for (int idx : selectedIndices) {
            Object layer = text.getLayer(idx);
            Boolean glow = null;
            if (layer instanceof TextLayer tl) glow = tl.isGlowing();
            else if (layer instanceof ShapeElementLayer sl) glow = sl.isGlowing();
            if (glow != null) {
                if (first) { allGlow = glow; first = false; }
                else if (allGlow != glow) allGlow = false;
            }
        }
        boolean newGlow = !allGlow;
        for (int idx : selectedIndices) {
            Object layer = text.getLayer(idx);
            int tc = text.getTextLayers().size();
            int ic = text.getImageLayers().size();
            if (layer instanceof TextLayer l) {
                TextLayer c = l.copy(); c.setGlowing(newGlow); text = text.updateTextLayer(idx, c);
            } else if (layer instanceof ShapeElementLayer l) {
                ShapeElementLayer c = l.copy(); c.setGlowing(newGlow); text = text.updateShapeLayer(idx - tc - ic, c);
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
            text = text.addTextLayer(new TextLayer(Component.empty(), 0xFFFFFFFF, false, 0, 0, 0, 0, 0, 0, 1.0f, 1.0f));
            editable.setText(text);
            selectedIndices.clear();
            selectedIndices.add(text.getTotalLayers() - 1);
            updateControls();
            pushUndoState();
        }
    }

    private void deleteLayer() {
        if (selectedIndices.isEmpty()) return;
        if (selectedIndices.size() == 1 && text.getTextLayers().size() == 1 && selectedIndices.contains(0)) return;
        pushUndoState();
        List<Integer> sorted = new ArrayList<>(selectedIndices);
        sorted.sort(Collections.reverseOrder());
        for (int idx : sorted) {
            SignpostText.LayerType type = text.getLayerType(idx);
            if (type == SignpostText.LayerType.TEXT) {
                if (text.getTextLayers().size() <= 1) continue;
                text = text.removeTextLayer(idx);
            } else if (type == SignpostText.LayerType.IMAGE) {
                text = text.removeImageLayer(idx - text.getTextLayers().size());
            } else {
                text = text.removeShapeLayer(idx - text.getTextLayers().size() - text.getImageLayers().size());
            }
        }
        selectedIndices.clear();
        if (text.getTotalLayers() > 0) selectedIndices.add(text.getTotalLayers() - 1);
        editable.setText(text);
        updateControls();
        pushUndoState();
    }

    private void moveLayer(int direction) {
        if (selectedIndices.size() != 1) return;
        int idx = selectedIndices.iterator().next();
        int newIdx = idx + direction;
        if (newIdx < 0 || newIdx >= text.getTotalLayers()) return;
        if (text.getLayerType(idx) == SignpostText.LayerType.TEXT) {
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
    // 键盘移动图层
    // ============================================================
    private void moveSelectedLayer(float dx, float dy, float dz) {
        if (selectedIndices.isEmpty()) return;
        pushUndoState();
        for (int idx : selectedIndices) {
            Object layer = text.getLayer(idx);
            int tc = text.getTextLayers().size();
            int ic = text.getImageLayers().size();
            if (idx < tc) {
                TextLayer c = ((TextLayer) layer).copy();
                c.move(dx, dy); c.setOffsetZ(c.getOffsetZ() + dz);
                text = text.updateTextLayer(idx, c);
            } else if (idx < tc + ic) {
                int ii = idx - tc;
                ImageLayer c = ((ImageLayer) layer).copy();
                c.move(dx, dy); c.setOffsetZ(c.getOffsetZ() + dz);
                text = text.updateImageLayer(ii, c);
            } else {
                int si = idx - tc - ic;
                ShapeElementLayer c = ((ShapeElementLayer) layer).copy();
                c.move(dx, dy); c.setOffsetZ(c.getOffsetZ() + dz);
                text = text.updateShapeLayer(si, c);
            }
        }
        editable.setText(text);
        updateTransformControls();
        pushUndoState();
    }

    // ============================================================
    // 公共方法
    // ============================================================
    public SignpostText getText() { return text; }
    public void setText(SignpostText newText) {
        this.text = newText;
        this.editable.setText(this.text);
        selectedIndices.clear();
        if (this.text.getTotalLayers() > 0) selectedIndices.add(0);
        updateControls();
    }

    // ============================================================
    // 鼠标/键盘
    // ============================================================
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        // 1. 图层列表的点击选择
        for (int i = 0; i < layerEditBoxes.size(); i++) {
            EditBox box = layerEditBoxes.get(i);
            if (box.isMouseOver(mouseX, mouseY)) {
                if (hasControlDown()) {
                    if (selectedIndices.contains(i)) selectedIndices.remove(i);
                    else selectedIndices.add(i);
                } else {
                    selectedIndices.clear();
                    selectedIndices.add(i);
                }
                updateControls();
                // ★ 关键：把焦点真正交给这个输入框
                this.setFocused(box);
                box.setFocused(true);
                return super.mouseClicked(mouseX, mouseY, button);
            }
        }

        // 2. 点击其他任意位置 → 清焦点，恢复图层快捷键
        clearAllEditBoxFocus();
        return super.mouseClicked(mouseX, mouseY, button);
    }

    private void clearAllEditBoxFocus() {
        this.setFocused(null);
        for (var child : this.children()) {
            if (child instanceof EditBox box) {
                box.setFocused(false);
            }
        }
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        // ESC 始终允许关闭界面
        if (keyCode == 256) {
            return super.keyPressed(keyCode, scanCode, modifiers);
        }

        // ✅ 关键：如果任意输入框有焦点，所有按键都交给输入框
        if (isAnyEditBoxFocused()) {
            return super.keyPressed(keyCode, scanCode, modifiers);
        }
        int total = text.getTotalLayers();

        if (keyCode == 90 && hasControlDown()) { undo(); return true; }
        if (keyCode == 89 && hasControlDown()) { redo(); return true; }
        if (keyCode == 67 && hasControlDown()) { copyLayer(); return true; }
        if (keyCode == 86 && hasControlDown()) { pasteLayer(); return true; }
        if (keyCode == 261) { deleteLayer(); return true; }

        if (selectedIndices.size() == 1) {
            int idx = selectedIndices.iterator().next();
            if (keyCode == 265 && idx > 0) {
                selectedIndices.clear(); selectedIndices.add(idx - 1);
                updateControls(); return true;
            }
            if (keyCode == 264 && idx < total - 1) {
                selectedIndices.clear(); selectedIndices.add(idx + 1);
                updateControls(); return true;
            }
        }

        if (!selectedIndices.isEmpty()) {
            double step = 1.0;
            if (hasControlDown()) step = 0.1;
            else if (hasShiftDown()) step = 0.25;

            // ---- 计算缩放步长 ----
            float scaleStep = 0.1f;
            if (hasControlDown()) scaleStep = 0.01f;
            else if (hasShiftDown()) scaleStep = 0.25f;

// ---- E 键：同时放大 XY ----
            if (keyCode == 69) {
                applyScaleDeltaBoth(scaleStep);
                return true;
            }

// ---- D 键：同时缩小 XY ----
            if (keyCode == 68) {
                applyScaleDeltaBoth(-scaleStep);
                return true;
            }

// ---- R 键：只放大 X ----
            if (keyCode == 82) {
                applyScaleDeltaAxis("scaleX", scaleStep);
                return true;
            }

// ---- F 键：只缩小 X ----
            if (keyCode == 70) {
                applyScaleDeltaAxis("scaleX", -scaleStep);
                return true;
            }

// ---- T 键：只放大 Y ----
            if (keyCode == 84) {
                applyScaleDeltaAxis("scaleY", scaleStep);
                return true;
            }

// ---- G 键：只缩小 Y ----
            if (keyCode == 71) {
                applyScaleDeltaAxis("scaleY", -scaleStep);
                return true;
            }

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
        if (keyCode == 257 && hasControlDown()) { addLayer(SignpostText.LayerType.TEXT); return true; }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void tick() { if (!this.isValid()) this.onDone(); }

    private boolean isValid() {
        return this.minecraft != null && this.minecraft.player != null
                && !this.editable.asBlockEntity().isRemoved()
                && !this.editable.playerIsTooFarAwayToEdit(this.minecraft.player.getUUID());
    }

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
    // 渲染
    // ============================================================
    public void renderBackground(GuiGraphics g, int mouseX, int mouseY, float partialTick) {}

    @Override
    public void render(GuiGraphics g, int mouseX, int mouseY, float partialTick) {
        g.drawCenteredString(this.font, this.title, this.width / 2, 6, 0xFFFFFF);

        int firstIdx = selectedIndices.isEmpty() ? 0 : selectedIndices.iterator().next();
        String info = Component.translatable("gui.contemporaryconstruction.edit.info",
                selectedIndices.isEmpty() ? 0 : firstIdx + 1,
                text.getTotalLayers(),
                text.getTextLayers().size(),
                text.getImageLayers().size(),
                text.getShapeLayers().size()).getString();
        g.drawString(this.font, Component.literal(info), listLeft, listTop - 12, 0xAAAAAA, false);

        if (selectedIndices.size() > 1) {
            g.drawString(this.font, Component.literal("已选中 " + selectedIndices.size() + " 个图层"),
                    listLeft, listTop - 28, 0xFFFFAA, false);
        }

        g.fill(ctrlLeft - 4, ctrlTop, ctrlLeft - 2, ctrlTop + ctrlHeight, 0x44FFFFFF);

        super.render(g, mouseX, mouseY, partialTick);

        // 在颜色按钮旁边画当前颜色方块
        if (colorBtn != null) {
            int bx = colorBtn.getX() + colorBtn.getWidth() + 4;
            int by = colorBtn.getY();
            int bs = colorBtn.getHeight();
            g.fill(bx - 1, by - 1, bx + bs + 1, by + bs + 1, 0xFF000000);
            g.fill(bx, by, bx + bs, by + bs, currentColorPreview);
        }
    }

    @Override
    public boolean isPauseScreen() { return false; }

    // ================================================================
    // 变换控制
    // ================================================================
    private void updateTransformControls() {
        if (isUpdatingControls) return;

        // ★ 关键：控件未初始化时直接返回，防 NPE
        if (offsetXBox == null || offsetYBox == null || offsetZBox == null
                || rotateXBox == null || rotateYBox == null || rotationBox == null
                || scaleXBox == null || scaleYBox == null) {
            return;
        }

        isUpdatingControls = true;
        try {
            if (selectedIndices.isEmpty()) {
                if (!offsetXBox.isFocused()) offsetXBox.setValue("0");
                if (!offsetYBox.isFocused()) offsetYBox.setValue("0");
                if (!offsetZBox.isFocused()) offsetZBox.setValue("0");
                if (!rotateXBox.isFocused()) rotateXBox.setValue("0");
                if (!rotateYBox.isFocused()) rotateYBox.setValue("0");
                if (!rotationBox.isFocused()) rotationBox.setValue("0");
                if (!scaleXBox.isFocused()) scaleXBox.setValue("1.0");
                if (!scaleYBox.isFocused()) scaleYBox.setValue("1.0");
                setTransformBoxesEnabled(false);
                return;
            }

            boolean isMultiSelect = selectedIndices.size() > 1;
            setTransformBoxesEnabled(!isMultiSelect);

            int first = selectedIndices.iterator().next();
            Object layer = text.getLayer(first);

            float ox = 0, oy = 0, oz = 0;
            float rx = 0, ry = 0, rz = 0;
            float sx = 1, sy = 1;

            if (layer instanceof TextLayer tl) {
                ox = tl.getOffsetX(); oy = tl.getOffsetY(); oz = tl.getOffsetZ();
                rx = tl.getRotateX(); ry = tl.getRotateY(); rz = tl.getRotateZ();
                sx = tl.getScaleX(); sy = tl.getScaleY();
            } else if (layer instanceof ImageLayer il) {
                ox = il.getOffsetX(); oy = il.getOffsetY(); oz = il.getOffsetZ();
                rx = il.getRotateX(); ry = il.getRotateY(); rz = il.getRotateZ();
                sx = il.getScaleX(); sy = il.getScaleY();
            } else if (layer instanceof ShapeElementLayer sl) {
                ox = sl.getOffsetX(); oy = sl.getOffsetY(); oz = sl.getOffsetZ();
                rx = sl.getRotateX(); ry = sl.getRotateY(); rz = sl.getRotateZ();
                sx = sl.getScaleX(); sy = sl.getScaleY();
            }

            if (!offsetXBox.isFocused()) offsetXBox.setValue(fmt(ox));
            if (!offsetYBox.isFocused()) offsetYBox.setValue(fmt(oy));
            if (!offsetZBox.isFocused()) offsetZBox.setValue(fmt(oz));
            if (!rotateXBox.isFocused()) rotateXBox.setValue(fmt(rx));
            if (!rotateYBox.isFocused()) rotateYBox.setValue(fmt(ry));
            if (!rotationBox.isFocused()) rotationBox.setValue(fmt(rz));
            if (!scaleXBox.isFocused()) scaleXBox.setValue(fmt(sx));
            if (!scaleYBox.isFocused()) scaleYBox.setValue(fmt(sy));

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
        if (selectedIndices.size() > 1) return;

        // 只接受完整的数字格式：可选负号 + 数字 + 可选小数点 + 数字
        if (value == null || !value.matches("-?\\d+(\\.\\d+)?")) {
            return;
        }

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

    private void applyTransformToLayerAbsolute(String type, float val) {
        if (selectedIndices.isEmpty()) return;
        pushUndoState();
        for (int idx : selectedIndices) {
            Object layer = text.getLayer(idx);
            int tc = text.getTextLayers().size();
            int ic = text.getImageLayers().size();
            switch (type) {
                case "offsetX":
                    if (layer instanceof TextLayer l) { TextLayer c = l.copy(); c.setOffsetX(val); text = text.updateTextLayer(idx, c); }
                    else if (layer instanceof ImageLayer l) { ImageLayer c = l.copy(); c.setOffsetX(val); text = text.updateImageLayer(idx - tc, c); }
                    else if (layer instanceof ShapeElementLayer l) { ShapeElementLayer c = l.copy(); c.setOffsetX(val); text = text.updateShapeLayer(idx - tc - ic, c); }
                    break;
                case "offsetY":
                    if (layer instanceof TextLayer l) { TextLayer c = l.copy(); c.setOffsetY(val); text = text.updateTextLayer(idx, c); }
                    else if (layer instanceof ImageLayer l) { ImageLayer c = l.copy(); c.setOffsetY(val); text = text.updateImageLayer(idx - tc, c); }
                    else if (layer instanceof ShapeElementLayer l) { ShapeElementLayer c = l.copy(); c.setOffsetY(val); text = text.updateShapeLayer(idx - tc - ic, c); }
                    break;
                case "offsetZ":
                    if (layer instanceof TextLayer l) { TextLayer c = l.copy(); c.setOffsetZ(val); text = text.updateTextLayer(idx, c); }
                    else if (layer instanceof ImageLayer l) { ImageLayer c = l.copy(); c.setOffsetZ(val); text = text.updateImageLayer(idx - tc, c); }
                    else if (layer instanceof ShapeElementLayer l) { ShapeElementLayer c = l.copy(); c.setOffsetZ(val); text = text.updateShapeLayer(idx - tc - ic, c); }
                    break;
                case "rotateX":
                    if (layer instanceof TextLayer l) { TextLayer c = l.copy(); c.setRotateX(val); text = text.updateTextLayer(idx, c); }
                    else if (layer instanceof ImageLayer l) { ImageLayer c = l.copy(); c.setRotateX(val); text = text.updateImageLayer(idx - tc, c); }
                    else if (layer instanceof ShapeElementLayer l) { ShapeElementLayer c = l.copy(); c.setRotateX(val); text = text.updateShapeLayer(idx - tc - ic, c); }
                    break;
                case "rotateY":
                    if (layer instanceof TextLayer l) { TextLayer c = l.copy(); c.setRotateY(val); text = text.updateTextLayer(idx, c); }
                    else if (layer instanceof ImageLayer l) { ImageLayer c = l.copy(); c.setRotateY(val); text = text.updateImageLayer(idx - tc, c); }
                    else if (layer instanceof ShapeElementLayer l) { ShapeElementLayer c = l.copy(); c.setRotateY(val); text = text.updateShapeLayer(idx - tc - ic, c); }
                    break;
                case "rotation":
                    if (layer instanceof TextLayer l) { TextLayer c = l.copy(); c.setRotateZ(val); text = text.updateTextLayer(idx, c); }
                    else if (layer instanceof ImageLayer l) { ImageLayer c = l.copy(); c.setRotateZ(val); text = text.updateImageLayer(idx - tc, c); }
                    else if (layer instanceof ShapeElementLayer l) { ShapeElementLayer c = l.copy(); c.setRotateZ(val); text = text.updateShapeLayer(idx - tc - ic, c); }
                    break;
                case "scaleX":
                    if (layer instanceof TextLayer l) { TextLayer c = l.copy(); c.setScaleX(val); text = text.updateTextLayer(idx, c); }
                    else if (layer instanceof ImageLayer l) { ImageLayer c = l.copy(); c.setScaleX(val); text = text.updateImageLayer(idx - tc, c); }
                    else if (layer instanceof ShapeElementLayer l) { ShapeElementLayer c = l.copy(); c.setScaleX(val); text = text.updateShapeLayer(idx - tc - ic, c); }
                    break;
                case "scaleY":
                    if (layer instanceof TextLayer l) { TextLayer c = l.copy(); c.setScaleY(val); text = text.updateTextLayer(idx, c); }
                    else if (layer instanceof ImageLayer l) { ImageLayer c = l.copy(); c.setScaleY(val); text = text.updateImageLayer(idx - tc, c); }
                    else if (layer instanceof ShapeElementLayer l) { ShapeElementLayer c = l.copy(); c.setScaleY(val); text = text.updateShapeLayer(idx - tc - ic, c); }
                    break;
            }
        }
        editable.setText(text);
        updateButtons();
        pushUndoState();
    }

    /**
     * 同时缩放 scaleX 和 scaleY（增量模式，每个图层各自增加）
     */
    /**
     * 同时缩放 scaleX 和 scaleY（增量模式）
     */
    private void applyScaleDeltaBoth(float delta) {
        if (selectedIndices.isEmpty()) return;
        pushUndoState();
        for (int idx : selectedIndices) {
            Object layer = text.getLayer(idx);
            int tc = text.getTextLayers().size();
            int ic = text.getImageLayers().size();

            if (layer instanceof TextLayer l) {
                float newSX = clamp(l.getScaleX() + delta, 0.1f, 3f);
                float newSY = clamp(l.getScaleY() + delta, 0.1f, 3f);
                TextLayer c = l.copy();
                c.setScaleX(newSX);
                c.setScaleY(newSY);
                text = text.updateTextLayer(idx, c);
            } else if (layer instanceof ImageLayer l) {
                float newSX = clamp(l.getScaleX() + delta, 0.1f, 3f);
                float newSY = clamp(l.getScaleY() + delta, 0.1f, 3f);
                ImageLayer c = l.copy();
                c.setScaleX(newSX);
                c.setScaleY(newSY);
                text = text.updateImageLayer(idx - tc, c);
            } else if (layer instanceof ShapeElementLayer l) {
                float newSX = clamp(l.getScaleX() + delta, 0.1f, 3f);
                float newSY = clamp(l.getScaleY() + delta, 0.1f, 3f);
                ShapeElementLayer c = l.copy();
                c.setScaleX(newSX);
                c.setScaleY(newSY);
                text = text.updateShapeLayer(idx - tc - ic, c);
            }
        }
        editable.setText(text);
        updateTransformControls();
        pushUndoState();
    }

    /**
     * 只缩放单个轴（scaleX 或 scaleY）
     * @param axis "scaleX" 或 "scaleY"
     * @param delta 增量
     */
    private void applyScaleDeltaAxis(String axis, float delta) {
        if (selectedIndices.isEmpty()) return;
        pushUndoState();
        for (int idx : selectedIndices) {
            Object layer = text.getLayer(idx);
            int tc = text.getTextLayers().size();
            int ic = text.getImageLayers().size();

            if (layer instanceof TextLayer l) {
                TextLayer c = l.copy();
                if (axis.equals("scaleX")) {
                    c.setScaleX(clamp(l.getScaleX() + delta, 0.1f, 3f));
                } else {
                    c.setScaleY(clamp(l.getScaleY() + delta, 0.1f, 3f));
                }
                text = text.updateTextLayer(idx, c);
            } else if (layer instanceof ImageLayer l) {
                ImageLayer c = l.copy();
                if (axis.equals("scaleX")) {
                    c.setScaleX(clamp(l.getScaleX() + delta, 0.1f, 3f));
                } else {
                    c.setScaleY(clamp(l.getScaleY() + delta, 0.1f, 3f));
                }
                text = text.updateImageLayer(idx - tc, c);
            } else if (layer instanceof ShapeElementLayer l) {
                ShapeElementLayer c = l.copy();
                if (axis.equals("scaleX")) {
                    c.setScaleX(clamp(l.getScaleX() + delta, 0.1f, 3f));
                } else {
                    c.setScaleY(clamp(l.getScaleY() + delta, 0.1f, 3f));
                }
                text = text.updateShapeLayer(idx - tc - ic, c);
            }
        }
        editable.setText(text);
        updateTransformControls();
        pushUndoState();
    }

    private void applyTransformToLayerDelta(String type, float delta) {
        if (selectedIndices.isEmpty()) return;
        pushUndoState();
        for (int idx : selectedIndices) {
            Object layer = text.getLayer(idx);
            int tc = text.getTextLayers().size();
            int ic = text.getImageLayers().size();
            float cur = 0;
            if (layer instanceof TextLayer l) {
                switch (type) {
                    case "offsetX": cur = l.getOffsetX(); break;
                    case "offsetY": cur = l.getOffsetY(); break;
                    case "offsetZ": cur = l.getOffsetZ(); break;
                    case "rotateX": cur = l.getRotateX(); break;
                    case "rotateY": cur = l.getRotateY(); break;
                    case "rotation": cur = l.getRotateZ(); break;
                    case "scaleX": cur = l.getScaleX(); break;
                    case "scaleY": cur = l.getScaleY(); break;
                }
            } else if (layer instanceof ImageLayer l) {
                switch (type) {
                    case "offsetX": cur = l.getOffsetX(); break;
                    case "offsetY": cur = l.getOffsetY(); break;
                    case "offsetZ": cur = l.getOffsetZ(); break;
                    case "rotateX": cur = l.getRotateX(); break;
                    case "rotateY": cur = l.getRotateY(); break;
                    case "rotation": cur = l.getRotateZ(); break;
                    case "scaleX": cur = l.getScaleX(); break;
                    case "scaleY": cur = l.getScaleY(); break;
                }
            } else if (layer instanceof ShapeElementLayer l) {
                switch (type) {
                    case "offsetX": cur = l.getOffsetX(); break;
                    case "offsetY": cur = l.getOffsetY(); break;
                    case "offsetZ": cur = l.getOffsetZ(); break;
                    case "rotateX": cur = l.getRotateX(); break;
                    case "rotateY": cur = l.getRotateY(); break;
                    case "rotation": cur = l.getRotateZ(); break;
                    case "scaleX": cur = l.getScaleX(); break;
                    case "scaleY": cur = l.getScaleY(); break;
                }
            }
            float nv = cur + delta;
            if (type.equals("scaleX") || type.equals("scaleY")) nv = clamp(nv, 0.1f, 3f);
            else if (type.equals("rotation") || type.equals("rotateY") || type.equals("rotateX")) nv = normRot(nv);
            // 复用绝对值方法
            switch (type) {
                case "offsetX":
                    if (layer instanceof TextLayer l) { TextLayer c = l.copy(); c.setOffsetX(nv); text = text.updateTextLayer(idx, c); }
                    else if (layer instanceof ImageLayer l) { ImageLayer c = l.copy(); c.setOffsetX(nv); text = text.updateImageLayer(idx - tc, c); }
                    else if (layer instanceof ShapeElementLayer l) { ShapeElementLayer c = l.copy(); c.setOffsetX(nv); text = text.updateShapeLayer(idx - tc - ic, c); }
                    break;
                case "offsetY":
                    if (layer instanceof TextLayer l) { TextLayer c = l.copy(); c.setOffsetY(nv); text = text.updateTextLayer(idx, c); }
                    else if (layer instanceof ImageLayer l) { ImageLayer c = l.copy(); c.setOffsetY(nv); text = text.updateImageLayer(idx - tc, c); }
                    else if (layer instanceof ShapeElementLayer l) { ShapeElementLayer c = l.copy(); c.setOffsetY(nv); text = text.updateShapeLayer(idx - tc - ic, c); }
                    break;
                case "offsetZ":
                    if (layer instanceof TextLayer l) { TextLayer c = l.copy(); c.setOffsetZ(nv); text = text.updateTextLayer(idx, c); }
                    else if (layer instanceof ImageLayer l) { ImageLayer c = l.copy(); c.setOffsetZ(nv); text = text.updateImageLayer(idx - tc, c); }
                    else if (layer instanceof ShapeElementLayer l) { ShapeElementLayer c = l.copy(); c.setOffsetZ(nv); text = text.updateShapeLayer(idx - tc - ic, c); }
                    break;
                case "rotateX":
                    if (layer instanceof TextLayer l) { TextLayer c = l.copy(); c.setRotateX(nv); text = text.updateTextLayer(idx, c); }
                    else if (layer instanceof ImageLayer l) { ImageLayer c = l.copy(); c.setRotateX(nv); text = text.updateImageLayer(idx - tc, c); }
                    else if (layer instanceof ShapeElementLayer l) { ShapeElementLayer c = l.copy(); c.setRotateX(nv); text = text.updateShapeLayer(idx - tc - ic, c); }
                    break;
                case "rotateY":
                    if (layer instanceof TextLayer l) { TextLayer c = l.copy(); c.setRotateY(nv); text = text.updateTextLayer(idx, c); }
                    else if (layer instanceof ImageLayer l) { ImageLayer c = l.copy(); c.setRotateY(nv); text = text.updateImageLayer(idx - tc, c); }
                    else if (layer instanceof ShapeElementLayer l) { ShapeElementLayer c = l.copy(); c.setRotateY(nv); text = text.updateShapeLayer(idx - tc - ic, c); }
                    break;
                case "rotation":
                    if (layer instanceof TextLayer l) { TextLayer c = l.copy(); c.setRotateZ(nv); text = text.updateTextLayer(idx, c); }
                    else if (layer instanceof ImageLayer l) { ImageLayer c = l.copy(); c.setRotateZ(nv); text = text.updateImageLayer(idx - tc, c); }
                    else if (layer instanceof ShapeElementLayer l) { ShapeElementLayer c = l.copy(); c.setRotateZ(nv); text = text.updateShapeLayer(idx - tc - ic, c); }
                    break;
                case "scaleX":
                    if (layer instanceof TextLayer l) { TextLayer c = l.copy(); c.setScaleX(nv); text = text.updateTextLayer(idx, c); }
                    else if (layer instanceof ImageLayer l) { ImageLayer c = l.copy(); c.setScaleX(nv); text = text.updateImageLayer(idx - tc, c); }
                    else if (layer instanceof ShapeElementLayer l) { ShapeElementLayer c = l.copy(); c.setScaleX(nv); text = text.updateShapeLayer(idx - tc - ic, c); }
                    break;
                case "scaleY":
                    if (layer instanceof TextLayer l) { TextLayer c = l.copy(); c.setScaleY(nv); text = text.updateTextLayer(idx, c); }
                    else if (layer instanceof ImageLayer l) { ImageLayer c = l.copy(); c.setScaleY(nv); text = text.updateImageLayer(idx - tc, c); }
                    else if (layer instanceof ShapeElementLayer l) { ShapeElementLayer c = l.copy(); c.setScaleY(nv); text = text.updateShapeLayer(idx - tc - ic, c); }
                    break;
            }
        }
        editable.setText(text);
        updateButtons();
        pushUndoState();
    }

    private float clamp(float v, float min, float max) { return Math.max(min, Math.min(max, v)); }
    private float normRot(float v) { v = ((v % 360) + 360) % 360; return v > 180 ? v - 360 : v; }
    private String fmt(float v) { return v == (int) v ? Integer.toString((int) v) : String.format("%.2f", v); }

    /**
     * 检查是否有任意输入框处于聚焦状态。
     * 优先检查 getFocused()，再遍历所有 EditBox 以防万一。
     */
    private boolean isAnyEditBoxFocused() {
        if (getFocused() instanceof EditBox) return true;
        // 遍历所有 widget，检查 EditBox
        for (var child : this.children()) {
            if (child instanceof EditBox box && box.isFocused()) {
                return true;
            }
        }
        return false;
    }

    private void openFontSelector() {
        if (selectedIndices.isEmpty()) return;
        int first = selectedIndices.iterator().next();
        Object layer = text.getLayer(first);
        if (!(layer instanceof TextLayer)) return;

        Minecraft.getInstance().setScreen(new FontSelectorScreen(this, fontId -> {
            pushUndoState();
            for (int idx : selectedIndices) {
                Object l = text.getLayer(idx);
                if (l instanceof TextLayer tl) {
                    TextLayer copy = tl.copy();
                    copy.setFontId(fontId);
                    text = text.updateTextLayer(idx, copy);
                }
            }
            editable.setText(text);
            updateControls();
            pushUndoState();
        }));
    }

    /**
     * 把字段名和 EditBox 关联起来，供 updateTransformControls() 回写值用
     */
    private final Map<String, EditBox> transformBoxes = new HashMap<>();

    private void bindTransformBox(String field, EditBox box) {
        transformBoxes.put(field, box);
    }

    /**
     * 用 field 名统一获取控件（替代原来分散的字段）
     */
    private EditBox getTransformBox(String field) {
        return transformBoxes.get(field);
    }

}