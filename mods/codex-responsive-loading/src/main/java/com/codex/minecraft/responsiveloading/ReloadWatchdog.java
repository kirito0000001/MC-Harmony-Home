package com.codex.minecraft.responsiveloading;

import com.mojang.logging.LogUtils;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;

final class ReloadWatchdog {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final long SLOW_THRESHOLD_NANOS = TimeUnit.SECONDS.toNanos(5);
    private static final long WARNING_REPEAT_NANOS = TimeUnit.SECONDS.toNanos(5);
    private static final Set<ReloadTiming> ACTIVE = ConcurrentHashMap.newKeySet();
    private static final ScheduledExecutorService EXECUTOR = Executors.newSingleThreadScheduledExecutor(daemonFactory());

    static {
        EXECUTOR.scheduleAtFixedRate(ReloadWatchdog::safeScan, 1L, 1L, TimeUnit.SECONDS);
    }

    private ReloadWatchdog() {
    }

    static void start(ReloadTiming timing) {
        ACTIVE.add(timing);
    }

    static void finish(ReloadTiming timing, Throwable throwable) {
        if (!ACTIVE.remove(timing)) {
            return;
        }

        long now = System.nanoTime();
        ReloadTiming.Snapshot snapshot = timing.snapshot(now);
        timing.markComplete();
        double elapsedMillis = nanosToMillis(snapshot.elapsedNanos());
        double preparationMillis = nanosToMillis(snapshot.preparationNanos());
        double applyMillis = nanosToMillis(snapshot.applyNanos());
        if (throwable == null) {
            LOGGER.info(
                "[Codex Responsive Loading] DONE listener={} elapsedMs={} prepareTasks={} prepareMs={} applyTasks={} applyMs={}",
                snapshot.listenerName(),
                elapsedMillis,
                snapshot.preparationTasks(),
                preparationMillis,
                snapshot.applyTasks(),
                applyMillis
            );
        } else {
            LOGGER.error(
                "[Codex Responsive Loading] FAILED listener={} elapsedMs={} phase={} task={} prepareTasks={} applyTasks={}",
                snapshot.listenerName(),
                elapsedMillis,
                snapshot.phase(),
                snapshot.taskIndex(),
                snapshot.preparationTasks(),
                snapshot.applyTasks(),
                throwable
            );
        }
    }

    private static void scan() {
        long now = System.nanoTime();
        for (ReloadTiming timing : ACTIVE) {
            for (ReloadTiming.SlowTask slowTask : timing.claimSlowWarnings(
                now,
                SLOW_THRESHOLD_NANOS,
                WARNING_REPEAT_NANOS
            )) {
                if (!ACTIVE.contains(timing)) {
                    continue;
                }
                ReloadTiming.Snapshot snapshot = timing.snapshot(now);
                LOGGER.warn(
                    "[Codex Responsive Loading] SLOW listener={} phase={} task={} taskElapsedMs={} listenerElapsedMs={} prepareTasks={} applyTasks={}",
                    slowTask.listenerName(),
                    slowTask.phase(),
                    slowTask.taskIndex(),
                    nanosToMillis(slowTask.elapsedNanos()),
                    nanosToMillis(snapshot.elapsedNanos()),
                    snapshot.preparationTasks(),
                    snapshot.applyTasks()
                );
            }
        }
    }

    private static void safeScan() {
        try {
            scan();
        } catch (Throwable throwable) {
            LOGGER.error("[Codex Responsive Loading] Reload watchdog scan failed; future scans will continue", throwable);
        }
    }

    private static ThreadFactory daemonFactory() {
        return runnable -> {
            Thread thread = new Thread(runnable, "Codex Responsive Loading Watchdog");
            thread.setDaemon(true);
            return thread;
        };
    }

    private static double nanosToMillis(long nanos) {
        return nanos / 1_000_000.0D;
    }
}
