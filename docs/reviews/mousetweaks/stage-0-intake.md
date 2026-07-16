# Mouse Tweaks 阶段零：快速初筛

检查对象：`MouseTweaks-neoforge-mc1.21-2.26.1.jar`。

## 一句话介绍

扩展背包和容器界面的鼠标操作，例如拖拽批量移动、快速整理与快捷转移，提高物品栏管理效率。

## 元数据与资源信号

- 模组 ID：`mousetweaks`。
- 显示名称：`Mouse Tweaks`。
- 版本：`2.26.1`。
- 许可证：BSD-3-Clause。
- 上游描述：为常规鼠标按键增加多种物品栏管理功能。
- 未发现 `assets/mousetweaks/models/item/`、`data/mousetweaks/loot_tables/`、`recipes/`、`tags/` 或 `advancements/` 资源。
- JAR 声明 `mousetweaks.mixins.json`，需在阶段一确认注入范围与容器兼容性。

## 初步判断

- 物品等级：高置信度 `不适用`。没有自身物品、配方或战利品资源；阶段一只需排除动态注册或数据包注入。
- 服务端：待确认。功能由客户端鼠标触发，但会操作真实容器槽位，阶段一、二必须检查它是否只发送原版点击包，以及连接到未安装 Mouse Tweaks 的服务器是否安全。
- 汉化：需检查鼠标操作配置、提示与错误信息的中英文完整性和中文自然度。
- Catalogue：需要确认功能边界后补双语介绍、官方链接和 `[Mouse Tweaks]` 问题入口。

## 后续流程

下一步进入阶段一：检查容器 Mixin、输入事件、批量移动算法、原版点击包使用方式及其对复杂容器模组的兼容性。
