package com.iktaun.Contemporary_Construction.datagen;

import com.iktaun.Contemporary_Construction.contemporaryconstruction;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class ModZhCnLangProvider extends LanguageProvider {
    public ModZhCnLangProvider(PackOutput output) {
        super(output, contemporaryconstruction.MOD_ID, "zh_cn");
    }

    @Override
    protected void addTranslations() {
        // ===== 创造模式标签 =====
        add("itemGroup.road_blocks", "道路方块");
        add("itemGroup.signal_note", "道路标识");
        add("itemGroup.other", "其他");
        add("itemGroup.barrier", "护栏");
        add("itemGroup.light", "光源方块");
        add("itemGroup.column", "柱子");

        // ===== 方块 =====
        add("block.contemporaryconstruction.bitumen_block", "沥青块");
        add("block.contemporaryconstruction.white_straight_signal_bitumen_block", "直线白标沥青块");
        add("block.contemporaryconstruction.white_straight_signal_bitumen_block_no_connection", "直线白标沥青块(无连接)");
        add("block.contemporaryconstruction.white_slant_straight_signal_bitumen_block", "斜线白标沥青块");
        add("block.contemporaryconstruction.yellow_slant_straight_signal_bitumen_block", "斜线黄标沥青块");
        add("block.contemporaryconstruction.white_short_slant_straight_signal_bitumen_block", "短斜线白标沥青块");
        add("block.contemporaryconstruction.yellow_short_slant_straight_signal_bitumen_block", "短斜线黄标沥青块");
        add("block.contemporaryconstruction.white_double_short_slant_straight_signal_bitumen_block", "双短斜线白标沥青块");
        add("block.contemporaryconstruction.yellow_double_short_slant_straight_signal_bitumen_block", "双短斜线黄标沥青块");
        add("block.contemporaryconstruction.yellow_straight_signal_bitumen_block", "直线黄标沥青块");
        add("block.contemporaryconstruction.yellow_straight_signal_bitumen_block_no_connection", "直线黄标沥青块(无连接)");
        add("block.contemporaryconstruction.white_double_straight_signal_bitumen_block", "双线白标沥青块");
        add("block.contemporaryconstruction.yellow_double_straight_signal_bitumen_block", "双线黄标沥青块");
        add("block.contemporaryconstruction.white_character_li_signal_bitumen_block", "白标沥青块-礼");
        add("block.contemporaryconstruction.white_character_rang_signal_bitumen_block", "白标沥青块-让");
        add("block.contemporaryconstruction.white_character_xing_signal_bitumen_block", "白标沥青块-行");
        add("block.contemporaryconstruction.white_character_jin_signal_bitumen_block", "白标沥青块-禁");
        add("block.contemporaryconstruction.white_character_ren_signal_bitumen_block", "白标沥青块-人");
        add("block.contemporaryconstruction.white_character_zhi_signal_bitumen_block", "白标沥青块-止");
        add("block.contemporaryconstruction.white_character_tong_signal_bitumen_block", "白标沥青块-通");
        add("block.contemporaryconstruction.white_character_zhuan_signal_bitumen_block", "白标沥青块-专");
        add("block.contemporaryconstruction.white_character_yon_signal_bitumen_block", "白标沥青块-用");
        add("block.contemporaryconstruction.white_direction_signal_bitumen_block", "直行/掉头箭头白标沥青块");
        add("block.contemporaryconstruction.yellow_direction_signal_bitumen_block", "直行/掉头箭头黄标沥青块");
        add("block.contemporaryconstruction.yellow_straight_quarter_circle_signal_bitumen_block", "直行/掉头连接黄标沥青块");
        add("block.contemporaryconstruction.white_straight_quarter_circle_signal_bitumen_block", "直行/掉头连接白标沥青块");
        add("block.contemporaryconstruction.yellow_quarter_circle_signal_bitumen_block", "掉头连接圆黄标沥青块");
        add("block.contemporaryconstruction.white_quarter_circle_signal_bitumen_block", "掉头连接圆白标沥青块");
        add("block.contemporaryconstruction.white_right_direction_signal_bitumen_block", "右转箭头白标沥青块");
        add("block.contemporaryconstruction.white_right_signal_bitumen_block", "右转箭头连接白标沥青块");
        add("block.contemporaryconstruction.white_right_straight_signal_bitumen_block", "右转/直行白标沥青块");
        add("block.contemporaryconstruction.white_left_direction_signal_bitumen_block", "左转箭头白标沥青块");
        add("block.contemporaryconstruction.white_left_right_straight_signal_bitumen_block", "左/右转/直行连接白标沥青块");
        add("block.contemporaryconstruction.white_left_signal_bitumen_block", "左转箭头连接白标沥青块");
        add("block.contemporaryconstruction.white_left_straight_signal_bitumen_block", "左转/直行白标沥青块");
        add("block.contemporaryconstruction.yellow_left_direction_signal_bitumen_block", "左转箭头黄标沥青块");
        add("block.contemporaryconstruction.yellow_left_right_straight_signal_bitumen_block", "左/右转/直行黄标沥青块");
        add("block.contemporaryconstruction.yellow_left_signal_bitumen_block", "左转箭头连接黄标沥青块");
        add("block.contemporaryconstruction.yellow_left_straight_signal_bitumen_block", "左转/直行黄标沥青块");
        add("block.contemporaryconstruction.yellow_right_direction_signal_bitumen_block", "右转箭头黄标沥青块");
        add("block.contemporaryconstruction.yellow_right_signal_bitumen_block", "右转箭头连接黄标沥青块");
        add("block.contemporaryconstruction.yellow_right_straight_signal_bitumen_block", "右转、直行黄标沥青块");

        add("block.contemporaryconstruction.barrier_cyan", "青色栏杆");
        add("block.contemporaryconstruction.barrier_red", "红色栏杆");
        add("block.contemporaryconstruction.barrier_blue", "蓝色栏杆");
        add("block.contemporaryconstruction.road_fence", "栏杆");
        add("block.contemporaryconstruction.speed_bump", "减速带");
        add("block.contemporaryconstruction.crush_barrel", "防撞桶");
        add("block.contemporaryconstruction.red_crush_column", "红色防撞柱");
        add("block.contemporaryconstruction.yellow_crush_column", "黄色防撞柱");
        add("block.contemporaryconstruction.concrete_barrier", "混凝土护栏");
        add("block.contemporaryconstruction.yellow_concrete_barrier", "黄色混凝土护栏");
        add("block.contemporaryconstruction.street_light_pole_blue_base", "路灯杆基座-蓝");
        add("block.contemporaryconstruction.street_light_pole_white", "路灯杆-白");
        add("block.contemporaryconstruction.tactile_paving", "盲道");
        add("block.contemporaryconstruction.street_light_block_a", "路灯(A型)");
        add("block.contemporaryconstruction.sign_post_blank", "路牌");
        add("block.contemporaryconstruction.sign_post_pole", "路牌杆");

        // ===== 物品 =====
        add("item.contemporaryconstruction.brush", "刷子");                    // 修正：brush 是物品
        add("item.contemporaryconstruction.signal_board", "指示牌");            // 修正：signal_board 是物品

        // ===== GUI：主编辑界面 =====
        add("gui.contemporaryconstruction.edit.title", "编辑路牌");
        add("gui.contemporaryconstruction.edit.text", "+文字");
        add("gui.contemporaryconstruction.edit.image", "图片");
        add("gui.contemporaryconstruction.edit.shape", "形状");
        add("gui.contemporaryconstruction.edit.export", "导出");
        add("gui.contemporaryconstruction.edit.preset.save", "保存预设");
        add("gui.contemporaryconstruction.edit.preset.load", "加载预设");
        add("gui.contemporaryconstruction.edit.glow", "发光");
        add("gui.contemporaryconstruction.edit.no_glow", "不发光");
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
        add("gui.contemporaryconstruction.edit.info", "图层: %s/%s  文字: %s  图片: %s  形状: %s");

        // ===== GUI：图片选择 =====
        add("gui.contemporaryconstruction.image.title", "选择图片");
        add("gui.contemporaryconstruction.image.no_images", "无图片");
        add("gui.contemporaryconstruction.image.file_list", "文件列表:");
        add("gui.contemporaryconstruction.image.scale", "缩放:");
        add("gui.contemporaryconstruction.image.add", "添加");
        add("gui.contemporaryconstruction.image.cancel", "取消");
        add("gui.contemporaryconstruction.image.select_hint", "<- 从左侧选择图片");
        add("gui.contemporaryconstruction.image.preview_info", "%dx%d -> %dx%d (%.2fx)");

        // ===== GUI：形状选择 =====
        add("gui.contemporaryconstruction.shape.title", "选择形状");
        add("gui.contemporaryconstruction.shape.width", "宽度:");
        add("gui.contemporaryconstruction.shape.height", "高度:");
        add("gui.contemporaryconstruction.shape.add", "添加");
        add("gui.contemporaryconstruction.shape.cancel", "取消");
        add("gui.contemporaryconstruction.shape.current_color", "当前颜色");
        add("gui.contemporaryconstruction.shape.no_shapes", "没有形状，请放入 persetelement 目录");

        // ===== GUI：预设 =====
        add("gui.contemporaryconstruction.preset.title", "预设列表");
        add("gui.contemporaryconstruction.preset.load", "加载");
        add("gui.contemporaryconstruction.preset.delete", "删除");
        add("gui.contemporaryconstruction.preset.cancel", "取消");
        add("gui.contemporaryconstruction.preset.search", "搜索预设");
        add("gui.contemporaryconstruction.preset.empty", "没有预设，请在路牌编辑界面保存预设");

        add("gui.contemporaryconstruction.preset.save.title", "保存预设");
        add("gui.contemporaryconstruction.preset.save.name", "请输入预设名称:");
        add("gui.contemporaryconstruction.preset.save.save", "保存");
        add("gui.contemporaryconstruction.preset.save.cancel", "取消");
        add("gui.contemporaryconstruction.preset.save.info", "将保存 %s 个图层 (%s 文字, %s 图片, %s 形状)");
        add("gui.contemporaryconstruction.preset.save.empty_name", "名称不能为空");
        add("gui.contemporaryconstruction.preset.save.invalid_name", "名称只能包含字母、数字、下划线和中文字符");
        add("gui.contemporaryconstruction.preset.save.exists", "预设已存在，请换一个名称");
        add("gui.contemporaryconstruction.preset.save.success", "预设 \"%s\" 已保存");
        add("gui.contemporaryconstruction.preset.save.failed", "保存失败，请检查文件权限");

        // ===== GUI：纹理导出 =====
        add("gui.contemporaryconstruction.export.title", "导出纹理");
        add("gui.contemporaryconstruction.export.settings", "纹理导出设置");
        add("gui.contemporaryconstruction.export.width", "宽度:");
        add("gui.contemporaryconstruction.export.height", "高度:");
        add("gui.contemporaryconstruction.export.format_png", "PNG");
        add("gui.contemporaryconstruction.export.format_jpg", "JPG");
        add("gui.contemporaryconstruction.export.filename", "文件名:");
        add("gui.contemporaryconstruction.export.preview", "预览");
        add("gui.contemporaryconstruction.export.export", "导出");
        add("gui.contemporaryconstruction.export.cancel", "取消");
        add("gui.contemporaryconstruction.export.preview_hint", "点击预览");
        add("gui.contemporaryconstruction.export.success", "导出成功: %s");
        add("gui.contemporaryconstruction.export.failed", "导出失败: %s");
        add("gui.contemporaryconstruction.export.preview_failed", "预览生成失败，无法导出");

        // ===== 消息提示 =====
        add("message.contemporaryconstruction.editing_by_other", "此路牌正在被其他玩家编辑");
        add("message.contemporaryconstruction.waxed", "此路牌已蜡封，无法编辑");
        add("message.contemporaryconstruction.no_shapes", "没有形状，请将图片放入 config/contemporaryconstruction/presets/persetelement/");
        add("message.contemporaryconstruction.not_editable", "此方块标记为可编辑，但未实现 IEditableWithBrush 接口");
    }
}