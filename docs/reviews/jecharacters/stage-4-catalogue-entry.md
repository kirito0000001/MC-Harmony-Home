# Just Enough Characters 阶段四：Catalogue 条目

检查对象：`Just Enough Characters 4.5.26`（模组 ID：`jecharacters`）。

## 游戏内介绍

Codex Catalogue Bridge 为 JEC 添加以下双语介绍：

- 英文：`Adds Pinyin and Pinyin-initial search matching to JEI, and extends it to supported search interfaces from other installed mods. It is a client-side search enhancement.`
- 中文：`为 JEI 提供全拼与拼音首字母检索，并为已兼容的其他模组搜索界面扩展同样的匹配方式。仅客户端生效。`

介绍准确说明了 JEI 拼音检索和对已兼容搜索界面的扩展范围，同时明确它不提供服务器玩法或物品内容。

## 网页与问题反馈

- 网页：`https://github.com/Towdium/JustEnoughCharacters`，来自 JEC 自身元数据的 `displayURL`。
- BUG：统一问题表单会预填 `[JEC]`，并附加已创建的 `bug,mod:jecharacters` 标签。

## 候选构建与验证

- Bridge 候选版本：`1.0.5-2026Reset`。
- 候选文件：`D:\MC-Harmony-Home\candidates\codex-catalogue-bridge-1.0.5-2026Reset.jar`。
- SHA-256：`AFC27FF315048E7BDABD346DA9368C6717B975477AFCCB1B1E73A4D8F25814CD`。
- 固定 JDK 21 和 Gradle 8.10 的 `build --offline --no-daemon` 已通过；新增单元测试覆盖 JEC 的官方网页、`[JEC]` 问题标题和双语介绍。
- 已直接检查候选 JAR 的覆盖 JSON、英文和简体中文资源，JEC 条目均已打包。

候选 Bridge 未替换游戏当前使用的 `1.0.1-2026Reset`，等待批次运行验证。

## 结论与后续流程

阶段四通过。下一阶段是阶段五：物品等级；JEC 不注册独立物品、方块、配方或战利品，预计将依据阶段零和阶段一的结论快速标记为“不适用”，随后直接进入阶段六。
