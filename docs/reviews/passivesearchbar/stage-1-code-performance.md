# Passive Search Bar 阶段一：代码与性能审查

检查对象：已安装的 `passivesearchbar-1.0.0.jar`，修复源码位于 `mods\passivesearchbar`。

## 代码范围

模组只有一个客户端 Mixin，目标为 `CreativeModeInventoryScreen`。修复后的实现：

- 在 `selectTab` 的尾部保留原版初始化流程，再允许搜索框失去焦点并清除自动焦点。
- 在 `mouseClicked` 的尾部，以无副作用的 `isMouseOver` 判断鼠标是否在搜索框内。
- 仅在主键点击搜索框外时清除焦点，不再手动调用任何控件点击处理。

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

1. `P1`：注入中手动调用 `EditBox.mouseClicked`，原版随后再次分发同一事件。
   影响：第二次及以后可能无法稳定取得键盘焦点。
   对策：已修复。候选版本改用无副作用的 `isMouseOver` 范围判断，并只在原版点击处理结束后清除主键外部点击的焦点；不再手动派发搜索框点击。

2. `P2`：两个 `@Redirect` 覆盖 `selectTab` 内所有匹配调用。
   影响：未来 NeoForge 或原版增加同类调用时可能被意外改变。
   对策：已修复。改为单个 `selectTab` 尾部注入，原版内部调用不再被拦截；标签切换完成后才调整必要焦点状态。

3. `P2`：当前焦点策略只接收“控件是否消费点击”。
   影响：无法区分鼠标按钮、搜索框范围和原版事件执行阶段。
   对策：已修复。策略接口现基于“是否主键点击、指针是否在搜索框内”的纯判断，并由单元测试覆盖。

4. `P3`：原本没有对连续打开、点击、关闭、再打开搜索框的回归测试。
   影响：同类焦点回归无法在构建前发现。
   对策：已新增“重复进入仍可再次取得焦点”的 Mixin 结构测试，以及主键内外点击和非主键点击的策略测试。

## P1 修复候选

- 候选文件：`D:\MC-Harmony-Home\candidates\passivesearchbar-1.0.1-2026Reset.jar`。
- SHA-256：`2237637B4D59E5E8929B5777EF07C3F7C3C03148A56B36C56444D7C8304784F6`。
- 版本号已统一为 `1.0.1-2026Reset`。
- 已验证候选 JAR 含正确的 NeoForge 元数据、Mixin 配置和更新后的 Mixin 字节码；字节码仅调用 `isMouseOver`，不再调用 `EditBox.mouseClicked`。
- JDK 23、Gradle 8.14.2 的离线 `build` 已通过，包含全部 6 个测试。
- 候选文件只存放在本地候选目录，尚未替换游戏中正在使用的 `passivesearchbar-1.0.0.jar`。

## 已实施的最小修复

修复仅处理焦点生命周期，不扩大模组功能：

1. 点击命中判断替换为不调用 `EditBox.mouseClicked` 的无副作用判断。
2. 原版先完成本次鼠标事件，再仅在主键点击搜索框外时清除焦点。
3. 过宽的 `selectTab` 重定向收窄为标签切换尾部的单个焦点调整。
4. 已新增失败测试并确认失败，再实施最小代码变更、离线构建与候选 JAR 检查。

## 结论与后续流程

阶段一完成：P1 焦点回归已完成测试驱动修复并生成候选包，游戏中的 JAR 尚未修改。下一步进入阶段二，正式检查客户端专用边界、网络行为和服务器目录处理。
