package com.codex.minecraft.responsiveloading;

import java.util.List;
import net.minecraft.server.packs.resources.PreparableReloadListener;

public final class ReloadListenerWrappers {
    private ReloadListenerWrappers() {
    }

    public static List<PreparableReloadListener> wrap(List<PreparableReloadListener> listeners) {
        return listeners.stream()
            .map(InstrumentedReloadListener::new)
            .map(PreparableReloadListener.class::cast)
            .toList();
    }
}
