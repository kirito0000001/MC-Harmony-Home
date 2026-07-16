# Passive Search Bar 阶段六：运行与发布

当前状态：阶段五已正式标记物品等级“不适用”；Passive Search Bar 和 Bridge 候选均已构建，等待十个候选一次的运行验证批次；当前进度为 `9 / 10`。

## 候选文件

- Passive Search Bar：`passivesearchbar-1.0.1-2026Reset.jar`。
- Passive Search Bar 位置：`D:\MC-Harmony-Home\candidates\passivesearchbar-1.0.1-2026Reset.jar`。
- Passive Search Bar SHA-256：`2237637B4D59E5E8929B5777EF07C3F7C3C03148A56B36C56444D7C8304784F6`。
- Bridge：`codex-catalogue-bridge-1.0.9-2026Reset.jar`。
- Bridge 位置：`D:\MC-Harmony-Home\candidates\codex-catalogue-bridge-1.0.9-2026Reset.jar`。
- Bridge SHA-256：`B11163879C1DB4A7A296A3B0D114232A9CD6614FE471B32F9279F87FAFB79591`。
- 两个候选均未替换正在使用的 Minecraft JAR。

## 批次验证要求

达到 `10 / 10` 后，关闭游戏、备份原始 Passive Search Bar 和 Bridge，再安装候选并验证：

1. 启动日志没有 Passive Search Bar、Bridge、NeoForge 或 Mixin 错误。
2. 进入创造模式搜索标签时，搜索框不会自动抢占键盘焦点。
3. 点击搜索框输入一次、点击其他位置、重新点击搜索框输入第二次及更多次，均能稳定输入并检索。
4. 搜索框外进行主键物品栏操作后，键盘焦点会回到正常物品栏控制；非主键点击不会意外清除搜索焦点。
5. JEI 搜索、普通生存背包、容器界面与服务器连接保持原有行为。
6. Catalogue 正确显示 Passive Search Bar 双语介绍、源码页面和预填 `[Passive Search Bar]` 的问题反馈链接。

## 发布边界

Passive Search Bar 由当前整合包维护，许可证为 `All Rights Reserved`。公开仓库保存其源码、测试、构建配置和审查记录；候选在完成批次运行验证前不创建 GitHub Release，也不覆盖当前游戏 JAR。
