package com.iktaun.Contemporary_Construction.datagen;

import com.iktaun.Contemporary_Construction.contemporaryconstruction;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class ModFrFrLangProvider extends LanguageProvider {
    public ModFrFrLangProvider(PackOutput output) {
        super(output, contemporaryconstruction.MOD_ID, "fr_fr");
    }

    @Override
    protected void addTranslations() {
        // ===== Onglets du mode créatif =====
        add("itemGroup.road_blocks", "Blocs de route");
        add("itemGroup.signal_note", "Marquage signalétique");
        add("itemGroup.barrier", "Barrière");
        add("itemGroup.other", "Autre");
        add("itemGroup.light", "Éclairage");
        add("itemGroup.column", "Colonne");

        // ===== Blocs =====
        add("block.contemporaryconstruction.bitumen_block", "Bloc de bitume");
        add("block.contemporaryconstruction.white_straight_signal_bitumen_block", "Bloc de bitume à signal droit blanc");
        add("block.contemporaryconstruction.white_straight_signal_bitumen_block_no_connection", "Bloc de bitume à signal droit blanc (sans connexion)");
        add("block.contemporaryconstruction.white_slant_straight_signal_bitumen_block", "Bloc de bitume à signal droit oblique blanc");
        add("block.contemporaryconstruction.yellow_short_slant_straight_signal_bitumen_block", "Bloc de bitume à signal droit oblique court jaune");
        add("block.contemporaryconstruction.white_short_slant_straight_signal_bitumen_block", "Bloc de bitume à signal droit oblique court blanc");
        add("block.contemporaryconstruction.yellow_double_short_slant_straight_signal_bitumen_block", "Bloc de bitume à double signal droit oblique court jaune");
        add("block.contemporaryconstruction.white_double_short_slant_straight_signal_bitumen_block", "Bloc de bitume à double signal droit oblique court blanc");
        add("block.contemporaryconstruction.yellow_slant_straight_signal_bitumen_block", "Bloc de bitume à signal droit oblique jaune");
        add("block.contemporaryconstruction.yellow_straight_signal_bitumen_block", "Bloc de bitume à signal droit jaune");
        add("block.contemporaryconstruction.yellow_straight_signal_bitumen_block_no_connection", "Bloc de bitume à signal droit jaune (sans connexion)");
        add("block.contemporaryconstruction.white_double_straight_signal_bitumen_block", "Bloc de bitume à double signal droit blanc");
        add("block.contemporaryconstruction.yellow_double_straight_signal_bitumen_block", "Bloc de bitume à double signal droit jaune");
        add("block.contemporaryconstruction.white_character_li_signal_bitumen_block", "Bloc de bitume à caractère Li blanc");
        add("block.contemporaryconstruction.white_character_rang_signal_bitumen_block", "Bloc de bitume à caractère Rang blanc");
        add("block.contemporaryconstruction.white_character_xing_signal_bitumen_block", "Bloc de bitume à caractère Xing blanc");
        add("block.contemporaryconstruction.white_character_jin_signal_bitumen_block", "Bloc de bitume à caractère Jin blanc");
        add("block.contemporaryconstruction.white_character_ren_signal_bitumen_block", "Bloc de bitume à caractère Ren blanc");
        add("block.contemporaryconstruction.white_character_zhi_signal_bitumen_block", "Bloc de bitume à caractère Zhi blanc");
        add("block.contemporaryconstruction.white_character_tong_signal_bitumen_block", "Bloc de bitume à caractère Tong blanc");
        add("block.contemporaryconstruction.white_character_zhuan_signal_bitumen_block", "Bloc de bitume à caractère Zhuan blanc");
        add("block.contemporaryconstruction.white_character_yon_signal_bitumen_block", "Bloc de bitume à caractère Yong blanc");
        add("block.contemporaryconstruction.white_direction_signal_bitumen_block", "Bloc de bitume à signal de direction blanc");
        add("block.contemporaryconstruction.yellow_direction_signal_bitumen_block", "Bloc de bitume à signal de direction jaune");
        add("block.contemporaryconstruction.yellow_straight_quarter_circle_signal_bitumen_block", "Bloc de bitume à signal quart de cercle droit jaune");
        add("block.contemporaryconstruction.white_straight_quarter_circle_signal_bitumen_block", "Bloc de bitume à signal quart de cercle droit blanc");
        add("block.contemporaryconstruction.yellow_quarter_circle_signal_bitumen_block", "Bloc de bitume à signal quart de cercle jaune");
        add("block.contemporaryconstruction.white_quarter_circle_signal_bitumen_block", "Bloc de bitume à signal quart de cercle blanc");
        add("block.contemporaryconstruction.white_right_direction_signal_bitumen_block", "Bloc de bitume à signal de direction à droite blanc");
        add("block.contemporaryconstruction.white_right_signal_bitumen_block", "Bloc de bitume à signal à droite blanc");
        add("block.contemporaryconstruction.white_right_straight_signal_bitumen_block", "Bloc de bitume à signal droite et tout droit blanc");
        add("block.contemporaryconstruction.white_left_direction_signal_bitumen_block", "Bloc de bitume à signal de direction à gauche blanc");
        add("block.contemporaryconstruction.white_left_right_straight_signal_bitumen_block", "Bloc de bitume à signal gauche, droite et tout droit blanc");
        add("block.contemporaryconstruction.white_left_signal_bitumen_block", "Bloc de bitume à signal à gauche blanc");
        add("block.contemporaryconstruction.white_left_straight_signal_bitumen_block", "Bloc de bitume à signal gauche et tout droit blanc");
        add("block.contemporaryconstruction.yellow_left_direction_signal_bitumen_block", "Bloc de bitume à signal de direction à gauche jaune");
        add("block.contemporaryconstruction.yellow_left_right_straight_signal_bitumen_block", "Bloc de bitume à signal gauche, droite et tout droit jaune");
        add("block.contemporaryconstruction.yellow_left_signal_bitumen_block", "Bloc de bitume à signal à gauche jaune");
        add("block.contemporaryconstruction.yellow_left_straight_signal_bitumen_block", "Bloc de bitume à signal gauche et tout droit jaune");
        add("block.contemporaryconstruction.yellow_right_direction_signal_bitumen_block", "Bloc de bitume à signal de direction à droite jaune");
        add("block.contemporaryconstruction.yellow_right_signal_bitumen_block", "Bloc de bitume à signal à droite jaune");
        add("block.contemporaryconstruction.yellow_right_straight_signal_bitumen_block", "Bloc de bitume à signal droite et tout droit jaune");

        add("block.contemporaryconstruction.barrier_cyan", "Barrière cyan");
        add("block.contemporaryconstruction.barrier_red", "Barrière rouge");
        add("block.contemporaryconstruction.barrier_blue", "Barrière bleue");
        add("block.contemporaryconstruction.road_fence", "Clôture routière");
        add("block.contemporaryconstruction.speed_bump", "Ralentisseur");
        add("block.contemporaryconstruction.crush_barrel", "Baril anti-choc");
        add("block.contemporaryconstruction.red_crush_column", "Colonne anti-choc rouge");
        add("block.contemporaryconstruction.yellow_crush_column", "Colonne anti-choc jaune");
        add("block.contemporaryconstruction.concrete_barrier", "Barrière en béton");
        add("block.contemporaryconstruction.yellow_concrete_barrier", "Barrière en béton jaune");
        add("block.contemporaryconstruction.tactile_paving", "Bande podotactile");
        add("block.contemporaryconstruction.street_light_pole_blue_base", "Base de lampadaire - bleue");
        add("block.contemporaryconstruction.street_light_pole_white", "Lampadaire - blanc");
        add("block.contemporaryconstruction.street_light_block_a", "Bloc de lampadaire (A)");
        add("block.contemporaryconstruction.sign_post_blank", "Poteau de signalisation");
        add("block.contemporaryconstruction.sign_post_pole", "Mât de signalisation");

        // ===== Objets =====
        add("item.contemporaryconstruction.brush", "Pinceau");
        add("item.contemporaryconstruction.signal_board", "Panneau de signalisation");

        // ===== GUI : Édition principale =====
        add("gui.contemporaryconstruction.edit.title", "Modifier le poteau de signalisation");
        add("gui.contemporaryconstruction.edit.text", "+ Texte");
        add("gui.contemporaryconstruction.edit.image", "Image");
        add("gui.contemporaryconstruction.edit.shape", "Forme");
        add("gui.contemporaryconstruction.edit.export", "Exporter");
        add("gui.contemporaryconstruction.edit.preset.save", "Enregistrer le préréglage");
        add("gui.contemporaryconstruction.edit.preset.load", "Charger le préréglage");
        add("gui.contemporaryconstruction.edit.glow", "Lueur");
        add("gui.contemporaryconstruction.edit.no_glow", "Sans lueur");
        add("gui.contemporaryconstruction.edit.offset_x", "X:");
        add("gui.contemporaryconstruction.edit.offset_y", "Y:");
        add("gui.contemporaryconstruction.edit.offset_z", "Z:");
        add("gui.contemporaryconstruction.edit.rotate_x", "RotX:");
        add("gui.contemporaryconstruction.edit.rotate_z", "RotZ:");
        add("gui.contemporaryconstruction.edit.rotate_y", "RotY:");
        add("gui.contemporaryconstruction.edit.scale_x", "ÉchX:");
        add("gui.contemporaryconstruction.edit.scale_y", "ÉchY:");
        add("gui.contemporaryconstruction.edit.done", "Terminé");
        add("gui.contemporaryconstruction.edit.cancel", "Annuler");
        add("gui.contemporaryconstruction.edit.empty", "(vide)");
        add("gui.contemporaryconstruction.edit.info", "Calque : %s/%s  Texte : %s  Image : %s  Forme : %s");

        // ===== GUI : Sélection d'image =====
        add("gui.contemporaryconstruction.image.title", "Sélectionner une image");
        add("gui.contemporaryconstruction.image.no_images", "Aucune image");
        add("gui.contemporaryconstruction.image.file_list", "Liste des fichiers :");
        add("gui.contemporaryconstruction.image.scale", "Échelle :");
        add("gui.contemporaryconstruction.image.add", "Ajouter");
        add("gui.contemporaryconstruction.image.cancel", "Annuler");
        add("gui.contemporaryconstruction.image.select_hint", "<- Sélectionnez une image à gauche");
        add("gui.contemporaryconstruction.image.preview_info", "%dx%d -> %dx%d (%.2fx)");

        // ===== GUI : Sélection de forme =====
        add("gui.contemporaryconstruction.shape.title", "Sélectionner une forme");
        add("gui.contemporaryconstruction.shape.width", "Largeur :");
        add("gui.contemporaryconstruction.shape.height", "Hauteur :");
        add("gui.contemporaryconstruction.shape.add", "Ajouter");
        add("gui.contemporaryconstruction.shape.cancel", "Annuler");
        add("gui.contemporaryconstruction.shape.current_color", "Couleur actuelle");
        add("gui.contemporaryconstruction.shape.no_shapes", "Aucune forme trouvée, placez-les dans le dossier persetelement");

        // ===== GUI : Préréglages =====
        add("gui.contemporaryconstruction.preset.title", "Préréglages");
        add("gui.contemporaryconstruction.preset.load", "Charger");
        add("gui.contemporaryconstruction.preset.delete", "Supprimer");
        add("gui.contemporaryconstruction.preset.cancel", "Annuler");
        add("gui.contemporaryconstruction.preset.search", "Rechercher des préréglages");
        add("gui.contemporaryconstruction.preset.empty", "Aucun préréglage, veuillez en enregistrer un dans l'éditeur de poteau");

        add("gui.contemporaryconstruction.preset.save.title", "Enregistrer le préréglage");
        add("gui.contemporaryconstruction.preset.save.name", "Entrez le nom du préréglage :");
        add("gui.contemporaryconstruction.preset.save.save", "Enregistrer");
        add("gui.contemporaryconstruction.preset.save.cancel", "Annuler");
        add("gui.contemporaryconstruction.preset.save.info", "Enregistrement de %s calque(s) (%s texte, %s images, %s formes)");
        add("gui.contemporaryconstruction.preset.save.empty_name", "Le nom ne peut pas être vide");
        add("gui.contemporaryconstruction.preset.save.invalid_name", "Le nom ne peut contenir que des lettres, des chiffres, des underscores et des caractères chinois");
        add("gui.contemporaryconstruction.preset.save.exists", "Le préréglage existe déjà, veuillez en choisir un autre nom");
        add("gui.contemporaryconstruction.preset.save.success", "Préréglage \"%s\" enregistré avec succès");
        add("gui.contemporaryconstruction.preset.save.failed", "Échec de l'enregistrement, veuillez vérifier les permissions de fichier");

        // ===== GUI : Export de texture =====
        add("gui.contemporaryconstruction.export.title", "Exporter la texture");
        add("gui.contemporaryconstruction.export.settings", "Paramètres d'export de texture");
        add("gui.contemporaryconstruction.export.width", "Largeur :");
        add("gui.contemporaryconstruction.export.height", "Hauteur :");
        add("gui.contemporaryconstruction.export.format_png", "PNG");
        add("gui.contemporaryconstruction.export.format_jpg", "JPG");
        add("gui.contemporaryconstruction.export.filename", "Nom du fichier :");
        add("gui.contemporaryconstruction.export.preview", "Aperçu");
        add("gui.contemporaryconstruction.export.export", "Exporter");
        add("gui.contemporaryconstruction.export.cancel", "Annuler");
        add("gui.contemporaryconstruction.export.preview_hint", "Cliquez sur Aperçu");
        add("gui.contemporaryconstruction.export.success", "Export réussi : %s");
        add("gui.contemporaryconstruction.export.failed", "Échec de l'export : %s");
        add("gui.contemporaryconstruction.export.preview_failed", "Échec de la génération de l'aperçu, impossible d'exporter");

        // ===== Messages =====
        add("message.contemporaryconstruction.editing_by_other", "Ce poteau de signalisation est en cours de modification par un autre joueur");
        add("message.contemporaryconstruction.waxed", "Ce poteau de signalisation est ciré et ne peut pas être modifié");
        add("message.contemporaryconstruction.no_shapes", "Aucune forme trouvée, veuillez placer des images dans config/contemporaryconstruction/presets/persetelement/");
        add("message.contemporaryconstruction.not_editable", "Ce bloc est marqué comme éditable mais n'implémente pas IEditableWithBrush");

        add("gui.contemporaryconstruction.edit.color", "🎨 Couleur");
        add("gui.contemporaryconstruction.color.title", "Sélecteur de couleur");
        add("gui.contemporaryconstruction.color.ok", "OK");
        add("gui.contemporaryconstruction.color.cancel", "Annuler");
        add("gui.contemporaryconstruction.color.invalid", "Couleur hexadécimale invalide");

        // ===== GUI : Sélection de police =====
        add("gui.contemporaryconstruction.edit.font", "Police");
        add("gui.contemporaryconstruction.font.title", "Sélectionner une police");
        add("gui.contemporaryconstruction.font.empty", "Aucune police personnalisée trouvée. Placez les fichiers .ttf dans config/contemporaryconstruction/fonts/");
        add("gui.contemporaryconstruction.font.vanilla", "Utiliser la police par défaut");
        add("gui.contemporaryconstruction.font.cancel", "Annuler");
    }
}