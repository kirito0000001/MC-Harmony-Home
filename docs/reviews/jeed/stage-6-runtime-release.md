# JEED 阶段六：运行与发布

当前状态：候选已构建，等待批次运行验证。

## 候选文件

- JEED 本地资源补丁候选：`jeed-1.21-2.3.2-2026Reset.jar`（仅保留在本机审查工作区）
- SHA-256：`034B5035B1778DEC84AE0EF8BBB8D3EE5407512948EC050215A6E790A2949493`
- 校验：53 个 class 文件与原 JAR 完全一致；仅更新 `assets/jeed/lang/zh_cn.json` 与 `META-INF/neoforge.mods.toml`。
- Catalogue Bridge：`mods/codex-catalogue-bridge/build/libs/codex-catalogue-bridge-1.0.2-2026Reset.jar`
- SHA-256：`F3206287B2D85A2FCCAC69E7121A2CC4CCB7ECBB4182E9DD6C3F5A286F6540FE`

## 批次验证要求

达到 `10 / 10` 后，关闭游戏、备份当前 JEED 与 Bridge JAR，再安装上述候选并验证：

- 游戏启动日志没有 JEED、JEI、Catalogue 或 Mixin 错误。
- JEI 的药水与状态效果页面显示补充说明。
- 背包中的状态效果图标可以正常悬浮和点击。
- Catalogue 中 JEED 的中英文介绍、网页和“提交问题”链接正确。

## 发布限制

上游仓库没有可验证的完整公开再分发许可证文本，故此候选仅作为本地补丁使用。公开仓库只保留 [JEED 2026Reset 补丁](../../../patches/jeed-1.21-2.3.2-2026Reset.patch)，不包含上游源码或完整 JAR；未经上游作者明确授权，不上传 JEED 完整 JAR 至 GitHub Release。
