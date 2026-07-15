# Catalogue Bridge and JEP Integration Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Build a client-only Catalogue bridge, connect Just Enough Professions to bilingual Catalogue text and GitHub feedback links, and publish the first audited release through `MC和谐家园`.

**Architecture:** `codex-catalogue-bridge` is a self-owned NeoForge client mod. It loads a small override registry, supplies Catalogue description translations, redirects only configured mod metadata getters, and scales only the selected right-side Catalogue title. The GitHub repository keeps source, manifests, documentation and issue forms; release assets contain only JARs permitted for public redistribution.

**Tech Stack:** Java 21 source toolchain on NeoForge 21.1.235, Gradle ModDev 2.0.91, SpongePowered Mixin, JUnit 5, Gson, GitHub CLI, GitHub Releases.

---

### Task 1: Establish a Reproducible Bridge Mod Project

**Files:**
- Create: `D:\MC和谐家园\mods\codex-catalogue-bridge\settings.gradle`
- Create: `D:\MC和谐家园\mods\codex-catalogue-bridge\build.gradle`
- Create: `D:\MC和谐家园\mods\codex-catalogue-bridge\src\main\resources\META-INF\neoforge.mods.toml`
- Create: `D:\MC和谐家园\mods\codex-catalogue-bridge\src\main\resources\codex_catalogue_bridge.mixins.json`
- Create: `D:\MC和谐家园\mods\codex-catalogue-bridge\src\main\java\com\codex\minecraft\cataloguebridge\CodexCatalogueBridge.java`
- Test: `D:\MC和谐家园\mods\codex-catalogue-bridge\src\test\java\com\codex\minecraft\cataloguebridge\MixinMetadataTest.java`

- [ ] **Step 1: Write the failing metadata test**

```java
@Test
void mixinConfigurationTargetsOnlyClientClasses() {
    JsonObject config = readJson("/codex_catalogue_bridge.mixins.json");
    assertEquals("com.codex.minecraft.cataloguebridge.mixin", config.get("package").getAsString());
    assertTrue(config.getAsJsonArray("client").asList().size() >= 2);
}
```

- [ ] **Step 2: Run the test to verify it fails**

Run:

```powershell
$env:JAVA_HOME='C:\Program Files\Java\jdk-23'
& 'C:\Users\liuyu\.gradle\wrapper\dists\gradle-8.14.2-bin\2pb3mgt1p815evrl3weanttgr\gradle-8.14.2\bin\gradle.bat' test --offline
```

Expected: Gradle reports that the project or `MixinMetadataTest` does not yet exist.

- [ ] **Step 3: Create the minimal NeoForge project**

Set `version = '1.0.0-2026Reset'`, `archivesName = 'codex-catalogue-bridge'`, Java toolchain 21, NeoForge `21.1.235`, and test dependencies matching the existing `codex-loot-rarity-ui` project. Define `catalogueJar` as `D:/其他应用/Minecraft/.minecraft/versions/1.21.1-NeoForge_21.1.235/mods/[模组目录] catalogue-neoforge-1.21.1-1.11.2.jar`, fail configuration when it is absent, and add `compileOnly files(catalogueJar)` so the two Mixin target classes compile without bundling Catalogue. Use this metadata:

```toml
[[mods]]
modId = "codex_catalogue_bridge"
version = "${file.jarVersion}"
displayName = "Codex Catalogue Bridge"
description = "Client-only Catalogue labels, links, and title-layout bridge."

[[dependencies.codex_catalogue_bridge]]
modId = "catalogue"
type = "required"
versionRange = "[1.11.2,1.12)"
ordering = "NONE"
side = "CLIENT"
```

Use a mixin file with `required: true`, `compatibilityLevel: JAVA_21`, package `com.codex.minecraft.cataloguebridge.mixin`, `client` entries only, and `defaultRequire: 1`.

- [ ] **Step 4: Run the test to verify it passes**

Run the same Gradle command. Expected: `BUILD SUCCESSFUL` and `MixinMetadataTest` passes.

- [ ] **Step 5: Commit the scaffold**

```powershell
git add mods/codex-catalogue-bridge
git commit -m "feat: scaffold catalogue bridge mod"
```

### Task 2: Add Tested Override and Title Layout Policies

