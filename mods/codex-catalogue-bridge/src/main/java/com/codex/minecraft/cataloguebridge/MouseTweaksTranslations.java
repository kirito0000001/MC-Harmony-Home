package com.codex.minecraft.cataloguebridge;

import java.util.Map;
import java.util.Optional;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public final class MouseTweaksTranslations {
    private static final Map<String, String> KEYS = Map.ofEntries(
        Map.entry("Mouse Tweaks Options", "codex_catalogue_bridge.mousetweaks.options"),
        Map.entry("RMB Tweak", "codex_catalogue_bridge.mousetweaks.right_drag"),
        Map.entry("Wheel Tweak", "codex_catalogue_bridge.mousetweaks.wheel_tweak"),
        Map.entry("LMB Tweak With Item", "codex_catalogue_bridge.mousetweaks.left_drag_with_item"),
        Map.entry("LMB Tweak Without Item", "codex_catalogue_bridge.mousetweaks.left_drag_empty"),
        Map.entry("Wheel Tweak Search Order", "codex_catalogue_bridge.mousetweaks.search_order"),
        Map.entry("Scroll Direction", "codex_catalogue_bridge.mousetweaks.scroll_direction"),
        Map.entry("Scroll Scaling", "codex_catalogue_bridge.mousetweaks.scroll_scaling"),
        Map.entry("Debug Mode", "codex_catalogue_bridge.mousetweaks.debug_mode"),
        Map.entry("Multiple Wheel Clicks Move Multiple Items", "codex_catalogue_bridge.mousetweaks.scaling_proportional"),
        Map.entry("Always Move One Item (macOS Compatibility)", "codex_catalogue_bridge.mousetweaks.scaling_one"),
        Map.entry("Down to Push, Up to Pull", "codex_catalogue_bridge.mousetweaks.direction_down_push"),
        Map.entry("Up to Push, Down to Pull", "codex_catalogue_bridge.mousetweaks.direction_up_push"),
        Map.entry("Inventory Position Aware", "codex_catalogue_bridge.mousetweaks.direction_position"),
        Map.entry("Inventory Position Aware, Inverted", "codex_catalogue_bridge.mousetweaks.direction_position_inverted"),
        Map.entry("First to Last", "codex_catalogue_bridge.mousetweaks.order_first"),
        Map.entry("Last to First", "codex_catalogue_bridge.mousetweaks.order_last")
    );

    private MouseTweaksTranslations() {
    }

    public static Optional<String> keyFor(String source) {
        return Optional.ofNullable(KEYS.get(source));
    }

    public static MutableComponent localizeLiteral(String source) {
        return keyFor(source).map(Component::translatable).orElseGet(() -> Component.literal(source));
    }
}
