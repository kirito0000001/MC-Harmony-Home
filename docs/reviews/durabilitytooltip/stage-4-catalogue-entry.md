# Durability Tooltip 阶段四：Catalogue 条目

检查对象：`Durability Tooltip 1.1.6`（模组 ID：`durabilitytooltip`）。

## 游戏内介绍

Codex Catalogue Bridge 添加以下双语介绍：

- 英文：`Adds configurable durability values, status text, or a bar to damageable item tooltips. Runs on the client and can limit display to vanilla tools or selected mod namespaces.`
- 中文：`为可损坏物品的提示补充可配置的耐久数值、状态文字或耐久条。仅客户端运行，可限制为原版工具或按模组命名空间隐藏。`

介绍说明三种耐久显示方式，也明确它只显示已有物品数据，不增加装备内容或服务端玩法。

## 网页与问题反馈

- 网页：`https://www.curseforge.com/minecraft/mc-mods/durability-tooltip`，来自上游元数据的 `displayURL`。
- BUG：统一问题表单会预填 `[Durability Tooltip]`，并附加已创建的 `bug,mod:durabilitytooltip` 标签。

## 候选构建与验证

- Bridge 候选版本：`1.0.10-2026Reset`。
- 候选文件：`D:\MC-Harmony-Home\candidates\codex-catalogue-bridge-1.0.10-2026Reset.jar`。
- SHA-256：`963A11D30BE2E0CB6AEFDDC0058623615809B9EC57063E2906BF94CCBCB01D60`。
- 固定 JDK 21 与 Gradle 8.10 的 `build --offline --no-daemon` 已通过；新增单元测试验证官方页面、`[Durability Tooltip]` 问题标题和双语介绍。
- 已直接检查候选 JAR 的覆盖 JSON、英文资源和简体中文资源，Durability Tooltip 条目均已打包。

候选 Bridge 未替换游戏当前使用的 `1.0.1-2026Reset`，等待批次运行验证。

## 结论与后续流程

阶段四通过。Durability Tooltip 不注册独立物品，因此阶段五将快速标记为“不适用”，随后直接进入阶段六。
