# 批次运行验证

批次大小：10 个完成源码改动或需要客户端安装验证的模组。

## 已完成批次：1

1. Just Enough Professions `4.0.5-2026Reset`：已完成游戏内验证。
2. Catalogue `1.11.2-2026Reset` + Bridge `1.0.1-2026Reset`：已完成游戏内验证，目录打开正常，中文界面、详情介绍与按钮标签正常。
3. Just Enough Effects Descriptions `1.21-2.3.2-2026Reset` + Bridge 条目：已完成游戏内验证。
4. Just Enough Items `19.38.0.366-2026Reset`：已完成游戏内验证；空搜索提示、正常搜索与物品浏览工作正常。
5. Just Enough Resources `1.6.0.17-2026Reset` + Bridge `1.0.4-2026Reset` 条目：已完成游戏内验证。
6. Just Enough Characters `4.5.26-2026Reset` + Bridge `1.0.5-2026Reset` 条目：已完成游戏内验证，中文与拼音搜索正常。
7. AppleSkin `3.0.9-2026Reset` + Bridge `1.0.6-2026Reset` 条目：已完成游戏内验证。
8. Mouse Tweaks `2.26.1` + Bridge 条目与双语配置界面：已完成游戏内验证。
9. Passive Search Bar `1.0.2-2026Reset` + Bridge 条目：完整焦点循环已验证，搜索框可以反复点击并正常输入。
10. Durability Tooltip `1.1.6` + Bridge 条目：已完成游戏内验证。

进度：`10 / 10`，运行验证完成。

Bridge `1.0.12-2026Reset` 已安装，为前十个条目提供语言感知标题和分段双语介绍。静态检查确认相关模组 ID 各自只出现一次，新 Bridge 与 Passive Search Bar 的 live 哈希均与候选一致。

2026-07-16 的统一启动验证通过：客户端、存档、Catalogue 条目和前十个模组功能均未发现异常。JEI 普通搜索曾因同时检索 23,552 个原料的名称与提示框文本而出现卡顿；将 `tooltipSearchMode` 改为 `REQUIRE_PREFIX` 后，普通名称/拼音搜索基本无延迟，提示框内容仍可使用 `$关键词` 查询。

## 启动诊断工具

`Codex Responsive Loading 1.0.0-2026Reset` 已作为独立客户端模组安装，不计入前十个内容模组的批次编号。

- 候选：`D:\MC-Harmony-Home\candidates\codex-responsive-loading-1.0.0-2026Reset.jar`
- 游戏文件：`[响应式启动] codex-responsive-loading-1.0.0-2026Reset.jar`
- SHA-256：`8CE55F4DFCAB15EAD8E49690807A42E75A5F1C7AFEE87F1101544BDE87C3FCA2`
- 静态验证：完整离线构建和全部 JUnit 测试通过；候选与游戏文件哈希一致，游戏目录中仅有一个同名模组。
- 运行验证：已完成一次完整启动，没有 `FAILED`。ModernFix 记录从启动到进入世界约 `173.7` 秒；资源重载监听器约持续 `74` 秒，其中 `ModelManager` 产生约 11.3 万个准备任务，`ClientModLoader` 的应用任务累计约 `5.36` 秒，作为后续启动优化依据。

## 当前批次：2

1. Carry On `2.2.5`：阶段 0 已完成，等待阶段一代码与性能审查。

进度：`0 / 10` 个候选完成；当前正在审查第 1 个模组。
