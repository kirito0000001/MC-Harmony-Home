# JEI 阶段一：代码与性能审查

检查对象：`Just Enough Items 19.38.0.366`

源码基线：官方 `1.21.1` 分支提交 `ce49c24326c0bcae3e2a70f44eabdf261de66b29`。该提交的源码版本为 `19.38.0`；发行流水线将构建号追加为安装 JAR 的 `.366`。

## 范围与结论

- 审查覆盖 1,045 个 Java 源文件的模块结构、异步与线程原语、文件资源处理、网络注册、客户端启动和物品搜索主路径。
- JEI 的实际启动与图形运行主要在客户端事件和主线程上执行；网络处理显式使用 NeoForge 的 `HandlerThread.MAIN`。
- 书签、查询历史和配置文件使用 `try-with-resources`，写入通过临时文件后原子替换；未发现未关闭流、强制 GC、强制退出或不受控进程调用。
- 不进行泛化的性能微优化。JEI 是 JEP、JEED 等扩展的共同基础，错误地改变线程、缓存或插件调用顺序的兼容性风险远大于未经测量的收益。

## 风险与对策

### 1. 配置保存时间表并发访问

- 风险等级：`P2`。
- 位置：`Common/.../ConfigSerializer.java` 的静态 `saveTimes`。
- 证据：配置保存由 `DelayedExecutor` 的后台单线程执行；配置文件监视回调仅设置重新加载标记，之后主线程读取配置时会同时读取同一张 `HashMap`。普通 `HashMap` 不保证并发 `get` 与 `put` 的内存可见性或结构安全。
- 影响：频繁修改 JEI 配置、编辑配置文件、切换资源或退出游戏的重叠窗口中，可能错误判断“文件由 JEI 自己刚保存”，极端情况下会出现竞态。
- 最小对策：将该表改为 `ConcurrentHashMap<Path, FileTime>`，并增加覆盖后台保存与主线程读取交错的回归测试。不改变配置格式、保存延迟或用户配置。

### 2. 空搜索时的公共线程池过滤

- 风险等级：`P3`，需要性能数据确认。
- 位置：`Gui/.../IngredientFilter.java` 的空搜索分支。
- 证据：在没有搜索词时，所有已注册物品会通过 `parallelStream()` 过滤、排序并生成缓存。缓存失效、重建搜索索引或首次打开界面时会同步等待该结果。
- 影响：拥有大量物品或较慢物品渲染扩展的整合包，可能在首次打开背包、修改筛选设置或重载后出现短暂卡顿；它也会与其他使用公共线程池的模组竞争 CPU。
- 对策：按本整合包的界面约定，空搜索不再列出全部物品。解析结果为空时直接返回空结果，不进入 `parallelStream()`、排序或缓存重建；右侧面板显示低对比度的“输入关键词搜索 / Type to search”提示。输入任意有效关键词后，原有搜索、排序、黑名单和分页路径保持不变。

### 3. 配置文件监视回调线程

- 风险等级：`P3`，目前不建议修改。
- 位置：`Common/.../FileWatcherThread.java`。
- 证据：每个 500 ms 的稳定文件变更批次都会创建一个非守护回调线程。当前 JEI 自己注册的回调只设置原子重新加载标记，因此正常使用开销很低。
- 影响：外部工具持续写入 JEI 配置时，短时间内可能创建多个回调线程；但修改通用回调模型会影响插件的磁盘写入完成保障。
- 对策：保持上游实现，不做“线程池化”或强制同步。若日志出现该线程频繁创建，再针对实际写入来源处理。

## 后续建议

## 已实施的最小改动

- `ConfigSerializer.saveTimes`：由 `HashMap` 改为 `ConcurrentHashMap`，只处理并发可见性与结构安全，不修改配置文件格式、延迟或保存流程。
- `IngredientFilter`：空查询直接产生空结果，避免无意义地遍历整合包所有注册物品。
- `IngredientListOverlay`：空结果时在物品面板中心显示双语提示；提示只在面板显示且结果为空时绘制。

## 回归证据

以下命令于 2026-07-16 在隔离的 D 盘 Gradle 缓存中通过：

```powershell
& 'D:\MC-Harmony-Home\tools\build-jei-neoforge.ps1' -Tasks '--no-configuration-cache',':Gui:test','--tests','mezz.jei.gui.overlay.IngredientListOverlayTest',':Common:test','--tests','mezz.jei.test.ConfigSerializerTest',':NeoForge:test','--tests','mezz.jei.test.IngredientFilterTest'
```

阶段一的两个已确认对策均已构建到候选 JAR；文件监视回调线程仍保持上游实现，继续作为性能观察项。候选不会直接覆盖正在使用的 JEI，等待十个模组一次的游戏内验证。
