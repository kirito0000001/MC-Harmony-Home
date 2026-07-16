# Passive Search Bar 阶段二：服务端部署判断

检查对象：`Passive Search Bar 1.0.1-2026Reset` 候选。

## 静态证据

- `neoforge.mods.toml` 将 NeoForge 和 Minecraft 两项依赖都声明为 `side="CLIENT"`。
- `passivesearchbar.mixins.json` 仅在 `client` 数组中加载 `CreativeModeInventoryScreenMixin`。
- 唯一 Mixin 仅引用 `CreativeModeInventoryScreen` 和 `EditBox`，只会在本地创造模式物品栏界面处理鼠标焦点。
- 全部 Java 源码只有模组入口、纯焦点判断和 GUI Mixin；没有 payload、packet、network、服务端事件、命令、世界数据或存档逻辑。
- 构建脚本也只配置客户端运行配置。

## 结论

- 分类：`client_only`。
- 不复制到 `D:\其他应用\QQ\接收文件\我的世界mod服务器版本`。
- 安装候选客户端后可以连接未安装该模组的服务器；服务端不会参与创造搜索框的焦点处理。
- 已在 `服务器模组筛选记录.csv` 记录为 `not_copied`。

## 后续流程

阶段三接着审查玩家可见的文本与语言资源。该模组没有配置界面、提示、命令反馈或物品文本，预计只需确认静态模组介绍的双语呈现路径。
