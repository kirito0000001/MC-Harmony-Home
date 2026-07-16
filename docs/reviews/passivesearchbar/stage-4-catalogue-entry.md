# Passive Search Bar 阶段四：Catalogue 条目

检查对象：`Passive Search Bar 1.0.1-2026Reset` 候选（模组 ID：`passivesearchbar`）。

## 游戏内介绍

Codex Catalogue Bridge 添加以下双语介绍：

- 英文：`Prevents the Creative inventory search box from taking keyboard focus automatically. Click it when you want to search; clicking elsewhere returns focus to normal inventory controls.`
- 中文：`防止创造模式物品栏打开时搜索框自动抢占键盘焦点。需要检索时点击搜索框输入；点击其他位置后，键盘输入会回到正常物品栏操作。`

介绍明确了它只改变创造物品栏的焦点行为，不会让玩家误以为它能修改 JEI 搜索、服务端菜单或普通生存背包。

## 网页与问题反馈

- 网页：`https://github.com/kirito0000001/MC-Harmony-Home/tree/main/mods/passivesearchbar`，直接进入本模组的源码与构建配置。
- BUG：统一问题表单会预填 `[Passive Search Bar]`，并附加已创建的 `bug,mod:passivesearchbar` 标签。

## 候选构建与验证

- Bridge 候选版本：`1.0.9-2026Reset`。
- 候选文件：`D:\MC-Harmony-Home\candidates\codex-catalogue-bridge-1.0.9-2026Reset.jar`。
- SHA-256：`B11163879C1DB4A7A296A3B0D114232A9CD6614FE471B32F9279F87FAFB79591`。
- 固定 JDK 21 与 Gradle 8.10 的 `build --offline --no-daemon` 已通过；新增单元测试验证源码页面、`[Passive Search Bar]` 问题标题和双语介绍。
- 已直接检查候选 JAR 的覆盖 JSON、英文资源和简体中文资源，Passive Search Bar 条目均已打包。

候选 Bridge 未替换游戏当前使用的 `1.0.1-2026Reset`，等待批次运行验证。

## 结论与后续流程

阶段四通过。Passive Search Bar 不注册独立物品，因此阶段五将快速标记为“不适用”，随后直接进入阶段六。
