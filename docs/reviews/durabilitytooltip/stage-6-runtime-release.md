# Durability Tooltip 阶段六：运行与发布

当前状态：阶段五已正式标记物品等级“不适用”；Durability Tooltip 本体无需重新构建，Bridge 候选已构建。当前批次登记为 `10 / 10`，但尚未安装任何候选。

## 候选文件

- Durability Tooltip：继续使用当前原始文件 `durabilitytooltip-1.1.6-neoforge-mc1.21.jar`，没有重新编译或替换该模组。
- Bridge：`codex-catalogue-bridge-1.0.10-2026Reset.jar`。
- Bridge 位置：`D:\MC-Harmony-Home\candidates\codex-catalogue-bridge-1.0.10-2026Reset.jar`。
- Bridge SHA-256：`963A11D30BE2E0CB6AEFDDC0058623615809B9EC57063E2906BF94CCBCB01D60`。
- 当前游戏使用的 Bridge 与 Durability Tooltip JAR 均未被替换。

## 批次验证要求

先补齐当前批次中缺失的 JEED、JEI 候选文件，再关闭游戏、备份原始候选目标并安装最新候选。安装后验证：

1. 启动日志没有 Durability Tooltip、Bridge、NeoForge、Config Lib 或 Mixin 错误。
2. 悬停任意可损坏工具、武器或护甲时，耐久数值、文字和耐久条三种样式均正常显示。
3. 简体中文下显示“耐久度”“毫发无损”“轻微受损”“严重损坏”“濒临破碎”；英文语言下显示对应英文。
4. `onlyVanillaTools`、`showWhenFull`、黑名单和颜色样式配置按原有语义生效。
5. 不安装 Durability Tooltip 的服务器连接正常，服务器目录保持不含该模组。
6. Catalogue 正确显示 Durability Tooltip 双语介绍、官方网页和预填 `[Durability Tooltip]` 的问题反馈链接。

## 发布边界

Durability Tooltip 上游使用 `All rights reserved`。公开仓库保存审查记录和 Bridge 覆盖；候选完成批次运行验证前不创建 GitHub Release，也不覆盖当前游戏 JAR。
