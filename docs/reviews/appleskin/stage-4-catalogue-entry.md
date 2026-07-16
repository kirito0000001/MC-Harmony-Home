# AppleSkin 阶段四：Catalogue 条目

检查对象：`AppleSkin 3.0.9`（模组 ID：`appleskin`）。

## 游戏内介绍

Codex Catalogue Bridge 为 AppleSkin 添加以下双语介绍：

- 英文：`Displays food hunger, saturation, exhaustion, and estimated health recovery in tooltips and HUD overlays. Optional server synchronization can provide exact food-state values.`
- 中文：`在物品提示和 HUD 叠加层中显示食物恢复的饥饿值、饱和度、疲劳值及预计生命恢复。可选服务器同步可提供更精确的食物状态。`

介绍说明了 AppleSkin 的实际 HUD 功能，也明确精确同步是可选增强，避免误导玩家把它当成服务器必装依赖。

## 网页与问题反馈

- 网页：`https://github.com/squeek502/AppleSkin`，来自 AppleSkin 自身元数据的 `displayURL`。
- BUG：统一问题表单会预填 `[AppleSkin]`，并附加已创建的 `bug,mod:appleskin` 标签。

## 候选构建与验证

- Bridge 候选版本：`1.0.6-2026Reset`。
- 候选文件：`D:\MC-Harmony-Home\candidates\codex-catalogue-bridge-1.0.6-2026Reset.jar`。
- SHA-256：`8FECB3C9D8497EA60AE88EBDA26752B60024B6D2E8EECB36B9556D0626EACFCE`。
- 固定 JDK 21 与 Gradle 8.10 的 `build --offline --no-daemon` 已通过；新增单元测试验证 AppleSkin 官方网页、`[AppleSkin]` 问题标题和双语介绍。
- 已直接检查候选 JAR 的覆盖 JSON、英文和简体中文资源，AppleSkin 条目均已打包。

候选 Bridge 未替换游戏当前使用的 `1.0.1-2026Reset`，等待批次运行验证。

## 结论与后续流程

阶段四通过。下一阶段是阶段五：物品等级；AppleSkin 不注册独立物品、方块、配方或战利品，将依据前序结论快速标记为“不适用”，随后直接进入阶段六。
