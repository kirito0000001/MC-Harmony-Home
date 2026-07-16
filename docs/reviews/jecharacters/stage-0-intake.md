# Just Enough Characters 阶段零：快速初筛

检查对象：`jecharacters-1.21.1-neoforge-4.5.26.jar`。

## 一句话介绍

为 JEI 搜索补充中文拼音与拼音首字母匹配，帮助玩家用中文习惯更快找到物品和配方。

## 元数据与资源信号

- 模组 ID：`jecharacters`。
- 显示名称：`Just Enough Characters`。
- 版本：`4.5.26`。
- 许可证：MIT License。
- 上游描述：`Pinyin search for JEI and more.`。
- 未发现 `assets/jecharacters/models/item/`、`data/jecharacters/loot_tables/`、`recipes/`、`tags/` 或 `advancements/` 资源。

## 初步判断

- 物品等级：高置信度 `不适用`。当前没有自己的物品、配方或战利品资源信号；阶段一只需排除动态注册或数据包注入。
- 服务端：待阶段一确认。功能从描述看是客户端 JEI 搜索增强，但需要检查实际入口、Mixin 和网络依赖后再归为 `client_only`。
- 汉化：需检查拼音搜索相关配置、提示和 JEI 搜索语法说明是否中英文完整且自然。
- Catalogue：需要在确认真实功能边界后补双语介绍、官方链接和 `[JEC]` 问题入口。

## 后续流程

下一步进入阶段一：检查搜索匹配实现、索引/缓存策略、线程与配置读写，确认是否有实际性能风险或可安全优化的空间。
