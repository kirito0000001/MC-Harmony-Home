# AppleSkin 阶段六：运行与发布

当前状态：阶段五已正式标记物品等级“不适用”；汉化候选和 Catalogue Bridge 候选均已构建，等待十个候选一次的运行验证批次；当前进度为 `7 / 10`。

## 候选文件

- AppleSkin 文件：`appleskin-neoforge-mc1.21-3.0.9-2026Reset.jar`。
- AppleSkin 位置：`D:\MC-Harmony-Home\candidates\appleskin-neoforge-mc1.21-3.0.9-2026Reset.jar`。
- AppleSkin SHA-256：`02AB6E6B999FD347390AF7E808514B58205367CFD0485096492CBA399EE2956C`。
- Bridge 文件：`codex-catalogue-bridge-1.0.6-2026Reset.jar`。
- Bridge SHA-256：`8FECB3C9D8497EA60AE88EBDA26752B60024B6D2E8EECB36B9556D0626EACFCE`。
- 两个候选均未替换正在使用的 Minecraft JAR。

## 批次验证要求

批次达到 `10 / 10` 后，关闭游戏、备份原始 AppleSkin 和 Bridge，再安装候选并验证：

- 启动日志没有 AppleSkin、NeoForge payload、JEI、Catalogue 或 Mixin 错误。
- 手持食物与副手食物时，物品提示、饱和度、疲劳值和预计生命恢复 HUD 显示正常。
- F3 调试界面中的食物数值、HUD 动画和透明度设置正常生效，中文配置提示采用修订后的术语。
- 未安装 AppleSkin 的服务器连接仍可正常进入；当前服务器目录保持不含 AppleSkin。
- Catalogue 正确显示 AppleSkin 双语介绍、官方网页和预填 `[AppleSkin]` 的问题反馈链接。

## 发布边界

AppleSkin 使用 The Unlicense。公开仓库可保存可审查的本地化构建脚本、审查记录和后续经验证的补丁；本候选在完成批次运行验证前不创建 GitHub Release，也不覆盖当前游戏 JAR。
