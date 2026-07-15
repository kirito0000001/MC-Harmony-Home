# Catalogue 阶段一：代码与性能审查

检查对象：`Catalogue 1.11.2`  
客户端文件：`[模组目录] catalogue-neoforge-1.21.1-1.11.2.jar`  
SHA-256：`B3C958772C61354FCCF04B52264F2E398C8BF9A28D192D675BF82359B647EF43`

## 结论

Catalogue 是客户端的模组目录界面。它不参与世界 tick、区块、实体、配方或网络同步；正常游戏过程没有持续性能开销。

以当前约 270 个模组的客户端规模，首次打开目录、首次滚动到带自定义横幅或图标的模组时，可能出现一次短暂的界面卡顿。原因是 PNG 文件会在客户端渲染线程同步解码并注册为动态纹理。读取后的模组元数据、横幅和图标会存入静态缓存，同一局游戏再次打开目录不会重复加载。

不建议为了这一处仅发生在目录界面的短暂卡顿，直接引入异步纹理补丁：动态纹理的注册必须回到渲染线程，异步实现会增加竞态、资源释放和兼容性风险，收益很小。

## 已核对的代码路径

- `ClientEvents` 仅在 `Dist.CLIENT` 订阅界面打开事件，将 NeoForge 原生 `ModListScreen` 替换为 `CatalogueModListScreen`。
- `ClientCatalogue` 仅在客户端初始化时读取 `config/catalogue.properties`。
- 第一次创建目录界面时，Catalogue 读取一次 NeoForge 已加载模组清单并写入 `CACHED_MODS`；之后复用。
- 横幅、图片图标和背景按需读取。背景纹理切换时会主动调用 `TextureManager.release` 释放上一张背景。
- 横幅/图标缓存会保留到游戏进程结束。这避免重复磁盘读取；代价是已浏览过的高分辨率图像会占用一定 GPU 内存。横幅有 `1200 x 240` 上限，独立图标没有额外大小限制。
- 收藏夹只在首次打开目录时读取，并在关闭目录时、内容发生变化时写入本地 `catalogue_favourites.txt`，不构成持续 I/O。

## 可选的小型修复

下列两处为资源管理问题，不是 FPS 优化：

1. `Config.load` 使用 `new FileInputStream(file)` 读取属性但没有显式关闭流。
2. `NeoForgePlatformHelper.loadNativeImage` 使用 `Files.newInputStream(path)` 读取 PNG 但没有显式关闭流。

在 Windows 上，浏览很多带横幅/图标的模组时，第二项可能暂时累积文件句柄。修复方式是将两处改为 try-with-resources，再制作 `1.11.2-2026Reset` 版本。该改动局限、兼容性风险低，但不会让世界帧率或服务器 TPS 变高。

## 阶段一决定

- 已制作 `Catalogue 1.11.2-2026Reset` 候选 JAR，只纳入上述两处流关闭修复；不加入异步加载，也不改变 Catalogue 的界面、链接或模组数据。
- 候选文件：`mods/catalogue/neoforge/build/libs/catalogue-neoforge-1.21.1-1.11.2-2026Reset.jar`。
- SHA-256：`3B0E87230B9E5DC1A51B9D05D9CD86A2AD40E788E2A721C67780A4E6F0816BC1`。
- 已通过流关闭回归检查、环境回归检查和完整 NeoForge `clean build`。产物字节码为 Java 21（class major version 65）。
- 构建入口：`mods/catalogue/tools/build-neoforge.ps1`。它固定使用 Gradle 8.10、JDK 21，并禁用构建期间的工具链自动下载。
- 当前候选 JAR 尚未复制到 `.minecraft`，不影响正在运行的游戏。
- 下一阶段应核对其在专用服务器上的加载行为与服务端清单归类，不能仅凭“它是 UI”跳过。

## 证据来源

- 已安装 JAR 的字节码与 `META-INF/neoforge.mods.toml`。
- 上游源码：`MrCrayfish/Catalogue` 的 `multiloader/1.21.1` 分支，提交 `85ec45d6537fd55d025a7caa02a149f870e8673c`，版本 `1.11.2`。
- 许可证：MIT。
