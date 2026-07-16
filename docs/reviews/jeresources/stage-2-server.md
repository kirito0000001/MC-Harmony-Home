# JER 阶段二：服务端部署判断

检查对象：`Just Enough Resources 1.6.0.17`。

## 静态证据

- JAR 中未发现网络、packet 或 payload 实现；没有把资源生成或掉落信息从服务端同步给客户端的协议。
- `CompatBase` 直接引用 `net.minecraft.client.Minecraft`、`ClientLevel`、`IntegratedServer` 与 `FakeClientLevel`，其查询设计以客户端 JEI 界面为入口。
- JER 的服务端事件只注册了配置、世界卸载处理和 `/jer_profile`；该命令在当前版本已停用，只显示“功能将来会重新加入”的提示。
- JER 的实际功能是把客户端可用的注册表、战利品表和兼容信息显示在 JEI 中。专用服务器安装后没有可供未安装 JER 客户端使用的同步或玩法功能。
- 元数据中 `dependencies.jeresources.side="BOTH"` 描述的是 JEI 依赖适用侧，并非 JER 自身需要部署在两端的声明。

## 结论

- 分类：`client_only`。
- 不复制到 `我的世界mod服务器版本`，也不作为服务器整合包依赖。
- 客户端应保留 JER，与 JEI 一起提供来源、资源生成和掉落查询。
- 服务器若安装 JER，只会增加无用加载和一份配置，不会让客户端获得额外同步功能。

## 后续流程

下一阶段是阶段三：汉化审查。将对比 `en_us` 与 `zh_cn`，检查资源/掉落类别、配置与提示是否完整且自然；该阶段不需要关闭游戏或安装候选。
