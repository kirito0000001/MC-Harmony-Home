# Codex Responsive Loading Design

## Goal

Create a standalone client-only NeoForge mod that keeps Windows startup behavior less disruptive and records enough live timing evidence to identify the exact resource listener or task responsible for a long startup stall.

The first release is diagnostic and protective. It does not move unknown mod work to different threads. True asynchronous optimization is deferred until one complete startup log identifies a specific safe target.

## Mod Identity

- Display name: `Codex Responsive Loading`
- Mod ID: `codex_responsive_loading`
- Version: `1.0.0-2026Reset`
- Minecraft: `1.21.1`
- NeoForge: `21.1.235`
- Side: client only
- Live filename: `[响应式启动] codex-responsive-loading-1.0.0-2026Reset.jar`

## Resource Reload Instrumentation

`ReloadableResourceManager.createReload` passes the ordered listener list into `SimpleReloadInstance.create`. A client Mixin redirects only that factory call and supplies wrappers around the same listeners in the same order.

Each wrapper preserves the delegate listener and records:

1. Listener start and completion.
2. Time spent in preparation-executor tasks.
3. Time spent in apply-executor tasks on the render thread.
4. Number of preparation and apply tasks.
5. Current phase and total elapsed time when a task exceeds the slow threshold.
6. Exceptional completion without suppressing or replacing the original exception.

Timing is logged immediately as each listener finishes. Unlike vanilla `ProfiledReloadInstance`, useful results remain in `debug.log` even when the process is closed before the whole reload completes.

## Window Responsiveness

The apply executor remains the original Minecraft executor. The wrapper only adds a throttled `GLFW.glfwPollEvents()` call before and after apply tasks when running on the render thread. Polling occurs between listener tasks, never from a worker thread and never inside an unknown task.

A single apply task can still block for a long time. On Windows, the mod calls `DisableProcessWindowsGhosting` through the JNA library already shipped with Minecraft. This prevents Windows from replacing the game window with the misleading ghost-window dialog while the process is still loading. It does not claim that a blocked render thread is interactive; the watchdog log remains the source of truth.

Non-Windows systems skip the native call. Any native-call failure is logged and startup continues normally.

## Slow Task Watchdog

A single daemon scheduled executor checks active listeners. After five seconds in the same phase it logs a warning containing the listener name, current phase, task index, and elapsed time. It repeats at five-second intervals until the listener finishes.

The watchdog never interrupts, cancels, reschedules, or moves resource work. Its thread is daemonized so it cannot keep Minecraft alive during shutdown.

## Safety Boundaries

- Listener order is unchanged.
- Preparation tasks remain on the original background executor.
- Apply tasks remain on the original render-thread executor.
- The original futures, completion values, and exceptions are preserved.
- No server classes, packets, registries, saves, items, or world data are added.
- Event polling is render-thread-only and throttled to at most once per 100 milliseconds.
- Windows ghosting protection is best-effort and never required for mod loading.

## Validation

Unit tests cover poll throttling, slow-warning timing, duration accumulation, metadata, and Mixin source contracts. The offline production build must contain the Mixin configuration, timing classes, Windows native bridge, and client-only metadata.

After installation, the next startup must be allowed to continue without pressing Close Program. The resulting log will be reviewed for `[Codex Responsive Loading]` start, slow, and completion records before any true asynchronous patch is designed.
