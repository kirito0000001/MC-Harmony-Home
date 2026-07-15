# MC 和谐家园

Minecraft 1.21.1 NeoForge 整合包的审查记录、客户端体验补丁、发布说明和可复现安装清单。

## 当前基线

- Minecraft：`1.21.1`
- NeoForge：`21.1.235`
- Java：`21+`

## 安装方式

`pack/manifest.json` 是当前已审查组件的唯一清单来源。客户端使用 `profiles/client/manifest.json`；服务器只使用 `profiles/server/manifest.json` 中已经完成服务端审查的条目。

公开再分发许可明确的构建通过 GitHub Release 下载。其他第三方模组保留原始发布地址与 SHA-256，由原站补齐，避免影响完整整合包体验或违反作者许可。

## 当前已审查模组

- [Just Enough Professions](mods/justenoughprofessions/README.md)：客户端 JEI 职业浏览页，使用 `4.0.5-2026Reset` 优化构建。[下载 JAR](https://github.com/kirito0000001/MC-Harmony-Home/releases/download/justenoughprofessions-4.0.5-2026Reset/JustEnoughProfessions-neoforge-1.21.1-4.0.5-2026Reset.jar)
- [Catalogue](mods/catalogue/README.md)：客户端模组目录，补齐中文界面并修复图片与配置读取流。[下载 JAR](https://github.com/kirito0000001/MC-Harmony-Home/releases/download/catalogue-1.11.2-2026Reset/catalogue-neoforge-1.21.1-1.11.2-2026Reset.jar)
- [Codex Catalogue Bridge](mods/codex-catalogue-bridge/README.md)：客户端 Catalogue 介绍、链接和长标题适配层。[下载 JAR](https://github.com/kirito0000001/MC-Harmony-Home/releases/download/codex-catalogue-bridge-1.0.1-2026Reset/codex-catalogue-bridge-1.0.1-2026Reset.jar)

## 问题反馈

在 GitHub Issues 中使用“模组问题反馈”表单。由游戏内 Catalogue 打开的链接会自动填入对应模组名和标签。
