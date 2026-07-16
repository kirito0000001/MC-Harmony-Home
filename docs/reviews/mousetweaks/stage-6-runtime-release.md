# Mouse Tweaks 阶段六：运行与发布

当前状态：阶段五已正式标记物品等级“不适用”；Bridge 候选已构建，等待十个候选一次的运行验证批次；当前进度为 `8 / 10`。

## 候选文件

- Mouse Tweaks：继续使用当前原始文件 `MouseTweaks-neoforge-mc1.21-2.26.1.jar`，没有重新编译或替换该模组。
- Bridge 文件：`codex-catalogue-bridge-1.0.8-2026Reset.jar`。
- Bridge 位置：`D:\MC-Harmony-Home\candidates\codex-catalogue-bridge-1.0.8-2026Reset.jar`。
- Bridge SHA-256：`FEE1CD3E4771435232691574DAD39EBC504D081D2CA1C4E2D6FAFD84BBF2E9A3`。
- 当前游戏使用的 Bridge 与 Mouse Tweaks JAR 均未被替换。

## 批次验证要求

批次达到 `10 / 10` 后，关闭游戏、备份原始 Bridge，再安装候选并验证：

- 启动日志没有 Mouse Tweaks、Bridge、NeoForge 或 Mixin 错误。
- 中文游戏中打开 Mouse Tweaks 配置界面，已覆盖的设置标题、拖拽、滚轮和方向选项显示自然中文；切换英文语言后显示英文。
- 背包和箱子中左键拖拽分配、右键拖拽分配、滚轮快速转移和快速移动均按原有方式工作。
- 与 JEI、Quark 等已安装容器相关模组共同使用时，常用容器界面可正常打开，未出现重复点击或无法取放物品。
- Catalogue 正确显示 Mouse Tweaks 双语介绍、官方网页和预填 `[Mouse Tweaks]` 的问题反馈链接。

## 发布边界

Mouse Tweaks 上游使用 LGPL-3.0。公开仓库可保存可审查的 Bridge 补丁、审查记录和后续经验证的发布材料；本候选在完成批次运行验证前不创建 GitHub Release，也不覆盖当前游戏 JAR。
