# 批次运行验证

批次大小：10 个完成源码改动或需要客户端安装验证的模组。

## 当前批次：1

1. Just Enough Professions `4.0.5-2026Reset`：已完成游戏内验证。
2. Catalogue `1.11.2-2026Reset` + Bridge `1.0.1-2026Reset`：已完成游戏内验证，目录打开正常，中文界面、详情介绍与按钮标签正常。
3. Just Enough Effects Descriptions `1.21-2.3.2-2026Reset` + Bridge 条目：已安装，待批次游戏内验证。
4. Just Enough Items `19.38.0.366-2026Reset`：已安装，待批次游戏内验证。
5. Just Enough Resources `1.6.0.17-2026Reset` + Bridge `1.0.4-2026Reset` 条目：已安装，待批次游戏内验证。
6. Just Enough Characters `4.5.26-2026Reset` + Bridge `1.0.5-2026Reset` 条目：已安装，待批次游戏内验证。
7. AppleSkin `3.0.9-2026Reset` + Bridge `1.0.6-2026Reset` 条目：已安装，待批次游戏内验证。
8. Mouse Tweaks `2.26.1` + Bridge 条目与双语配置界面：已统一文件名，并移除一份字节完全相同的重复 JAR，待游戏内验证。
9. Passive Search Bar `1.0.2-2026Reset` + Bridge 条目：已安装焦点同步修复，待验证“打开不抢焦点、点击可输入、点外部失焦、再次点击可输入”。
10. Durability Tooltip `1.1.6` + Bridge 条目：已统一文件名，待批次游戏内验证。

进度：`10 / 10`。

Bridge `1.0.12-2026Reset` 已安装，为前十个条目提供语言感知标题和分段双语介绍。静态检查确认相关模组 ID 各自只出现一次，新 Bridge 与 Passive Search Bar 的 live 哈希均与候选一致。

下一步重新启动 `1.21.1-NeoForge_21.1.235`：先确认能够进入主菜单，再检查 Catalogue 的十个中英文标题、长介绍滚动、Mouse Tweaks 操作说明，以及 Passive Search Bar 的完整焦点循环。运行验证通过后再发布该批构建。

## 启动诊断工具

`Codex Responsive Loading 1.0.0-2026Reset` 已作为独立客户端模组安装，不计入前十个内容模组的批次编号。

- 候选：`D:\MC-Harmony-Home\candidates\codex-responsive-loading-1.0.0-2026Reset.jar`
- 游戏文件：`[响应式启动] codex-responsive-loading-1.0.0-2026Reset.jar`
- SHA-256：`8CE55F4DFCAB15EAD8E49690807A42E75A5F1C7AFEE87F1101544BDE87C3FCA2`
- 静态验证：完整离线构建和全部 JUnit 测试通过；候选与游戏文件哈希一致，游戏目录中仅有一个同名模组。
- 运行验证：下次启动后检查 `logs\debug.log` 中的 `[Codex Responsive Loading] START`、`SLOW`、`DONE` 或 `FAILED` 记录。不要在 Windows 提示等待时主动结束进程；日志会指出持续时间较长的资源监听器和阶段。
