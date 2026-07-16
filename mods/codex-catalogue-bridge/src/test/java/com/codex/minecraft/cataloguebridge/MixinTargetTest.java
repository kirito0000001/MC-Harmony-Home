package com.codex.minecraft.cataloguebridge;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

class MixinTargetTest {
    @Test
    void mixinConfigurationNamesCatalogueAndOptionalMouseTweaksTargets() {
        JsonArray clientMixins = readJson("/codex_catalogue_bridge.mixins.json").getAsJsonArray("client");
        assertTrue(clientMixins.toString().contains("NeoForgeModDataMixin"));
        assertTrue(clientMixins.toString().contains("CatalogueModListScreenMixin"));
        assertTrue(clientMixins.toString().contains("MouseTweaksConfigScreenMixin"));
    }

    @Test
    void mouseTweaksHardCodedLabelsMapToBridgeTranslationKeys() {
        assertEquals(
            "codex_catalogue_bridge.mousetweaks.options",
            MouseTweaksTranslations.keyFor("Mouse Tweaks Options").orElseThrow()
        );
        assertEquals(
            "codex_catalogue_bridge.mousetweaks.scroll_direction",
            MouseTweaksTranslations.keyFor("Scroll Direction").orElseThrow()
        );
        assertTrue(MouseTweaksTranslations.keyFor("Unknown label").isEmpty());
    }

    @Test
    void mouseTweaksConstructorRedirectHandlerIsStatic() throws Exception {
        String source = Files.readString(Path.of(
            "src/main/java/com/codex/minecraft/cataloguebridge/mixin/MouseTweaksConfigScreenMixin.java"
        ));

        assertTrue(source.contains("private static MutableComponent localizeMouseTweaksLiteral(String source)"));
    }

    private static JsonObject readJson(String resource) {
        try (InputStreamReader reader = new InputStreamReader(
            MixinTargetTest.class.getResourceAsStream(resource),
            StandardCharsets.UTF_8
        )) {
            return JsonParser.parseReader(reader).getAsJsonObject();
        } catch (Exception exception) {
            throw new AssertionError("Unable to read mixin metadata", exception);
        }
    }
}
