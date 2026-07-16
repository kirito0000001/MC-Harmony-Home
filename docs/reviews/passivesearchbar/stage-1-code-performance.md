# Passive Search Bar 阶段一：代码与性能审查

检查对象：`passivesearchbar-1.0.0.jar`，对应源码位于 `work/PassiveSearchBarNeoForge`。

## 代码范围

模组只有一个客户端 Mixin，目标为 `CreativeModeInventoryScreen`：

- 在 `selectTab` 中重定向 `EditBox.setCanLoseFocus`，将参数固定为 `true`。
- 在 `selectTab` 中重定向 `EditBox.setFocused`，将参数固定为 `false`。
- 在 `mouseClicked` 开头判断搜索框是否处理点击，并在点击搜索框外时清除焦点。

没有 tick 事件、世界数据、注册表、网络包、后台线程或磁盘读写；正常性能开销仅发生在创造物品栏的鼠标点击和标签页切换时。

## 已确认的功能缺陷（P1）

用户复现：首次在创造物品栏搜索框输入后，后续重新进入或点击搜索框时无法再输入。

根因已由源码和已安装 JAR 的字节码共同确认：

```java
@Inject(method = "mouseClicked", at = @At("HEAD"))
private void passiveSearchBar$clearFocusAfterOutsideClick(...) {
    if (SearchBoxFocusPolicy.shouldClearFocus(this.searchBox.mouseClicked(mouseX, mouseY, button))) {
        this.searchBox.setFocused(false);
    }
}
```

该注入位于原版 `mouseClicked` 的 `HEAD`，且没有取消原方法。它为了判断鼠标是否位于搜索框内，直接调用了一次会改变控件状态的 `searchBox.mouseClicked(...)`；随后原版屏幕仍会分发同一次点击给控件。也就是说，搜索框会收到同一点击两次。反复切换搜索标签时，双重分发与 `selectTab` 中强制清焦点的重定向叠加，造成焦点状态无法稳定恢复。

这不是输入法或键位冲突问题，也不是 JEI 搜索框本身的问题。

## 风险与对策

| 优先级 | 风险 | 影响 | 对策 |
| --- | --- | --- | --- |
| P1 | 在注入中手动调用 `EditBox.mouseClicked`，原版随后再次分发同一事件 | 第二次及以后可能无法稳定取得键盘焦点 | 不再把状态变更方法当作命中测试；改用无副作用的鼠标范围判断，并只在原版处理完成后清除外部点击的焦点 |
| P2 | 两个 `@Redirect` 覆盖 `selectTab` 内所有匹配调用 | 未来 NeoForge 或原版增加同类调用时可能被意外改变 | 改为精确的尾部注入，仅在搜索标签激活完成后调整必要状态，保留原版初始化流程 |
| P2 | 当前焦点策略只接收“控件是否消费点击” | 无法区分鼠标按钮、搜索框范围和原版事件执行阶段 | 将策略接口改为基于“是否主键点击、指针是否在搜索框内”的纯判断，并用单元测试覆盖 |
| P3 | 没有对连续打开、点击、关闭、再打开搜索框的回归测试 | 同类焦点回归无法在构建前发现 | 新增“重复进入仍可再次取得焦点”的策略测试和 Mixin 结构测试 |

## 建议的最小修复

下一步修复只处理焦点生命周期，不扩大模组功能：

1. 将点击命中判断替换为不调用 `EditBox.mouseClicked` 的无副作用判断。
2. 让原版先完成本次鼠标事件，再仅在主键点击搜索框外时清除焦点。
3. 将过宽的 `selectTab` 重定向收窄为只在搜索标签初始化完成后执行一次的焦点调整。
4. 先新增失败测试并确认失败，再实施最小代码变更、离线构建与候选 JAR 检查。

## 结论与后续流程

阶段一完成：已确认一个需要修复的 P1 焦点回归，尚未修改游戏中的 JAR。下一步应先完成该 P1 的测试驱动修复，再重新构建候选包；修复通过后，阶段二继续确认客户端专用边界。
