# Mouse Tweaks 阶段四：Catalogue 条目

检查对象：`Mouse Tweaks 2.26.1`（模组 ID：`mousetweaks`）。

## 游戏内介绍

Codex Catalogue Bridge 为 Mouse Tweaks 添加以下双语介绍：

- 英文：`Adds mouse-driven inventory controls including drag distribution, quick movement, and scroll-wheel transfers. It uses the normal container click protocol and is client-side only.`
- 中文：`为背包和容器提供鼠标拖拽分配、快速转移和滚轮移动等操作。使用原版容器点击协议，仅客户端需要。`

介绍覆盖实际使用频率最高的三类操作，并明确它不会改变服务端容器规则，避免把客户端便利功能误解为服务端必装内容。

## 网页与问题反馈

- 网页：`https://www.curseforge.com/minecraft/mc-mods/mouse-tweaks`。
- BUG：统一问题表单会预填 `[Mouse Tweaks]`，并附加已创建的 `bug,mod:mousetweaks` 标签。

## 候选构建与验证

- Bridge 候选版本：`1.0.8-2026Reset`。
- 候选文件：`D:\MC-Harmony-Home\candidates\codex-catalogue-bridge-1.0.8-2026Reset.jar`。
- SHA-256：`FEE1CD3E4771435232691574DAD39EBC504D081D2CA1C4E2D6FAFD84BBF2E9A3`。
- 固定 JDK 21 与 Gradle 8.10 的 `build --offline --no-daemon` 已通过；新增单元测试验证官方网页、`[Mouse Tweaks]` 问题标题和双语介绍。
- 已直接检查候选 JAR 的覆盖 JSON、英文和简体中文资源，以及可选的 `MouseTweaksConfigScreenMixin` 类；所需内容均已打包。

候选 Bridge 未替换游戏当前使用的 `1.0.1-2026Reset`，等待批次运行验证。

## 结论与后续流程

阶段四通过。Mouse Tweaks 不注册独立物品，因此阶段五将快速标记为“不适用”，并直接进入阶段六。
