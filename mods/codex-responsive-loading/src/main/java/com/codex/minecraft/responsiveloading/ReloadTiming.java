package com.codex.minecraft.responsiveloading;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

final class ReloadTiming {
    enum Phase {
        DISPATCH,
        PREPARATION,
        APPLY,
        COMPLETE
    }

    record TaskKey(Phase phase, int taskIndex) {
    }

    record SlowTask(String listenerName, Phase phase, int taskIndex, long elapsedNanos) {
    }

    record Snapshot(
        String listenerName,
        Phase phase,
        int taskIndex,
        int preparationTasks,
        int applyTasks,
        long preparationNanos,
        long applyNanos,
        long elapsedNanos
    ) {
    }

    private static final TaskKey DISPATCH_TASK = new TaskKey(Phase.DISPATCH, 0);

    private final String listenerName;
    private final long startedNanos;
    private final ConcurrentHashMap<TaskKey, Activity> activeTasks = new ConcurrentHashMap<>();
    private final AtomicReference<TaskKey> latestTask = new AtomicReference<>(DISPATCH_TASK);
    private final AtomicInteger preparationTasks = new AtomicInteger();
    private final AtomicInteger applyTasks = new AtomicInteger();
    private final AtomicLong preparationNanos = new AtomicLong();
    private final AtomicLong applyNanos = new AtomicLong();

    ReloadTiming(String listenerName, long startedNanos) {
        this.listenerName = listenerName;
        this.startedNanos = startedNanos;
        activeTasks.put(DISPATCH_TASK, new Activity(DISPATCH_TASK, startedNanos));
    }

    void startTask(Phase phase, int taskIndex, long nowNanos) {
        activeTasks.remove(DISPATCH_TASK);
        TaskKey key = new TaskKey(phase, taskIndex);
        activeTasks.put(key, new Activity(key, nowNanos));
        latestTask.set(key);
    }

    void finishTask(Phase phase, int taskIndex) {
        activeTasks.remove(new TaskKey(phase, taskIndex));
    }

    void markComplete() {
        activeTasks.clear();
        latestTask.set(new TaskKey(Phase.COMPLETE, 0));
    }

    void recordPreparation(long nanos) {
        preparationTasks.incrementAndGet();
        preparationNanos.addAndGet(Math.max(0L, nanos));
    }

    void recordApply(long nanos) {
        applyTasks.incrementAndGet();
        applyNanos.addAndGet(Math.max(0L, nanos));
    }

    List<SlowTask> claimSlowWarnings(long nowNanos, long thresholdNanos, long repeatNanos) {
        List<SlowTask> warnings = new ArrayList<>();
        for (Activity activity : activeTasks.values()) {
            if (nowNanos - activity.startedNanos < thresholdNanos) {
                continue;
            }
            while (true) {
                long previous = activity.lastSlowWarningNanos.get();
                if (previous != Long.MIN_VALUE && nowNanos - previous < repeatNanos) {
                    break;
                }
                if (activity.lastSlowWarningNanos.compareAndSet(previous, nowNanos)) {
                    warnings.add(new SlowTask(
                        listenerName,
                        activity.key.phase(),
                        activity.key.taskIndex(),
                        Math.max(0L, nowNanos - activity.startedNanos)
                    ));
                    break;
                }
            }
        }
        return List.copyOf(warnings);
    }

    Snapshot snapshot(long nowNanos) {
        TaskKey current = activeTasks.values().stream()
            .min((left, right) -> Long.compare(left.startedNanos, right.startedNanos))
            .map(activity -> activity.key)
            .orElseGet(latestTask::get);
        return new Snapshot(
            listenerName,
            current.phase(),
            current.taskIndex(),
            preparationTasks.get(),
            applyTasks.get(),
            preparationNanos.get(),
            applyNanos.get(),
            Math.max(0L, nowNanos - startedNanos)
        );
    }

    private static final class Activity {
        private final TaskKey key;
        private final long startedNanos;
        private final AtomicLong lastSlowWarningNanos = new AtomicLong(Long.MIN_VALUE);

        private Activity(TaskKey key, long startedNanos) {
            this.key = key;
            this.startedNanos = startedNanos;
        }
    }
}
