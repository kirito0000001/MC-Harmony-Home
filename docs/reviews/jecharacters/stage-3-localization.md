# Just Enough Characters 阶段三：汉化审查

检查对象：`Just Enough Characters 4.5.26`。

## 完整性

- `en_us.json`：5 个语言键。
- 原 `zh_cn.json`：5 个语言键。
- 修订候选 `zh_cn.json`：5 个语言键。
- 与英文相比，候选中文缺失键和额外键均为 `0`；JSON 已通过 PowerShell 解析验证。

## 质量修订

- 将“搞定！报告已导出”改为“分析完成，报告已保存”，与游戏内提示的语气统一。
- 将写入失败提示中的粗俗口语替换为“写入分析报告时发生错误”。
- 为 `/jech` 的帮助输出补充“用法：”前缀，保留原命令、参数和英文布尔值，避免改变实际命令语法。
- 英文文本完整且含义清晰，本次未改动 `en_us.json`。繁体中文已有独立提示文件，不混入简体中文改动范围。

## 候选产物

- 生成脚本：[build-jecharacters-localization.ps1](../../../tools/build-jecharacters-localization.ps1)。
- 候选 JAR：`D:\MC-Harmony-Home\candidates\jecharacters-1.21.1-neoforge-4.5.26-2026Reset.jar`。
- SHA-256：`60F3DC07882A9F912B7A0CD0A7F897AACFA62760E42CBFF48EEFF2B10C6E2F14`。
- 构建脚本逐条确认三段原文本各恰好出现一次后再写入候选。

游戏当前加载的 `jecharacters-1.21.1-neoforge-4.5.26.jar` 未被修改。

## 结论与后续流程

阶段三通过。修订仅触及简体中文语言资源，不改变搜索、核心补丁、配置或服务端行为。

下一阶段是阶段四：补充 Catalogue 的双语介绍、官方网页和预填 `[JEC]` 的问题反馈入口；该阶段只构建候选 Bridge，不需要关闭游戏。
