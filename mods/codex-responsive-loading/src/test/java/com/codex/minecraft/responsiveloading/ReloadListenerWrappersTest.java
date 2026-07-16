package com.codex.minecraft.responsiveloading;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Executor;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import org.junit.jupiter.api.Test;

class ReloadListenerWrappersTest {
    @Test
    void preservesListenerOrderAndNames() {
        PreparableReloadListener first = namedListener("FirstListener");
        PreparableReloadListener second = namedListener("SecondListener");

        List<PreparableReloadListener> wrapped = ReloadListenerWrappers.wrap(List.of(first, second));

        assertEquals(List.of("FirstListener", "SecondListener"), wrapped.stream().map(PreparableReloadListener::getName).toList());
        assertNotSame(first, wrapped.get(0));
        assertNotSame(second, wrapped.get(1));
    }

    @Test
    void retainsTheOriginalFutureAndRoutesTasksThroughTheOriginalExecutors() {
        List<Runnable> preparationQueue = new ArrayList<>();
        List<Runnable> applyQueue = new ArrayList<>();
        List<String> events = new ArrayList<>();
        CompletableFuture<Void> originalFuture = new CompletableFuture<>();
        PreparableReloadListener delegate = listener("Measured", (preparationExecutor, applyExecutor) -> {
            preparationExecutor.execute(() -> events.add("prepare"));
            applyExecutor.execute(() -> events.add("apply"));
            return originalFuture;
        });

        CompletableFuture<Void> returned = new InstrumentedReloadListener(delegate).reload(
            null,
            null,
            null,
            null,
            preparationQueue::add,
            applyQueue::add
        );

        assertSame(originalFuture, returned);
        assertEquals(1, preparationQueue.size());
        assertEquals(1, applyQueue.size());
        assertEquals(List.of(), events);
        preparationQueue.removeFirst().run();
        applyQueue.removeFirst().run();
        assertEquals(List.of("prepare", "apply"), events);
        originalFuture.complete(null);
    }

    @Test
    void preservesAsynchronousAndSynchronousFailures() {
        RuntimeException asynchronousFailure = new RuntimeException("async");
        CompletableFuture<Void> originalFuture = new CompletableFuture<>();
        PreparableReloadListener asynchronous = listener("AsyncFailure", (preparationExecutor, applyExecutor) -> originalFuture);
        CompletableFuture<Void> returned = new InstrumentedReloadListener(asynchronous).reload(
            null, null, null, null, Runnable::run, Runnable::run
        );

        originalFuture.completeExceptionally(asynchronousFailure);
        CompletionException completion = assertThrows(CompletionException.class, returned::join);
        assertSame(asynchronousFailure, completion.getCause());

        IllegalStateException synchronousFailure = new IllegalStateException("sync");
        PreparableReloadListener synchronous = listener("SyncFailure", (preparationExecutor, applyExecutor) -> {
            throw synchronousFailure;
        });
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () ->
            new InstrumentedReloadListener(synchronous).reload(
                null, null, null, null, Runnable::run, Runnable::run
            )
        );
        assertSame(synchronousFailure, thrown);
    }

    private static PreparableReloadListener namedListener(String name) {
        return new PreparableReloadListener() {
            @Override
            public CompletableFuture<Void> reload(
                PreparationBarrier barrier,
                ResourceManager resourceManager,
                ProfilerFiller preparationProfiler,
                ProfilerFiller reloadProfiler,
                Executor preparationExecutor,
                Executor reloadExecutor
            ) {
                return CompletableFuture.completedFuture(null);
            }

            @Override
            public String getName() {
                return name;
            }
        };
    }

    private static PreparableReloadListener listener(String name, ReloadAction action) {
        return new PreparableReloadListener() {
            @Override
            public CompletableFuture<Void> reload(
                PreparationBarrier barrier,
                ResourceManager resourceManager,
                ProfilerFiller preparationProfiler,
                ProfilerFiller reloadProfiler,
                Executor preparationExecutor,
                Executor applyExecutor
            ) {
                return action.reload(preparationExecutor, applyExecutor);
            }

            @Override
            public String getName() {
                return name;
            }
        };
    }

    @FunctionalInterface
    private interface ReloadAction {
        CompletableFuture<Void> reload(Executor preparationExecutor, Executor applyExecutor);
    }
}
