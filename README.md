# Contemporary Construction
# 现代化建设

A Minecraft 1.20.1 Forge mod themed around urban construction. It includes a complete implementation of road blocks, signal markings, barriers, signposts, and more, along with a **visual signpost editing system**.
一个面向 Minecraft 1.20.1 Forge 的城市建设类模组，包含道路方块、信号标线、护栏、路牌等内容的完整实现，并提供**可视化的路牌编辑系统**。

[![Minecraft](https://img.shields.io/badge/Minecraft-1.20.1-blue)](https://www.minecraft.net/)
[![Forge](https://img.shields.io/badge/Forge-47.4.10-orange)](https://files.minecraftforge.net/)
[![License](https://img.shields.io/badge/License-AGPL--3.0--or--later-green)](https://www.gnu.org/licenses/agpl-3.0.html)

---

## 📖 Introduction
## 📖 简介

Contemporary Construction is a Minecraft mod centered on urban construction. The project is mainly composed of:
Contemporary Construction 是一个城市建设主题的 Minecraft 模组。本项目主要由以下部分组成：

- **Roads & Signals**: Asphalt blocks, straight/diagonal/directional markings, T-junction and crossroad markings, and more
- **道路与信号系统**：沥青方块、直线/斜线/方向标线、T 字/十字路口标线等

- **Urban Facilities**: Fences, crash barrels, crash columns, concrete barriers, speed bumps, tactile paving, street lights, and handrails
- **城市设施**：护栏、防撞桶、防撞柱、混凝土护栏、减速带、盲道、路灯、栏杆

- **Signpost System**: Multi-layer editing, custom fonts, image/shape/text overlays, and preset saving/loading
- **路牌系统**：支持多图层编辑、自定义字体、图片/形状/文字叠加、预设保存与加载

---

## ✨ Key Features
## ✨ 核心特性

### 🎨 Signpost Editing System
### 🎨 路牌编辑系统

The signpost is the most distinctive part of this mod, offering a WYSIWYG layer editing experience:
路牌是本模组最有特色的部分，提供所见即所得的图层编辑能力：

- **Multi-layer support**: Text, image, and shape layers can be freely stacked
- **多图层支持**：文字图层、图片图层、形状图层可任意叠加

- **Full transforms**: Offset (X/Y/Z), rotation (X/Y/Z), and scale (X/Y) all supported
- **完整变换**：偏移（X/Y/Z）、旋转（X/Y/Z）、缩放（X/Y）全支持

- **Style controls**: Color (color wheel picker), bold, italic, glow
- **样式控制**：颜色（色轮选择器）、加粗、斜体、发光

- **Custom fonts**: Import any TTF font for signposts without affecting the rest of the game
- **自定义字体**：支持导入任意 TTF 字体用于路牌，不影响游戏其他部分

- **Preset system**: Save your favorite signpost styles and reuse them with one click
- **预设系统**：保存喜欢的路牌样式，一键复用

- **Texture export**: Export edited results as PNG/JPG images
- **纹理导出**：将编辑结果导出为 PNG/JPG 图片

- **Undo/Redo**: Complete editing history
- **撤销/重做**：完整的编辑历史

- **Multiplayer collaboration**: Editor locking mechanism to prevent conflicts from simultaneous edits
- **多人协作**：编辑锁定机制，防止多人同时编辑冲突

### 🌍 Multi-language Support
### 🌍 多语言支持

Ships with UI translations for 10 languages:
内置 10 种语言的界面翻译：

🇺🇸 English · 🇨🇳 简体中文 · 🇭🇰 繁體中文 · 🇯🇵 日本語 · 🇰🇷 한국어
🇩🇪 Deutsch · 🇫🇷 Français · 🇮🇹 Italiano · 🇪🇸 Español · 🇷🇺 Русский

---

## 🔧 Installation
## 🔧 安装

### For Players
### 玩家安装

1. Install **Minecraft 1.20.1** and **Forge 47.4.10** or higher 安装 **Minecraft 1.20.1** 和 **Forge 47.4.10** 或更高版本

2. Place the mod `.jar` file into the `.minecraft/mods/` directory 将模组 `.jar` 文件放入 `.minecraft/mods/` 目录

3. Launch the game 启动游戏

### For Developers
### 开发者安装

See [README.txt](README.txt) for details. Quick steps:
参考 [README.txt](README.txt) 中的说明。简要步骤：

```bash
# IntelliJ IDEA
./gradlew genIntellijRuns

# Eclipse
./gradlew genEclipseRuns

# Run the client
./gradlew runClient