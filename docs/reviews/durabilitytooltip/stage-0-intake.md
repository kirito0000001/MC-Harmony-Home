# Durability Tooltip 阶段零：快速初筛

检查对象：`durabilitytooltip-1.1.6-neoforge-mc1.21.jar`。

## 一句话介绍

在物品提示中显示工具、武器和护甲的耐久度，可按配置使用数值、文字状态或耐久条样式。

## 元数据与资源信号

- 模组 ID：`durabilitytooltip`。
- 显示名称：`Durability Tooltip`。
- 版本：`1.1.6`。
- 作者：`SuperMartijn642`。
- 许可证：`All rights reserved`。
- 上游页面：`https://www.curseforge.com/minecraft/mc-mods/durability-tooltip`。
- JAR 包含客户端入口 `DurabilityTooltipClient`、配置类、提示样式类、访问转换器和 Mixin 配置。
- 英文和简体中文语言资源均有 12 个键；已发现的中文包括“耐久度”“毫发无损”“轻微受损”“严重损坏”“濒临破碎”。
- 未发现 `models/item/`、`loot_tables/`、`recipes/`、`tags/` 或 `advancements/` 资源。
- 元数据将 NeoForge、Minecraft 和 Config Lib 依赖都标为 `BOTH`，不能仅依赖元数据判断服务端部署边界。

## 初步判断

1. 物品等级：高置信度 `不适用`。它显示已有装备耐久度，不注册自身物品、配方或战利品；阶段一只需排除动态注册。

2. 服务端：待确认。功能目标是客户端提示，但 `BOTH` 依赖声明和配置库意味着需要检查通用初始化、网络包与服务端类加载情况后再作正式结论。

3. 汉化：已有完整的简体中文资源，阶段三将逐项检查中英文键是否对齐、中文术语是否自然，以及配置界面是否存在未覆盖文本。

4. Catalogue：功能边界确认后补双语介绍、官方页面和预填 `[Durability Tooltip]` 的问题反馈入口。

## 后续流程

下一步进入阶段一：检查模组入口、客户端提示注册、Config Lib 初始化、Mixin、访问转换器、网络行为及其对物品提示渲染的性能影响。
