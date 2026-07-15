# Catalogue 阶段四：模组介绍与链接

检查对象：`Catalogue 1.11.2-2026Reset` 与 `Codex Catalogue Bridge 1.0.1-2026Reset`

结论：已完成。

## 游戏内介绍

Catalogue 是客户端工具类模组，不需要像玩法模组一样展开机制说明。游戏内详情页使用以下信息：

- 英文：`Replaces the NeoForge mod list with a searchable catalogue for viewing mod details, links, filters, favorites, and update status.`
- 中文：`将 NeoForge 原版模组列表替换为可搜索的目录，集中查看模组信息、链接、筛选、收藏和更新状态。`

该内容说明了玩家实际会用到的搜索、信息查看、链接、筛选、收藏和更新提示，不虚构服务端或玩法功能。

## 网页与 BUG 链接

Catalogue Bridge 为模组 ID `catalogue` 配置了：

- 网页：`https://github.com/kirito0000001/MC-Harmony-Home/tree/main/mods/catalogue`
- BUG：`https://github.com/kirito0000001/MC-Harmony-Home/issues/new?template=mod-bug.yml&title=%5BCatalogue%5D%20&labels=bug`

因此，Catalogue 详情页的网页按钮会前往审核源码与构建说明；提交 BUG 按钮会打开统一表单，并预填 `[Catalogue]` 标题前缀。

## 文档与验证

- 上游 `README.md` 保持原内容，并追加 `MC Harmony Home 2026Reset` 段落，说明用途、客户端部署、中文界面修复和上游 MIT 许可证。
- `CatalogueOverridesTest` 已验证 `catalogue` 覆盖项、网页链接、BUG 标题和双语介绍。
- Bridge 的完整 `clean test build --offline` 通过。
- Bridge 最终 SHA-256：`02376A0565BC19FC95392585D42483208F64D945D390BF1DDCA1B369B3F8875F`。
- 候选 JAR 未安装、未发布；在所有阶段结束后再统一创建 Release 与上传。
