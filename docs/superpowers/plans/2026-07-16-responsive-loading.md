# Codex Responsive Loading Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Build and install a client-only mod that records resource reload bottlenecks immediately and reduces misleading Windows not-responding dialogs without changing task thread ownership.

**Architecture:** Redirect the resource reload factory to wrap listeners while preserving order and executors. Add pure timing and polling policies, a daemon slow-task watchdog, render-thread event polling at apply-task boundaries, and a best-effort Windows ghosting bridge.

**Tech Stack:** Java 21 bytecode, JDK 23 build toolchain, NeoForge 21.1.235, Sponge Mixin, LWJGL GLFW, JNA, JUnit 5, Gradle 8.14.2.

---

### Task 1: Scaffold the standalone client mod

**Files:**
- Create: `mods/codex-responsive-loading/build.gradle`
- Create: `mods/codex-responsive-loading/gradle.properties`
- Create: `mods/codex-responsive-loading/settings.gradle`
- Create: `mods/codex-responsive-loading/src/main/java/com/codex/minecraft/responsiveloading/CodexResponsiveLoading.java`
- Create: `mods/codex-responsive-loading/src/main/resources/META-INF/neoforge.mods.toml`
- Create: `mods/codex-responsive-loading/src/main/resources/codex_responsive_loading.mixins.json`
- Create: `mods/codex-responsive-loading/src/main/resources/pack.mcmeta`
- Create: `tools/build-codex-responsive-loading.ps1`

- [ ] Copy the known Gradle 8.14.2 wrapper binaries from Passive Search Bar.
- [ ] Add metadata tests requiring client-only dependencies and version `1.0.0-2026Reset`.
- [ ] Run the pinned build script and confirm the new tests fail before resources exist.
- [ ] Add the minimal entry point and resources needed to compile.

### Task 2: Add pure timing and polling policies

**Files:**
- Create: `mods/codex-responsive-loading/src/main/java/com/codex/minecraft/responsiveloading/ReloadTiming.java`
- Create: `mods/codex-responsive-loading/src/main/java/com/codex/minecraft/responsiveloading/EventPollPolicy.java`
- Test: `mods/codex-responsive-loading/src/test/java/com/codex/minecraft/responsiveloading/ReloadTimingTest.java`
- Test: `mods/codex-responsive-loading/src/test/java/com/codex/minecraft/responsiveloading/EventPollPolicyTest.java`

- [ ] Add failing tests for prepare/apply accumulation, task counts, five-second slow warnings, and 100-millisecond poll throttling.
- [ ] Run focused tests and confirm the expected failures.
- [ ] Implement the minimal thread-safe policy classes.
- [ ] Run focused tests and confirm they pass.

### Task 3: Wrap resource listeners and log active stalls

**Files:**
- Create: `mods/codex-responsive-loading/src/main/java/com/codex/minecraft/responsiveloading/InstrumentedReloadListener.java`
- Create: `mods/codex-responsive-loading/src/main/java/com/codex/minecraft/responsiveloading/ReloadWatchdog.java`
- Create: `mods/codex-responsive-loading/src/main/java/com/codex/minecraft/responsiveloading/ReloadListenerWrappers.java`
- Create: `mods/codex-responsive-loading/src/main/java/com/codex/minecraft/responsiveloading/mixin/ReloadableResourceManagerMixin.java`
- Test: `mods/codex-responsive-loading/src/test/java/com/codex/minecraft/responsiveloading/MixinContractTest.java`

- [ ] Add failing source-contract tests for the `SimpleReloadInstance.create` redirect, listener-order preservation, original-executor delegation, and immediate start/slow/done logging.
- [ ] Implement listener wrappers and the daemon watchdog without changing futures or exceptions.
- [ ] Run the complete test suite.

### Task 4: Add render-thread polling and Windows ghosting protection

**Files:**
- Create: `mods/codex-responsive-loading/src/main/java/com/codex/minecraft/responsiveloading/WindowResponsiveness.java`
- Create: `mods/codex-responsive-loading/src/main/java/com/codex/minecraft/responsiveloading/WindowsGhosting.java`
- Test: `mods/codex-responsive-loading/src/test/java/com/codex/minecraft/responsiveloading/WindowResponsivenessContractTest.java`

- [ ] Add failing tests requiring render-thread checks, throttled GLFW polling, Windows-only native invocation, and failure isolation.
- [ ] Implement boundary polling and the JNA `DisableProcessWindowsGhosting` bridge.
- [ ] Run all tests and the full offline build.

### Task 5: Package and install

**Files:**
- Create: `candidates/codex-responsive-loading-1.0.0-2026Reset.jar`
- Modify: live `mods` directory under `1.21.1-NeoForge_21.1.235`
- Modify: `docs/reviews/batch-validation.md`

- [ ] Inspect the candidate JAR contents and SHA-256.
- [ ] Confirm Minecraft is not running and back up any existing JAR with the same mod ID.
- [ ] Install as `[响应式启动] codex-responsive-loading-1.0.0-2026Reset.jar`.
- [ ] Confirm the mod ID occurs exactly once in the live directory.
- [ ] Record the next-start validation procedure and push the repository commits.
