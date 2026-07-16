# JER 阶段三：汉化审查

检查对象：`Just Enough Resources 1.6.0.17`。

## 完整性

- `en_us.json`：144 个语言键。
- 原 `zh_cn.json`：144 个语言键。
- 修订候选 `zh_cn.json`：144 个语言键。
- 与英文的缺失键、额外键均为 `0`；候选包 JSON 已通过 PowerShell `ConvertFrom-Json` 解析验证。

因此，本次不补齐缺失翻译，只修正已有中文的术语、语义和可读性。

## 质量修订

- 将仍为英文的 `diyData` 配置说明改为中文，并保留“需要重启 Minecraft”的操作条件。
- 明确维度黑名单的实际含义：JER 不分析这些维度中的资源生成信息。
- 将“地牢资源”“生物资源”等泛化名称调整为“地牢战利品”“生物掉落”。
- 修正村庄房屋和堡垒遗迹箱子的机器翻译，例如“乡村沙漠之家”“堡垒桥”。
- 将 `jer.worldgen.dimensions` 从错误的“有效生物群系”修正为“有效维度”。
- 统一“可获得的附魔”“工作站方块”“生成概率”等 JEI/JER 常用术语。
- 修正三个逻辑反向的条件提示：`pastWorldTime`、`beforeWorldTime` 和 `belowLooting`。

## 候选产物

- 生成脚本：[build-jeresources-localization.ps1](../../../tools/build-jeresources-localization.ps1)。
- 候选 JAR：`D:\MC-Harmony-Home\candidates\JustEnoughResources-NeoForge-1.21.1-1.6.0.17-2026Reset.jar`。
- SHA-256：`EC2DCA75F221F0B83E39D02EA74F54EDF8C1782E3FAB4227E616F18F6D00BD68`。
- 脚本会逐条确认 21 个原文本均恰好出现一次，才写入替换结果。

候选包仅用于本地批次验证；游戏当前加载的
`JustEnoughResources-NeoForge-1.21.1-1.6.0.17.jar` 未被修改。

## 结论与后续流程

阶段三通过。修订范围仅限 `assets/jeresources/lang/zh_cn.json`，不触及游戏逻辑、配置格式或服务端行为。

下一阶段是阶段四：为 JER 补充 Catalogue 模组介绍页的双语说明、项目链接和带 `[JER]` 前缀的问题反馈入口。该阶段同样只构建候选补丁，不需要关闭游戏。
