# MC 和谐家园 Catalogue 与公开发布设计

## 目标

为 Minecraft `1.21.1-NeoForge_21.1.235` 整合包建立公开 GitHub 仓库 `kirito0000001/MC-Harmony-Home`，作为已完成审查模组的统一资料、发布和问题反馈入口。仓库页面标题使用“MC 和谐家园”。

第一批接入对象为 Just Enough Professions（JEP）`4.0.5-2026Reset`。后续每个模组完成既定审查阶段后，沿用相同流程接入。

玩家在 Catalogue 中选择已接入模组时应得到以下体验：

- 右侧详情标题不会越界；过长时自动缩小。
- 经审查确认需要别名的模组可显示简短中文名，同时保留原模组 ID 和原始识别信息。
- 模组说明提供自然的 `en_us` 和 `zh_cn` 版本。
- `Website` 打开该模组在 `MC和谐家园` 的发布页，页面提供下载、版本、哈希、改动、来源和许可证。
- `Submit Bug` 打开同仓库的 Issue 表单，并自动预填 `[模组名] ` 标题和对应模组标签。

## 非目标

- 不给没有真实配置界面的模组添加虚假的“配置”按钮；JEP 保持无配置。
- 当前阶段不修改原模组的玩法、网络协议、存档、配方或服务端逻辑。
- 不把许可证不允许再分发的第三方 JAR 上传到公开仓库或 GitHub Release。
- 不把整个模组包的大型二进制文件直接提交进 Git 历史。

## 方案对比与决定

### 方案一：逐个重写模组 JAR 元数据

直接修改每个 JAR 的显示名、主页、问题跟踪地址和介绍。它的初始实现简单，但每次原模组升级都要重新处理 JAR，也会让非开放许可模组的维护与公开发布边界变得模糊。

不采用。

### 方案二：只建立 GitHub 页面

不改变游戏内 Catalogue。维护成本低，但玩家无法从模组列表直接进入发布页或提交带上下文的问题，也不能解决右侧标题越界。

不采用。

### 方案三：客户端 Catalogue 桥接补丁

新增自有、客户端专用的 `codex-catalogue-bridge` 模组，集中读取覆盖表并对 Catalogue 的公开数据接口做窄范围 Mixin 覆盖。原模组 JAR 保持不变，原版升级时只需更新覆盖表、重新验证，不需要重复修改每个上游文件。

采用。

## 客户端桥接补丁

### 模组边界

模块名：`codex-catalogue-bridge`

目标版本：`1.0.0-2026Reset`

加载侧：`client_only`

前置：NeoForge `21.1.235`、Catalogue `1.11.2`

它只处理 Catalogue UI 的文本和外部链接；不注册物品、方块、网络包、命令、存档数据或服务端事件。

### 覆盖表

桥接补丁内部维护 `catalogue_overrides.json`。每个条目以模组 ID 为键，包含：

```json
{
  "justenoughprofessions": {
    "alias": null,
    "homepage": "https://github.com/kirito0000001/MC-Harmony-Home/tree/main/mods/justenoughprofessions",
    "issueTracker": "https://github.com/kirito0000001/MC-Harmony-Home/issues/new?template=mod-bug.yml&title=%5BJust%20Enough%20Professions%5D%20&labels=bug%2Cmod%3Ajustenoughprofessions"
  }
}
```

`alias` 默认是 `null`。只有逐模组审查确认“原名过长、识别性差或中文用途更重要”时才填入短别名。别名会同时影响 Catalogue 左侧列表、右侧标题和模组搜索建议；模组 ID、版本、作者和许可证保持原样可见。

### 标题适配（B）

桥接补丁只在 `CatalogueModListScreen` 绘制右侧已选模组标题时介入：

1. 使用当前字体测量显示名宽度。
2. 宽度不超过详情区域可用宽度时，按 Catalogue 原始大小绘制。
3. 超过时等比缩小，最低缩放到 `0.75`。
4. 仍超出时裁剪显示并附加省略号，防止覆盖版本、Mod ID 或背景区域。

这项规则适用于所有模组，不依赖 JEP 专用名称。

### 别名与链接覆盖（C）

Catalogue `1.11.2` 的 `NeoForgeModData` 提供 `getDisplayName()`、`getHomepage()` 和 `getIssueTracker()`。桥接补丁只在覆盖表存在该模组 ID 时替换这些返回值；不存在条目时完整保留 Catalogue 原行为。

因此：

- `Website` 进入 GitHub 模组发布页，不直接触发 JAR 下载。
- 发布页的下载区链接到对应 GitHub Release 资产或官方来源。
- `Submit Bug` 使用统一表单，但 URL 已带模组名标题与标签。
- 没有覆盖条目的模组仍保留作者原始网站和 Issue 地址。

### 双语介绍

桥接补丁提供：

```text
assets/codex_catalogue_bridge/lang/en_us.json
assets/codex_catalogue_bridge/lang/zh_cn.json
```

每个已接入模组使用 Catalogue 优先读取的键：

```text
fml.menu.mods.info.description.<modid>
```

JEP 的文案限定为 1 至 2 句，说明它为 JEI 展示村民职业与对应工作方块；不重复作者、版本、许可证或按钮功能。英文和中文表达相同事实，但不做生硬逐字翻译。

## GitHub 仓库与发布

### 仓库结构

