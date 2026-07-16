# JEI 阶段六：运行与发布

当前状态：阶段五已完成并标记物品等级“不适用”；源码候选已构建，等待十个模组一次的运行验证批次；进度为 `4 / 10`。

## 候选文件

- 文件：`jei-1.21.1-neoforge-19.38.0.366-2026Reset.jar`
- 位置：仅在本地审查工作区的 `mods/jei/NeoForge/build/libs/` 中。
- `META-INF/neoforge.mods.toml` 内版本：`19.38.0.366-2026Reset`。
- SHA-256：`4FA2188BCC91015C5A3D3247E5DD89719B4C239C2F1357A76D3D52DD9C3C8F1C`。
- 已确认包内含 `en_us.json` 与 `zh_cn.json` 的 `jei.message.search.prompt` 键。
- 未安装到正在使用的 Minecraft 实例。
- Catalogue 条目依赖本地 `codex-catalogue-bridge-1.0.3-2026Reset` 候选；该 Bridge 也未替换游戏中的 `1.0.2-2026Reset`。

## 批次验证要求

批次达到 `10 / 10` 后，关闭游戏，备份当前 JEI JAR，再安装候选并验证：

- 启动日志没有 JEI、JEED、Catalogue 或 Mixin 错误。
- 背包或容器界面打开后，未输入关键词时右侧物品面板为空，并显示“输入关键词搜索”。
- 输入物品名、模组名或已有 JEI 搜索语法后，结果、分页、收藏、配方和用途查询正常。
- 修改 JEI 配置或退出世界时，没有配置保存、文件监视或线程相关错误。
- 安装对应的 Bridge 候选后，Catalogue 正确显示 JEI 的中英文介绍、官方网页链接和带 `[JEI]` 前缀的问题反馈链接。

## 发布边界

JEI 上游使用 MIT 许可证，文本补丁和审查记录可以公开；本候选在完成批次运行验证前不创建 GitHub Release，也不覆盖现有游戏 JAR。公开仓库只保存可审查的补丁、构建工具和记录，不提交上游源码工作树或构建缓存。
