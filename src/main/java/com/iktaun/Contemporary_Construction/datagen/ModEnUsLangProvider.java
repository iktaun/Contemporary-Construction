package com.iktaun.Contemporary_Construction.datagen;

import com.iktaun.Contemporary_Construction.contemporaryconstruction;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class ModEnUsLangProvider extends LanguageProvider {
    public ModEnUsLangProvider(PackOutput output) {
        super(output, contemporaryconstruction.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        // ===== 创造模式标签 =====
        add("itemGroup.road_blocks", "Road Blocks");
        add("itemGroup.signal_note", "Signal Note");
        add("itemGroup.barrier", "Barrier");
        add("itemGroup.other", "Other");
        add("itemGroup.light", "Light");
        add("itemGroup.column", "Column");

        // ===== 方块 =====
        add("block.contemporaryconstruction.bitumen_block", "Bitumen Block");
        add("block.contemporaryconstruction.white_straight_signal_bitumen_block", "White Straight Signal Bitumen Block");
        add("block.contemporaryconstruction.white_straight_signal_bitumen_block_no_connection", "White Straight Signal Bitumen Block(No Connection)");
        add("block.contemporaryconstruction.white_slant_straight_signal_bitumen_block", "White Slant Straight Signal Bitumen Block");
        add("block.contemporaryconstruction.yellow_short_slant_straight_signal_bitumen_block", "Yellow Slant Straight Signal Bitumen Block");
        add("block.contemporaryconstruction.white_short_slant_straight_signal_bitumen_block", "White Short Slant Straight Signal Bitumen Block");
        add("block.contemporaryconstruction.yellow_double_short_slant_straight_signal_bitumen_block", "Yellow Short Slant Straight Signal Bitumen Block");
        add("block.contemporaryconstruction.white_double_short_slant_straight_signal_bitumen_block", "White Double Short Slant Straight Signal Bitumen Block");
        add("block.contemporaryconstruction.yellow_slant_straight_signal_bitumen_block", "Yellow Double Short Slant Straight Signal Bitumen Block");
        add("block.contemporaryconstruction.yellow_straight_signal_bitumen_block", "Yellow Straight Signal Bitumen Block");
        add("block.contemporaryconstruction.yellow_straight_signal_bitumen_block_no_connection", "Yellow Straight Signal Bitumen Block(No Connection)");
        add("block.contemporaryconstruction.white_double_straight_signal_bitumen_block", "White Double Straight Signal Bitumen Block");
        add("block.contemporaryconstruction.yellow_double_straight_signal_bitumen_block", "Yellow Double Straight Signal Bitumen Block");
        add("block.contemporaryconstruction.white_character_li_signal_bitumen_block", "White Character Li Signal Bitumen Block");
        add("block.contemporaryconstruction.white_character_rang_signal_bitumen_block", "White Character Rang Signal Bitumen Block");
        add("block.contemporaryconstruction.white_character_xing_signal_bitumen_block", "White Character Xing Signal Bitumen Block");
        add("block.contemporaryconstruction.white_character_jin_signal_bitumen_block", "White Character Jin Signal Bitumen Block");
        add("block.contemporaryconstruction.white_character_ren_signal_bitumen_block", "White Character Ren Signal Bitumen Block");
        add("block.contemporaryconstruction.white_character_zhi_signal_bitumen_block", "White Character Zhi Signal Bitumen Block");
        add("block.contemporaryconstruction.white_character_tong_signal_bitumen_block", "White Character Tong Signal Bitumen Block");
        add("block.contemporaryconstruction.white_character_zhuan_signal_bitumen_block", "White Character Zhuan Signal Bitumen Block");
        add("block.contemporaryconstruction.white_character_yon_signal_bitumen_block", "White Character Yon Signal Bitumen Block");
        add("block.contemporaryconstruction.white_direction_signal_bitumen_block", "White Direction Signal Bitumen Block");
        add("block.contemporaryconstruction.yellow_direction_signal_bitumen_block", "Yellow Direction Signal Bitumen Block");
        add("block.contemporaryconstruction.yellow_straight_quarter_circle_signal_bitumen_block", "Yellow Straight Quarter Circle Signal Bitumen Block");
        add("block.contemporaryconstruction.white_straight_quarter_circle_signal_bitumen_block", "White Straight Quarter Circle Signal Bitumen Block");
        add("block.contemporaryconstruction.yellow_quarter_circle_signal_bitumen_block", "Yellow Quarter Circle Signal Bitumen Block");
        add("block.contemporaryconstruction.white_quarter_circle_signal_bitumen_block", "White Quarter Circle Signal Bitumen Block");
        add("block.contemporaryconstruction.white_right_direction_signal_bitumen_block", "White Right Direction Signal Bitumen Block");
        add("block.contemporaryconstruction.white_right_signal_bitumen_block", "White Right Signal Bitumen Block");
        add("block.contemporaryconstruction.white_right_straight_signal_bitumen_block", "White Right Straight Signal Bitumen Block");
        add("block.contemporaryconstruction.white_left_direction_signal_bitumen_block", "White Left Direction Signal Bitumen Block");
        add("block.contemporaryconstruction.white_left_right_straight_signal_bitumen_block", "White Left Right Straight Signal Bitumen Block");
        add("block.contemporaryconstruction.white_left_signal_bitumen_block", "White Left Signal Bitumen Block");
        add("block.contemporaryconstruction.white_left_straight_signal_bitumen_block", "White Left Straight Signal Bitumen Block");
        add("block.contemporaryconstruction.yellow_left_direction_signal_bitumen_block", "Yellow Left Direction Signal Bitumen Block");
        add("block.contemporaryconstruction.yellow_left_right_straight_signal_bitumen_block", "Yellow Left Right Straight Signal Bitumen Block");
        add("block.contemporaryconstruction.yellow_left_signal_bitumen_block", "Yellow Left Signal Bitumen Block");
        add("block.contemporaryconstruction.yellow_left_straight_signal_bitumen_block", "Yellow Left Straight Signal Bitumen Block");
        add("block.contemporaryconstruction.yellow_right_direction_signal_bitumen_block", "Yellow Right Direction Signal Bitumen Block");
        add("block.contemporaryconstruction.yellow_right_signal_bitumen_block", "Yellow Right Signal Bitumen Block");
        add("block.contemporaryconstruction.yellow_right_straight_signal_bitumen_block", "Yellow Right Straight Signal Bitumen Block");

        add("block.contemporaryconstruction.barrier_cyan", "Barrier Cyan");
        add("block.contemporaryconstruction.barrier_red", "Barrier Red");        // 修正笔误
        add("block.contemporaryconstruction.barrier_blue", "Barrier Blue");
        add("block.contemporaryconstruction.road_fence", "Road Fence");
        add("block.contemporaryconstruction.speed_bump", "Speed Bump");
        add("block.contemporaryconstruction.crush_barrel", "Crush Barrel");
        add("block.contemporaryconstruction.red_crush_column", "Red Crush Column");
        add("block.contemporaryconstruction.yellow_crush_column", "Yellow Crush Column");
        add("block.contemporaryconstruction.concrete_barrier", "Concrete Barrier");
        add("block.contemporaryconstruction.yellow_concrete_barrier", "Yellow Concrete Barrier");
        add("block.contemporaryconstruction.tactile_paving", "Tactile Paving");
        add("block.contemporaryconstruction.street_light_pole_blue_base", "Street Light Pole Base-Blue");
        add("block.contemporaryconstruction.street_light_pole_white", "Street Light Pole-White");
        add("block.contemporaryconstruction.street_light_block_a", "Street Light Block(A)");
        add("block.contemporaryconstruction.sign_post_blank", "Signpost");
        add("block.contemporaryconstruction.sign_post_pole", "Signpost Pole");

        // ===== 物品 =====
        add("item.contemporaryconstruction.brush", "Brush");                    // 修正：brush 是物品
        add("item.contemporaryconstruction.signal_board", "Signal Board");      // 修正：signal_board 是物品

        // ===== GUI：主编辑界面 =====
        add("gui.contemporaryconstruction.edit.title", "Edit Signpost");
        add("gui.contemporaryconstruction.edit.text", "+ Text");
        add("gui.contemporaryconstruction.edit.image", "Image");
        add("gui.contemporaryconstruction.edit.shape", "Shape");
        add("gui.contemporaryconstruction.edit.export", "Export");
        add("gui.contemporaryconstruction.edit.preset.save", "Save Preset");
        add("gui.contemporaryconstruction.edit.preset.load", "Load Preset");
        add("gui.contemporaryconstruction.edit.glow", "Glow");
        add("gui.contemporaryconstruction.edit.no_glow", "No Glow");
        add("gui.contemporaryconstruction.edit.offset_x", "X:");
        add("gui.contemporaryconstruction.edit.offset_y", "Y:");
        add("gui.contemporaryconstruction.edit.offset_z", "Z:");
        add("gui.contemporaryconstruction.edit.rotate_x","RotX:");
        add("gui.contemporaryconstruction.edit.rotate_z", "RotZ:");
        add("gui.contemporaryconstruction.edit.rotate_y", "RotY:");
        add("gui.contemporaryconstruction.edit.scale_x", "SX:");
        add("gui.contemporaryconstruction.edit.scale_y", "SY:");
        add("gui.contemporaryconstruction.edit.done", "Done");
        add("gui.contemporaryconstruction.edit.cancel", "Cancel");
        add("gui.contemporaryconstruction.edit.empty", "(empty)");
        add("gui.contemporaryconstruction.edit.info", "Layer: %s/%s  Text: %s  Image: %s  Shape: %s");

        // ===== GUI：图片选择 =====
        add("gui.contemporaryconstruction.image.title", "Select Image");
        add("gui.contemporaryconstruction.image.no_images", "No Images");
        add("gui.contemporaryconstruction.image.file_list", "File List:");
        add("gui.contemporaryconstruction.image.scale", "Scale:");
        add("gui.contemporaryconstruction.image.add", "Add");
        add("gui.contemporaryconstruction.image.cancel", "Cancel");
        add("gui.contemporaryconstruction.image.select_hint", "<- Select an image from the left");
        add("gui.contemporaryconstruction.image.preview_info", "%dx%d -> %dx%d (%.2fx)");

        // ===== GUI：形状选择 =====
        add("gui.contemporaryconstruction.shape.title", "Select Shape");
        add("gui.contemporaryconstruction.shape.width", "Width:");
        add("gui.contemporaryconstruction.shape.height", "Height:");
        add("gui.contemporaryconstruction.shape.add", "Add");
        add("gui.contemporaryconstruction.shape.cancel", "Cancel");
        add("gui.contemporaryconstruction.shape.current_color", "Current Color");
        add("gui.contemporaryconstruction.shape.no_shapes", "No shapes found, put them in persetelement folder");

        // ===== GUI：预设 =====
        add("gui.contemporaryconstruction.preset.title", "Presets");
        add("gui.contemporaryconstruction.preset.load", "Load");
        add("gui.contemporaryconstruction.preset.delete", "Delete");
        add("gui.contemporaryconstruction.preset.cancel", "Cancel");
        add("gui.contemporaryconstruction.preset.search", "Search presets");
        add("gui.contemporaryconstruction.preset.empty", "No presets, please save one in the signpost editor");

        add("gui.contemporaryconstruction.preset.save.title", "Save Preset");
        add("gui.contemporaryconstruction.preset.save.name", "Enter preset name:");
        add("gui.contemporaryconstruction.preset.save.save", "Save");
        add("gui.contemporaryconstruction.preset.save.cancel", "Cancel");
        add("gui.contemporaryconstruction.preset.save.info", "Saving %s layer(s) (%s text, %s images, %s shapes)");
        add("gui.contemporaryconstruction.preset.save.empty_name", "Name cannot be empty");
        add("gui.contemporaryconstruction.preset.save.invalid_name", "Name can only contain letters, numbers, underscores, and Chinese characters");
        add("gui.contemporaryconstruction.preset.save.exists", "Preset already exists, please choose another name");
        add("gui.contemporaryconstruction.preset.save.success", "Preset \"%s\" saved successfully");
        add("gui.contemporaryconstruction.preset.save.failed", "Save failed, please check file permissions");

        // ===== GUI：纹理导出 =====
        add("gui.contemporaryconstruction.export.title", "Export Texture");
        add("gui.contemporaryconstruction.export.settings", "Texture Export Settings");
        add("gui.contemporaryconstruction.export.width", "Width:");
        add("gui.contemporaryconstruction.export.height", "Height:");
        add("gui.contemporaryconstruction.export.format_png", "PNG");
        add("gui.contemporaryconstruction.export.format_jpg", "JPG");
        add("gui.contemporaryconstruction.export.filename", "File name:");
        add("gui.contemporaryconstruction.export.preview", "Preview");
        add("gui.contemporaryconstruction.export.export", "Export");
        add("gui.contemporaryconstruction.export.cancel", "Cancel");
        add("gui.contemporaryconstruction.export.preview_hint", "Click Preview");
        add("gui.contemporaryconstruction.export.success", "Export successful: %s");
        add("gui.contemporaryconstruction.export.failed", "Export failed: %s");
        add("gui.contemporaryconstruction.export.preview_failed", "Preview generation failed, cannot export");

        // ===== 消息提示 =====
        add("message.contemporaryconstruction.editing_by_other", "This signpost is being edited by another player");
        add("message.contemporaryconstruction.waxed", "This signpost is waxed and cannot be edited");
        add("message.contemporaryconstruction.no_shapes", "No shapes found, please put images in config/contemporaryconstruction/presets/persetelement/");
        add("message.contemporaryconstruction.not_editable", "This block is marked as editable but does not implement IEditableWithBrush");
    }
}