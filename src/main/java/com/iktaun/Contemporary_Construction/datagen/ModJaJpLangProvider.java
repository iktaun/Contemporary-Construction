package com.iktaun.Contemporary_Construction.datagen;

import com.iktaun.Contemporary_Construction.contemporaryconstruction;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class ModJaJpLangProvider extends LanguageProvider {
    public ModJaJpLangProvider(PackOutput output) {
        super(output, contemporaryconstruction.MOD_ID, "ja_jp");
    }

    @Override
    protected void addTranslations() {
        // ===== クリエイティブタブ =====
        add("itemGroup.road_blocks", "道路ブロック");
        add("itemGroup.signal_note", "信号標示");
        add("itemGroup.barrier", "バリア");
        add("itemGroup.other", "その他");
        add("itemGroup.light", "ライト");
        add("itemGroup.column", "柱");

        // ===== ブロック =====
        add("block.contemporaryconstruction.bitumen_block", "アスファルトブロック");
        add("block.contemporaryconstruction.white_straight_signal_bitumen_block", "白直進信号アスファルトブロック");
        add("block.contemporaryconstruction.white_straight_signal_bitumen_block_no_connection", "白直進信号アスファルトブロック（接続なし）");
        add("block.contemporaryconstruction.white_slant_straight_signal_bitumen_block", "白斜め直進信号アスファルトブロック");
        add("block.contemporaryconstruction.yellow_short_slant_straight_signal_bitumen_block", "黄短斜め直進信号アスファルトブロック");
        add("block.contemporaryconstruction.white_short_slant_straight_signal_bitumen_block", "白短斜め直進信号アスファルトブロック");
        add("block.contemporaryconstruction.yellow_double_short_slant_straight_signal_bitumen_block", "黄二重短斜め直進信号アスファルトブロック");
        add("block.contemporaryconstruction.white_double_short_slant_straight_signal_bitumen_block", "白二重短斜め直進信号アスファルトブロック");
        add("block.contemporaryconstruction.yellow_slant_straight_signal_bitumen_block", "黄斜め直進信号アスファルトブロック");
        add("block.contemporaryconstruction.yellow_straight_signal_bitumen_block", "黄直進信号アスファルトブロック");
        add("block.contemporaryconstruction.yellow_straight_signal_bitumen_block_no_connection", "黄直進信号アスファルトブロック（接続なし）");
        add("block.contemporaryconstruction.white_double_straight_signal_bitumen_block", "白二重直進信号アスファルトブロック");
        add("block.contemporaryconstruction.yellow_double_straight_signal_bitumen_block", "黄二重直進信号アスファルトブロック");
        add("block.contemporaryconstruction.white_character_li_signal_bitumen_block", "白文字「礼」信号アスファルトブロック");
        add("block.contemporaryconstruction.white_character_rang_signal_bitumen_block", "白文字「譲」信号アスファルトブロック");
        add("block.contemporaryconstruction.white_character_xing_signal_bitumen_block", "白文字「行」信号アスファルトブロック");
        add("block.contemporaryconstruction.white_character_jin_signal_bitumen_block", "白文字「禁」信号アスファルトブロック");
        add("block.contemporaryconstruction.white_character_ren_signal_bitumen_block", "白文字「人」信号アスファルトブロック");
        add("block.contemporaryconstruction.white_character_zhi_signal_bitumen_block", "白文字「直」信号アスファルトブロック");
        add("block.contemporaryconstruction.white_character_tong_signal_bitumen_block", "白文字「通」信号アスファルトブロック");
        add("block.contemporaryconstruction.white_character_zhuan_signal_bitumen_block", "白文字「転」信号アスファルトブロック");
        add("block.contemporaryconstruction.white_character_yon_signal_bitumen_block", "白文字「用」信号アスファルトブロック");
        add("block.contemporaryconstruction.white_direction_signal_bitumen_block", "白方向信号アスファルトブロック");
        add("block.contemporaryconstruction.yellow_direction_signal_bitumen_block", "黄方向信号アスファルトブロック");
        add("block.contemporaryconstruction.yellow_straight_quarter_circle_signal_bitumen_block", "黄直進四分円信号アスファルトブロック");
        add("block.contemporaryconstruction.white_straight_quarter_circle_signal_bitumen_block", "白直進四分円信号アスファルトブロック");
        add("block.contemporaryconstruction.yellow_quarter_circle_signal_bitumen_block", "黄四分円信号アスファルトブロック");
        add("block.contemporaryconstruction.white_quarter_circle_signal_bitumen_block", "白四分円信号アスファルトブロック");
        add("block.contemporaryconstruction.white_right_direction_signal_bitumen_block", "白右方向信号アスファルトブロック");
        add("block.contemporaryconstruction.white_right_signal_bitumen_block", "白右折信号アスファルトブロック");
        add("block.contemporaryconstruction.white_right_straight_signal_bitumen_block", "白右直進信号アスファルトブロック");
        add("block.contemporaryconstruction.white_left_direction_signal_bitumen_block", "白左方向信号アスファルトブロック");
        add("block.contemporaryconstruction.white_left_right_straight_signal_bitumen_block", "白左右直進信号アスファルトブロック");
        add("block.contemporaryconstruction.white_left_signal_bitumen_block", "白左折信号アスファルトブロック");
        add("block.contemporaryconstruction.white_left_straight_signal_bitumen_block", "白左直進信号アスファルトブロック");
        add("block.contemporaryconstruction.yellow_left_direction_signal_bitumen_block", "黄左方向信号アスファルトブロック");
        add("block.contemporaryconstruction.yellow_left_right_straight_signal_bitumen_block", "黄左右直進信号アスファルトブロック");
        add("block.contemporaryconstruction.yellow_left_signal_bitumen_block", "黄左折信号アスファルトブロック");
        add("block.contemporaryconstruction.yellow_left_straight_signal_bitumen_block", "黄左直進信号アスファルトブロック");
        add("block.contemporaryconstruction.yellow_right_direction_signal_bitumen_block", "黄右方向信号アスファルトブロック");
        add("block.contemporaryconstruction.yellow_right_signal_bitumen_block", "黄右折信号アスファルトブロック");
        add("block.contemporaryconstruction.yellow_right_straight_signal_bitumen_block", "黄右直進信号アスファルトブロック");

        add("block.contemporaryconstruction.barrier_cyan", "シアンバリア");
        add("block.contemporaryconstruction.barrier_red", "赤バリア");
        add("block.contemporaryconstruction.barrier_blue", "青バリア");
        add("block.contemporaryconstruction.road_fence", "道路フェンス");
        add("block.contemporaryconstruction.speed_bump", "スピードバンプ");
        add("block.contemporaryconstruction.crush_barrel", "クラッシュバレル");
        add("block.contemporaryconstruction.red_crush_column", "赤クラッシュコラム");
        add("block.contemporaryconstruction.yellow_crush_column", "黄クラッシュコラム");
        add("block.contemporaryconstruction.concrete_barrier", "コンクリートバリア");
        add("block.contemporaryconstruction.yellow_concrete_barrier", "黄コンクリートバリア");
        add("block.contemporaryconstruction.tactile_paving", "点字ブロック");
        add("block.contemporaryconstruction.street_light_pole_blue_base", "街灯ポールベース-青");
        add("block.contemporaryconstruction.street_light_pole_white", "街灯ポール-白");
        add("block.contemporaryconstruction.street_light_block_a", "街灯ブロック(A)");
        add("block.contemporaryconstruction.sign_post_blank", "標識柱");
        add("block.contemporaryconstruction.sign_post_pole", "標識ポール");

        // ===== アイテム =====
        add("item.contemporaryconstruction.brush", "ブラシ");
        add("item.contemporaryconstruction.signal_board", "信号板");

        // ===== GUI：メイン編集画面 =====
        add("gui.contemporaryconstruction.edit.title", "標識柱を編集");
        add("gui.contemporaryconstruction.edit.text", "+ テキスト");
        add("gui.contemporaryconstruction.edit.image", "画像");
        add("gui.contemporaryconstruction.edit.shape", "図形");
        add("gui.contemporaryconstruction.edit.export", "エクスポート");
        add("gui.contemporaryconstruction.edit.preset.save", "プリセットを保存");
        add("gui.contemporaryconstruction.edit.preset.load", "プリセットを読み込む");
        add("gui.contemporaryconstruction.edit.glow", "発光");
        add("gui.contemporaryconstruction.edit.no_glow", "発光なし");
        add("gui.contemporaryconstruction.edit.offset_x", "X:");
        add("gui.contemporaryconstruction.edit.offset_y", "Y:");
        add("gui.contemporaryconstruction.edit.offset_z", "Z:");
        add("gui.contemporaryconstruction.edit.rotate_x", "回転X:");
        add("gui.contemporaryconstruction.edit.rotate_z", "回転Z:");
        add("gui.contemporaryconstruction.edit.rotate_y", "回転Y:");
        add("gui.contemporaryconstruction.edit.scale_x", "拡大X:");
        add("gui.contemporaryconstruction.edit.scale_y", "拡大Y:");
        add("gui.contemporaryconstruction.edit.done", "完了");
        add("gui.contemporaryconstruction.edit.cancel", "キャンセル");
        add("gui.contemporaryconstruction.edit.empty", "(空)");
        add("gui.contemporaryconstruction.edit.info", "レイヤー: %s/%s  テキスト: %s  画像: %s  図形: %s");

        // ===== GUI：画像選択 =====
        add("gui.contemporaryconstruction.image.title", "画像を選択");
        add("gui.contemporaryconstruction.image.no_images", "画像なし");
        add("gui.contemporaryconstruction.image.file_list", "ファイル一覧:");
        add("gui.contemporaryconstruction.image.scale", "拡大縮小:");
        add("gui.contemporaryconstruction.image.add", "追加");
        add("gui.contemporaryconstruction.image.cancel", "キャンセル");
        add("gui.contemporaryconstruction.image.select_hint", "<- 左から画像を選択");
        add("gui.contemporaryconstruction.image.preview_info", "%dx%d -> %dx%d (%.2fx)");

        // ===== GUI：図形選択 =====
        add("gui.contemporaryconstruction.shape.title", "図形を選択");
        add("gui.contemporaryconstruction.shape.width", "幅:");
        add("gui.contemporaryconstruction.shape.height", "高さ:");
        add("gui.contemporaryconstruction.shape.add", "追加");
        add("gui.contemporaryconstruction.shape.cancel", "キャンセル");
        add("gui.contemporaryconstruction.shape.current_color", "現在の色");
        add("gui.contemporaryconstruction.shape.no_shapes", "図形が見つかりません。persetelement フォルダーに入れてください");

        // ===== GUI：プリセット =====
        add("gui.contemporaryconstruction.preset.title", "プリセット");
        add("gui.contemporaryconstruction.preset.load", "読み込む");
        add("gui.contemporaryconstruction.preset.delete", "削除");
        add("gui.contemporaryconstruction.preset.cancel", "キャンセル");
        add("gui.contemporaryconstruction.preset.search", "プリセットを検索");
        add("gui.contemporaryconstruction.preset.empty", "プリセットがありません。標識柱エディターで保存してください");

        add("gui.contemporaryconstruction.preset.save.title", "プリセットを保存");
        add("gui.contemporaryconstruction.preset.save.name", "プリセット名を入力:");
        add("gui.contemporaryconstruction.preset.save.save", "保存");
        add("gui.contemporaryconstruction.preset.save.cancel", "キャンセル");
        add("gui.contemporaryconstruction.preset.save.info", "%s レイヤーを保存中（テキスト %s、画像 %s、図形 %s）");
        add("gui.contemporaryconstruction.preset.save.empty_name", "名前を空にできません");
        add("gui.contemporaryconstruction.preset.save.invalid_name", "名前に使用できるのは英字、数字、アンダースコア、中国語のみです");
        add("gui.contemporaryconstruction.preset.save.exists", "プリセットは既に存在します。別の名前を選んでください");
        add("gui.contemporaryconstruction.preset.save.success", "プリセット「%s」を保存しました");
        add("gui.contemporaryconstruction.preset.save.failed", "保存に失敗しました。ファイル権限を確認してください");

        // ===== GUI：テクスチャ書き出し =====
        add("gui.contemporaryconstruction.export.title", "テクスチャをエクスポート");
        add("gui.contemporaryconstruction.export.settings", "テクスチャエクスポート設定");
        add("gui.contemporaryconstruction.export.width", "幅:");
        add("gui.contemporaryconstruction.export.height", "高さ:");
        add("gui.contemporaryconstruction.export.format_png", "PNG");
        add("gui.contemporaryconstruction.export.format_jpg", "JPG");
        add("gui.contemporaryconstruction.export.filename", "ファイル名:");
        add("gui.contemporaryconstruction.export.preview", "プレビュー");
        add("gui.contemporaryconstruction.export.export", "エクスポート");
        add("gui.contemporaryconstruction.export.cancel", "キャンセル");
        add("gui.contemporaryconstruction.export.preview_hint", "プレビューをクリック");
        add("gui.contemporaryconstruction.export.success", "エクスポート成功: %s");
        add("gui.contemporaryconstruction.export.failed", "エクスポート失敗: %s");
        add("gui.contemporaryconstruction.export.preview_failed", "プレビュー生成に失敗しました。エクスポートできません");

        // ===== メッセージ =====
        add("message.contemporaryconstruction.editing_by_other", "この標識柱は他のプレイヤーが編集中です");
        add("message.contemporaryconstruction.waxed", "この標識柱はワックス済みで編集できません");
        add("message.contemporaryconstruction.no_shapes", "図形が見つかりません。config/contemporaryconstruction/presets/persetelement/ に画像を入れてください");
        add("message.contemporaryconstruction.not_editable", "このブロックは編集可能とマークされていますが、IEditableWithBrush を実装していません");

        add("gui.contemporaryconstruction.edit.color", "🎨 色");
        add("gui.contemporaryconstruction.color.title", "色選択");
        add("gui.contemporaryconstruction.color.ok", "OK");
        add("gui.contemporaryconstruction.color.cancel", "キャンセル");
        add("gui.contemporaryconstruction.color.invalid", "無効な16進数カラー");

        // ===== GUI：フォント選択 =====
        add("gui.contemporaryconstruction.edit.font", "フォント");
        add("gui.contemporaryconstruction.font.title", "フォントを選択");
        add("gui.contemporaryconstruction.font.empty", "カスタムフォントが見つかりません。config/contemporaryconstruction/fonts/ に .ttf ファイルを入れてください");
        add("gui.contemporaryconstruction.font.vanilla", "バニラフォントを使用");
        add("gui.contemporaryconstruction.font.cancel", "キャンセル");
    }
}