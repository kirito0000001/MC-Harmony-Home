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
7. Codex Catalogue Bridge `1.0.11-2026Reset`，集中包含 JEED、JEI、JER、JEC、AppleSkin、Mouse Tweaks、Passive Search Bar 和 Durability Tooltip 的 Catalogue 覆盖。

Mouse Tweaks 与 Durability Tooltip 本体没有被替换；它们在本批仅通过最新 Bridge 获得 Catalogue 信息。

首次启动发现 Mouse Tweaks 构造期 Mixin 的静态处理器错误，已只升级 Bridge 到 `1.0.11-2026Reset` 并保留失败版本备份。详情见 [Mouse Tweaks 启动热修复](mousetweaks/runtime-hotfix-20260716.md)。

## 运行验证

现在需要启动 `1.21.1-NeoForge_21.1.235` 并进行批次验证。优先检查启动是否正常，再检查 JEI 空搜索提示、JEED 状态说明、JER 信息页、JEC 拼音搜索、AppleSkin 提示、Mouse Tweaks 配置汉化、Passive Search Bar 多次输入和 Durability Tooltip 的三种耐久样式。

任何启动崩溃或功能异常都可直接从上述备份目录恢复对应原 JAR；不要让 PCL 自动下载覆盖这批已验证哈希的候选文件。

## 2026-07-16 本地化与命名规整

第二次安装时间：`2026-07-16 17:31:28`。

- Bridge 升级为 `1.0.12-2026Reset`，SHA-256：`D69A838B3DE99D3EAB1AD3E4EA68D4537C65B385C515E8CEE55EA39D1A576893`。
- Passive Search Bar 升级为 `1.0.2-2026Reset`，SHA-256：`7C7610ABB6AC18FB79A56C4271374318C3AC8F5B00E081904E55054C44EF440B`。
- 两个 live JAR 均与候选文件逐字节一致。
- 前十个已审查模组统一采用 `[中文功能] 原始技术名-版本.jar`；以后每个模组完成汉化审查后沿用相同格式。
- 源码目录继续使用稳定的模组 ID，不随中文文件名变化。
- `鼠标手势.jar` 与 Mouse Tweaks 官方文件的 SHA-256 完全相同，已在备份后从 live 目录移除，只保留统一命名的一份。
- 静态扫描确认 `justenoughprofessions`、`catalogue`、`jeed`、`jei`、`jeresources`、`jecharacters`、`appleskin`、`mousetweaks`、`passivesearchbar`、`durabilitytooltip` 与 `codex_catalogue_bridge` 均各自只加载一份 JAR。

备份位置：

`D:\MC-Harmony-Home\backups\codex-localization-batch-20260716-173128`

`filename-map.txt` 记录了每个旧文件名、新文件名和重复文件处理方式。该备份目录只保留在本机，不提交到 Git。
