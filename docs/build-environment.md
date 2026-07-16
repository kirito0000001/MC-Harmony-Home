# Minecraft Mod Build Environment

This repository must stay in an ASCII-only path. The current canonical root is `D:\MC-Harmony-Home`; do not build through a Chinese-named directory or a junction to one.

## Fixed Toolchain

- Java: Eclipse Temurin JDK 21 at `C:\Users\liuyu\.gradle\jdks\eclipse_adoptium-21-amd64-windows.2`.
- Bridge Gradle: cached Gradle 8.10 at `C:\Users\liuyu\.gradle\wrapper\dists\gradle-8.10-bin\deqhafrv1ntovfmgh0nh3npr9\gradle-8.10\bin\gradle.bat`.
- The Minecraft client may use a different runtime. Do not change global `JAVA_HOME` or launcher Java settings for builds.

## Bridge

Run `tools\build-codex-catalogue-bridge.ps1`. It sets JDK 21 only for its child process and executes `build --offline --no-daemon`, including the JUnit tests. Do not invoke Gradle manually from an alternate directory.

When supplying a custom `String[]` `GradleArgs` value, use a named argument, for example `-GradleArgs @('test', '--offline', '--no-daemon')`. Positional arguments can bind a later value to `JavaHome`. In interpolated PowerShell strings, write `${count}:` rather than `$count:` when a variable is immediately followed by a colon; otherwise PowerShell treats the colon as part of an invalid variable reference.

## JEED Source Build

Run `tools\build-jeed-neoforge.ps1` for normal local builds. It uses the same fixed JDK 21 and Gradle 8.10, and defaults to a full offline NeoForge build.

For a fresh machine or an intentionally cleared Gradle cache, run `tools\build-jeed-neoforge.ps1 -Online` once. The script reads the active Windows system proxy only for that child Gradle process, adds bounded HTTP timeouts, and caches all required dependencies. It does not use the stale Git proxy setting.

JEED's repositories are scoped to their actual owners: Architectury for `dev.architectury`, Fabric for `net.fabricmc` and MixinExtras, and NeoForge Releases for `net.neoforged.*` and `cpw.mods.*`. Keep those content filters when updating the project; removing them reintroduces long sequential repository waits.

## JEED Local Patch

`tools\build-jeed-local-patch.ps1` remains available for resource-only iterations. It copies the original JAR, updates only `zh_cn.json` and the version metadata, then emits a SHA-256 hash. It does not compile or alter Java classes.

## Passive Search Bar

Run `tools\build-passive-search-bar.ps1`. It pins only the child build to Oracle JDK 23 at `C:\Program Files\Java\jdk-23` and uses the project's cached Gradle Wrapper 8.14.2 with `build --offline --no-daemon` by default. The source is tracked at `mods\passivesearchbar`; do not build the older temporary copy under the Codex workspace.

The project may print Gradle 8.14 deprecation notices. They are emitted by the current NeoForge ModDev plugin integration and are not test failures; record them when upgrading Gradle or the plugin, but do not change the Gradle or JDK version during a normal source review.

## First Dependency Resolution

If a new source project needs dependencies that are not cached, use its pinned JDK and Gradle version for one online resolution. After that, return to `--offline`. Do not switch JDK versions, Gradle versions, proxy settings, and repositories in the same attempt; inspect the first missing dependency or timeout before changing one variable.

## Validation

- Run the project-specific build script.
- Confirm the generated JAR SHA-256 and contents.
- Keep candidates outside the live Minecraft `mods` directory until the 10-mod batch validation point.
