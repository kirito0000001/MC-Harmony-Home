# Catalogue 阶段六：运行验证与发布准备

检查对象：`Catalogue 1.11.2-2026Reset` 与 `Codex Catalogue Bridge 1.0.1-2026Reset`

结论：运行验证通过，准备发布。

## 客户端验证

- 已备份原始 Catalogue 和旧桥接补丁。
- 已安装候选 Catalogue 与 Bridge 1.0.1，并通过 PCL 启动 `1.21.1-NeoForge_21.1.235`。
- 模组目录可正常打开，列表和详情页正常显示。
- 简体中文界面正常；运行验证发现并修复了 `Website`、`Submit Bug` 两个硬编码英文，最终显示为“网页”“提交问题”。
- Catalogue 候选 SHA-256：`3B0E87230B9E5DC1A51B9D05D9CD86A2AD40E788E2A721C67780A4E6F0816BC1`。
- Bridge 候选 SHA-256：`02376A0565BC19FC95392585D42483208F64D945D390BF1DDCA1B369B3F8875F`。

## 回退位置

- 原始 Catalogue 与 Bridge：`backups/catalogue-20260715-2026Reset-validation`。
- 按钮修复前的 Catalogue 候选：`backups/catalogue-20260715-literal-button-fix`。

## 发布范围

- 发布 Catalogue `1.11.2-2026Reset`，许可证 MIT。
- 发布 Codex Catalogue Bridge `1.0.1-2026Reset`，仅客户端使用。
- 不发布到服务器配置，也不改变世界、存档、配方或网络协议。
