# Mouse Tweaks 启动热修复

时间：`2026-07-16 16:12`。

## 根因

批次启动报告确认首个致命错误来自 Codex Catalogue Bridge 的 `MouseTweaksConfigScreenMixin`：

`@Redirect handler before super() invocation must be static`

Mouse Tweaks 的 `ConfigScreen` 构造函数在调用父类构造函数前创建部分文字组件。Bridge 在该构造函数中重定向 `Component.literal` 时，处理器原本是实例方法。Mixin 对这种 `super()` 前的构造期重定向要求静态处理器，因此 Mouse Tweaks 无法创建，NeoForge 随后进入损坏加载状态。后续模型、声音和资源错误都是该失败的连带症状，不是第一原因。

## 修复与验证

1. 新增回归测试，断言 `localizeMouseTweaksLiteral(String)` 的源码签名包含 `private static`。
2. 旧实现运行该测试时按预期失败。
3. 将处理器改为静态方法；它只调用静态翻译映射，不访问 Mixin 实例状态，因此不改变翻译行为。
4. 修复后专用测试通过，固定 JDK 21 与 Gradle 8.10 的完整离线 `build` 也通过。
5. 使用 `javap` 直接检查候选 JAR，确认处理器字节码签名为 `private static`。

## 已安装候选

- Bridge 候选：`codex-catalogue-bridge-1.0.11-2026Reset.jar`。
- 候选 SHA-256：`8CC7CA2EC46AEF5B8DF8A659FE60F9249A69C5063397FCD086B45B92E45B1323`。
- 已复制到游戏目录中原有的 `codex-catalogue-bridge-1.0.1-2026Reset.jar` 文件名，避免 PCL 文件索引变化。
- 失败的 Bridge `1.0.10` 已备份为 `codex-catalogue-bridge-1.0.10-2026Reset-failed-mixin.jar`。
- 备份目录：`D:\其他应用\Minecraft\.minecraft\versions\1.21.1-NeoForge_21.1.235\backups\codex-batch-1-20260716-160841`。

## 重试检查

重新启动后优先确认启动日志不再包含 `MouseTweaksConfigScreenMixin`、`InvalidInjectionException` 或 `before super() invocation must be static`。进入主菜单后，再继续批次一的 JEI、JEED、JER、JEC、AppleSkin、Passive Search Bar 和 Durability Tooltip 验证。
