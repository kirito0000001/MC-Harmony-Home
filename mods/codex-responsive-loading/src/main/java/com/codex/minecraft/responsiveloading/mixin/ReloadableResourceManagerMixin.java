package com.codex.minecraft.responsiveloading.mixin;

import com.codex.minecraft.responsiveloading.ReloadListenerWrappers;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ReloadInstance;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleReloadInstance;
import net.minecraft.util.Unit;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ReloadableResourceManager.class)
abstract class ReloadableResourceManagerMixin {
    @Redirect(
        method = "createReload",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/packs/resources/SimpleReloadInstance;create(Lnet/minecraft/server/packs/resources/ResourceManager;Ljava/util/List;Ljava/util/concurrent/Executor;Ljava/util/concurrent/Executor;Ljava/util/concurrent/CompletableFuture;Z)Lnet/minecraft/server/packs/resources/ReloadInstance;"
        )
    )
    private static ReloadInstance codexResponsiveLoading$wrapListeners(
        ResourceManager resourceManager,
        List<PreparableReloadListener> listeners,
        Executor preparationExecutor,
        Executor applyExecutor,
        CompletableFuture<Unit> initialTask,
        boolean profile
    ) {
        return SimpleReloadInstance.create(
            resourceManager,
            ReloadListenerWrappers.wrap(listeners),
            preparationExecutor,
            applyExecutor,
            initialTask,
            profile
        );
    }
}
