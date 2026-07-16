# 批次一：候选安装记录

安装时间：`2026-07-16 16:08:41`。

## 安装前检查

- 未检测到 `1.21.1-NeoForge_21.1.235` 的 Minecraft Java 进程。
- 七个目标文件与候选文件均已验证内部模组 ID 一致。
- 候选安装后逐项重新计算 SHA-256，全部与候选哈希相符。
- 原文件名保持不变，避免 PCL 因文件名变化产生额外索引或下载判断。

## 备份位置

`D:\其他应用\Minecraft\.minecraft\versions\1.21.1-NeoForge_21.1.235\backups\codex-batch-1-20260716-160841`

该目录保存七个替换前的 JAR 和 `manifest.csv`。清单同时记录每个原 JAR 与候选 JAR 的完整 SHA-256。

## 已安装候选

1. JEED `1.21-2.3.2-2026Reset`。
2. JEI `19.38.0.366-2026Reset`。
3. Just Enough Resources `1.6.0.17-2026Reset`。
4. Just Enough Characters `4.5.26-2026Reset`。
5. AppleSkin `3.0.9-2026Reset`。
6. Passive Search Bar `1.0.1-2026Reset`。
7. Codex Catalogue Bridge `1.0.10-2026Reset`，集中包含 JEED、JEI、JER、JEC、AppleSkin、Mouse Tweaks、Passive Search Bar 和 Durability Tooltip 的 Catalogue 覆盖。

Mouse Tweaks 与 Durability Tooltip 本体没有被替换；它们在本批仅通过最新 Bridge 获得 Catalogue 信息。

## 运行验证

现在需要启动 `1.21.1-NeoForge_21.1.235` 并进行批次验证。优先检查启动是否正常，再检查 JEI 空搜索提示、JEED 状态说明、JER 信息页、JEC 拼音搜索、AppleSkin 提示、Mouse Tweaks 配置汉化、Passive Search Bar 多次输入和 Durability Tooltip 的三种耐久样式。

任何启动崩溃或功能异常都可直接从上述备份目录恢复对应原 JAR；不要让 PCL 自动下载覆盖这批已验证哈希的候选文件。
