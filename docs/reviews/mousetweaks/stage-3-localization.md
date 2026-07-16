# Mouse Tweaks 阶段三：汉化审查

检查对象：`Mouse Tweaks 2.26.1`。

## 资源结构与方案

Mouse Tweaks 不包含标准 `en_us.json` 或 `zh_cn.json`。其配置界面的标题、开关和枚举选项以 `Component.literal` 的英文字符串直接写在 `ConfigScreen.class` 中，普通资源包无法覆盖。

直接替换类常量会导致英文游戏也固定显示中文，不符合双语兼容要求。因此本次不修改 Mouse Tweaks 原 JAR，而是在 Codex Catalogue Bridge 中加入一个 `@Pseudo` 可选 Mixin：

- 仅当 `yalter.mousetweaks.ConfigScreen` 存在时，才将 17 个已知英文标签重定向到语言键。
- 未知文本继续使用上游原文，避免扩大补丁影响面。
- 未安装 Mouse Tweaks 时，`@Pseudo` 目标不会创建硬依赖。

## 双语覆盖

Bridge 的 `en_us.json` 与 `zh_cn.json` 均为 17 个配置界面文本提供了对应语言键，覆盖：

- 设置标题、右键拖拽、左键拖拽、滚轮快速转移和调试模式。
- 滚轮搜索顺序、滚动方向、滚动数量。
- 多物品/单物品滚动、四种方向规则和两种搜索顺序。

中文使用“手持物品”“滚轮快速转移”“物品栏”“从前到后”等游戏内自然术语；英文保留 Mouse Tweaks 上游原文作为英文显示与未知文本的回退。

上游内部诊断日志仍保留英文，避免改变日志检索和上游问题反馈上下文；玩家可见的配置界面已实现中英切换。

## 候选构建与验证

- Bridge 候选版本：`1.0.7-2026Reset`。
- 候选文件：`D:\MC-Harmony-Home\candidates\codex-catalogue-bridge-1.0.7-2026Reset.jar`。
- SHA-256：`3E578F68CBB570894A4C65CEEEA1A045FDCADAE39E2607779BBF3DC88FC95536`。
- 固定 JDK 21 与 Gradle 8.10 的 `build --offline --no-daemon` 已通过；新增测试验证翻译映射、未知文本回退和 Mixin 配置。
- 已直接检查候选 JAR 中的 Mixin 类、Mixin 配置及英文、简体中文语言资源。

当前游戏中的 Mouse Tweaks 和 Bridge JAR 均未被替换。

## 结论与后续流程

阶段三通过。翻译通过可选 Bridge 补丁实现，保留中英文兼容，不改变 Mouse Tweaks 的物品栏操作、配置格式或服务端边界。

下一阶段是阶段四：为 Mouse Tweaks 添加 Catalogue 双语介绍、官方网页和预填 `[Mouse Tweaks]` 的问题反馈入口；该阶段将在同一 Bridge 候选序列上继续构建，不需要关闭游戏。
