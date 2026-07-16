# AppleSkin 阶段三：汉化审查

检查对象：`AppleSkin 3.0.9`。

## 完整性

- `en_us.json`：22 个语言键。
- 原 `zh_cn.json`：22 个语言键。
- 修订候选 `zh_cn.json`：22 个语言键。
- 候选中文与英文相比缺失键、额外键均为 `0`；JSON 已通过 PowerShell 解析验证。

## 质量修订

- 将物品提示、手持食物和副手食物的“正在拿取”措辞统一为自然的“手持”表达。
- 将 `exhaustion` 统一为“疲劳值”，明确疲劳条是饥饿条下方的进度，而不是含糊的“消耗度”。
- 将 `Debug Screen` 明确为 “F3 调试界面”，保留饥饿值、饱和度和疲劳值三项实际信息。
- 统一 HUD、Shift、透明度、叠加层和原版动画的排版与术语；数值范围 `1.0` 与 `0.0` 保持不变。
- 改写 AppleSkin 的简体中文简介，使其描述为食物相关 HUD 增强，而不是生硬的“增加了各种改进”。

## 候选产物

- 生成脚本：[build-appleskin-localization.ps1](../../../tools/build-appleskin-localization.ps1)。
- 候选 JAR：`D:\MC-Harmony-Home\candidates\appleskin-neoforge-mc1.21-3.0.9-2026Reset.jar`。
- SHA-256：`02AB6E6B999FD347390AF7E808514B58205367CFD0485096492CBA399EE2956C`。
- 脚本逐条验证 19 段原文各恰好出现一次后才写入候选；构建中发现一条原 JSON 的冒号格式差异后已修正匹配规则并重新完整验证。

游戏当前加载的 `appleskin-neoforge-mc1.21-3.0.9.jar` 未被修改。

## 结论与后续流程

阶段三通过。修订仅触及简体中文语言资源，不改变 HUD、网络同步、配置格式或服务器部署边界。

下一阶段是阶段四：为 AppleSkin 添加 Catalogue 双语介绍、官方网页和预填 `[AppleSkin]` 的问题反馈入口；该阶段只构建候选 Bridge，不需要关闭游戏。
