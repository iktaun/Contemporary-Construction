# Contemporary Construction（现代化建设）

一个面向 Minecraft 1.20.1 Forge 的城市建设类模组，包含道路方块、信号标线、护栏、路牌等内容的完整实现，并提供**可视化的路牌编辑系统**。

[![Minecraft](https://img.shields.io/badge/Minecraft-1.20.1-blue)](https://www.minecraft.net/)
[![Forge](https://img.shields.io/badge/Forge-47.4.10-orange)](https://files.minecraftforge.net/)
[![License](https://img.shields.io/badge/License-AGPL--3.0--or--later-green)](https://www.gnu.org/licenses/agpl-3.0.html)

---

## 📖 简介

Contemporary Construction 是一个城市建设主题的 Minecraft 模组。本项目主要由以下部分组成：

- **道路与信号系统**：沥青方块、直线/斜线/方向标线、T 字/十字路口标线等
- **城市设施**：护栏、防撞桶、防撞柱、混凝土护栏、减速带、盲道、路灯、栏杆
- **路牌系统**：支持多图层编辑、自定义字体、图片/形状/文字叠加、预设保存与加载

---

## ✨ 核心特性

### 🎨 路牌编辑系统

路牌是本模组最有特色的部分，提供所见即所得的图层编辑能力：

- **多图层支持**：文字图层、图片图层、形状图层可任意叠加
- **完整变换**：偏移（X/Y/Z）、旋转（X/Y/Z）、缩放（X/Y）全支持
- **样式控制**：颜色（色轮选择器）、加粗、斜体、发光
- **自定义字体**：支持导入任意 TTF 字体用于路牌，不影响游戏其他部分
- **预设系统**：保存喜欢的路牌样式，一键复用
- **纹理导出**：将编辑结果导出为 PNG/JPG 图片
- **撤销/重做**：完整的编辑历史
- **多人协作**：编辑锁定机制，防止多人同时编辑冲突

### 🌍 多语言支持

内置 10 种语言的界面翻译：

🇺🇸 English · 🇨🇳 简体中文 · 🇭🇰 繁體中文 · 🇯🇵 日本語 · 🇰🇷 한국어
🇩🇪 Deutsch · 🇫🇷 Français · 🇮🇹 Italiano · 🇪🇸 Español · 🇷🇺 Русский

---

## 🔧 安装

### 玩家安装

1. 安装 **Minecraft 1.20.1** 和 **Forge 47.4.10** 或更高版本
2. 将模组 `.jar` 文件放入 `.minecraft/mods/` 目录
3. 启动游戏

### 开发者安装

参考 [README.txt](README.txt) 中的说明。简要步骤：

```bash
# IntelliJ IDEA
./gradlew genIntellijRuns

# Eclipse
./gradlew genEclipseRuns

# 运行客户端
./gradlew runClient