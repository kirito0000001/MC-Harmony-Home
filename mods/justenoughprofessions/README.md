# Just Enough Professions

## 发布构建

- 审核构建：`4.0.5-2026Reset`
- Minecraft：`1.21.1`
- NeoForge：`21.1.235`
- SHA-256：`8D9702B337C2BFF018A3B37896BB07B2F3161BB881D4114C9142A01C86F5DFCC`
- 加载侧：`client_only`
- 许可证：MIT
- 上游源码：[Mrbysco/JustEnoughProfessions](https://github.com/Mrbysco/JustEnoughProfessions)

## 用途

在 JEI 中提供村民职业浏览页，显示职业模型和对应工作方块。

## 本次修改

- JEI 注册阶段复用 POI 快照，并优化集合去重。
- 职业未变化时不重复写入村民职业数据。
- 复用职业显示组件与固定渲染旋转值。
- 不改变物品、配方、存档、网络协议或服务器玩法。

## 下载与回退

GitHub Release 提供此构建。原版 JAR 备份位于：

```text
D:\其他应用\Minecraft\.minecraft\versions\1.21.1-NeoForge_21.1.235\backups\justenoughprofessions-20260715-2026Reset
```

停止游戏后，删除当前 `4.0.5-2026Reset` JAR 并恢复备份中的 `4.0.5` JAR 即可回退。
