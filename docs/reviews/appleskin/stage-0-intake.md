# AppleSkin 阶段零：快速初筛

检查对象：`appleskin-neoforge-mc1.21-3.0.9.jar`。

## 一句话介绍

为食物与饥饿值提供更直观的 HUD 信息提示，帮助玩家判断食物的恢复效果与饱和度收益。

## 元数据与资源信号

- 模组 ID：`appleskin`。
- 显示名称：`AppleSkin`。
- 版本：`3.0.9`，由安装文件名确认；模组元数据使用 `${file.jarVersion}` 占位符。
- 许可证：The Unlicense。
- 上游描述：`Adds various food-related HUD improvements`。
- 元数据依赖 NeoForge，且明确声明 `side="CLIENT"`。
- 未发现 `assets/appleskin/models/item/`、`data/appleskin/loot_tables/`、`recipes/`、`tags/` 或 `advancements/` 资源。

## 初步判断

- 物品等级：高置信度 `不适用`。AppleSkin 显示现有食物数据，不注册自己的可获取内容；阶段一只需排除动态注册或数据包注入。
- 服务端：高置信度 `client_only`，但仍在阶段二检查客户端入口、网络包与事件使用后作正式结论。
- 汉化：需检查 HUD 提示、配置和食物效果说明的中英文完整性与自然度。
- Catalogue：需要在功能边界确认后补双语介绍、官方链接和 `[AppleSkin]` 问题入口。

## 后续流程

下一步进入阶段一：检查 HUD 渲染、食物数据缓存、事件订阅、配置读写与潜在的每帧开销。
