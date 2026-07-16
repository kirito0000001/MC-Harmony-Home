# First Ten Mods Localization And Search Focus Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Fully localize the first ten reviewed Catalogue entries, repair repeatable creative-search focus, and normalize their live JAR filenames.

**Architecture:** Codex Catalogue Bridge owns language-aware display-title overrides and bilingual long descriptions without modifying third-party JARs. Passive Search Bar owns only creative-inventory focus policy and synchronizes the screen's focused child with the EditBox. Live filenames gain a Chinese functional prefix while preserving technical names and versions.

**Tech Stack:** Java 21/23, NeoForge 21.1.235, Sponge Mixin, JUnit 5, Gradle wrappers, PowerShell, JSON language resources.

---

### Task 1: Add language-aware Catalogue titles

**Files:**
- Modify: `mods/codex-catalogue-bridge/src/main/java/com/codex/minecraft/cataloguebridge/CatalogueOverride.java`
- Modify: `mods/codex-catalogue-bridge/src/main/java/com/codex/minecraft/cataloguebridge/CatalogueOverrides.java`
- Modify: `mods/codex-catalogue-bridge/src/main/java/com/codex/minecraft/cataloguebridge/mixin/NeoForgeModDataMixin.java`
- Modify: `mods/codex-catalogue-bridge/src/main/resources/data/codex_catalogue_bridge/catalogue_overrides.json`
- Modify: `mods/codex-catalogue-bridge/src/main/resources/assets/codex_catalogue_bridge/lang/en_us.json`
- Modify: `mods/codex-catalogue-bridge/src/main/resources/assets/codex_catalogue_bridge/lang/zh_cn.json`
- Test: `mods/codex-catalogue-bridge/src/test/java/com/codex/minecraft/cataloguebridge/CatalogueOverridesTest.java`

- [ ] Add failing tests asserting all ten overrides expose a title translation key, both language files contain it, and missing translations fall back to the metadata display name.
- [ ] Run `mods\codex-catalogue-bridge\gradlew.bat test --offline --no-daemon` and confirm the new assertions fail.
- [ ] Replace static aliases with optional title translation keys and resolve them through Minecraft `I18n` in `NeoForgeModDataMixin`.
- [ ] Add the ten English and Chinese titles and update the override JSON.
- [ ] Run the Bridge test suite and confirm all tests pass.

### Task 2: Expand the ten bilingual Catalogue descriptions

**Files:**
- Modify: `mods/codex-catalogue-bridge/src/main/resources/assets/codex_catalogue_bridge/lang/en_us.json`
- Modify: `mods/codex-catalogue-bridge/src/main/resources/assets/codex_catalogue_bridge/lang/zh_cn.json`
- Test: `mods/codex-catalogue-bridge/src/test/java/com/codex/minecraft/cataloguebridge/CatalogueOverridesTest.java`

- [ ] Add failing assertions that each description contains separated overview and operation paragraphs, and that Mouse Tweaks mentions scrolling, direction, drag distribution, and the resulting transfer.
- [ ] Run the focused Bridge tests and confirm they fail against the current short descriptions.
- [ ] Write complete English and natural Chinese descriptions in overview, operation, result, and optional-example order.
- [ ] Run all Bridge tests and confirm they pass.

### Task 3: Repair Passive Search Bar repeat focus

**Files:**
- Modify: `mods/passivesearchbar/src/main/java/com/liuyu/passivesearchbar/SearchBoxFocusPolicy.java`
- Modify: `mods/passivesearchbar/src/main/java/com/liuyu/passivesearchbar/mixin/CreativeModeInventoryScreenMixin.java`
- Modify: `mods/passivesearchbar/gradle.properties`
- Test: `mods/passivesearchbar/src/test/java/com/liuyu/passivesearchbar/SearchBoxFocusPolicyTest.java`
- Test: `mods/passivesearchbar/src/test/java/com/liuyu/passivesearchbar/CreativeInventoryFocusMixinTest.java`

- [ ] Add a failing focus-sequence test for open-unfocused, click-inside-focused, click-outside-cleared, and click-inside-refocused behavior.
- [ ] Add a failing source-contract test requiring outside clicks to clear the screen focused child rather than only the EditBox boolean.
- [ ] Run `tools\build-passive-search-bar.ps1 -GradleArgs @('test','--offline','--no-daemon')` and confirm the new tests fail.
- [ ] Synchronize focus through `CreativeModeInventoryScreen.setFocused(...)`, retaining the passive initial state and primary-button policy.
- [ ] Bump the mod version to `1.0.2-2026Reset` and run the full Passive Search Bar build.

### Task 4: Build and inspect candidates

**Files:**
- Modify: `mods/codex-catalogue-bridge/gradle.properties`
- Create: `candidates/codex-catalogue-bridge-<version>-2026Reset.jar`
- Create: `candidates/passivesearchbar-1.0.2-2026Reset.jar`

- [ ] Bump the Bridge patch version with the `2026Reset` suffix.
- [ ] Build Bridge and Passive Search Bar offline with their fixed wrappers.
- [ ] Run all unit tests and confirm no failures.
- [ ] Inspect both archives for the expected language JSON, override JSON, Mixin metadata, and compiled classes.
- [ ] Compute and record SHA-256 hashes.

### Task 5: Normalize filenames and install

**Files:**
- Create: `backups/codex-localization-batch-<timestamp>/filename-map.txt`
- Modify: ten JAR filenames under `D:\其他应用\Minecraft\.minecraft\versions\1.21.1-NeoForge_21.1.235\mods`

- [ ] Confirm no Minecraft Java process is running.
- [ ] Back up the current Bridge, Passive Search Bar, and all ten filename targets using literal paths.
- [ ] Write the exact old-to-new filename map.
- [ ] Install the two candidates and rename all ten files to `[中文功能] original-technical-name-version.jar`.
- [ ] Confirm each expected mod ID exists exactly once and no stale duplicate JAR remains.

### Task 6: Final verification and documentation

**Files:**
- Modify: `docs/reviews/batch-validation.md`
- Modify: `docs/reviews/batch-1-installation.md`

- [ ] Run repository tests and both production builds once more.
- [ ] Verify the live JAR hashes match the candidates.
- [ ] Verify `git status -sb` contains only intended work.
- [ ] Record the filename convention as the default for future localized mods.
- [ ] Commit the implementation in focused commits and push with command-local proxy bypass if the configured proxy fails.
