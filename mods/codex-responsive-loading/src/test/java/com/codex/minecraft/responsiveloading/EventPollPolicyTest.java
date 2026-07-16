package com.codex.minecraft.responsiveloading;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class EventPollPolicyTest {
    @Test
    void pollsOnlyOnRenderThreadAfterTheMinimumInterval() {
        assertTrue(EventPollPolicy.shouldPoll(true, 1_000L, Long.MIN_VALUE, 100L));
        assertFalse(EventPollPolicy.shouldPoll(false, 1_000L, Long.MIN_VALUE, 100L));
        assertFalse(EventPollPolicy.shouldPoll(true, 1_050L, 1_000L, 100L));
        assertTrue(EventPollPolicy.shouldPoll(true, 1_100L, 1_000L, 100L));
    }
}
