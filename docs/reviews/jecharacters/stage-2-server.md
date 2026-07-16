# Just Enough Characters 阶段二：服务端部署判断

检查对象：`Just Enough Characters 4.5.26`。

## 静态证据

- JEI 集成通过 `IAdvancedSearchRegistration.replaceSearchStorage` 替换客户端搜索存储，不注册方块、物品、配方、实体或服务端数据。
- `JustEnoughCharactersForge.EventHandler` 的进入世界提示先检查 `Level.isClientSide`，之后直接访问 `Minecraft.getInstance()` 与客户端语言设置。
- `/jech` 注册在 `RegisterClientCommandsEvent`，用于切换本地调试日志、输入法设置和手动生成诊断报告；它不是服务器命令。
- JAR 和嵌入 CoreMod 的类引用集中于客户端搜索、GUI、ASM 变换和本地文件输出；未发现 NeoForge payload、网络通道、服务器 tick、专用服务器事件或存档读写实现。

## 结论

- 分类：`client_only`。
- 不复制到 `D:\其他应用\QQ\接收文件\我的世界mod服务器版本`。
- 客户端保留 JEC 即可获得 JEI 及已命中界面的拼音搜索；服务器安装不会给未安装 JEC 的玩家增加搜索能力。
- 已在 `服务器模组筛选记录.csv` 记录为 `not_copied`。

## 后续流程

下一阶段是阶段三：检查 JEC 自身命令、配置、提示和错误信息的中英文完整性与中文自然度。该阶段不需要关闭游戏或安装候选。
