package com.codex.minecraft.cataloguebridge;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

class CatalogueOverridesTest {
    @Test
    void configuredJepOverridesExposeLinksButNoAlias() {
        CatalogueOverride entry = CatalogueOverrides.forMod("justenoughprofessions").orElseThrow();

        assertTrue(entry.alias().isEmpty());
        assertTrue(entry.homepage().endsWith("/mods/justenoughprofessions"));
        assertTrue(entry.issueTracker().contains("title=%5BJust%20Enough%20Professions%5D%20"));
    }

    @Test
    void unknownModsDoNotReceiveOverrides() {
        assertTrue(CatalogueOverrides.forMod("minecraft").isEmpty());
    }

    @Test
    void jepCatalogueDescriptionExistsInBothLanguages() {
        assertEquals(
            "Adds a JEI profession browser that shows each villager profession and its matching job-site blocks.",
            readLanguage("en_us").get("fml.menu.mods.info.description.justenoughprofessions").getAsString()
        );
        assertEquals(
            "在 JEI 中加入村民职业浏览页，展示每种职业及其对应的工作方块。",
            readLanguage("zh_cn").get("fml.menu.mods.info.description.justenoughprofessions").getAsString()
        );
    }

    private static JsonObject readLanguage(String language) {
        String resource = "/assets/codex_catalogue_bridge/lang/" + language + ".json";
        try (InputStreamReader reader = new InputStreamReader(
            CatalogueOverridesTest.class.getResourceAsStream(resource),
            StandardCharsets.UTF_8
        )) {
            return JsonParser.parseReader(reader).getAsJsonObject();
        } catch (Exception exception) {
            throw new AssertionError("Unable to read " + resource, exception);
        }
    }
}
