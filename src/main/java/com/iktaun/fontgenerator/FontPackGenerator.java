package com.iktaun.fontgenerator;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FontPackGenerator extends JFrame {

    public enum Language {
        CHINESE("zh_cn"),
        ENGLISH("en_us");

        public final String code;

        Language(String code) {
            this.code = code;
        }
    }

    private DefaultListModel<String> fontListModel;
    private JList<String> fontList;
    private JLabel statusLabel;
    private JLabel titleLabel;
    private File minecraftDir;
    private File fontsDir;
    private File resourcePacksDir;
    private Language currentLanguage;

    private JButton addBtn, generateBtn, openFolderBtn, refreshBtn, langBtn;
    private JPanel centerPanel;
    private JLabel dropHint, hintLabel;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        Language selected = showLanguageDialog();
        SwingUtilities.invokeLater(() -> new FontPackGenerator(selected).setVisible(true));
    }

    private static Language showLanguageDialog() {
        String[] options = {"🀄 中文 (简体)", "🌐 English"};
        int choice = JOptionPane.showOptionDialog(
                null,
                "请选择界面语言 / Please select interface language:\n----------------------------\nFont Pack Generator",
                "语言选择 / Language Selection",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );
        if (choice == JOptionPane.CLOSED_OPTION || choice == -1) {
            return Language.CHINESE;
        }
        return choice == 0 ? Language.CHINESE : Language.ENGLISH;
    }

    public FontPackGenerator(Language language) {
        this.currentLanguage = language;
        I18n_CC.load(language.code);
        initWindow();
        detectMinecraftDir();
        initDirectories();
        initUI();
        refreshFontList();
    }

    private void initWindow() {
        setTitle(I18n_CC.get("app.title"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 480);
        setLocationRelativeTo(null);
        setResizable(true);
        setMinimumSize(new Dimension(800, 600));
        setIconImage(createIcon());
    }

    private Image createIcon() {
        BufferedImage icon = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = icon.createGraphics();
        g.setColor(new Color(0, 120, 200));
        g.fillRect(0, 0, 16, 16);
        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.BOLD, 12));
        g.drawString("F", 3, 13);
        g.dispose();
        return icon;
    }

    private void detectMinecraftDir() {
        String userHome = System.getProperty("user.home");
        String osName = System.getProperty("os.name").toLowerCase();
        File[] candidates;
        if (osName.contains("win")) {
            candidates = new File[]{
                    new File(userHome, "AppData/Roaming/.minecraft"),
                    new File(userHome, ".minecraft")
            };
        } else if (osName.contains("mac")) {
            candidates = new File[]{
                    new File(userHome, "Library/Application Support/minecraft"),
                    new File(userHome, ".minecraft")
            };
        } else {
            candidates = new File[]{new File(userHome, ".minecraft")};
        }
        for (File f : candidates) {
            if (f.exists() && f.isDirectory()) {
                minecraftDir = f;
                return;
            }
        }
        int choice = JOptionPane.showConfirmDialog(this,
                I18n_CC.get("msg.no_minecraft_dir"),
                I18n_CC.get("msg.hint"),
                JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            chooseMinecraftDir();
        } else {
            minecraftDir = new File(".");
        }
    }

    private void chooseMinecraftDir() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setDialogTitle(I18n_CC.get("msg.select_minecraft_dir"));
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            minecraftDir = chooser.getSelectedFile();
        } else {
            minecraftDir = new File(".");
        }
    }

    private void initDirectories() {
        fontsDir = new File(minecraftDir, "config/contemporaryconstruction/fonts");
        if (!fontsDir.exists()) fontsDir.mkdirs();
        resourcePacksDir = new File(minecraftDir, "resourcepacks");
        if (!resourcePacksDir.exists()) resourcePacksDir.mkdirs();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel topPanel = new JPanel(new BorderLayout());
        titleLabel = new JLabel(I18n_CC.get("app.title"), SwingConstants.CENTER);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 20));
        topPanel.add(titleLabel, BorderLayout.CENTER);
        JLabel pathLabel = new JLabel("📂 " + minecraftDir.getAbsolutePath());
        pathLabel.setFont(new Font("微软雅黑", Font.PLAIN, 10));
        pathLabel.setForeground(Color.GRAY);
        pathLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(pathLabel, BorderLayout.SOUTH);
        add(topPanel, BorderLayout.NORTH);

        centerPanel = new JPanel(new BorderLayout(5, 5));
        centerPanel.setBorder(BorderFactory.createTitledBorder(I18n_CC.get("panel.fonts")));

        fontListModel = new DefaultListModel<>();
        fontList = new JList<>(fontListModel);
        fontList.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        fontList.setFixedCellHeight(32);
        JScrollPane scrollPane = new JScrollPane(fontList);
        scrollPane.setPreferredSize(new Dimension(400, 150));
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        dropHint = new JLabel("📥 " + I18n_CC.get("hint.drag_drop"), SwingConstants.CENTER);
        dropHint.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        dropHint.setForeground(Color.GRAY);
        centerPanel.add(dropHint, BorderLayout.SOUTH);
        setupDragDrop(centerPanel);
        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout(5, 5));
        bottomPanel.setBorder(new EmptyBorder(5, 0, 5, 0));

        statusLabel = new JLabel(I18n_CC.get("status.ready"));
        statusLabel.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        statusLabel.setForeground(Color.GRAY);
        bottomPanel.add(statusLabel, BorderLayout.NORTH);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));

        addBtn = new JButton(I18n_CC.get("btn.add"));
        addBtn.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        addBtn.addActionListener(e -> addFontFile());
        btnPanel.add(addBtn);

        generateBtn = new JButton(I18n_CC.get("btn.generate"));
        generateBtn.setFont(new Font("微软雅黑", Font.BOLD, 14));
        generateBtn.setBackground(new Color(0, 150, 50));
        generateBtn.setForeground(Color.WHITE);
        generateBtn.setOpaque(true);
        generateBtn.setBorderPainted(false);
        generateBtn.setFocusPainted(false);
        generateBtn.addActionListener(e -> generateResourcePack());
        btnPanel.add(generateBtn);

        openFolderBtn = new JButton(I18n_CC.get("btn.open_folder"));
        openFolderBtn.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        openFolderBtn.addActionListener(e -> {
            try { Desktop.getDesktop().open(fontsDir); }
            catch (IOException ex) {
                statusLabel.setText("❌ " + I18n_CC.get("status.open_folder_failed") + ex.getMessage());
                statusLabel.setForeground(Color.RED);
            }
        });
        btnPanel.add(openFolderBtn);

        refreshBtn = new JButton(I18n_CC.get("btn.refresh"));
        refreshBtn.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        refreshBtn.addActionListener(e -> refreshFontList());
        btnPanel.add(refreshBtn);

        langBtn = new JButton("🌐 " + I18n_CC.get("btn.lang"));
        langBtn.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        langBtn.addActionListener(e -> switchLanguage());
        btnPanel.add(langBtn);

        bottomPanel.add(btnPanel, BorderLayout.CENTER);

        hintLabel = new JLabel(I18n_CC.get("hint.main"));
        hintLabel.setFont(new Font("微软雅黑", Font.PLAIN, 11));
        hintLabel.setForeground(Color.GRAY);
        hintLabel.setHorizontalAlignment(SwingConstants.CENTER);
        bottomPanel.add(hintLabel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void setupDragDrop(Component target) {
        new DropTarget(target, new DropTargetAdapter() {
            @Override
            public void drop(DropTargetDropEvent dtde) {
                dtde.acceptDrop(DnDConstants.ACTION_COPY);
                try {
                    @SuppressWarnings("unchecked")
                    List<File> files = (List<File>) dtde.getTransferable()
                            .getTransferData(DataFlavor.javaFileListFlavor);
                    int count = 0;
                    for (File f : files) {
                        if (isFontFile(f)) {
                            copyFontFile(f);
                            count++;
                        }
                    }
                    if (count > 0) {
                        refreshFontList();
                        statusLabel.setText("✅ " + I18n_CC.get("status.added") + count + " " + I18n_CC.get("status.font_files"));
                        statusLabel.setForeground(new Color(0, 150, 0));
                    } else {
                        statusLabel.setText("⚠️ " + I18n_CC.get("status.no_valid_fonts"));
                        statusLabel.setForeground(Color.ORANGE);
                    }
                } catch (Exception e) {
                    statusLabel.setText(I18n_CC.get("status.drop_failed") + e.getMessage());
                    statusLabel.setForeground(Color.RED);
                }
            }
        });
    }

    private boolean isFontFile(File f) {
        String name = f.getName().toLowerCase();
        return name.endsWith(".ttf") || name.endsWith(".otf");
    }

    private void addFontFile() {
        JFileChooser chooser = new JFileChooser();
        chooser.setMultiSelectionEnabled(true);
        chooser.setFileFilter(new FileNameExtensionFilter(I18n_CC.get("filter.font"), "ttf", "otf"));
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            int count = 0;
            for (File f : chooser.getSelectedFiles()) {
                copyFontFile(f);
                count++;
            }
            if (count > 0) {
                refreshFontList();
                statusLabel.setText("✅ " + I18n_CC.get("status.added") + count + " " + I18n_CC.get("status.font_files"));
                statusLabel.setForeground(new Color(0, 150, 0));
            }
        }
    }

    private void copyFontFile(File source) {
        try {
            File dest = new File(fontsDir, source.getName());
            if (dest.exists()) {
                int result = JOptionPane.showConfirmDialog(this,
                        I18n_CC.get("msg.file_exists") + "\"" + source.getName() + "\"",
                        I18n_CC.get("msg.file_exists_title"),
                        JOptionPane.YES_NO_OPTION);
                if (result != JOptionPane.YES_OPTION) return;
            }
            Files.copy(source.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            statusLabel.setText(I18n_CC.get("status.copy_failed") + e.getMessage());
            statusLabel.setForeground(Color.RED);
        }
    }

    private void refreshFontList() {
        fontListModel.clear();
        File[] files = fontsDir.listFiles((d, name) -> isFontFile(new File(name)));
        if (files != null && files.length > 0) {
            Arrays.sort(files, (a, b) -> a.getName().compareToIgnoreCase(b.getName()));
            for (File f : files) {
                fontListModel.addElement("📄 " + f.getName() + " (" + formatFileSize(f.length()) + ")");
            }
            statusLabel.setText(I18n_CC.get("status.found") + files.length + " " + I18n_CC.get("status.font_files"));
        } else {
            statusLabel.setText(I18n_CC.get("status.found") + "0 " + I18n_CC.get("status.font_files"));
        }
        statusLabel.setForeground(Color.GRAY);
    }

    private String formatFileSize(long bytes) {
        if (bytes < 1024) return bytes + " B";
        if (bytes < 1048576) return (bytes / 1024) + " KB";
        return String.format("%.1f MB", bytes / 1048576.0);
    }

    private void generateResourcePack() {
        File[] fontFiles = fontsDir.listFiles((d, name) -> isFontFile(new File(name)));
        if (fontFiles == null || fontFiles.length == 0) {
            statusLabel.setText(I18n_CC.get("status.no_fonts"));
            statusLabel.setForeground(Color.RED);
            JOptionPane.showMessageDialog(this,
                    I18n_CC.get("msg.no_fonts_please_add"),
                    I18n_CC.get("msg.no_fonts_title"),
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String zipName = fontFiles.length == 1
                ? "CC_Fonts_" + fontFiles[0].getName().replaceAll("\\.[^.]+$", "").replaceAll("[^a-zA-Z0-9_\\-]", "_") + ".zip"
                : "CC_Fonts_Multiple.zip";
        File zipFile = new File(resourcePacksDir, zipName);

        if (zipFile.exists()) {
            int result = JOptionPane.showConfirmDialog(this,
                    I18n_CC.get("msg.pack_exists") + "\"" + zipName + "\"",
                    I18n_CC.get("msg.pack_exists_title"),
                    JOptionPane.YES_NO_OPTION);
            if (result != JOptionPane.YES_OPTION) return;
        }

        try {
            generateZip(zipFile, fontFiles);
            statusLabel.setText(I18n_CC.get("status.success") + zipFile.getAbsolutePath());
            statusLabel.setForeground(new Color(0, 150, 0));

            int result = JOptionPane.showConfirmDialog(this,
                    I18n_CC.get("msg.success") + "\n\n" + I18n_CC.get("msg.file_location") + zipFile.getAbsolutePath() + "\n\n" + I18n_CC.get("msg.open_folder_question"),
                    I18n_CC.get("msg.success_title"), JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) Desktop.getDesktop().open(resourcePacksDir);

            JOptionPane.showMessageDialog(this,
                    I18n_CC.get("msg.next_steps"),
                    I18n_CC.get("msg.usage_title"),
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            statusLabel.setText(I18n_CC.get("status.failed") + e.getMessage());
            statusLabel.setForeground(Color.RED);
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    I18n_CC.get("msg.generation_failed") + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void generateZip(File zipFile, File[] fontFiles) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(zipFile);
             ZipOutputStream zos = new ZipOutputStream(fos)) {

            StringBuilder json = new StringBuilder("{\n  \"providers\": [\n");
            for (int i = 0; i < fontFiles.length; i++) {
                String name = fontFiles[i].getName();
                json.append("    {\n      \"type\": \"ttf\",\n      \"file\": \"contemporaryconstruction:font/")
                        .append(name).append("\",\n      \"shift\": [0, 0],\n      \"size\": 12,\n      \"oversample\": 2.0\n    }");
                if (i < fontFiles.length - 1) json.append(",");
                json.append("\n");
            }
            json.append("  ]\n}");

            zos.putNextEntry(new ZipEntry("assets/contemporaryconstruction/font/fonts.json"));
            zos.write(json.toString().getBytes("UTF-8"));
            zos.closeEntry();

            for (File fontFile : fontFiles) {
                zos.putNextEntry(new ZipEntry("assets/contemporaryconstruction/font/" + fontFile.getName()));
                Files.copy(fontFile.toPath(), zos);
                zos.closeEntry();
            }

            int packFormat = getPackFormat();
            String packMeta = String.format(
                    "{\n  \"pack\": {\n    \"pack_format\": %d,\n    \"description\": \"Contemporary Construction Fonts\"\n  }\n}",
                    packFormat);
            zos.putNextEntry(new ZipEntry("pack.mcmeta"));
            zos.write(packMeta.getBytes("UTF-8"));
            zos.closeEntry();
        }
    }

    private int getPackFormat() {
        String mcVersion = System.getProperty("mc.version", "1.20.1");
        switch (mcVersion) {
            case "1.20.1": case "1.20.2": return 15;
            case "1.20.3": case "1.20.4": return 18;
            case "1.20.5": case "1.20.6": return 22;
            case "1.21": case "1.21.1": return 26;
            case "1.21.2": case "1.21.3": case "1.21.4": return 28;
            case "1.21.5": case "1.21.6": case "1.21.7": return 30;
            case "1.21.8": case "1.21.9": case "1.21.10": return 32;
            case "1.21.11": case "1.21.12": case "1.21.13": case "1.21.14": return 35;
            case "1.21.15": case "1.21.16": return 38;
            default: return 15;
        }
    }

    private void switchLanguage() {
        Language newLang = (currentLanguage == Language.CHINESE) ? Language.ENGLISH : Language.CHINESE;
        this.currentLanguage = newLang;
        I18n_CC.load(newLang.code);

        setTitle(I18n_CC.get("app.title"));
        titleLabel.setText(I18n_CC.get("app.title"));
        centerPanel.setBorder(BorderFactory.createTitledBorder(I18n_CC.get("panel.fonts")));
        dropHint.setText("📥 " + I18n_CC.get("hint.drag_drop"));
        addBtn.setText(I18n_CC.get("btn.add"));
        generateBtn.setText(I18n_CC.get("btn.generate"));
        openFolderBtn.setText(I18n_CC.get("btn.open_folder"));
        refreshBtn.setText(I18n_CC.get("btn.refresh"));
        langBtn.setText("🌐 " + I18n_CC.get("btn.lang"));
        hintLabel.setText(I18n_CC.get("hint.main"));
        statusLabel.setText(I18n_CC.get("status.language_switched"));
        statusLabel.setForeground(Color.GRAY);
        refreshFontList();
    }
}