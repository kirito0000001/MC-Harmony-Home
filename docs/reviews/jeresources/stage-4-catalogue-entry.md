# JER 阶段四：Catalogue 条目

检查对象：`Just Enough Resources 1.6.0.17`（模组 ID：`jeresources`）。

## 游戏内介绍

Codex Catalogue Bridge 为 JER 添加了以下双语介绍：

- 英文：`Adds JEI pages for resource generation, mob drops, chest loot, village trades, and world-generation information. It only displays information and does not modify the world or loot.`
- 中文：`为 JEI 补充资源生成、生物掉落、战利品箱、村民交易和世界生成信息页，仅用于查询展示，不会修改世界或战利品。`

介绍明确 JER 的作用是查询与展示，不会把它误写成修改世界生成、战利品或服务器玩法的模组。

## 网页与问题反馈

- 网页：`https://www.curseforge.com/minecraft/mc-mods/just-enough-resources-jer`，来自 JER 自身元数据中的 `displayURL`。
- BUG：统一问题表单会预填 `[JER]` 标题，并附加已创建的 `bug,mod:jeresources` 标签。

## 候选构建与验证

- Bridge 候选版本：`1.0.4-2026Reset`。
- 候选文件：`D:\MC-Harmony-Home\candidates\codex-catalogue-bridge-1.0.4-2026Reset.jar`。
- SHA-256：`DE2FF9EEBAC880E1796685DBB68CC20CD3147548E3FB7A9E1FDE6510203FCABA`。
- 固定 JDK 21 与 Gradle 8.10 的 `build --offline --no-daemon` 已通过；新增单元测试验证 JER 的官方网页、`[JER]` 问题前缀和双语介绍。
- 已直接检查候选 JAR 内的覆盖 JSON 和两个语言文件，JER 条目均已打包。

NeoForge 模组元数据中的版本为 `${file.jarVersion}`，由构建产物版本提供，属于正常占位符，不是候选包缺失版本信息。

## 构建记录更新

[build-environment.md](../../build-environment.md) 已记录两条本次遇到的 PowerShell 规则：

- `String[]` 参数必须用命名绑定，例如 `-GradleArgs @('test', '--offline', '--no-daemon')`，避免位置参数误绑定到 `JavaHome`。
- 字符串中变量后紧接冒号时使用 `${count}:`，不能写成 `$count:`。

## 结论与后续流程

阶段四通过。候选 Bridge 未替换游戏正在使用的版本，仍等待 10 个模组候选的统一客户端验证。

下一阶段是阶段五：物品等级。JER 不注册独立物品、方块、配方或战利品条目，预计会快速标记为“不适用”，并保留理由。