**Files:**
- Create: `D:\MC和谐家园\mods\codex-catalogue-bridge\src\main\resources\data\codex_catalogue_bridge\catalogue_overrides.json`
- Create: `D:\MC和谐家园\mods\codex-catalogue-bridge\src\main\java\com\codex\minecraft\cataloguebridge\CatalogueOverrides.java`
- Create: `D:\MC和谐家园\mods\codex-catalogue-bridge\src\main\java\com\codex\minecraft\cataloguebridge\TitleLayout.java`
- Create: `D:\MC和谐家园\mods\codex-catalogue-bridge\src\test\java\com\codex\minecraft\cataloguebridge\CatalogueOverridesTest.java`
- Create: `D:\MC和谐家园\mods\codex-catalogue-bridge\src\test\java\com\codex\minecraft\cataloguebridge\TitleLayoutTest.java`

- [ ] **Step 1: Write failing parser and title-layout tests**

```java
@Test
void configuredJepOverridesExposeLinksButNoAlias() {
    CatalogueOverride entry = CatalogueOverrides.forMod("justenoughprofessions").orElseThrow();
    assertTrue(entry.alias().isEmpty());
    assertTrue(entry.homepage().endsWith("/mods/justenoughprofessions"));
    assertTrue(entry.issueTracker().contains("title=%5BJust%20Enough%20Professions%5D%20"));
}

@Test
void unknownModsDoNotReceiveOverrides() {
    assertTrue(CatalogueOverrides.forMod("minecraft").isEmpty());
}

@Test
void fittingTitleKeepsCatalogueDefaultScale() {
    assertEquals(new TitleLayout.Result(2.0F, false, 0), TitleLayout.fit(300, 800));
}

@Test
void longTitleScalesBeforeItTrims() {
    assertEquals(new TitleLayout.Result(1.25F, false, 0), TitleLayout.fit(640, 800));
}

@Test
void extremeTitleUsesMinimumScaleAndTrimWidth() {
    assertEquals(new TitleLayout.Result(0.75F, true, 1066), TitleLayout.fit(2000, 800));
}
```

- [ ] **Step 2: Run the tests to verify they fail**

Run:

```powershell
$env:JAVA_HOME='C:\Program Files\Java\jdk-23'
& 'C:\Users\liuyu\.gradle\wrapper\dists\gradle-8.14.2-bin\2pb3mgt1p815evrl3weanttgr\gradle-8.14.2\bin\gradle.bat' test --tests '*CatalogueOverridesTest' --tests '*TitleLayoutTest' --offline
```

Expected: compilation fails because `CatalogueOverrides`, `CatalogueOverride`, and `TitleLayout` are undefined.

- [ ] **Step 3: Implement the minimal policies**

Use this record and scale contract:

```java
public record CatalogueOverride(Optional<String> alias, String homepage, String issueTracker) {}

public final class TitleLayout {
    public static final float DEFAULT_SCALE = 2.0F;
    public static final float MIN_SCALE = 0.75F;

    public record Result(float scale, boolean trim, int trimWidth) {}

    public static Result fit(int titleWidth, int availablePixels) {
        if (titleWidth * DEFAULT_SCALE <= availablePixels) {
            return new Result(DEFAULT_SCALE, false, 0);
        }
        float requestedScale = (float) availablePixels / titleWidth;
        if (requestedScale >= MIN_SCALE) {
            return new Result(requestedScale, false, 0);
        }
        return new Result(MIN_SCALE, true, (int) (availablePixels / MIN_SCALE));
    }
}
```

Load the JSON with Gson from `/data/codex_catalogue_bridge/catalogue_overrides.json`. Parse only string `alias`, `homepage`, and `issueTracker` fields. Invalid or missing resource data returns an immutable empty map. The initial JSON has only `justenoughprofessions`, alias `null`, and the GitHub website/issue URLs defined in the design.

- [ ] **Step 4: Run the tests to verify they pass**

Run the same Gradle command. Expected: all five tests pass.

- [ ] **Step 5: Commit the policies**

```powershell
git add mods/codex-catalogue-bridge
git commit -m "feat: add catalogue override policies"
```

### Task 3: Apply Catalogue Overrides Without Editing Upstream JARs

