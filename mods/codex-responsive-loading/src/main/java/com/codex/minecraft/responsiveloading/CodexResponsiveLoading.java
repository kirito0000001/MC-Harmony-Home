package com.codex.minecraft.responsiveloading;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;

@Mod(value = CodexResponsiveLoading.MOD_ID, dist = Dist.CLIENT)
public final class CodexResponsiveLoading {
    public static final String MOD_ID = "codex_responsive_loading";

    public CodexResponsiveLoading() {
        WindowsGhosting.disableBestEffort();
    }
}
