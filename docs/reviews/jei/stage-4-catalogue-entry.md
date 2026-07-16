# JEI 阶段四：Catalogue 条目

结论：已完成，条目由 `Codex Catalogue Bridge 1.0.3-2026Reset` 候选提供。

## 游戏内介绍

- 英文：`Provides searchable item, block, recipe, and usage lookup for Minecraft and installed mods. Supports recipe transfer, bookmarks, and configurable item management tools.`
- 中文：`为原版与已安装模组提供物品、方块、配方和用途的搜索查询，支持配方转移、书签与可配置的物品管理功能。`

介绍覆盖玩家实际会用到的搜索、配方与用途查询、配方转移、书签和物品管理；不把可选服务器作弊权限描述为普通玩法功能。

## 网页与问题反馈

- 网页：`https://www.curseforge.com/minecraft/mc-mods/jei`，指向 JEI 的官方玩家下载与说明页面。
- BUG：统一表单会预填 `[JEI]`，并附加 `bug,mod:jei` 标签。
- 本地 `2026Reset` 候选尚未完成批次验证，因此网页按钮不指向未发布的候选 JAR。

## 验证与部署

- `CatalogueOverridesTest` 已覆盖 JEI 的覆盖项、官方网页、BUG 标题以及双语描述。
- Bridge 的 `test build --offline` 已通过。
- 候选文件：`codex-catalogue-bridge-1.0.3-2026Reset.jar`。
- SHA-256：`B35508FC52AAD964DBF9CBF9D66067085A1871B55F299143EF144675F2E2F6E3`。
- 未替换正在使用的 Bridge `1.0.2-2026Reset`。

## 后续流程

下一阶段是阶段五：物品等级。JEI 本身不注册独立物品、方块、配方或战利品，因此将依据阶段零和源码结论快速标记为“不适用”，并写明理由。该阶段不需要关闭游戏或安装候选。
