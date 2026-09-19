package com.iktaun.Contemporary_Construction.datagen;

import com.iktaun.Contemporary_Construction.contemporaryconstruction;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class ModDeDeLangProvider extends LanguageProvider {
    public ModDeDeLangProvider(PackOutput output) {
        super(output, contemporaryconstruction.MOD_ID, "de_de");
    }

    @Override
    protected void addTranslations() {
        // ===== Kreativmodus-Tabs =====
        add("itemGroup.road_blocks", "Straßenblöcke");
        add("itemGroup.signal_note", "Signalmarkierung");
        add("itemGroup.barrier", "Absperrung");
        add("itemGroup.other", "Sonstiges");
        add("itemGroup.light", "Licht");
        add("itemGroup.column", "Säule");

        // ===== Blöcke =====
        add("block.contemporaryconstruction.bitumen_block", "Bitumenblock");
        add("block.contemporaryconstruction.white_straight_signal_bitumen_block", "Weißer gerader Signal-Bitumenblock");
        add("block.contemporaryconstruction.white_straight_signal_bitumen_block_no_connection", "Weißer gerader Signal-Bitumenblock (keine Verbindung)");
        add("block.contemporaryconstruction.white_slant_straight_signal_bitumen_block", "Weißer schräger gerader Signal-Bitumenblock");
        add("block.contemporaryconstruction.yellow_short_slant_straight_signal_bitumen_block", "Gelber kurzer schräger gerader Signal-Bitumenblock");
        add("block.contemporaryconstruction.white_short_slant_straight_signal_bitumen_block", "Weißer kurzer schräger gerader Signal-Bitumenblock");
        add("block.contemporaryconstruction.yellow_double_short_slant_straight_signal_bitumen_block", "Gelber doppelter kurzer schräger gerader Signal-Bitumenblock");
        add("block.contemporaryconstruction.white_double_short_slant_straight_signal_bitumen_block", "Weißer doppelter kurzer schräger gerader Signal-Bitumenblock");
        add("block.contemporaryconstruction.yellow_slant_straight_signal_bitumen_block", "Gelber schräger gerader Signal-Bitumenblock");
        add("block.contemporaryconstruction.yellow_straight_signal_bitumen_block", "Gelber gerader Signal-Bitumenblock");
        add("block.contemporaryconstruction.yellow_straight_signal_bitumen_block_no_connection", "Gelber gerader Signal-Bitumenblock (keine Verbindung)");
        add("block.contemporaryconstruction.white_double_straight_signal_bitumen_block", "Weißer doppelter gerader Signal-Bitumenblock");
        add("block.contemporaryconstruction.yellow_double_straight_signal_bitumen_block", "Gelber doppelter gerader Signal-Bitumenblock");
        add("block.contemporaryconstruction.white_character_li_signal_bitumen_block", "Weißer Zeichen-Li-Signal-Bitumenblock");
        add("block.contemporaryconstruction.white_character_rang_signal_bitumen_block", "Weißer Zeichen-Rang-Signal-Bitumenblock");
        add("block.contemporaryconstruction.white_character_xing_signal_bitumen_block", "Weißer Zeichen-Xing-Signal-Bitumenblock");
        add("block.contemporaryconstruction.white_character_jin_signal_bitumen_block", "Weißer Zeichen-Jin-Signal-Bitumenblock");
        add("block.contemporaryconstruction.white_character_ren_signal_bitumen_block", "Weißer Zeichen-Ren-Signal-Bitumenblock");
        add("block.contemporaryconstruction.white_character_zhi_signal_bitumen_block", "Weißer Zeichen-Zhi-Signal-Bitumenblock");
        add("block.contemporaryconstruction.white_character_tong_signal_bitumen_block", "Weißer Zeichen-Tong-Signal-Bitumenblock");
        add("block.contemporaryconstruction.white_character_zhuan_signal_bitumen_block", "Weißer Zeichen-Zhuan-Signal-Bitumenblock");
        add("block.contemporaryconstruction.white_character_yon_signal_bitumen_block", "Weißer Zeichen-Yong-Signal-Bitumenblock");
        add("block.contemporaryconstruction.white_direction_signal_bitumen_block", "Weißer Richtungs-Signal-Bitumenblock");
        add("block.contemporaryconstruction.yellow_direction_signal_bitumen_block", "Gelber Richtungs-Signal-Bitumenblock");
        add("block.contemporaryconstruction.yellow_straight_quarter_circle_signal_bitumen_block", "Gelber gerader Viertelkreis-Signal-Bitumenblock");
        add("block.contemporaryconstruction.white_straight_quarter_circle_signal_bitumen_block", "Weißer gerader Viertelkreis-Signal-Bitumenblock");
        add("block.contemporaryconstruction.yellow_quarter_circle_signal_bitumen_block", "Gelber Viertelkreis-Signal-Bitumenblock");
        add("block.contemporaryconstruction.white_quarter_circle_signal_bitumen_block", "Weißer Viertelkreis-Signal-Bitumenblock");
        add("block.contemporaryconstruction.white_right_direction_signal_bitumen_block", "Weißer Rechtsrichtungs-Signal-Bitumenblock");
        add("block.contemporaryconstruction.white_right_signal_bitumen_block", "Weißer Rechtsabbiege-Signal-Bitumenblock");
        add("block.contemporaryconstruction.white_right_straight_signal_bitumen_block", "Weißer rechts-gerade Signal-Bitumenblock");
        add("block.contemporaryconstruction.white_left_direction_signal_bitumen_block", "Weißer Linksrichtungs-Signal-Bitumenblock");
        add("block.contemporaryconstruction.white_left_right_straight_signal_bitumen_block", "Weißer links-rechts-gerade Signal-Bitumenblock");
        add("block.contemporaryconstruction.white_left_signal_bitumen_block", "Weißer Linksabbiege-Signal-Bitumenblock");
        add("block.contemporaryconstruction.white_left_straight_signal_bitumen_block", "Weißer links-gerade Signal-Bitumenblock");
        add("block.contemporaryconstruction.yellow_left_direction_signal_bitumen_block", "Gelber Linksrichtungs-Signal-Bitumenblock");
        add("block.contemporaryconstruction.yellow_left_right_straight_signal_bitumen_block", "Gelber links-rechts-gerade Signal-Bitumenblock");
        add("block.contemporaryconstruction.yellow_left_signal_bitumen_block", "Gelber Linksabbiege-Signal-Bitumenblock");
        add("block.contemporaryconstruction.yellow_left_straight_signal_bitumen_block", "Gelber links-gerade Signal-Bitumenblock");
        add("block.contemporaryconstruction.yellow_right_direction_signal_bitumen_block", "Gelber Rechtsrichtungs-Signal-Bitumenblock");
        add("block.contemporaryconstruction.yellow_right_signal_bitumen_block", "Gelber Rechtsabbiege-Signal-Bitumenblock");
        add("block.contemporaryconstruction.yellow_right_straight_signal_bitumen_block", "Gelber rechts-gerade Signal-Bitumenblock");

        add("block.contemporaryconstruction.barrier_cyan", "Cyanfarbene Absperrung");
        add("block.contemporaryconstruction.barrier_red", "Rote Absperrung");
        add("block.contemporaryconstruction.barrier_blue", "Blaue Absperrung");
        add("block.contemporaryconstruction.road_fence", "Straßenzaun");
        add("block.contemporaryconstruction.speed_bump", "Bodenwelle");
        add("block.contemporaryconstruction.crush_barrel", "Anpralldämpfer");
        add("block.contemporaryconstruction.red_crush_column", "Rote Anprallschutzsäule");
        add("block.contemporaryconstruction.yellow_crush_column", "Gelbe Anprallschutzsäule");
        add("block.contemporaryconstruction.concrete_barrier", "Betonabsperrung");
        add("block.contemporaryconstruction.yellow_concrete_barrier", "Gelbe Betonabsperrung");
        add("block.contemporaryconstruction.tactile_paving", "Blindenleitsystem");
        add("block.contemporaryconstruction.street_light_pole_blue_base", "Straßenlaternenmast-Basis-Blau");
        add("block.contemporaryconstruction.street_light_pole_white", "Straßenlaternenmast-Weiß");
        add("block.contemporaryconstruction.street_light_block_a", "Straßenlaternenblock (A)");
        add("block.contemporaryconstruction.sign_post_blank", "Schildermast");
        add("block.contemporaryconstruction.sign_post_pole", "Schildermaststange");

        // ===== Gegenstände =====
        add("item.contemporaryconstruction.brush", "Pinsel");
        add("item.contemporaryconstruction.signal_board", "Signaltafel");

        // ===== GUI: Hauptbearbeitung =====
        add("gui.contemporaryconstruction.edit.title", "Schildermast bearbeiten");
        add("gui.contemporaryconstruction.edit.text", "+ Text");
        add("gui.contemporaryconstruction.edit.image", "Bild");
        add("gui.contemporaryconstruction.edit.shape", "Form");
        add("gui.contemporaryconstruction.edit.export", "Exportieren");
        add("gui.contemporaryconstruction.edit.preset.save", "Voreinstellung speichern");
        add("gui.contemporaryconstruction.edit.preset.load", "Voreinstellung laden");
        add("gui.contemporaryconstruction.edit.glow", "Leuchten");
        add("gui.contemporaryconstruction.edit.no_glow", "Kein Leuchten");
        add("gui.contemporaryconstruction.edit.offset_x", "X:");
        add("gui.contemporaryconstruction.edit.offset_y", "Y:");
        add("gui.contemporaryconstruction.edit.offset_z", "Z:");
        add("gui.contemporaryconstruction.edit.rotate_z", "Drehen Z:");
        add("gui.contemporaryconstruction.edit.rotate_y", "Drehen Y:");
        add("gui.contemporaryconstruction.edit.rotate_x", "Drehen X:");
        add("gui.contemporaryconstruction.edit.scale_x", "Skalierung X:");
        add("gui.contemporaryconstruction.edit.scale_y", "Skalierung Y:");
        add("gui.contemporaryconstruction.edit.done", "Fertig");
        add("gui.contemporaryconstruction.edit.cancel", "Abbrechen");
        add("gui.contemporaryconstruction.edit.empty", "(leer)");
        add("gui.contemporaryconstruction.edit.info", "Ebene: %s/%s  Text: %s  Bild: %s  Form: %s");

        // ===== GUI: Bildauswahl =====
        add("gui.contemporaryconstruction.image.title", "Bild auswählen");
        add("gui.contemporaryconstruction.image.no_images", "Keine Bilder");
        add("gui.contemporaryconstruction.image.file_list", "Dateiliste:");
        add("gui.contemporaryconstruction.image.scale", "Skalierung:");
        add("gui.contemporaryconstruction.image.add", "Hinzufügen");
        add("gui.contemporaryconstruction.image.cancel", "Abbrechen");
        add("gui.contemporaryconstruction.image.select_hint", "<- Wählen Sie ein Bild von links");
        add("gui.contemporaryconstruction.image.preview_info", "%dx%d -> %dx%d (%.2fx)");

        // ===== GUI: Formauswahl =====
        add("gui.contemporaryconstruction.shape.title", "Form auswählen");
        add("gui.contemporaryconstruction.shape.width", "Breite:");
        add("gui.contemporaryconstruction.shape.height", "Höhe:");
        add("gui.contemporaryconstruction.shape.add", "Hinzufügen");
        add("gui.contemporaryconstruction.shape.cancel", "Abbrechen");
        add("gui.contemporaryconstruction.shape.current_color", "Aktuelle Farbe");
        add("gui.contemporaryconstruction.shape.no_shapes", "Keine Formen gefunden, legen Sie sie in den Ordner persetelement");

        // ===== GUI: Voreinstellungen =====
        add("gui.contemporaryconstruction.preset.title", "Voreinstellungen");
        add("gui.contemporaryconstruction.preset.load", "Laden");
        add("gui.contemporaryconstruction.preset.delete", "Löschen");
        add("gui.contemporaryconstruction.preset.cancel", "Abbrechen");
        add("gui.contemporaryconstruction.preset.search", "Voreinstellungen suchen");
        add("gui.contemporaryconstruction.preset.empty", "Keine Voreinstellungen, bitte speichern Sie eine im Schildermast-Editor");

        add("gui.contemporaryconstruction.preset.save.title", "Voreinstellung speichern");
        add("gui.contemporaryconstruction.preset.save.name", "Voreinstellungsnamen eingeben:");
        add("gui.contemporaryconstruction.preset.save.save", "Speichern");
        add("gui.contemporaryconstruction.preset.save.cancel", "Abbrechen");
        add("gui.contemporaryconstruction.preset.save.info", "Speichere %s Ebene(n) (%s Text, %s Bilder, %s Formen)");
        add("gui.contemporaryconstruction.preset.save.empty_name", "Name darf nicht leer sein");
        add("gui.contemporaryconstruction.preset.save.invalid_name", "Name darf nur Buchstaben, Zahlen, Unterstriche und chinesische Zeichen enthalten");
        add("gui.contemporaryconstruction.preset.save.exists", "Voreinstellung existiert bereits, bitte wählen Sie einen anderen Namen");
        add("gui.contemporaryconstruction.preset.save.success", "Voreinstellung \"%s\" erfolgreich gespeichert");
        add("gui.contemporaryconstruction.preset.save.failed", "Speichern fehlgeschlagen, bitte Dateiberechtigungen prüfen");

        // ===== GUI: Textur-Export =====
        add("gui.contemporaryconstruction.export.title", "Textur exportieren");
        add("gui.contemporaryconstruction.export.settings", "Textur-Export-Einstellungen");
        add("gui.contemporaryconstruction.export.width", "Breite:");
        add("gui.contemporaryconstruction.export.height", "Höhe:");
        add("gui.contemporaryconstruction.export.format_png", "PNG");
        add("gui.contemporaryconstruction.export.format_jpg", "JPG");
        add("gui.contemporaryconstruction.export.filename", "Dateiname:");
        add("gui.contemporaryconstruction.export.preview", "Vorschau");
        add("gui.contemporaryconstruction.export.export", "Exportieren");
        add("gui.contemporaryconstruction.export.cancel", "Abbrechen");
        add("gui.contemporaryconstruction.export.preview_hint", "Vorschau anklicken");
        add("gui.contemporaryconstruction.export.success", "Export erfolgreich: %s");
        add("gui.contemporaryconstruction.export.failed", "Export fehlgeschlagen: %s");
        add("gui.contemporaryconstruction.export.preview_failed", "Vorschau-Generierung fehlgeschlagen, Export nicht möglich");

        // ===== Nachrichten =====
        add("message.contemporaryconstruction.editing_by_other", "Dieser Schildermast wird von einem anderen Spieler bearbeitet");
        add("message.contemporaryconstruction.waxed", "Dieser Schildermast ist gewachst und kann nicht bearbeitet werden");
        add("message.contemporaryconstruction.no_shapes", "Keine Formen gefunden, bitte legen Sie Bilder in config/contemporaryconstruction/presets/persetelement/");
        add("message.contemporaryconstruction.not_editable", "Dieser Block ist als bearbeitbar markiert, implementiert aber nicht IEditableWithBrush");
    }
}