```text
MC和谐家园/
  README.md
  pack/
    manifest.json
    installation.md
  profiles/
    client/
      manifest.json
    server/
      manifest.json
      installation.md
      config/
      mods/
  mods/
    justenoughprofessions/
      README.md
      release.json
      NOTICE.md
  .github/
    ISSUE_TEMPLATE/
      mod-bug.yml
  docs/
    review-index.md
```

`pack/manifest.json` 是完整整合包的单一清单来源。每个模组至少记录：模组 ID、显示名、实例版本、客户端/服务端分类、来源 URL、SHA-256、安装方式和许可证状态。

`profiles/client/manifest.json` 与 `profiles/server/manifest.json` 都由总清单生成。客户端清单包含完整游玩体验所需的内容与客户端辅助；服务器清单只包含已经确认属于 `server_required`、`server_only` 或对应 `library_required` 依赖闭包的模组。`profiles/server/config/` 只保存确认需要随服务端部署的配置，`profiles/server/mods/` 预留为构建出的服务器包内容，不将未核准 JAR 提前放入版本库。

`mods/<modid>/README.md` 是 Website 的落地页，说明用途、兼容性、已完成审查阶段、版本、哈希、来源、许可证、回退路径与下载方式。

`mods/<modid>/release.json` 为机器可读的发布记录，保存当前审核构建与下载位置，供以后生成安装清单或 PCL 兼容导入文件。

### 完整整合包体验与许可证规则

完整体验来自完整清单，而不是把所有第三方 JAR 无差别塞进 Git：

- 明确允许公开再分发的模组可作为 GitHub Release 资产提供，保留原许可证与 NOTICE。
- 自有补丁和按开源许可证允许发布的派生构建，可作为 Release 资产提供；JEP 的 MIT 许可证允许此方式。
- 未获得再分发许可的模组记录原始发布地址、固定版本和 SHA-256，由安装清单从原站补齐。
- 每个模组的许可证状态必须先审查后才能改变为“直传”。未知状态默认外部来源。

后续将从 `pack/manifest.json` 生成完整安装说明和 PCL 兼容导入产物；具体导入格式先以 PCL 当前可识别格式实测，不假设某种第三方打包格式一定兼容。

## 服务器专用预留

服务器交付与客户端 Catalogue 桥接补丁完全分离。`codex-catalogue-bridge` 永远是 `client_only`，不进入服务器清单。

为后续服务器优化和运维功能预留独立模块：

```text
mods/codex-server-ops/
```

该模块仅在实际需求经过服务器阶段审查后才创建和发布，统一使用 `-2026Reset` 版本标记。它的职责范围限于服务端性能监控、诊断、管理或兼容修复；不得引用 Catalogue、JEI、渲染、按键、HUD 或其他客户端类。

每个接入模组的服务器判断必须记录以下证据：注册内容、事件与 tick、网络频道、专用服务器可加载性、直接依赖与传递依赖。JEP 的 `client_only` 结论同样必须补齐这些证据，不能仅凭其 JEI 定位跳过。

### 问题反馈

统一使用 `.github/ISSUE_TEMPLATE/mod-bug.yml`。字段包括：受影响模组、审核构建版本、原模组版本、Minecraft/NeoForge 版本、复现步骤、预期结果、实际结果、日志/崩溃报告和是否影响联机。

每个 Catalogue 的问题链接预填该模组标题和标签，例如 JEP：

```text
[Just Enough Professions]
labels: bug, mod:justenoughprofessions
```

这让同一仓库的 Issue 既能集中管理，也能按模组筛选。

## JEP 首个接入

1. 补完 JEP 阶段二服务器部署判断，基于代码、依赖、网络与专用服务器证据得出 `client_only`，不以直觉跳过。
2. 在桥接补丁中加入 JEP 的中英文 Catalogue 介绍和发布/问题链接。
3. 创建 JEP 发布页、机器可读发布记录、许可证通知和完整清单条目。
4. JEP `4.0.5-2026Reset` 的公开 Release 必须带 MIT 许可证、原作者署名、源码来源、修改说明和 SHA-256。
5. 在游戏中验证：标题适配、介绍中英文切换、Website 链接、Submit Bug 预填、职业 JEI 页面正常。

## 风险与回退

- Catalogue 更新可能改变 Mixin 目标：桥接补丁在启动时版本检查，只允许已验证的 Catalogue 版本加载；不匹配时禁用覆盖逻辑并保留原 UI。
- GitHub 站点或链接不可用不会影响游戏玩法：按钮失效不影响模组加载、存档或联机。
- 桥接补丁出现问题时从 `mods` 移除即可恢复 Catalogue 与原模组元数据；不需要回滚上游 JAR。
- 许可证状态不明确时不上传 JAR，直到获得许可证文本或作者明确许可。

## 验收标准

- 右侧长标题不覆盖版本或 Mod ID；普通标题保持原大小。
- 无别名模组的左侧显示和搜索行为不变；有别名模组的显示名、搜索建议一致。
- JEP 在中英文下均有自然、完整的介绍。
- Website 打开 JEP 发布页；该页包含下载、版本、哈希、来源、许可证和回退说明。
- Submit Bug 打开同仓库表单，标题前缀为 `[Just Enough Professions]`，并带 JEP 标签。
- 服务器目录不含 JEP 或桥接补丁。
- `pack/manifest.json` 能完整列出当前已审查模组及其安装来源。
- `profiles/server/manifest.json` 只包含已审查确认的服务端模组和依赖闭包；客户端专用模组不会进入服务器包。
