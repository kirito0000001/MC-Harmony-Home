# Passive Search Bar 阶段零：快速初筛

检查对象：`passivesearchbar-1.0.0.jar`。

## 一句话介绍

阻止创造模式物品栏的搜索框在打开界面时自动抢占键盘焦点，避免刚打开背包就误把移动或快捷键输入搜索栏。

## 元数据与资源信号

- 模组 ID：`passivesearchbar`。
- 显示名称：`Passive Search Bar`。
- 版本：`1.0.0`。
- 作者：`liuyu`。
- 许可证：`All Rights Reserved`。
- 上游描述：`Prevents the Creative inventory search box from automatically taking keyboard focus.`
- 元数据将 NeoForge 与 Minecraft 依赖都声明为 `side="CLIENT"`。
- JAR 仅包含主类、搜索框焦点策略类和 `CreativeModeInventoryScreenMixin`；未发现物品模型、物品语言资源、`loot_tables/`、`recipes/`、`tags/` 或 `advancements/`。

## 初步判断

- 物品等级：高置信度 `不适用`。它只改变创造物品栏搜索框的焦点策略，不注册自身物品、配方或战利品；阶段一只需排除动态注册。
- 服务端：高置信度 `client_only`。元数据已经明确限制客户端侧，功能也只涉及本地 GUI 焦点；阶段二仍会确认没有网络包或服务端事件注册。
- 汉化：JAR 当前没有标准语言资源，需在阶段三核查玩家可见文本是否来自原版控件，是否有可翻译的配置或提示。
- Catalogue：功能边界确认后补双语介绍、官网或仓库链接，以及预填 `[Passive Search Bar]` 的问题反馈入口。

## 后续流程

下一步进入阶段一：检查 `CreativeModeInventoryScreenMixin` 的注入点、焦点判定时机和是否影响正常点击搜索框、JEI 搜索框或其他 GUI。
