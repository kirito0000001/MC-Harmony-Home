package com.codex.minecraft.responsiveloading;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;

final class WindowResponsiveness {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final long MINIMUM_INTERVAL_NANOS = TimeUnit.MILLISECONDS.toNanos(100);
    private static final AtomicLong LAST_POLL_NANOS = new AtomicLong(Long.MIN_VALUE);
    private static final AtomicBoolean POLLING = new AtomicBoolean();
    private static final AtomicBoolean FAILURE_LOGGED = new AtomicBoolean();

    private WindowResponsiveness() {
    }

    static void pollBestEffort() {
        if (!POLLING.compareAndSet(false, true)) {
            return;
        }

        try {
            try {
                long now = System.nanoTime();
                while (true) {
                    long previous = LAST_POLL_NANOS.get();
                    if (!EventPollPolicy.shouldPoll(
                        RenderSystem.isOnRenderThread(),
                        now,
                        previous,
                        MINIMUM_INTERVAL_NANOS
                    )) {
                        return;
                    }
                    if (LAST_POLL_NANOS.compareAndSet(previous, now)) {
                        break;
                    }
                }

                GLFW.glfwPollEvents();
            } catch (Throwable throwable) {
                if (FAILURE_LOGGED.compareAndSet(false, true)) {
                    LOGGER.warn("[Codex Responsive Loading] GLFW event polling failed; continuing without it", throwable);
                }
            }
        } finally {
            POLLING.set(false);
        }
    }
}
