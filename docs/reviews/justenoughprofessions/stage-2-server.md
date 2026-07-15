# Just Enough Professions 阶段二：服务器部署判断

检查对象：`Just Enough Professions 4.0.5-2026Reset`

结论：`client_only`

## 代码与依赖证据

- NeoForge 入口是 `NeoForgeProfessionPlugin`，通过 `@JeiPlugin` 注册职业 JEI 分类和条目；没有常规服务端事件订阅入口。
- 注册逻辑只向 JEI 提供村民职业与工作方块的展示数据，不注册物品、方块、实体、菜单、配方、命令、维度、群系或世界生成。
- 源码没有自定义网络包、网络频道、服务器 tick、区块加载、存档数据或权限逻辑。
- 职业页面预览使用 `RenderHelper`、`GuiGraphics`、`Minecraft.getInstance()` 与村民模型渲染，属于客户端图形路径。
- 模组元数据要求 JEI；专用服务器通常不安装 JEI。即使元数据的依赖侧写为 `BOTH`，该 JAR 对服务端没有独立玩法价值，且会引入不必要的 JEI 依赖风险。

## 部署规则

- 不复制 JEP 到 `profiles/server/manifest.json`、服务器暂存目录或服务器 `mods` 目录。
- 不复制 `codex-catalogue-bridge`；它依赖 Catalogue 客户端界面并只在 `CLIENT` 侧声明。
- 客户端安装 JEP 不会要求服务器安装，也不改变服务端存档、网络协议或配方。

## 联机验证重点

客户端连接服务器后，打开 JEI 的“职业”页面，确认职业模型和工作方块列表正常。该验证只检查客户端 UI；服务端不应出现 JEP 或桥接补丁加载日志。
