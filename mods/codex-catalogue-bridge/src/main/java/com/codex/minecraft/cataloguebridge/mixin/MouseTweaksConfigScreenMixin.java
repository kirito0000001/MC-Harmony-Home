package com.codex.minecraft.cataloguebridge.mixin;

import com.codex.minecraft.cataloguebridge.MouseTweaksTranslations;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Pseudo
@Mixin(targets = "yalter.mousetweaks.ConfigScreen", remap = false)
abstract class MouseTweaksConfigScreenMixin {
    @Redirect(
        method = {"<init>", "init"},
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/network/chat/Component;literal(Ljava/lang/String;)Lnet/minecraft/network/chat/MutableComponent;"
        ),
        require = 0
    )
    private MutableComponent localizeMouseTweaksLiteral(String source) {
        return MouseTweaksTranslations.localizeLiteral(source);
    }
}
