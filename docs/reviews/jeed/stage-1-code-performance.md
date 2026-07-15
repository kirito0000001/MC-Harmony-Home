# JEED 阶段一：代码与性能审查

状态：五项已确认的正确性修复已经完成源码构建；尚未替换游戏内文件。

## 性能结论

- `getEffectList()` 在 JEI、REI 或 EMI 注册效果信息时运行，列表规模有限，不是每 tick 或每帧的热点。
- 运行时仅处理当前可见的效果图标和提示；此路径未发现网络、磁盘 IO、执行器或异步任务。
- 未发现有可测量收益且低风险的源码性能优化点。没有性能剖析依据时加入缓存或异步只会增加缓存失效与线程安全风险。

## 正确性修复

1. **JEI 点击效果的结果被丢弃**：`getClickableIngredientUnderMouse` 创建了 `IClickableIngredient` 却返回空结果。现在会返回 JEI 构建的结果，并安全转换泛型以符合方法签名。
2. **材料顺序不稳定**：`groupIngredients` 使用 `HashMap`，但调用方按 ID 排序。现在改为 `LinkedHashMap`，使显示的来源物品顺序可预测。
3. **查看器插件可能不存在**：公开 API 与原生客户端点击路径会直接调用 `Jeed.PLUGIN`。现在没有兼容查看器时不会吞掉原始点击。
4. **Stylish Effects 兼容层假定列表非空**：两个提示文本移除路径都加入非空检查，点击钩子也会在存在查看器插件时才消费事件。
5. **缺失描述会显示原始翻译键**：按住 Shift 的描述路径改为使用 `EffectInfo.getDescription`，保留 JEED 的“无可用描述”回退。

## 验证

- `tools/test-jeed-source-review.ps1`：修复后通过。
- `tools/build-jeed-neoforge.ps1`：使用 JDK 21、Gradle 8.10 的离线 `:neoforge:build` 通过。
- 上游已有的弃用 API 与缺失注解类警告仍然存在；它们不是本次改动引入，且不妨碍构建成功。
