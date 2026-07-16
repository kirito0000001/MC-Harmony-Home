# Durability Tooltip 阶段二：服务端部署判断

检查对象：`Durability Tooltip 1.1.6`。

## 静态证据

- `DurabilityTooltipClient` 的 `@EventBusSubscriber` 明确使用 `Dist.CLIENT`，唯一 `ItemTooltipEvent` 订阅不会在专用服务端注册。
- 事件只读取客户端已有的 `ItemStack`、耐久数值和本地配置，再向当前提示列表追加聊天组件；没有改变物品数据或向服务器发送请求。
- 所有七项配置定义都调用 `dontSync()`，服务端不会向客户端同步耐久样式、颜色、黑名单或显示条件。
- JAR 条目和类依赖中未发现 payload、packet、网络处理器、服务端事件、服务器命令、世界数据或存档读写逻辑。
- `net.minecraft.network.chat.Component` 只用于本地提示文本组件，不代表网络数据包。
- 通用入口会在两端初始化 Config Lib，但这是本地配置定义；服务端安装不会增加任何玩法、同步或性能收益。

## 结论

- 分类：`client_only`。
- 不复制到 `D:\其他应用\QQ\接收文件\我的世界mod服务器版本`。
- 客户端安装后可以连接未安装 Durability Tooltip 的服务器；提示内容完全由客户端手中已有的物品数据生成。
- 已在 `服务器模组筛选记录.csv` 记录为 `not_copied`。

## 后续流程

下一阶段是阶段三：检查 12 个英文、简体中文语言键的完整性、中文自然度，以及配置注释与游戏内文本的边界。
