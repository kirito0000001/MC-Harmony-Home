package com.codex.minecraft.responsiveloading;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class WindowResponsivenessContractTest {
    @Test
    void pollsGlfwOnlyThroughTheRenderThreadThrottle() throws IOException {
        String source = readMainSource("WindowResponsiveness.java");

        assertTrue(source.contains("RenderSystem.isOnRenderThread()"));
        assertTrue(source.contains("EventPollPolicy.shouldPoll"));
        assertTrue(source.contains("GLFW.glfwPollEvents()"));
        assertTrue(source.contains("TimeUnit.MILLISECONDS.toNanos(100)"));
        assertTrue(source.contains("POLLING.compareAndSet(false, true)"));
        assertTrue(source.contains("POLLING.set(false)"));
    }

    @Test
    void windowsGhostingIsBestEffortAndPlatformGuarded() throws IOException {
        String source = readMainSource("WindowsGhosting.java");

        assertTrue(source.contains("Platform.isWindows()"));
        assertTrue(source.contains("Native.load(\"user32\""));
        assertTrue(source.contains("DisableProcessWindowsGhosting"));
        assertTrue(source.contains("extends StdCallLibrary"));
        assertTrue(source.contains("catch (Throwable"));
    }

    @Test
    void applyTasksPollBeforeAndAfterDelegating() throws IOException {
        String source = readMainSource("InstrumentedReloadListener.java");

        int applyExecutor = source.indexOf("applyExecutor.execute");
        int firstPoll = source.indexOf("WindowResponsiveness.pollBestEffort", applyExecutor);
        int taskRun = source.indexOf("task.run()", firstPoll);
        int secondPoll = source.indexOf("WindowResponsiveness.pollBestEffort", taskRun);

        assertTrue(applyExecutor >= 0);
        assertTrue(firstPoll > applyExecutor);
        assertTrue(taskRun > firstPoll);
        assertTrue(secondPoll > taskRun);
    }

    private static String readMainSource(String fileName) throws IOException {
        return Files.readString(Path.of(
            "src/main/java/com/codex/minecraft/responsiveloading/" + fileName
        ));
    }
}
