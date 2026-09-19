package com.iktaun.Contemporary_Construction.datagen;

import com.iktaun.Contemporary_Construction.contemporaryconstruction;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class ModZhHkLangProvider extends LanguageProvider {
    public ModZhHkLangProvider(PackOutput output) {
        super(output, contemporaryconstruction.MOD_ID, "zh_hk");
    }

    @Override
    protected void addTranslations() {
        // ===== 創造模式標籤 =====
        add("itemGroup.road_blocks", "道路方塊");
        add("itemGroup.signal_note", "信號標記");
        add("itemGroup.barrier", "護欄");
        add("itemGroup.other", "其他");
        add("itemGroup.light", "燈光");
        add("itemGroup.column", "柱");

        // ===== 方塊 =====
        add("block.contemporaryconstruction.bitumen_block", "瀝青塊");
        add("block.contemporaryconstruction.white_straight_signal_bitumen_block", "白色直行信號瀝青塊");
        add("block.contemporaryconstruction.white_straight_signal_bitumen_block_no_connection", "白色直行信號瀝青塊（無連接）");
        add("block.contemporaryconstruction.white_slant_straight_signal_bitumen_block", "白色斜直行信號瀝青塊");
        add("block.contemporaryconstruction.yellow_short_slant_straight_signal_bitumen_block", "黃色短斜直行信號瀝青塊");
        add("block.contemporaryconstruction.white_short_slant_straight_signal_bitumen_block", "白色短斜直行信號瀝青塊");
        add("block.contemporaryconstruction.yellow_double_short_slant_straight_signal_bitumen_block", "黃色雙短斜直行信號瀝青塊");
        add("block.contemporaryconstruction.white_double_short_slant_straight_signal_bitumen_block", "白色雙短斜直行信號瀝青塊");
        add("block.contemporaryconstruction.yellow_slant_straight_signal_bitumen_block", "黃色斜直行信號瀝青塊");
        add("block.contemporaryconstruction.yellow_straight_signal_bitumen_block", "黃色直行信號瀝青塊");
        add("block.contemporaryconstruction.yellow_straight_signal_bitumen_block_no_connection", "黃色直行信號瀝青塊（無連接）");
        add("block.contemporaryconstruction.white_double_straight_signal_bitumen_block", "白色雙直行信號瀝青塊");
        add("block.contemporaryconstruction.yellow_double_straight_signal_bitumen_block", "黃色雙直行信號瀝青塊");
        add("block.contemporaryconstruction.white_character_li_signal_bitumen_block", "白色「禮」字信號瀝青塊");
        add("block.contemporaryconstruction.white_character_rang_signal_bitumen_block", "白色「讓」字信號瀝青塊");
        add("block.contemporaryconstruction.white_character_xing_signal_bitumen_block", "白色「行」字信號瀝青塊");
        add("block.contemporaryconstruction.white_character_jin_signal_bitumen_block", "白色「禁」字信號瀝青塊");
        add("block.contemporaryconstruction.white_character_ren_signal_bitumen_block", "白色「人」字信號瀝青塊");
        add("block.contemporaryconstruction.white_character_zhi_signal_bitumen_block", "白色「直」字信號瀝青塊");
        add("block.contemporaryconstruction.white_character_tong_signal_bitumen_block", "白色「通」字信號瀝青塊");
        add("block.contemporaryconstruction.white_character_zhuan_signal_bitumen_block", "白色「轉」字信號瀝青塊");
        add("block.contemporaryconstruction.white_character_yon_signal_bitumen_block", "白色「用」字信號瀝青塊");
        add("block.contemporaryconstruction.white_direction_signal_bitumen_block", "白色方向信號瀝青塊");
        add("block.contemporaryconstruction.yellow_direction_signal_bitumen_block", "黃色方向信號瀝青塊");
        add("block.contemporaryconstruction.yellow_straight_quarter_circle_signal_bitumen_block", "黃色直行四分之一圓信號瀝青塊");
        add("block.contemporaryconstruction.white_straight_quarter_circle_signal_bitumen_block", "白色直行四分之一圓信號瀝青塊");
        add("block.contemporaryconstruction.yellow_quarter_circle_signal_bitumen_block", "黃色四分之一圓信號瀝青塊");
        add("block.contemporaryconstruction.white_quarter_circle_signal_bitumen_block", "白色四分之一圓信號瀝青塊");
        add("block.contemporaryconstruction.white_right_direction_signal_bitumen_block", "白色右方向信號瀝青塊");
        add("block.contemporaryconstruction.white_right_signal_bitumen_block", "白色右轉信號瀝青塊");
        add("block.contemporaryconstruction.white_right_straight_signal_bitumen_block", "白色右直行信號瀝青塊");
        add("block.contemporaryconstruction.white_left_direction_signal_bitumen_block", "白色左方向信號瀝青塊");
        add("block.contemporaryconstruction.white_left_right_straight_signal_bitumen_block", "白色左右直行信號瀝青塊");
        add("block.contemporaryconstruction.white_left_signal_bitumen_block", "白色左轉信號瀝青塊");
        add("block.contemporaryconstruction.white_left_straight_signal_bitumen_block", "白色左直行信號瀝青塊");
        add("block.contemporaryconstruction.yellow_left_direction_signal_bitumen_block", "黃色左方向信號瀝青塊");
        add("block.contemporaryconstruction.yellow_left_right_straight_signal_bitumen_block", "黃色左右直行信號瀝青塊");
        add("block.contemporaryconstruction.yellow_left_signal_bitumen_block", "黃色左轉信號瀝青塊");
        add("block.contemporaryconstruction.yellow_left_straight_signal_bitumen_block", "黃色左直行信號瀝青塊");
        add("block.contemporaryconstruction.yellow_right_direction_signal_bitumen_block", "黃色右方向信號瀝青塊");
        add("block.contemporaryconstruction.yellow_right_signal_bitumen_block", "黃色右轉信號瀝青塊");
        add("block.contemporaryconstruction.yellow_right_straight_signal_bitumen_block", "黃色右直行信號瀝青塊");

        add("block.contemporaryconstruction.barrier_cyan", "青色護欄");
        add("block.contemporaryconstruction.barrier_red", "紅色護欄");
        add("block.contemporaryconstruction.barrier_blue", "藍色護欄");
        add("block.contemporaryconstruction.road_fence", "道路護欄");
        add("block.contemporaryconstruction.speed_bump", "減速帶");
        add("block.contemporaryconstruction.crush_barrel", "防撞桶");
        add("block.contemporaryconstruction.red_crush_column", "紅色防撞柱");
        add("block.contemporaryconstruction.yellow_crush_column", "黃色防撞柱");
        add("block.contemporaryconstruction.concrete_barrier", "混凝土護欄");
        add("block.contemporaryconstruction.yellow_concrete_barrier", "黃色混凝土護欄");
        add("block.contemporaryconstruction.tactile_paving", "盲道");
        add("block.contemporaryconstruction.street_light_pole_blue_base", "路燈桿底座-藍色");
        add("block.contemporaryconstruction.street_light_pole_white", "路燈桿-白色");
        add("block.contemporaryconstruction.street_light_block_a", "路燈方塊(A)");
        add("block.contemporaryconstruction.sign_post_blank", "標誌桿");
        add("block.contemporaryconstruction.sign_post_pole", "標誌桿柱");

        // ===== 物品 =====
        add("item.contemporaryconstruction.brush", "刷子");
        add("item.contemporaryconstruction.signal_board", "信號板");

        // ===== GUI：主編輯界面 =====
        add("gui.contemporaryconstruction.edit.title", "編輯標誌桿");
        add("gui.contemporaryconstruction.edit.text", "+ 文字");
        add("gui.contemporaryconstruction.edit.image", "圖片");
        add("gui.contemporaryconstruction.edit.shape", "形狀");
        add("gui.contemporaryconstruction.edit.export", "匯出");
        add("gui.contemporaryconstruction.edit.preset.save", "儲存預設");
        add("gui.contemporaryconstruction.edit.preset.load", "載入預設");
        add("gui.contemporaryconstruction.edit.glow", "發光");
        add("gui.contemporaryconstruction.edit.no_glow", "不發光");
        add("gui.contemporaryconstruction.edit.offset_x", "X:");
        add("gui.contemporaryconstruction.edit.offset_y", "Y:");
        add("gui.contemporaryconstruction.edit.offset_z", "Z:");
        add("gui.contemporaryconstruction.edit.rotate_x", "RotX:");
        add("gui.contemporaryconstruction.edit.rotate_z", "RotZ:");
        add("gui.contemporaryconstruction.edit.rotate_y", "RotY:");
        add("gui.contemporaryconstruction.edit.scale_x", "SX:");
        add("gui.contemporaryconstruction.edit.scale_y", "SY:");
        add("gui.contemporaryconstruction.edit.done", "完成");
        add("gui.contemporaryconstruction.edit.cancel", "取消");
        add("gui.contemporaryconstruction.edit.empty", "(空)");
        add("gui.contemporaryconstruction.edit.info", "圖層: %s/%s  文字: %s  圖片: %s  形狀: %s");

        // ===== GUI：圖片選擇 =====
        add("gui.contemporaryconstruction.image.title", "選擇圖片");
        add("gui.contemporaryconstruction.image.no_images", "沒有圖片");
        add("gui.contemporaryconstruction.image.file_list", "檔案列表:");
        add("gui.contemporaryconstruction.image.scale", "縮放:");
        add("gui.contemporaryconstruction.image.add", "新增");
        add("gui.contemporaryconstruction.image.cancel", "取消");
        add("gui.contemporaryconstruction.image.select_hint", "<- 從左側選擇圖片");
        add("gui.contemporaryconstruction.image.preview_info", "%dx%d -> %dx%d (%.2fx)");

        // ===== GUI：形狀選擇 =====
        add("gui.contemporaryconstruction.shape.title", "選擇形狀");
        add("gui.contemporaryconstruction.shape.width", "寬度:");
        add("gui.contemporaryconstruction.shape.height", "高度:");
        add("gui.contemporaryconstruction.shape.add", "新增");
        add("gui.contemporaryconstruction.shape.cancel", "取消");
        add("gui.contemporaryconstruction.shape.current_color", "目前顏色");
        add("gui.contemporaryconstruction.shape.no_shapes", "找不到形狀，請將它們放入 persetelement 資料夾");

        // ===== GUI：預設 =====
        add("gui.contemporaryconstruction.preset.title", "預設");
        add("gui.contemporaryconstruction.preset.load", "載入");
        add("gui.contemporaryconstruction.preset.delete", "刪除");
        add("gui.contemporaryconstruction.preset.cancel", "取消");
        add("gui.contemporaryconstruction.preset.search", "搜尋預設");
        add("gui.contemporaryconstruction.preset.empty", "沒有預設，請在標誌桿編輯器中儲存一個");

        add("gui.contemporaryconstruction.preset.save.title", "儲存預設");
        add("gui.contemporaryconstruction.preset.save.name", "輸入預設名稱:");
        add("gui.contemporaryconstruction.preset.save.save", "儲存");
        add("gui.contemporaryconstruction.preset.save.cancel", "取消");
        add("gui.contemporaryconstruction.preset.save.info", "正在儲存 %s 個圖層（%s 文字，%s 圖片，%s 形狀）");
        add("gui.contemporaryconstruction.preset.save.empty_name", "名稱不能為空");
        add("gui.contemporaryconstruction.preset.save.invalid_name", "名稱只能包含字母、數字、底線和中文字");
        add("gui.contemporaryconstruction.preset.save.exists", "預設已存在，請選擇其他名稱");
        add("gui.contemporaryconstruction.preset.save.success", "預設「%s」儲存成功");
        add("gui.contemporaryconstruction.preset.save.failed", "儲存失敗，請檢查檔案權限");

        // ===== GUI：紋理匯出 =====
        add("gui.contemporaryconstruction.export.title", "匯出材質");
        add("gui.contemporaryconstruction.export.settings", "材質匯出設定");
        add("gui.contemporaryconstruction.export.width", "寬度:");
        add("gui.contemporaryconstruction.export.height", "高度:");
        add("gui.contemporaryconstruction.export.format_png", "PNG");
        add("gui.contemporaryconstruction.export.format_jpg", "JPG");
        add("gui.contemporaryconstruction.export.filename", "檔案名稱:");
        add("gui.contemporaryconstruction.export.preview", "預覽");
        add("gui.contemporaryconstruction.export.export", "匯出");
        add("gui.contemporaryconstruction.export.cancel", "取消");
        add("gui.contemporaryconstruction.export.preview_hint", "點擊預覽");
        add("gui.contemporaryconstruction.export.success", "匯出成功: %s");
        add("gui.contemporaryconstruction.export.failed", "匯出失敗: %s");
        add("gui.contemporaryconstruction.export.preview_failed", "預覽產生失敗，無法匯出");

        // ===== 訊息提示 =====
        add("message.contemporaryconstruction.editing_by_other", "此標誌桿正被其他玩家編輯");
        add("message.contemporaryconstruction.waxed", "此標誌桿已打蠟，無法編輯");
        add("message.contemporaryconstruction.no_shapes", "找不到形狀，請將圖片放入 config/contemporaryconstruction/presets/persetelement/");
        add("message.contemporaryconstruction.not_editable", "此方塊標記為可編輯，但未實作 IEditableWithBrush");
    }
}