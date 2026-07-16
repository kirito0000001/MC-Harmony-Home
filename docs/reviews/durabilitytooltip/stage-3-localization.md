# Durability Tooltip 阶段三：汉化审查

检查对象：`Durability Tooltip 1.1.6`。

## 完整性

- `en_us.json`：12 个语言键。
- `zh_cn.json`：12 个语言键。
- 中文与英文相比缺失键、额外键均为 `0`。
- `TooltipStyle` 的字节码引用了耐久条、数值、文字三种样式的全部 12 个 `durabilitytooltip.info.*` 键，均可由现有中英文资源解析。

## 中文质量

- `Durability:` 统一为“耐久度：”，全角冒号与原版中文提示一致。
- `Pristine` 译为“毫发无损”，表示完全未受损，适合装备状态语境。
- `Slightly damaged`、`Severely damaged`、`Nearly broken` 分别译为“轻微受损”“严重损坏”“濒临破碎”，等级递进清晰，也符合 Minecraft 装备耐久度的自然表达。
- 数值格式 `%1$d / %2$d`、耐久条边框和实心/空心字符保持不变，不会影响数值替换或视觉对齐。

## 配置注释边界

- `durabilitytooltip-common.toml` 的英文说明来自模组代码中传入 Config Lib 的硬编码注释，不属于语言资源。
- 已检查已安装的 Config Lib JAR，未发现该模组可调用的配置屏幕实现；玩家正常游玩时不会看到这些英文注释。
- 为了仅翻译维护者 TOML 注释而修改上游类，会增加升级冲突，却不改善游戏内提示，因此本阶段不创建无效的本地化补丁。

## 结论与后续流程

阶段三通过。游戏内耐久提示已有完整、自然的中英文显示，当前 JAR 不做改动。下一阶段是阶段四：补充 Catalogue 双语介绍、官方页面和预填 `[Durability Tooltip]` 的问题反馈入口。