**Files:**
- Create: `D:\MC和谐家园\mods\codex-catalogue-bridge\src\main\java\com\codex\minecraft\cataloguebridge\mixin\NeoForgeModDataMixin.java`
- Create: `D:\MC和谐家园\mods\codex-catalogue-bridge\src\main\java\com\codex\minecraft\cataloguebridge\mixin\CatalogueModListScreenMixin.java`
- Modify: `D:\MC和谐家园\mods\codex-catalogue-bridge\src\main\resources\codex_catalogue_bridge.mixins.json`
- Test: `D:\MC和谐家园\mods\codex-catalogue-bridge\src\test\java\com\codex\minecraft\cataloguebridge\MixinTargetTest.java`

- [ ] **Step 1: Write the failing target-contract test**

```java
@Test
void mixinConfigurationNamesTheThreeNarrowCatalogueTargets() {
    JsonArray client = readJson("/codex_catalogue_bridge.mixins.json").getAsJsonArray("client");
    assertTrue(client.toString().contains("NeoForgeModDataMixin"));
    assertTrue(client.toString().contains("CatalogueModListScreenMixin"));
}
```

- [ ] **Step 2: Run the test to verify it fails**

Run:

```powershell
$env:JAVA_HOME='C:\Program Files\Java\jdk-23'
& 'C:\Users\liuyu\.gradle\wrapper\dists\gradle-8.14.2-bin\2pb3mgt1p815evrl3weanttgr\gradle-8.14.2\bin\gradle.bat' test --tests '*MixinTargetTest' --offline
```

Expected: assertion fails because the mixin entries are absent.

- [ ] **Step 3: Implement the mixins**

Target Catalogue `1.11.2` methods exactly:

```text
com.mrcrayfish.catalogue.client.NeoForgeModData#getDisplayName()
com.mrcrayfish.catalogue.client.NeoForgeModData#getHomepage()
com.mrcrayfish.catalogue.client.NeoForgeModData#getIssueTracker()
com.mrcrayfish.catalogue.client.screen.CatalogueModListScreen#drawModInfo(Lnet/minecraft/client/gui/GuiGraphics;IIF)V
```

`NeoForgeModDataMixin` shadows `getModId()` and injects at `HEAD` into the three getters. It only calls `CallbackInfoReturnable#setReturnValue` when `CatalogueOverrides.forMod(getModId())` provides a non-empty replacement; alias stays untouched when the JSON alias is null.

`CatalogueModListScreenMixin` redirects only ordinal `0` of this invocation inside `drawModInfo`:

```text
Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Ljava/lang/String;III)I
```

The redirect reads the current `PoseStack` translation with `graphics.pose().last().pose().m30()`, calculates available detail-panel width as `graphics.guiWidth() - round(translationX) - 10`, and uses `TitleLayout.fit(font.width(title), availableWidth)`. It applies the ratio `result.scale() / 2.0F` around the original draw call because Catalogue already applies a `2.0F` title scale. When `result.trim()` is true, it uses `font.plainSubstrByWidth(title, result.trimWidth() - font.width("...")) + "..."` before drawing.

- [ ] **Step 4: Run the bridge test suite and build**

Run:

```powershell
$env:JAVA_HOME='C:\Program Files\Java\jdk-23'
& 'C:\Users\liuyu\.gradle\wrapper\dists\gradle-8.14.2-bin\2pb3mgt1p815evrl3weanttgr\gradle-8.14.2\bin\gradle.bat' clean test build --offline
```

Expected: all unit tests pass and `build/libs/codex-catalogue-bridge-1.0.0-2026Reset.jar` exists.

- [ ] **Step 5: Commit the bridge implementation**

```powershell
git add mods/codex-catalogue-bridge
git commit -m "feat: bridge catalogue titles and external links"
```

### Task 4: Complete JEP Server Review and Bilingual Catalogue Description

**Files:**
- Create: `D:\MC和谐家园\docs\reviews\justenoughprofessions\stage-2-server.md`
- Create: `D:\MC和谐家园\mods\codex-catalogue-bridge\src\main\resources\assets\codex_catalogue_bridge\lang\en_us.json`
- Create: `D:\MC和谐家园\mods\codex-catalogue-bridge\src\main\resources\assets\codex_catalogue_bridge\lang\zh_cn.json`
- Modify: `D:\MC和谐家园\mods\codex-catalogue-bridge\src\test\java\com\codex\minecraft\cataloguebridge\CatalogueOverridesTest.java`

- [ ] **Step 1: Write failing language-key tests**

