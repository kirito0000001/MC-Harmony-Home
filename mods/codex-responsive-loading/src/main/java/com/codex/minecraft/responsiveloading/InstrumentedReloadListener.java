package com.codex.minecraft.responsiveloading;

import com.mojang.logging.LogUtils;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import org.slf4j.Logger;

final class InstrumentedReloadListener implements PreparableReloadListener {
    private static final Logger LOGGER = LogUtils.getLogger();

    private final PreparableReloadListener delegate;

    InstrumentedReloadListener(PreparableReloadListener delegate) {
        this.delegate = delegate;
    }

    @Override
    public CompletableFuture<Void> reload(
        PreparationBarrier barrier,
        ResourceManager resourceManager,
        ProfilerFiller preparationProfiler,
        ProfilerFiller reloadProfiler,
        Executor preparationExecutor,
        Executor applyExecutor
    ) {
        String name = getName();
        ReloadTiming timing = new ReloadTiming(name, System.nanoTime());
        AtomicInteger preparationTaskIndex = new AtomicInteger();
        AtomicInteger applyTaskIndex = new AtomicInteger();
        ReloadWatchdog.start(timing);
        LOGGER.info("[Codex Responsive Loading] START listener={}", name);

        Executor measuredPreparationExecutor = task -> preparationExecutor.execute(() -> {
            int taskIndex = preparationTaskIndex.incrementAndGet();
            long started = System.nanoTime();
            timing.startTask(ReloadTiming.Phase.PREPARATION, taskIndex, started);
            try {
                task.run();
            } finally {
                timing.recordPreparation(System.nanoTime() - started);
                timing.finishTask(ReloadTiming.Phase.PREPARATION, taskIndex);
            }
        });
        Executor measuredApplyExecutor = task -> applyExecutor.execute(() -> {
            int taskIndex = applyTaskIndex.incrementAndGet();
            long started = System.nanoTime();
            timing.startTask(ReloadTiming.Phase.APPLY, taskIndex, started);
            WindowResponsiveness.pollBestEffort();
            try {
                task.run();
            } finally {
                WindowResponsiveness.pollBestEffort();
                timing.recordApply(System.nanoTime() - started);
                timing.finishTask(ReloadTiming.Phase.APPLY, taskIndex);
            }
        });

        CompletableFuture<Void> future;
        try {
            future = delegate.reload(
                barrier,
                resourceManager,
                preparationProfiler,
                reloadProfiler,
                measuredPreparationExecutor,
                measuredApplyExecutor
            );
        } catch (Throwable throwable) {
            ReloadWatchdog.finish(timing, throwable);
            throw throwable;
        }

        future.whenComplete((ignored, throwable) -> ReloadWatchdog.finish(timing, throwable));
        return future;
    }

    @Override
    public String getName() {
        return delegate.getName();
    }
}
