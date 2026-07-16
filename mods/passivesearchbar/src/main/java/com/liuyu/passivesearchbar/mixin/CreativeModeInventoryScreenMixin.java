package com.liuyu.passivesearchbar.mixin;

import com.liuyu.passivesearchbar.SearchBoxFocusPolicy;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CreativeModeInventoryScreen.class)
public abstract class CreativeModeInventoryScreenMixin {
    @Shadow private EditBox searchBox;

    @Inject(method = "selectTab", at = @At("TAIL"))
    private void passiveSearchBar$clearAutomaticFocus(CallbackInfo callback) {
        this.searchBox.setCanLoseFocus(true);
        this.searchBox.setFocused(false);
    }

    @Inject(method = "mouseClicked", at = @At("TAIL"))
    private void passiveSearchBar$clearFocusAfterOutsideClick(
        double mouseX,
        double mouseY,
        int button,
        CallbackInfoReturnable<Boolean> callback
    ) {
        if (SearchBoxFocusPolicy.shouldClearFocus(button, this.searchBox.isMouseOver(mouseX, mouseY))) {
            this.searchBox.setFocused(false);
        }
    }
}