```java
@Test
void jepCatalogueDescriptionExistsInBothLanguages() {
    assertEquals(
        enUs().get("fml.menu.mods.info.description.justenoughprofessions"),
        "Adds a JEI profession browser that shows each villager profession and its matching job-site blocks."
    );
    assertEquals(
        zhCn().get("fml.menu.mods.info.description.justenoughprofessions"),
        "在 JEI 中加入村民职业浏览页，展示每种职业及其对应的工作方块。"
    );
}
```

- [ ] **Step 2: Run the test to verify it fails**

Run:

```powershell
$env:JAVA_HOME='C:\Program Files\Java\jdk-23'
& 'C:\Users\liuyu\.gradle\wrapper\dists\gradle-8.14.2-bin\2pb3mgt1p815evrl3weanttgr\gradle-8.14.2\bin\gradle.bat' test --tests '*CatalogueOverridesTest' --offline
```

Expected: resource lookup fails because the two language files do not yet exist.

- [ ] **Step 3: Add the language files and server evidence record**

Add exactly the tested descriptions. The stage-two report must conclude `client_only` using these facts: JEP registers a JEI plugin and JEI category only; it has no packet registration, server tick, command, save data, world generation, or server event path; it requires JEI; and its visible entity rendering path imports client GUI classes. State that JEP and `codex-catalogue-bridge` are excluded from `profiles/server/manifest.json`.

- [ ] **Step 4: Run tests and build**

Run the Task 3 build command. Expected: all tests pass and the generated JAR contains both language JSON files.

- [ ] **Step 5: Commit the JEP review and translations**

```powershell
git add docs/reviews/justenoughprofessions mods/codex-catalogue-bridge
git commit -m "docs: classify JEP as client only"
```

### Task 5: Add Release Metadata, Manifests, and the GitHub Issue Form

**Files:**
- Create: `D:\MC和谐家园\README.md`
- Create: `D:\MC和谐家园\pack\manifest.json`
- Create: `D:\MC和谐家园\profiles\client\manifest.json`
- Create: `D:\MC和谐家园\profiles\server\manifest.json`
- Create: `D:\MC和谐家园\profiles\server\installation.md`
- Create: `D:\MC和谐家园\mods\justenoughprofessions\README.md`
- Create: `D:\MC和谐家园\mods\justenoughprofessions\release.json`
- Create: `D:\MC和谐家园\mods\justenoughprofessions\NOTICE.md`
- Create: `D:\MC和谐家园\.github\ISSUE_TEMPLATE\mod-bug.yml`
- Test: `D:\MC和谐家园\tools\test_manifest.ps1`

- [ ] **Step 1: Write the failing manifest validation script**

```powershell
$pack = Get-Content -Raw -LiteralPath "$PSScriptRoot\..\pack\manifest.json" | ConvertFrom-Json
$jep = $pack.mods | Where-Object modId -eq 'justenoughprofessions'
if ($null -eq $jep) { throw 'JEP is missing from pack manifest' }
if ($jep.side -ne 'client_only') { throw 'JEP must be client_only' }
if ($jep.sha256 -ne '8D9702B337C2BFF018A3B37896BB07B2F3161BB881D4114C9142A01C86F5DFCC') { throw 'Unexpected JEP hash' }
```

- [ ] **Step 2: Run it to verify it fails**

Run:

```powershell
pwsh -File .\tools\test_manifest.ps1
```

Expected: file-not-found error because `pack/manifest.json` does not exist.

- [ ] **Step 3: Add the initial release metadata**

Set `pack/manifest.json` to include only JEP and the bridge mod. JEP uses the validated SHA-256 and `client_only`; bridge is `client_only` and has a locally computed SHA-256 after Task 3. Set `profiles/server/manifest.json` to `{ "mods": [] }` and document that no server-mod audit has completed yet.

JEP's release page must link its original source, record MIT license, identify the `4.0.5-2026Reset` optimization changes, include the backup path, and show a GitHub Release download URL. `NOTICE.md` must include the upstream MIT copyright notice. The issue form must contain `mod`, `build`, `minecraft`, `reproduction`, `expected`, `actual`, `logs`, and `multiplayer` fields.

- [ ] **Step 4: Run the manifest validator**

Run:

```powershell
pwsh -File .\tools\test_manifest.ps1
```

Expected: it exits with code 0.

- [ ] **Step 5: Commit release infrastructure**

```powershell
git add README.md pack profiles mods/justenoughprofessions .github tools/test_manifest.ps1
git commit -m "feat: add JEP release and profile manifests"
```

