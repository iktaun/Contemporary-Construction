package com.iktaun.Contemporary_Construction.datagen;

import com.iktaun.Contemporary_Construction.contemporaryconstruction;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class ModKoKrLangProvider extends LanguageProvider {
    public ModKoKrLangProvider(PackOutput output) {
        super(output, contemporaryconstruction.MOD_ID, "ko_kr");
    }

    @Override
    protected void addTranslations() {
        // ===== 크리에이티브 탭 =====
        add("itemGroup.road_blocks", "도로 블록");
        add("itemGroup.signal_note", "신호 표시");
        add("itemGroup.barrier", "방호벽");
        add("itemGroup.other", "기타");
        add("itemGroup.light", "조명");
        add("itemGroup.column", "기둥");

        // ===== 블록 =====
        add("block.contemporaryconstruction.bitumen_block", "아스팔트 블록");
        add("block.contemporaryconstruction.white_straight_signal_bitumen_block", "흰색 직진 신호 아스팔트 블록");
        add("block.contemporaryconstruction.white_straight_signal_bitumen_block_no_connection", "흰색 직진 신호 아스팔트 블록(연결 없음)");
        add("block.contemporaryconstruction.white_slant_straight_signal_bitumen_block", "흰색 사선 직진 신호 아스팔트 블록");
        add("block.contemporaryconstruction.yellow_short_slant_straight_signal_bitumen_block", "노란색 짧은 사선 직진 신호 아스팔트 블록");
        add("block.contemporaryconstruction.white_short_slant_straight_signal_bitumen_block", "흰색 짧은 사선 직진 신호 아스팔트 블록");
        add("block.contemporaryconstruction.yellow_double_short_slant_straight_signal_bitumen_block", "노란색 이중 짧은 사선 직진 신호 아스팔트 블록");
        add("block.contemporaryconstruction.white_double_short_slant_straight_signal_bitumen_block", "흰색 이중 짧은 사선 직진 신호 아스팔트 블록");
        add("block.contemporaryconstruction.yellow_slant_straight_signal_bitumen_block", "노란색 사선 직진 신호 아스팔트 블록");
        add("block.contemporaryconstruction.yellow_straight_signal_bitumen_block", "노란색 직진 신호 아스팔트 블록");
        add("block.contemporaryconstruction.yellow_straight_signal_bitumen_block_no_connection", "노란색 직진 신호 아스팔트 블록(연결 없음)");
        add("block.contemporaryconstruction.white_double_straight_signal_bitumen_block", "흰색 이중 직진 신호 아스팔트 블록");
        add("block.contemporaryconstruction.yellow_double_straight_signal_bitumen_block", "노란색 이중 직진 신호 아스팔트 블록");
        add("block.contemporaryconstruction.white_character_li_signal_bitumen_block", "흰색 '礼' 문자 신호 아스팔트 블록");
        add("block.contemporaryconstruction.white_character_rang_signal_bitumen_block", "흰색 '让' 문자 신호 아스팔트 블록");
        add("block.contemporaryconstruction.white_character_xing_signal_bitumen_block", "흰색 '行' 문자 신호 아스팔트 블록");
        add("block.contemporaryconstruction.white_character_jin_signal_bitumen_block", "흰색 '禁' 문자 신호 아스팔트 블록");
        add("block.contemporaryconstruction.white_character_ren_signal_bitumen_block", "흰색 '人' 문자 신호 아스팔트 블록");
        add("block.contemporaryconstruction.white_character_zhi_signal_bitumen_block", "흰색 '直' 문자 신호 아스팔트 블록");
        add("block.contemporaryconstruction.white_character_tong_signal_bitumen_block", "흰색 '通' 문자 신호 아스팔트 블록");
        add("block.contemporaryconstruction.white_character_zhuan_signal_bitumen_block", "흰색 '转' 문자 신호 아스팔트 블록");
        add("block.contemporaryconstruction.white_character_yon_signal_bitumen_block", "흰색 '用' 문자 신호 아스팔트 블록");
        add("block.contemporaryconstruction.white_direction_signal_bitumen_block", "흰색 방향 신호 아스팔트 블록");
        add("block.contemporaryconstruction.yellow_direction_signal_bitumen_block", "노란색 방향 신호 아스팔트 블록");
        add("block.contemporaryconstruction.yellow_straight_quarter_circle_signal_bitumen_block", "노란색 직진 사분원 신호 아스팔트 블록");
        add("block.contemporaryconstruction.white_straight_quarter_circle_signal_bitumen_block", "흰색 직진 사분원 신호 아스팔트 블록");
        add("block.contemporaryconstruction.yellow_quarter_circle_signal_bitumen_block", "노란색 사분원 신호 아스팔트 블록");
        add("block.contemporaryconstruction.white_quarter_circle_signal_bitumen_block", "흰색 사분원 신호 아스팔트 블록");
        add("block.contemporaryconstruction.white_right_direction_signal_bitumen_block", "흰색 우측 방향 신호 아스팔트 블록");
        add("block.contemporaryconstruction.white_right_signal_bitumen_block", "흰색 우회전 신호 아스팔트 블록");
        add("block.contemporaryconstruction.white_right_straight_signal_bitumen_block", "흰색 우측 직진 신호 아스팔트 블록");
        add("block.contemporaryconstruction.white_left_direction_signal_bitumen_block", "흰색 좌측 방향 신호 아스팔트 블록");
        add("block.contemporaryconstruction.white_left_right_straight_signal_bitumen_block", "흰색 좌우 직진 신호 아스팔트 블록");
        add("block.contemporaryconstruction.white_left_signal_bitumen_block", "흰색 좌회전 신호 아스팔트 블록");
        add("block.contemporaryconstruction.white_left_straight_signal_bitumen_block", "흰색 좌측 직진 신호 아스팔트 블록");
        add("block.contemporaryconstruction.yellow_left_direction_signal_bitumen_block", "노란색 좌측 방향 신호 아스팔트 블록");
        add("block.contemporaryconstruction.yellow_left_right_straight_signal_bitumen_block", "노란색 좌우 직진 신호 아스팔트 블록");
        add("block.contemporaryconstruction.yellow_left_signal_bitumen_block", "노란색 좌회전 신호 아스팔트 블록");
        add("block.contemporaryconstruction.yellow_left_straight_signal_bitumen_block", "노란색 좌측 직진 신호 아스팔트 블록");
        add("block.contemporaryconstruction.yellow_right_direction_signal_bitumen_block", "노란색 우측 방향 신호 아스팔트 블록");
        add("block.contemporaryconstruction.yellow_right_signal_bitumen_block", "노란색 우회전 신호 아스팔트 블록");
        add("block.contemporaryconstruction.yellow_right_straight_signal_bitumen_block", "노란색 우측 직진 신호 아스팔트 블록");

        add("block.contemporaryconstruction.barrier_cyan", "청록색 방호벽");
        add("block.contemporaryconstruction.barrier_red", "빨간색 방호벽");
        add("block.contemporaryconstruction.barrier_blue", "파란색 방호벽");
        add("block.contemporaryconstruction.road_fence", "도로 펜스");
        add("block.contemporaryconstruction.speed_bump", "과속방지턱");
        add("block.contemporaryconstruction.crush_barrel", "충격 흡수 배럴");
        add("block.contemporaryconstruction.red_crush_column", "빨간색 충격 흡수 기둥");
        add("block.contemporaryconstruction.yellow_crush_column", "노란색 충격 흡수 기둥");
        add("block.contemporaryconstruction.concrete_barrier", "콘크리트 방호벽");
        add("block.contemporaryconstruction.yellow_concrete_barrier", "노란색 콘크리트 방호벽");
        add("block.contemporaryconstruction.tactile_paving", "점자 블록");
        add("block.contemporaryconstruction.street_light_pole_blue_base", "가로등 기둥 베이스-파란색");
        add("block.contemporaryconstruction.street_light_pole_white", "가로등 기둥-흰색");
        add("block.contemporaryconstruction.street_light_block_a", "가로등 블록(A)");
        add("block.contemporaryconstruction.sign_post_blank", "표지판 기둥");
        add("block.contemporaryconstruction.sign_post_pole", "표지판 폴");

        // ===== 아이템 =====
        add("item.contemporaryconstruction.brush", "브러시");
        add("item.contemporaryconstruction.signal_board", "신호판");

        // ===== GUI：메인 편집 화면 =====
        add("gui.contemporaryconstruction.edit.title", "표지판 편집");
        add("gui.contemporaryconstruction.edit.text", "+ 텍스트");
        add("gui.contemporaryconstruction.edit.image", "이미지");
        add("gui.contemporaryconstruction.edit.shape", "도형");
        add("gui.contemporaryconstruction.edit.export", "내보내기");
        add("gui.contemporaryconstruction.edit.preset.save", "프리셋 저장");
        add("gui.contemporaryconstruction.edit.preset.load", "프리셋 불러오기");
        add("gui.contemporaryconstruction.edit.glow", "발광");
        add("gui.contemporaryconstruction.edit.no_glow", "발광 없음");
        add("gui.contemporaryconstruction.edit.offset_x", "X:");
        add("gui.contemporaryconstruction.edit.offset_y", "Y:");
        add("gui.contemporaryconstruction.edit.offset_z", "Z:");
        add("gui.contemporaryconstruction.edit.rotate_x", "회전X:");
        add("gui.contemporaryconstruction.edit.rotate_z", "회전Z:");
        add("gui.contemporaryconstruction.edit.rotate_y", "회전Y:");
        add("gui.contemporaryconstruction.edit.scale_x", "크기X:");
        add("gui.contemporaryconstruction.edit.scale_y", "크기Y:");
        add("gui.contemporaryconstruction.edit.done", "완료");
        add("gui.contemporaryconstruction.edit.cancel", "취소");
        add("gui.contemporaryconstruction.edit.empty", "(비어 있음)");
        add("gui.contemporaryconstruction.edit.info", "레이어: %s/%s  텍스트: %s  이미지: %s  도형: %s");

        // ===== GUI：이미지 선택 =====
        add("gui.contemporaryconstruction.image.title", "이미지 선택");
        add("gui.contemporaryconstruction.image.no_images", "이미지 없음");
        add("gui.contemporaryconstruction.image.file_list", "파일 목록:");
        add("gui.contemporaryconstruction.image.scale", "크기:");
        add("gui.contemporaryconstruction.image.add", "추가");
        add("gui.contemporaryconstruction.image.cancel", "취소");
        add("gui.contemporaryconstruction.image.select_hint", "<- 왼쪽에서 이미지를 선택하세요");
        add("gui.contemporaryconstruction.image.preview_info", "%dx%d -> %dx%d (%.2fx)");

        // ===== GUI：도형 선택 =====
        add("gui.contemporaryconstruction.shape.title", "도형 선택");
        add("gui.contemporaryconstruction.shape.width", "너비:");
        add("gui.contemporaryconstruction.shape.height", "높이:");
        add("gui.contemporaryconstruction.shape.add", "추가");
        add("gui.contemporaryconstruction.shape.cancel", "취소");
        add("gui.contemporaryconstruction.shape.current_color", "현재 색상");
        add("gui.contemporaryconstruction.shape.no_shapes", "도형을 찾을 수 없습니다. persetelement 폴더에 넣어주세요");

        // ===== GUI：프리셋 =====
        add("gui.contemporaryconstruction.preset.title", "프리셋");
        add("gui.contemporaryconstruction.preset.load", "불러오기");
        add("gui.contemporaryconstruction.preset.delete", "삭제");
        add("gui.contemporaryconstruction.preset.cancel", "취소");
        add("gui.contemporaryconstruction.preset.search", "프리셋 검색");
        add("gui.contemporaryconstruction.preset.empty", "프리셋이 없습니다. 표지판 편집기에서 저장해주세요");

        add("gui.contemporaryconstruction.preset.save.title", "프리셋 저장");
        add("gui.contemporaryconstruction.preset.save.name", "프리셋 이름 입력:");
        add("gui.contemporaryconstruction.preset.save.save", "저장");
        add("gui.contemporaryconstruction.preset.save.cancel", "취소");
        add("gui.contemporaryconstruction.preset.save.info", "%s개 레이어 저장 중 (%s 텍스트, %s 이미지, %s 도형)");
        add("gui.contemporaryconstruction.preset.save.empty_name", "이름은 비워둘 수 없습니다");
        add("gui.contemporaryconstruction.preset.save.invalid_name", "이름에는 문자, 숫자, 밑줄, 중국어만 사용할 수 있습니다");
        add("gui.contemporaryconstruction.preset.save.exists", "프리셋이 이미 존재합니다. 다른 이름을 선택하세요");
        add("gui.contemporaryconstruction.preset.save.success", "프리셋 \"%s\" 저장 성공");
        add("gui.contemporaryconstruction.preset.save.failed", "저장 실패. 파일 권한을 확인하세요");

        // ===== GUI：텍스처 내보내기 =====
        add("gui.contemporaryconstruction.export.title", "텍스처 내보내기");
        add("gui.contemporaryconstruction.export.settings", "텍스처 내보내기 설정");
        add("gui.contemporaryconstruction.export.width", "너비:");
        add("gui.contemporaryconstruction.export.height", "높이:");
        add("gui.contemporaryconstruction.export.format_png", "PNG");
        add("gui.contemporaryconstruction.export.format_jpg", "JPG");
        add("gui.contemporaryconstruction.export.filename", "파일 이름:");
        add("gui.contemporaryconstruction.export.preview", "미리보기");
        add("gui.contemporaryconstruction.export.export", "내보내기");
        add("gui.contemporaryconstruction.export.cancel", "취소");
        add("gui.contemporaryconstruction.export.preview_hint", "미리보기 클릭");
        add("gui.contemporaryconstruction.export.success", "내보내기 성공: %s");
        add("gui.contemporaryconstruction.export.failed", "내보내기 실패: %s");
        add("gui.contemporaryconstruction.export.preview_failed", "미리보기 생성 실패. 내보낼 수 없습니다");

        // ===== 메시지 =====
        add("message.contemporaryconstruction.editing_by_other", "이 표지판은 다른 플레이어가 편집 중입니다");
        add("message.contemporaryconstruction.waxed", "이 표지판은 왁스 처리되어 편집할 수 없습니다");
        add("message.contemporaryconstruction.no_shapes", "도형을 찾을 수 없습니다. config/contemporaryconstruction/presets/persetelement/에 이미지를 넣어주세요");
        add("message.contemporaryconstruction.not_editable", "이 블록은 편집 가능으로 표시되었지만 IEditableWithBrush를 구현하지 않았습니다");

        add("gui.contemporaryconstruction.edit.color", "🎨 색상");
        add("gui.contemporaryconstruction.color.title", "색상 선택");
        add("gui.contemporaryconstruction.color.ok", "확인");
        add("gui.contemporaryconstruction.color.cancel", "취소");
        add("gui.contemporaryconstruction.color.invalid", "잘못된 16진수 색상");

        // ===== GUI：글꼴 선택 =====
        add("gui.contemporaryconstruction.edit.font", "글꼴");
        add("gui.contemporaryconstruction.font.title", "글꼴 선택");
        add("gui.contemporaryconstruction.font.empty", "사용자 정의 글꼴을 찾을 수 없습니다. config/contemporaryconstruction/fonts/에 .ttf 파일을 넣어주세요");
        add("gui.contemporaryconstruction.font.vanilla", "기본 글꼴 사용");
        add("gui.contemporaryconstruction.font.cancel", "취소");
    }
}