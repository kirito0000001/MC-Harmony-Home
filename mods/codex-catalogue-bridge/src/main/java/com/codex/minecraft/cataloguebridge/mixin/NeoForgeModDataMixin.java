package com.codex.minecraft.cataloguebridge.mixin;

import com.codex.minecraft.cataloguebridge.CatalogueOverride;
import com.codex.minecraft.cataloguebridge.CatalogueOverrides;
import com.mrcrayfish.catalogue.client.NeoForgeModData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NeoForgeModData.class)
abstract class NeoForgeModDataMixin {
    @Shadow
    public abstract String getModId();

    @Inject(method = "getDisplayName", at = @At("HEAD"), cancellable = true)
    private void overrideDisplayName(CallbackInfoReturnable<String> callback) {
        CatalogueOverrides.forMod(getModId())
            .flatMap(CatalogueOverride::alias)
            .ifPresent(callback::setReturnValue);
    }

    @Inject(method = "getHomepage", at = @At("HEAD"), cancellable = true)
    private void overrideHomepage(CallbackInfoReturnable<String> callback) {
        CatalogueOverrides.forMod(getModId())
            .map(CatalogueOverride::homepage)
            .ifPresent(callback::setReturnValue);
    }

    @Inject(method = "getIssueTracker", at = @At("HEAD"), cancellable = true)
    private void overrideIssueTracker(CallbackInfoReturnable<String> callback) {
        CatalogueOverrides.forMod(getModId())
            .map(CatalogueOverride::issueTracker)
            .ifPresent(callback::setReturnValue);
    }
}
