# Just Enough Characters 阶段一：代码与性能审查

检查对象：`Just Enough Characters 4.5.26`，模组 ID 为 `jecharacters`。

## 审查范围与方法

对安装 JAR、嵌入的 NeoForge CoreMod、配置文件和当前实例的类目标进行静态检查。使用 JDK 21 的 `javap` 检查搜索、配置、命令和核心变换入口；将 CoreMod 的 156 个潜在目标与当前 `mods` 目录的类清单交叉比对。

## 核心行为

- `JechJeiPlugin` 调用 JEI 的 `replaceSearchStorage`，以 `JechSearchStorage` 替换高级搜索存储。
- `JechSearchStorage` 使用 PinIn 的 `TreeSearcher` 建立包含匹配索引；查询直接调用 `tree.search`，没有轮询、定时器或后台执行器。
- JEI 向搜索存储写入当前语言下的显示文本。因此其他模组的物品只要拥有正常中文显示名，在被 JEI 收录后同样可用全拼和首字母搜索；缺失中文名则无法得到有意义的拼音键。
- `Match.searchers` 使用 `WeakHashMap` 支撑的 Set 保存搜索存储。配置变更会重新配置 PinIn 并刷新仍存活的索引，不会强引用旧搜索存储。

## 配置基线

当前 `config/jecharacters-client.toml` 已符合推荐值：

- `enableVerbose=false`，避免每次索引写入和每次搜索匹配记录大量日志。
- 全拼输入法与七项模糊拼音匹配均开启，便于中文检索。
- `enableQuote=false`，保持 JEI 默认的搜索词切分方式。
- `config/jecharacters-extra.json` 没有额外注入目标，`removals` 也为空。

## 风险与对策

### P2：CoreMod 的跨模组搜索改写

嵌入 CoreMod 读取 `targets.json`，不只处理 JEI；其潜在清单含 156 个类所有者。当前整合包实际命中 11 个目标类，分别位于 JEI、Create、Jade、Patchouli、Waystones、Cloth Config、FTB Library 和 MaliLib。这样可让这些界面的搜索框也支持拼音，但会随上游方法签名变动带来兼容性风险。

对策：当前没有崩溃或界面异常证据，保留默认目标以维持功能覆盖。若以后某个已命中界面出现搜索异常，在 `config/jecharacters-extra.json` 的 `removals` 中精确添加对应目标字符串即可禁用单点变换，不需要删除 JEC、重编译 JAR 或影响 JEI 拼音搜索。

### P3：手动性能分析命令的 I/O

`/jech profile` 会启动一个最低优先级线程，逐个扫描模组 JAR 和 jar-in-jar 的字节码，并把报告写入 `logs/jecharacters.txt`。它不是启动时或搜索时自动执行的任务。

对策：只在排查搜索兼容性时手动运行，正常游玩不运行该命令；无需修改代码。

### P3：调试日志放大

若手动将 `enableVerbose` 打开，索引写入和匹配路径都会输出日志，在大型整合包中可能造成明显磁盘 I/O 和日志膨胀。

对策：保持当前默认 `false`。这是一项配置约束，不需要源码补丁。

## 结论

未发现可在不损失兼容性或拼音功能前提下安全合入的代码性能优化。正常路径使用树索引，配置重载使用弱引用集合，手动分析也不在后台常驻；因此本阶段不修改 JAR、源码或当前配置。

下一阶段是阶段二：确认其客户端侧入口、无网络同步和无服务端玩法依赖，决定是否复制到服务器模组目录。该阶段不需要关闭游戏。
