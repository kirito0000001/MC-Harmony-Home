package com.codex.minecraft.responsiveloading;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ReloadInstance;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.Unit;
import org.junit.jupiter.api.Test;

class MixinContractTest {
    @Test
    void redirectsOnlyTheReloadFactoryAndWrapsTheListenerList() throws IOException {
        String source = Files.readString(Path.of(
            "src/main/java/com/codex/minecraft/responsiveloading/mixin/ReloadableResourceManagerMixin.java"
        ));

        assertTrue(source.contains("@Redirect"));
        assertTrue(source.contains("SimpleReloadInstance;create"));
        assertTrue(source.contains("ReloadListenerWrappers.wrap(listeners)"));
    }

    @Test
    void listenerWrapperRetainsExecutorsCompletionAndExceptions() throws IOException {
        String source = Files.readString(Path.of(
            "src/main/java/com/codex/minecraft/responsiveloading/InstrumentedReloadListener.java"
        ));

        assertTrue(source.contains("preparationExecutor.execute"));
        assertTrue(source.contains("applyExecutor.execute"));
        assertTrue(source.contains("delegate.reload"));
        assertTrue(source.contains("whenComplete"));
        assertTrue(source.contains("ReloadWatchdog.finish"));
    }

    @Test
    void redirectHandlerBytecodeHasTheExactFactorySignature() throws ReflectiveOperationException {
        Class<?> mixin = Class.forName(
            "com.codex.minecraft.responsiveloading.mixin.ReloadableResourceManagerMixin"
        );
        var handlers = List.of(mixin.getDeclaredMethods()).stream()
            .filter(method -> method.getName().contains("wrapListeners"))
            .toList();

        assertEquals(1, handlers.size());
        assertEquals(ReloadInstance.class, handlers.getFirst().getReturnType());
        assertArrayEquals(
            new Class<?>[] {
                ResourceManager.class,
                List.class,
                Executor.class,
                Executor.class,
                CompletableFuture.class,
                boolean.class
            },
            handlers.getFirst().getParameterTypes()
        );
    }

    @Test
    void watchdogSchedulerAndCompletionRaceAreIsolated() throws IOException {
        String source = Files.readString(Path.of(
            "src/main/java/com/codex/minecraft/responsiveloading/ReloadWatchdog.java"
        ));

        assertTrue(source.contains("ReloadWatchdog::safeScan"));
        assertTrue(source.contains("catch (Throwable"));
        assertTrue(source.contains("ACTIVE.contains(timing)"));
    }

    @Test
    void failureLoggingSnapshotsTheActiveTaskBeforeCompletion() throws IOException {
        String source = Files.readString(Path.of(
            "src/main/java/com/codex/minecraft/responsiveloading/ReloadWatchdog.java"
        ));
        String finish = source.substring(
            source.indexOf("static void finish"),
            source.indexOf("private static void scan")
        );

        assertTrue(finish.indexOf("timing.snapshot(now)") < finish.indexOf("timing.markComplete()"));
    }
}
