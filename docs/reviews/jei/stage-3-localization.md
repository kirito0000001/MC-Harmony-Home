# JEI 阶段三：汉化审查

检查对象：`Just Enough Items 19.38.0.366-2026Reset`。

## 审查结果

- 初始比对发现简体中文缺少 7 个英文翻译键：通知避让配置及说明、幽灵物品快速移动快捷键、4 条服务端配方同步提示。
- 新增 `LocalizationResourceTest`，要求 `en_us.json` 中每个非注释键都存在于 `zh_cn.json`；修复后中英文键数量和集合一致，没有额外或缺失键。
- 补充的中文使用游戏内自然术语：`通知避让`、`快速移动幽灵物品`，并完整保留服务器提示的 `%s` 占位符。
- 配方界面标题原先写死为 `Recipes`，现改为 `gui.jei.recipes`，英文为 `Recipes`、简体中文为 `配方`。
- 仍相同的少量文本仅为 `SHIFT`、`CTRL`、`ALT`、`mB` 单位和村民刷怪蛋搜索别名 `HMMM`，不属于未汉化的玩家界面文本。
- 仅调试模式的文字、资源 ID 与异常诊断字段保持原样，不会进入普通游玩流程。

## 验证

以下针对性回归和完整构建均通过：

```powershell
& 'D:\MC-Harmony-Home\tools\build-jei-neoforge.ps1' -Tasks '--no-configuration-cache',':Common:test','--tests','mezz.jei.test.LocalizationResourceTest',':Common:test','--tests','mezz.jei.test.ConfigSerializerTest',':Gui:test','--tests','mezz.jei.gui.overlay.IngredientListOverlayTest',':NeoForge:test','--tests','mezz.jei.test.IngredientFilterTest'

& 'D:\MC-Harmony-Home\tools\build-jei-neoforge.ps1' -Tasks '--no-configuration-cache',':NeoForge:build'
```

## 后续流程

下一阶段是阶段四：Catalogue 模组介绍与链接。将核对 JEI 的双语简介、网页链接和预填问题反馈链接是否准确，并调整已知的标题换行或溢出问题。该阶段只修改 Catalogue/Bridge 的元数据，不需要关闭游戏、安装候选或复制服务端文件。