### Task 6: Publish the First Public Repository and Validate In Game

**Files:**
- Modify: `D:\MC和谐家园\mods\codex-catalogue-bridge\src\main\resources\data\codex_catalogue_bridge\catalogue_overrides.json`
- Modify: `D:\MC和谐家园\pack\manifest.json`
- Create: `D:\MC和谐家园\docs\reviews\justenoughprofessions\stage-4-catalogue-verification.md`

- [ ] **Step 1: Build and record the bridge JAR hash**

Run:

```powershell
$env:JAVA_HOME='C:\Program Files\Java\jdk-23'
& 'C:\Users\liuyu\.gradle\wrapper\dists\gradle-8.14.2-bin\2pb3mgt1p815evrl3weanttgr\gradle-8.14.2\bin\gradle.bat' -p .\mods\codex-catalogue-bridge clean test build --offline
Get-FileHash -Algorithm SHA256 .\mods\codex-catalogue-bridge\build\libs\codex-catalogue-bridge-1.0.0-2026Reset.jar
```

- [ ] **Step 2: Create the public GitHub repository and push without global proxy changes**

Run:

```powershell
gh repo create 'kirito0000001/MC和谐家园' --public --source . --remote origin --disable-wiki
git -c http.proxy= -c https.proxy= push -u origin main
```

Expected: GitHub returns the new repository URL, and `origin/main` points to the current local commit.

- [ ] **Step 3: Create labels and the JEP release**

Run:

```powershell
gh label create 'bug' --color 'd73a4a' --repo 'kirito0000001/MC和谐家园'
gh label create 'mod:justenoughprofessions' --color '1d76db' --repo 'kirito0000001/MC和谐家园'
gh release create 'justenoughprofessions-4.0.5-2026Reset' 'C:\Users\liuyu\Documents\Codex\2026-06-14\files-mentioned-by-the-user-codex\.work\mod-audit\JustEnoughProfessions-neoforge-build\build\libs\JustEnoughProfessions-neoforge-1.21.1-4.0.5-2026Reset.jar#JustEnoughProfessions-neoforge-1.21.1-4.0.5-2026Reset.jar' --repo 'kirito0000001/MC和谐家园' --title 'Just Enough Professions 4.0.5-2026Reset' --notes 'Client-only optimization build. See mods/justenoughprofessions/README.md for source, license, hash, and rollback details.'
```

If label creation reports an existing label, inspect it with `gh label list --repo 'kirito0000001/MC和谐家园'` and continue only when its name and color match the command.

- [ ] **Step 4: Verify release URLs and push the metadata commit**

Use this fixed release page URL in `release.json` and `pack/manifest.json`:

```text
https://github.com/kirito0000001/MC%E5%92%8C%E8%B0%90%E5%AE%B6%E5%9B%AD/releases/tag/justenoughprofessions-4.0.5-2026Reset
```

Keep `catalogue_overrides.json` pointed at the JEP repository page, not the direct asset download. Run `pwsh -File .\tools\test_manifest.ps1`, then commit and push:

```powershell
git add mods/codex-catalogue-bridge pack/manifest.json mods/justenoughprofessions/release.json
git commit -m "chore: link JEP public release"
git -c http.proxy= -c https.proxy= push origin main
```

- [ ] **Step 5: Install only the bridge JAR and verify Catalogue**

Back up the current `mods` folder entry list, copy `codex-catalogue-bridge-1.0.0-2026Reset.jar` into `D:\其他应用\Minecraft\.minecraft\versions\1.21.1-NeoForge_21.1.235\mods`, and launch the client manually. Verify:

```text
1. JEP right-side title stays within the detail panel.
2. JEP description is correct in zh_cn and en_us.
3. Website opens the JEP GitHub page.
4. Submit Bug opens the repository issue form with [Just Enough Professions] title and mod label.
5. JEP JEI profession page still renders villagers and workstation blocks.
```

Record screenshots, launch-log evidence, and any rollback action in `stage-4-catalogue-verification.md`.

- [ ] **Step 6: Final remote verification and commit**

Run:

```powershell
git status -sb
git remote -v
git config --get http.proxy
git config --get https.proxy
gh api repos/kirito0000001/MC和谐家园/commits/main --jq '{sha:.sha,message:.commit.message}'
```

Expected: clean `main`, `origin` targets the public repository, proxy values remain unchanged, and GitHub reports the latest pushed commit.
