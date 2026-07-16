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
        CreativeModeInventoryScreen screen = (CreativeModeInventoryScreen) (Object) this;
        this.searchBox.setCanLoseFocus(true);
        if (screen.getFocused() == this.searchBox) {
            screen.setFocused(null);
        }
        this.searchBox.setFocused(false);
    }

    @Inject(method = "mouseClicked", at = @At("TAIL"))
    private void passiveSearchBar$clearFocusAfterOutsideClick(
        double mouseX,
        double mouseY,
        int button,
        CallbackInfoReturnable<Boolean> callback
    ) {
        CreativeModeInventoryScreen screen = (CreativeModeInventoryScreen) (Object) this;
        switch (SearchBoxFocusPolicy.changeAfterClick(
            button,
            this.searchBox.isMouseOver(mouseX, mouseY)
        )) {
            case FOCUS_SEARCH -> screen.setFocused(this.searchBox);
            case CLEAR_SEARCH -> {
                if (screen.getFocused() == this.searchBox) {
                    screen.setFocused(null);
                }
                this.searchBox.setFocused(false);
            }
            case KEEP -> {
            }
        }
    }
}
