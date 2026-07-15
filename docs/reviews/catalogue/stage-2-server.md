# Catalogue 阶段二：服务器部署判断

检查对象：`Catalogue 1.11.2-2026Reset`

结论：`client_only`

## 代码与元数据证据

- NeoForge 的 `@Mod` 主入口 `Catalogue` 构造函数为空，不注册服务端玩法、数据或同步逻辑。
- 所有实际行为都在 `ClientEvents` 与 `ClientCatalogue` 中，二者均明确标记 `Dist.CLIENT`。
- 源码未发现自定义网络包、网络频道、服务端 tick、区块/实体事件、命令、存档数据、能力、世界生成或 Mixin。
- Catalogue 提供的是本地模组目录、收藏夹和链接页面；服务端不会向客户端同步其中任何内容。
- 模组元数据包含 `updateJSONURL`。NeoForge 在服务器加载 JAR 时仍会发起一次版本检查请求，网络不可达时只记录超时警告，但这是额外且没有玩法价值的服务端工作。

## 专用服务器烟雾验证

使用 `Catalogue 1.11.2-2026Reset` 的 NeoForge 1.21.1 开发专用服务器已实际完成启动：

- 日志出现 `Starting minecraft server version 1.21.1`。
- 日志出现 `Done (12.391s)!`。
- 没有 Catalogue 的客户端类加载、网络协议或启动崩溃。
- NeoForge 的版本检查线程记录了 Catalogue 更新 URL 超时警告；不影响启动，但进一步说明没有必要让服务器加载这个 UI 模组。

该验证证明 JAR 在专用服务器上是“可加载”的，不等于它应当进入服务器包。

## 部署规则

- 客户端保留 Catalogue 与 `codex-catalogue-bridge`。
- 不将 Catalogue 或桥接补丁复制到 `profiles/server/manifest.json`、服务器暂存目录或服务器 `mods` 目录。
- 客户端连接服务器时，Catalogue 继续只展示本机客户端的模组信息；它不改变服务器存档、配方、网络协议或权限。
