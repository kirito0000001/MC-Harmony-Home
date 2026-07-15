package com.codex.minecraft.cataloguebridge.mixin;

import com.codex.minecraft.cataloguebridge.TitleLayout;
import com.mrcrayfish.catalogue.client.screen.CatalogueModListScreen;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(CatalogueModListScreen.class)
abstract class CatalogueModListScreenMixin {
    @Redirect(
        method = "drawModInfo(Lnet/minecraft/client/gui/GuiGraphics;IIF)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Ljava/lang/String;III)I",
            ordinal = 0
        )
    )
    private int drawFittedTitle(GuiGraphics graphics, Font font, String title, int x, int y, int color) {
        int contentLeft = Math.round(graphics.pose().last().pose().m30());
        int availableWidth = Math.max(1, graphics.guiWidth() - contentLeft - 10);
        TitleLayout.Result layout = TitleLayout.fit(font.width(title), availableWidth);
        String displayedTitle = layout.trim()
            ? font.plainSubstrByWidth(title, Math.max(0, layout.trimWidth() - font.width("..."))) + "..."
            : title;

        PoseStack poseStack = graphics.pose();
        poseStack.pushPose();
        float additionalScale = layout.scale() / TitleLayout.DEFAULT_SCALE;
        poseStack.scale(additionalScale, additionalScale, 1.0F);
        int result = graphics.drawString(font, displayedTitle, x, y, color);
        poseStack.popPose();
        return result;
    }
}
