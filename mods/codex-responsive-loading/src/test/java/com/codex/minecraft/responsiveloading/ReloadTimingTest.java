package com.codex.minecraft.responsiveloading;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;

class ReloadTimingTest {
    @Test
    void accumulatesPreparationAndApplyTasks() {
        ReloadTiming timing = new ReloadTiming("ModelManager", 1_000L);

        timing.startTask(ReloadTiming.Phase.PREPARATION, 1, 2_000L);
        timing.recordPreparation(2_000L);
        timing.recordPreparation(3_000L);
        timing.finishTask(ReloadTiming.Phase.PREPARATION, 1);
        timing.startTask(ReloadTiming.Phase.APPLY, 1, 6_000L);
        timing.recordApply(5_000L);

        ReloadTiming.Snapshot snapshot = timing.snapshot(11_000L);
        assertEquals("ModelManager", snapshot.listenerName());
        assertEquals(ReloadTiming.Phase.APPLY, snapshot.phase());
        assertEquals(2, snapshot.preparationTasks());
        assertEquals(1, snapshot.applyTasks());
        assertEquals(5_000L, snapshot.preparationNanos());
        assertEquals(5_000L, snapshot.applyNanos());
        assertEquals(10_000L, snapshot.elapsedNanos());
    }

    @Test
    void claimsSlowWarningsOnlyAfterThresholdAndRepeatInterval() {
        long second = TimeUnit.SECONDS.toNanos(1);
        ReloadTiming timing = new ReloadTiming("ShaderManager", 10L * second);

        assertTrue(timing.claimSlowWarnings(14L * second, 5L * second, 5L * second).isEmpty());
        assertEquals(1, timing.claimSlowWarnings(15L * second, 5L * second, 5L * second).size());
        assertTrue(timing.claimSlowWarnings(19L * second, 5L * second, 5L * second).isEmpty());
        assertEquals(1, timing.claimSlowWarnings(20L * second, 5L * second, 5L * second).size());
    }

    @Test
    void resetsSlowWarningAgeWhenTheActiveTaskChanges() {
        long second = TimeUnit.SECONDS.toNanos(1);
        ReloadTiming timing = new ReloadTiming("ShaderManager", 0L);

        timing.startTask(ReloadTiming.Phase.PREPARATION, 1, 10L * second);
        assertEquals(1, timing.claimSlowWarnings(15L * second, 5L * second, 5L * second).size());

        timing.finishTask(ReloadTiming.Phase.PREPARATION, 1);
        timing.startTask(ReloadTiming.Phase.PREPARATION, 2, 16L * second);
        assertTrue(timing.claimSlowWarnings(20L * second, 5L * second, 5L * second).isEmpty());
        assertEquals(1, timing.claimSlowWarnings(21L * second, 5L * second, 5L * second).size());
    }

    @Test
    void independentlyTracksConcurrentPreparationTasks() {
        long second = TimeUnit.SECONDS.toNanos(1);
        ReloadTiming timing = new ReloadTiming("ConcurrentManager", 0L);

        timing.startTask(ReloadTiming.Phase.PREPARATION, 1, 10L * second);
        timing.startTask(ReloadTiming.Phase.PREPARATION, 2, 12L * second);
        timing.finishTask(ReloadTiming.Phase.PREPARATION, 2);

        var warnings = timing.claimSlowWarnings(15L * second, 5L * second, 5L * second);
        assertEquals(1, warnings.size());
        assertEquals(ReloadTiming.Phase.PREPARATION, warnings.getFirst().phase());
        assertEquals(1, warnings.getFirst().taskIndex());
    }
}
