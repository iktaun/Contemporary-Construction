package com.iktaun.Contemporary_Construction.datagen;

import com.iktaun.Contemporary_Construction.contemporaryconstruction;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class ModRuRuLangProvider extends LanguageProvider {
    public ModRuRuLangProvider(PackOutput output) {
        super(output, contemporaryconstruction.MOD_ID, "ru_ru");
    }

    @Override
    protected void addTranslations() {
        // ===== Вкладки креатива =====
        add("itemGroup.road_blocks", "Дорожные блоки");
        add("itemGroup.signal_note", "Сигнальная разметка");
        add("itemGroup.barrier", "Барьер");
        add("itemGroup.other", "Прочее");
        add("itemGroup.light", "Освещение");
        add("itemGroup.column", "Колонна");

        // ===== Блоки =====
        add("block.contemporaryconstruction.bitumen_block", "Битумный блок");
        add("block.contemporaryconstruction.white_straight_signal_bitumen_block", "Белый битумный блок с сигналом «прямо»");
        add("block.contemporaryconstruction.white_straight_signal_bitumen_block_no_connection", "Белый битумный блок с сигналом «прямо» (без соединения)");
        add("block.contemporaryconstruction.white_slant_straight_signal_bitumen_block", "Белый битумный блок с наклонным сигналом «прямо»");
        add("block.contemporaryconstruction.yellow_short_slant_straight_signal_bitumen_block", "Жёлтый битумный блок с коротким наклонным сигналом «прямо»");
        add("block.contemporaryconstruction.white_short_slant_straight_signal_bitumen_block", "Белый битумный блок с коротким наклонным сигналом «прямо»");
        add("block.contemporaryconstruction.yellow_double_short_slant_straight_signal_bitumen_block", "Жёлтый битумный блок с двойным коротким наклонным сигналом «прямо»");
        add("block.contemporaryconstruction.white_double_short_slant_straight_signal_bitumen_block", "Белый битумный блок с двойным коротким наклонным сигналом «прямо»");
        add("block.contemporaryconstruction.yellow_slant_straight_signal_bitumen_block", "Жёлтый битумный блок с наклонным сигналом «прямо»");
        add("block.contemporaryconstruction.yellow_straight_signal_bitumen_block", "Жёлтый битумный блок с сигналом «прямо»");
        add("block.contemporaryconstruction.yellow_straight_signal_bitumen_block_no_connection", "Жёлтый битумный блок с сигналом «прямо» (без соединения)");
        add("block.contemporaryconstruction.white_double_straight_signal_bitumen_block", "Белый битумный блок с двойным сигналом «прямо»");
        add("block.contemporaryconstruction.yellow_double_straight_signal_bitumen_block", "Жёлтый битумный блок с двойным сигналом «прямо»");
        add("block.contemporaryconstruction.white_character_li_signal_bitumen_block", "Белый битумный блок с иероглифом Li");
        add("block.contemporaryconstruction.white_character_rang_signal_bitumen_block", "Белый битумный блок с иероглифом Rang");
        add("block.contemporaryconstruction.white_character_xing_signal_bitumen_block", "Белый битумный блок с иероглифом Xing");
        add("block.contemporaryconstruction.white_character_jin_signal_bitumen_block", "Белый битумный блок с иероглифом Jin");
        add("block.contemporaryconstruction.white_character_ren_signal_bitumen_block", "Белый битумный блок с иероглифом Ren");
        add("block.contemporaryconstruction.white_character_zhi_signal_bitumen_block", "Белый битумный блок с иероглифом Zhi");
        add("block.contemporaryconstruction.white_character_tong_signal_bitumen_block", "Белый битумный блок с иероглифом Tong");
        add("block.contemporaryconstruction.white_character_zhuan_signal_bitumen_block", "Белый битумный блок с иероглифом Zhuan");
        add("block.contemporaryconstruction.white_character_yon_signal_bitumen_block", "Белый битумный блок с иероглифом Yong");
        add("block.contemporaryconstruction.white_direction_signal_bitumen_block", "Белый битумный блок с указателем направления");
        add("block.contemporaryconstruction.yellow_direction_signal_bitumen_block", "Жёлтый битумный блок с указателем направления");
        add("block.contemporaryconstruction.yellow_straight_quarter_circle_signal_bitumen_block", "Жёлтый битумный блок с четвертькругом «прямо»");
        add("block.contemporaryconstruction.white_straight_quarter_circle_signal_bitumen_block", "Белый битумный блок с четвертькругом «прямо»");
        add("block.contemporaryconstruction.yellow_quarter_circle_signal_bitumen_block", "Жёлтый битумный блок с четвертькругом");
        add("block.contemporaryconstruction.white_quarter_circle_signal_bitumen_block", "Белый битумный блок с четвертькругом");
        add("block.contemporaryconstruction.white_right_direction_signal_bitumen_block", "Белый битумный блок с указателем вправо");
        add("block.contemporaryconstruction.white_right_signal_bitumen_block", "Белый битумный блок с сигналом «направо»");
        add("block.contemporaryconstruction.white_right_straight_signal_bitumen_block", "Белый битумный блок с сигналом «направо и прямо»");
        add("block.contemporaryconstruction.white_left_direction_signal_bitumen_block", "Белый битумный блок с указателем влево");
        add("block.contemporaryconstruction.white_left_right_straight_signal_bitumen_block", "Белый битумный блок с сигналом «влево, вправо и прямо»");
        add("block.contemporaryconstruction.white_left_signal_bitumen_block", "Белый битумный блок с сигналом «налево»");
        add("block.contemporaryconstruction.white_left_straight_signal_bitumen_block", "Белый битумный блок с сигналом «налево и прямо»");
        add("block.contemporaryconstruction.yellow_left_direction_signal_bitumen_block", "Жёлтый битумный блок с указателем влево");
        add("block.contemporaryconstruction.yellow_left_right_straight_signal_bitumen_block", "Жёлтый битумный блок с сигналом «влево, вправо и прямо»");
        add("block.contemporaryconstruction.yellow_left_signal_bitumen_block", "Жёлтый битумный блок с сигналом «налево»");
        add("block.contemporaryconstruction.yellow_left_straight_signal_bitumen_block", "Жёлтый битумный блок с сигналом «налево и прямо»");
        add("block.contemporaryconstruction.yellow_right_direction_signal_bitumen_block", "Жёлтый битумный блок с указателем вправо");
        add("block.contemporaryconstruction.yellow_right_signal_bitumen_block", "Жёлтый битумный блок с сигналом «направо»");
        add("block.contemporaryconstruction.yellow_right_straight_signal_bitumen_block", "Жёлтый битумный блок с сигналом «направо и прямо»");

        add("block.contemporaryconstruction.barrier_cyan", "Голубой барьер");
        add("block.contemporaryconstruction.barrier_red", "Красный барьер");
        add("block.contemporaryconstruction.barrier_blue", "Синий барьер");
        add("block.contemporaryconstruction.road_fence", "Дорожное ограждение");
        add("block.contemporaryconstruction.speed_bump", "Лежачий полицейский");
        add("block.contemporaryconstruction.crush_barrel", "Аварийный бочонок");
        add("block.contemporaryconstruction.red_crush_column", "Красная аварийная стойка");
        add("block.contemporaryconstruction.yellow_crush_column", "Жёлтая аварийная стойка");
        add("block.contemporaryconstruction.concrete_barrier", "Бетонный барьер");
        add("block.contemporaryconstruction.yellow_concrete_barrier", "Жёлтый бетонный барьер");
        add("block.contemporaryconstruction.tactile_paving", "Тактильная плитка");
        add("block.contemporaryconstruction.street_light_pole_blue_base", "Основание фонарного столба - синее");
        add("block.contemporaryconstruction.street_light_pole_white", "Фонарный столб - белый");
        add("block.contemporaryconstruction.street_light_block_a", "Блок фонаря (A)");
        add("block.contemporaryconstruction.sign_post_blank", "Стойка для знака");
        add("block.contemporaryconstruction.sign_post_pole", "Столб для знака");

        // ===== Предметы =====
        add("item.contemporaryconstruction.brush", "Кисть");
        add("item.contemporaryconstruction.signal_board", "Сигнальный щит");

        // ===== GUI: Главное редактирование =====
        add("gui.contemporaryconstruction.edit.title", "Редактировать стойку знака");
        add("gui.contemporaryconstruction.edit.text", "+ Текст");
        add("gui.contemporaryconstruction.edit.image", "Изображение");
        add("gui.contemporaryconstruction.edit.shape", "Форма");
        add("gui.contemporaryconstruction.edit.export", "Экспорт");
        add("gui.contemporaryconstruction.edit.preset.save", "Сохранить пресет");
        add("gui.contemporaryconstruction.edit.preset.load", "Загрузить пресет");
        add("gui.contemporaryconstruction.edit.glow", "Свечение");
        add("gui.contemporaryconstruction.edit.no_glow", "Без свечения");
        add("gui.contemporaryconstruction.edit.offset_x", "X:");
        add("gui.contemporaryconstruction.edit.offset_y", "Y:");
        add("gui.contemporaryconstruction.edit.offset_z", "Z:");
        add("gui.contemporaryconstruction.edit.rotate_x", "Поворот X:");
        add("gui.contemporaryconstruction.edit.rotate_z", "Поворот Z:");
        add("gui.contemporaryconstruction.edit.rotate_y", "Поворот Y:");
        add("gui.contemporaryconstruction.edit.scale_x", "Масштаб X:");
        add("gui.contemporaryconstruction.edit.scale_y", "Масштаб Y:");
        add("gui.contemporaryconstruction.edit.done", "Готово");
        add("gui.contemporaryconstruction.edit.cancel", "Отмена");
        add("gui.contemporaryconstruction.edit.empty", "(пусто)");
        add("gui.contemporaryconstruction.edit.info", "Слой: %s/%s  Текст: %s  Изображение: %s  Форма: %s");

        // ===== GUI: Выбор изображения =====
        add("gui.contemporaryconstruction.image.title", "Выбрать изображение");
        add("gui.contemporaryconstruction.image.no_images", "Нет изображений");
        add("gui.contemporaryconstruction.image.file_list", "Список файлов:");
        add("gui.contemporaryconstruction.image.scale", "Масштаб:");
        add("gui.contemporaryconstruction.image.add", "Добавить");
        add("gui.contemporaryconstruction.image.cancel", "Отмена");
        add("gui.contemporaryconstruction.image.select_hint", "<- Выберите изображение слева");
        add("gui.contemporaryconstruction.image.preview_info", "%dx%d -> %dx%d (%.2fx)");

        // ===== GUI: Выбор формы =====
        add("gui.contemporaryconstruction.shape.title", "Выбрать форму");
        add("gui.contemporaryconstruction.shape.width", "Ширина:");
        add("gui.contemporaryconstruction.shape.height", "Высота:");
        add("gui.contemporaryconstruction.shape.add", "Добавить");
        add("gui.contemporaryconstruction.shape.cancel", "Отмена");
        add("gui.contemporaryconstruction.shape.current_color", "Текущий цвет");
        add("gui.contemporaryconstruction.shape.no_shapes", "Формы не найдены, поместите их в папку persetelement");

        // ===== GUI: Пресеты =====
        add("gui.contemporaryconstruction.preset.title", "Пресеты");
        add("gui.contemporaryconstruction.preset.load", "Загрузить");
        add("gui.contemporaryconstruction.preset.delete", "Удалить");
        add("gui.contemporaryconstruction.preset.cancel", "Отмена");
        add("gui.contemporaryconstruction.preset.search", "Поиск пресетов");
        add("gui.contemporaryconstruction.preset.empty", "Нет пресетов, сохраните один в редакторе стойки знака");

        add("gui.contemporaryconstruction.preset.save.title", "Сохранить пресет");
        add("gui.contemporaryconstruction.preset.save.name", "Введите имя пресета:");
        add("gui.contemporaryconstruction.preset.save.save", "Сохранить");
        add("gui.contemporaryconstruction.preset.save.cancel", "Отмена");
        add("gui.contemporaryconstruction.preset.save.info", "Сохранение %s слоя(ёв) (%s текст, %s изображений, %s форм)");
        add("gui.contemporaryconstruction.preset.save.empty_name", "Имя не может быть пустым");
        add("gui.contemporaryconstruction.preset.save.invalid_name", "Имя может содержать только буквы, цифры, подчёркивания и китайские иероглифы");
        add("gui.contemporaryconstruction.preset.save.exists", "Пресет уже существует, выберите другое имя");
        add("gui.contemporaryconstruction.preset.save.success", "Пресет \"%s\" успешно сохранён");
        add("gui.contemporaryconstruction.preset.save.failed", "Ошибка сохранения, проверьте права доступа к файлам");

        // ===== GUI: Экспорт текстуры =====
        add("gui.contemporaryconstruction.export.title", "Экспорт текстуры");
        add("gui.contemporaryconstruction.export.settings", "Настройки экспорта текстуры");
        add("gui.contemporaryconstruction.export.width", "Ширина:");
        add("gui.contemporaryconstruction.export.height", "Высота:");
        add("gui.contemporaryconstruction.export.format_png", "PNG");
        add("gui.contemporaryconstruction.export.format_jpg", "JPG");
        add("gui.contemporaryconstruction.export.filename", "Имя файла:");
        add("gui.contemporaryconstruction.export.preview", "Предпросмотр");
        add("gui.contemporaryconstruction.export.export", "Экспорт");
        add("gui.contemporaryconstruction.export.cancel", "Отмена");
        add("gui.contemporaryconstruction.export.preview_hint", "Нажмите «Предпросмотр»");
        add("gui.contemporaryconstruction.export.success", "Экспорт успешен: %s");
        add("gui.contemporaryconstruction.export.failed", "Ошибка экспорта: %s");
        add("gui.contemporaryconstruction.export.preview_failed", "Не удалось создать предпросмотр, экспорт невозможен");

        // ===== Сообщения =====
        add("message.contemporaryconstruction.editing_by_other", "Эта стойка знака редактируется другим игроком");
        add("message.contemporaryconstruction.waxed", "Эта стойка знака вощеная и не может быть отредактирована");
        add("message.contemporaryconstruction.no_shapes", "Формы не найдены, поместите изображения в config/contemporaryconstruction/presets/persetelement/");
        add("message.contemporaryconstruction.not_editable", "Этот блок помечен как редактируемый, но не реализует IEditableWithBrush");

        add("gui.contemporaryconstruction.edit.color", "🎨 Цвет");
        add("gui.contemporaryconstruction.color.title", "Выбор цвета");
        add("gui.contemporaryconstruction.color.ok", "OK");
        add("gui.contemporaryconstruction.color.cancel", "Отмена");
        add("gui.contemporaryconstruction.color.invalid", "Неверный шестнадцатеричный цвет");

        // ===== GUI: Выбор шрифта =====
        add("gui.contemporaryconstruction.edit.font", "Шрифт");
        add("gui.contemporaryconstruction.font.title", "Выбор шрифта");
        add("gui.contemporaryconstruction.font.empty", "Пользовательские шрифты не найдены. Поместите файлы .ttf в config/contemporaryconstruction/fonts/");
        add("gui.contemporaryconstruction.font.vanilla", "Использовать стандартный шрифт");
        add("gui.contemporaryconstruction.font.cancel", "Отмена");
    }
}