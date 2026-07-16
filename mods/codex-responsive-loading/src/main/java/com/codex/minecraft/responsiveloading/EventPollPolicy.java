package com.codex.minecraft.responsiveloading;

final class EventPollPolicy {
    private EventPollPolicy() {
    }

    static boolean shouldPoll(boolean onRenderThread, long nowNanos, long lastPollNanos, long minimumIntervalNanos) {
        if (!onRenderThread) {
            return false;
        }
        return lastPollNanos == Long.MIN_VALUE || nowNanos - lastPollNanos >= minimumIntervalNanos;
    }
}
