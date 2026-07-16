# AppleSkin 阶段二：服务端部署判断

检查对象：`AppleSkin 3.0.9`。

## 静态证据

- 模组元数据将 NeoForge 依赖标为 `side="CLIENT"`，所有食物提示、HUD、物品提示和客户端配置都在客户端侧注册。
- `AppleSkin` 仅在 `FMLEnvironment.dist.isClient()` 时注册 HUD、提示和客户端初始化；服务端不会加载这些客户端渲染入口。
- 该 JAR 同时注册两个 `playToClient` payload：饱和度和疲劳值。注册器调用 `optional()`，不是双端强制网络协议。
- 服务端发送前调用 `ServerGamePacketListenerImpl.hasChannel`，只有客户端声明支持对应 payload 时才发送，不安装 AppleSkin 的玩家不会收到未知数据包。
- 若服务器安装 AppleSkin，`SyncHandler` 会在每个 `ServerPlayer` 的 tick 中比较饱和度与疲劳值，变化时才发送同步包。

## 结论

- 分类：`optional_server_enhancement`。
- 默认：`not_copied`，不复制到 `D:\其他应用\QQ\接收文件\我的世界mod服务器版本`。
- 客户端独立安装时，食物提示、恢复预览与基础 HUD 均可使用；没有服务端 AppleSkin 时，精确疲劳和饱和度同步属于可接受的功能降级。
- 在服务器安装同版本 AppleSkin 可以让已安装客户端获得更精确的数据，但会新增每位在线玩家每 tick 的两次数值比较和变化时的数据包。你的服务器优化目标下，不将它作为默认服务器依赖。
- 已在 `服务器模组筛选记录.csv` 记录为可选增强且不复制。

## 后续流程

下一阶段是阶段三：审查食物提示、HUD 配置与调试文本的中英文完整性和简体中文术语。该阶段不需要关闭游戏或安装候选。
