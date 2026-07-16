# Mouse Tweaks 阶段二：服务端部署判断

检查对象：`Mouse Tweaks 2.26.1`。

## 静态证据

- `MouseTweaksNeo` 仅在 `FMLEnvironment.dist == Dist.CLIENT` 时初始化并注册 `ScreenEvent` 输入处理；非客户端环境直接停止初始化。
- 主逻辑直接引用 `Minecraft`、`AbstractContainerScreen`、`CreativeModeInventoryScreen`、`LocalPlayer` 和客户端配置界面。
- 容器移动通过原版界面的 `slotClicked`，使用 `PICKUP` 或 `QUICK_MOVE`，由远端服务器按既有容器协议验证每一次操作。
- `jdeps` 未发现服务端类依赖；JAR 条目中没有 payload、packet、network、server 或 command 实现。

## 结论

- 分类：`client_only`。
- 不复制到 `D:\其他应用\QQ\接收文件\我的世界mod服务器版本`。
- 客户端安装后可连接未安装 Mouse Tweaks 的服务器；服务器不需要了解鼠标拖拽或滚轮手势，只需继续处理原版槽位点击。
- 已在 `服务器模组筛选记录.csv` 记录为 `not_copied`。

## 后续流程

下一阶段是阶段三：检查配置界面、快捷操作、错误提示和模组介绍的中英文完整性与简体中文自然度。该阶段不需要关闭游戏或安装候选。
