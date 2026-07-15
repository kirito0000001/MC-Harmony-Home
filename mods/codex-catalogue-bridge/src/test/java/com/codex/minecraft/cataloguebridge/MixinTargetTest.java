package com.codex.minecraft.cataloguebridge;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

class MixinTargetTest {
    @Test
    void mixinConfigurationNamesTheTwoNarrowCatalogueTargets() {
        JsonArray clientMixins = readJson("/codex_catalogue_bridge.mixins.json").getAsJsonArray("client");
        assertTrue(clientMixins.toString().contains("NeoForgeModDataMixin"));
        assertTrue(clientMixins.toString().contains("CatalogueModListScreenMixin"));
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
