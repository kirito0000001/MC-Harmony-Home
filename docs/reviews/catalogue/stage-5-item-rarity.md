# Catalogue 阶段五：物品等级划分

检查对象：`Catalogue 1.11.2-2026Reset` 与 `Codex Catalogue Bridge 1.0.1-2026Reset`

结论：不适用，默认通过。

## 检查结果

- Catalogue 不注册自己的物品、方块、实体、创造模式标签、配方、战利品表、标签或数据包内容。
- Catalogue 源码中的 `TextureManager.register` 仅注册客户端动态纹理，用于显示其他模组的横幅和图标；它不是 Minecraft 物品注册。
- Catalogue 的资源仅包含界面纹理、语言文件、横幅、图标和背景，不包含物品模型、方块状态、配方或战利品数据。
- Codex Catalogue Bridge 只提供客户端 Mixin、语言键和链接覆盖数据，同样不注册物品或玩法内容。

## 处理结果

- 不写入物品等级数据库。
- 不生成 Loot Beams、名称颜色或战利品覆盖规则。
- 不计入整合包白、绿、蓝、紫、金、红任何一个物品等级的比例。
- 后续若 Catalogue 新增可获取内容，应在该版本的阶段五中单独评估。
