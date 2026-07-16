package com.codex.minecraft.responsiveloading;

import com.mojang.logging.LogUtils;
import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.win32.StdCallLibrary;
import org.slf4j.Logger;

final class WindowsGhosting {
    private static final Logger LOGGER = LogUtils.getLogger();

    private WindowsGhosting() {
    }

    static void disableBestEffort() {
        if (!Platform.isWindows()) {
            return;
        }

        try {
            User32 user32 = Native.load("user32", User32.class);
            user32.DisableProcessWindowsGhosting();
            LOGGER.info("[Codex Responsive Loading] Windows process ghosting disabled");
        } catch (Throwable throwable) {
            LOGGER.warn("[Codex Responsive Loading] Could not disable Windows process ghosting; continuing normally", throwable);
        }
    }

    private interface User32 extends StdCallLibrary {
        void DisableProcessWindowsGhosting();
    }
}
