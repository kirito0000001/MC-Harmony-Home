# Catalogue 阶段三：汉化审查

检查对象：`Catalogue 1.11.2-2026Reset` 与 `Codex Catalogue Bridge 1.0.1-2026Reset`

结论：已完成。

## 自带界面语言

- Catalogue 的 `en_us.json` 有 32 条界面键。
- 原始 `zh_cn.json` 仅有 2 条，其余界面会回退为英文。
- 已将 `zh_cn.json` 补齐为相同的 32 条键，包括搜索、配置、网页、提交问题、模组文件夹、许可证、作者、更新、收藏、筛选、排序、前置库和高级搜索等内容。
- 游戏内验证发现 `Website` 与 `Submit Bug` 原本是硬编码文本，未经过语言文件。已改为 `catalogue.gui.website` 与 `catalogue.gui.submit_bug`，简体中文显示为“网页”“提交问题”。
- 翻译保持 Minecraft 常用术语：`模组`、`前置库`、`配置`、`筛选`、`收藏`，并保留 `Catalogue` 作为产品名称。

## 模组详情介绍

NeoForge 的模组元数据描述只能提供单一默认文本，因此详情页的双语介绍由已安装的 Catalogue Bridge 提供：

- 英文：说明它以可搜索的目录替换 NeoForge 模组列表，并涵盖信息、链接、筛选、收藏和更新状态。
- 中文：`将 NeoForge 原版模组列表替换为可搜索的目录，集中查看模组信息、链接、筛选、收藏和更新状态。`

这与 JEP 已使用的桥接语言键机制一致；英语客户端显示英文，简体中文客户端显示中文。

## 验证与产物

- `test-catalogue-localization.ps1` 已验证 Catalogue 英中键完全一致，并确认桥接补丁同时存在两种语言的详情介绍。
- Catalogue 构建通过，JAR：`mods/catalogue/neoforge/build/libs/catalogue-neoforge-1.21.1-1.11.2-2026Reset.jar`。
- Catalogue SHA-256：`3B0E87230B9E5DC1A51B9D05D9CD86A2AD40E788E2A721C67780A4E6F0816BC1`。
- Bridge 构建与单元测试通过，JAR：`mods/codex-catalogue-bridge/build/libs/codex-catalogue-bridge-1.0.1-2026Reset.jar`。
- Bridge SHA-256：`02376A0565BC19FC95392585D42483208F64D945D390BF1DDCA1B369B3F8875F`。
- 两个构建已完成客户端运行验证并发布到 GitHub Release。
