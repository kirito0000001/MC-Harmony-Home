# Just Enough Characters 阶段六：运行与发布

当前状态：阶段五已正式标记物品等级“不适用”；汉化候选和 Catalogue Bridge 候选均已构建，等待十个候选一次的运行验证批次；当前进度为 `6 / 10`。

## 候选文件

- JEC 文件：`jecharacters-1.21.1-neoforge-4.5.26-2026Reset.jar`。
- JEC 位置：`D:\MC-Harmony-Home\candidates\jecharacters-1.21.1-neoforge-4.5.26-2026Reset.jar`。
- JEC SHA-256：`60F3DC07882A9F912B7A0CD0A7F897AACFA62760E42CBFF48EEFF2B10C6E2F14`。
- Bridge 文件：`codex-catalogue-bridge-1.0.5-2026Reset.jar`。
- Bridge SHA-256：`AFC27FF315048E7BDABD346DA9368C6717B975477AFCCB1B1E73A4D8F25814CD`。
- 两个候选均未替换正在使用的 Minecraft JAR。

## 批次验证要求

批次达到 `10 / 10` 后，关闭游戏、备份原始 JEC 和 Bridge，再安装候选并验证：

- 启动日志没有 JEC、JEI、Catalogue 或 CoreMod 变换错误。
- JEI 物品面板可用中文名、全拼和拼音首字母搜索，结果与原本搜索语法一致。
- 已命中的 Create、Jade、Patchouli、Waystones、Cloth Config、FTB Library 和 MaliLib 搜索界面能正常打开与搜索。
- `/jech` 的帮助、分析完成和写入失败提示显示为修订后的中文；未开启 `enableVerbose` 时日志不因搜索操作膨胀。
- Catalogue 正确显示 JEC 双语介绍、官方网页和预填 `[JEC]` 的问题反馈链接。

## 发布边界

JEC 使用 MIT License。公开仓库可保存可审查的本地化构建脚本、审查记录和后续经验证的补丁；本候选在完成批次运行验证前不创建 GitHub Release，也不覆盖当前游戏 JAR。
