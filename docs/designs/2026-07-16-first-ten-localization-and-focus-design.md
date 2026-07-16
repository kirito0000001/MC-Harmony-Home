# First Ten Mods Localization And Search Focus Design

## Scope

This batch covers the first ten reviewed mods:

1. Just Enough Professions (`justenoughprofessions`)
2. Catalogue (`catalogue`)
3. Just Enough Effect Descriptions (`jeed`)
4. Just Enough Items (`jei`)
5. Just Enough Resources (`jeresources`)
6. Just Enough Characters (`jecharacters`)
7. AppleSkin (`appleskin`)
8. Mouse Tweaks (`mousetweaks`)
9. Passive Search Bar (`passivesearchbar`)
10. Durability Tooltip (`durabilitytooltip`)

The work includes language-aware Catalogue titles, expanded bilingual descriptions, a Passive Search Bar focus fix, and normalized live JAR filenames.

## Catalogue Titles

Chinese Catalogue titles use the format `中文名称（English Name）`. English keeps the original English title. The Bridge resolves a translation key at runtime and falls back to the original mod metadata display name when the key is absent, preventing raw translation keys from appearing.

The Chinese titles are:

1. `JEI 村民职业（Just Enough Professions）`
2. `模组目录（Catalogue）`
3. `JEI 状态效果说明（Just Enough Effect Descriptions）`
4. `JEI 物品与配方查询（Just Enough Items）`
5. `JEI 资源查询（Just Enough Resources）`
6. `JEI 拼音搜索（Just Enough Characters）`
7. `食物信息显示（AppleSkin）`
8. `鼠标物品栏优化（Mouse Tweaks）`
9. `创造搜索框焦点优化（Passive Search Bar）`
10. `耐久提示（Durability Tooltip）`

## Catalogue Descriptions

Each language receives a complete description. Chinese is written as natural player-facing text rather than a literal translation. Descriptions follow this order:

1. Overview: what the mod changes and where it is visible.
2. Operation: the exact mouse, keyboard, screen, or lookup action.
3. Result: what information or behavior the player gets.
4. Example: included only where the feature is complex enough to benefit from one.

Paragraphs use blank lines so Catalogue wraps and scrolls them naturally. Mouse Tweaks must explicitly explain right-drag distribution, left-drag collection or placement, and scrolling over a slot to move items between the player inventory and the open container.

## Passive Search Bar Focus

The intended behavior is:

1. Opening a creative tab with a search box does not automatically capture keyboard input.
2. A primary click inside the search box focuses it immediately.
3. A primary click outside clears both the EditBox focus and the screen container's focused-child reference.
4. Clicking the search box again after an outside click focuses it again.
5. Non-primary clicks do not unexpectedly change focus.

The current implementation directly clears only `EditBox.setFocused(false)`. The replacement synchronizes focus through the owning screen container so the widget state and focused-child reference cannot diverge. Tests cover the complete open, click, clear, and refocus sequence before the Mixin is changed.

## Filename Convention

Reviewed localized mods use this live-file format:

`[中文功能] original-technical-name-version.jar`

The technical name, loader marker, Minecraft version, mod version, and `2026Reset` suffix are retained. Source directories continue to use stable mod IDs so build scripts and repository links do not move.

This convention applies to the current ten mods and to every later mod after its localization review is complete. Renaming a JAR does not change its mod ID, configuration directory, network identity, or save data. A mapping of old names to new names is recorded before installation.

## Build And Validation

Bridge and Passive Search Bar changes use test-first development. Builds use the repository's fixed wrappers and documented JDKs. Candidate JARs remain outside the live mods directory until tests and archive inspection pass.

Installation creates a literal-path backup, replaces only the two changed binaries, and renames the ten reviewed files. Runtime validation checks game startup, Chinese and English title fallback, description scrolling, Mouse Tweaks instructions, and the complete Passive Search Bar focus sequence.
