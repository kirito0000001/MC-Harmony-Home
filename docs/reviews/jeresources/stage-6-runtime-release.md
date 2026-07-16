# JER 阶段六：运行与发布

当前状态：阶段五已正式标记物品等级“不适用”；汉化候选和 Catalogue Bridge 候选均已构建，等待十个候选一次的运行验证批次；当前进度为 `5 / 10`。

## 候选文件

- JER 文件：`JustEnoughResources-NeoForge-1.21.1-1.6.0.17-2026Reset.jar`。
- JER 位置：`D:\MC-Harmony-Home\candidates\JustEnoughResources-NeoForge-1.21.1-1.6.0.17-2026Reset.jar`。
- JER SHA-256：`EC2DCA75F221F0B83E39D02EA74F54EDF8C1782E3FAB4227E616F18F6D00BD68`。
- Bridge 文件：`codex-catalogue-bridge-1.0.4-2026Reset.jar`。
- Bridge SHA-256：`DE2FF9EEBAC880E1796685DBB68CC20CD3147548E3FB7A9E1FDE6510203FCABA`。
- 两个候选均未替换正在使用的 Minecraft JAR。

## 批次验证要求

批次达到 `10 / 10` 后，关闭游戏、备份原始 JER 和 Bridge，再安装候选并验证：

- 启动日志没有 JER、JEI、Catalogue 或 Mixin 错误。
- JEI 中的资源生成、生物掉落、地牢战利品、村民交易和世界生成页面可正常打开。
- “有效维度”、时间条件、掠夺等级条件及村庄、堡垒遗迹箱子名称显示为修订后的中文语义。
- Catalogue 正确显示 JER 双语介绍、官方网页和预填 `[JER]` 的问题反馈链接。

## 发布边界

JER 的元数据许可证为 `Don't Be a Jerk` 非商业许可。公开仓库只保存本地化构建脚本与审查记录，不提交或发布完整 JER JAR；得到上游明确再分发许可前，不创建该候选的 GitHub Release。
