package com.codex.minecraft.cataloguebridge;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.junit.jupiter.api.Test;

class CatalogueOverridesTest {
    private static final Map<String, String> REVIEWED_TITLE_KEYS = Map.ofEntries(
        Map.entry("justenoughprofessions", "codex_catalogue_bridge.title.justenoughprofessions"),
        Map.entry("catalogue", "codex_catalogue_bridge.title.catalogue"),
        Map.entry("jeed", "codex_catalogue_bridge.title.jeed"),
        Map.entry("jei", "codex_catalogue_bridge.title.jei"),
        Map.entry("jeresources", "codex_catalogue_bridge.title.jeresources"),
        Map.entry("jecharacters", "codex_catalogue_bridge.title.jecharacters"),
        Map.entry("appleskin", "codex_catalogue_bridge.title.appleskin"),
        Map.entry("mousetweaks", "codex_catalogue_bridge.title.mousetweaks"),
        Map.entry("passivesearchbar", "codex_catalogue_bridge.title.passivesearchbar"),
        Map.entry("durabilitytooltip", "codex_catalogue_bridge.title.durabilitytooltip")
    );

    private static final Map<String, String> REVIEWED_HOMEPAGES = Map.ofEntries(
        Map.entry("justenoughprofessions", "https://github.com/kirito0000001/MC-Harmony-Home/tree/main/mods/justenoughprofessions"),
        Map.entry("catalogue", "https://github.com/kirito0000001/MC-Harmony-Home/tree/main/mods/catalogue"),
        Map.entry("jeed", "https://www.curseforge.com/minecraft/mc-mods/just-enough-effect-descriptions-jeed"),
        Map.entry("jei", "https://www.curseforge.com/minecraft/mc-mods/jei"),
        Map.entry("jeresources", "https://www.curseforge.com/minecraft/mc-mods/just-enough-resources-jer"),
        Map.entry("jecharacters", "https://github.com/Towdium/JustEnoughCharacters"),
        Map.entry("appleskin", "https://github.com/squeek502/AppleSkin"),
        Map.entry("mousetweaks", "https://www.curseforge.com/minecraft/mc-mods/mouse-tweaks"),
        Map.entry("passivesearchbar", "https://github.com/kirito0000001/MC-Harmony-Home/tree/main/mods/passivesearchbar"),
        Map.entry("durabilitytooltip", "https://www.curseforge.com/minecraft/mc-mods/durability-tooltip")
    );

    @Test
    void reviewedModsExposeBilingualTitlesStructuredDescriptionsAndLinks() {
        JsonObject english = readLanguage("en_us");
        JsonObject chinese = readLanguage("zh_cn");

        for (Map.Entry<String, String> reviewed : REVIEWED_TITLE_KEYS.entrySet()) {
            String modId = reviewed.getKey();
            String titleKey = reviewed.getValue();
            CatalogueOverride entry = CatalogueOverrides.forMod(modId).orElseThrow();

            assertEquals(titleKey, entry.alias().orElseThrow(), modId + " title override");
            assertEquals(REVIEWED_HOMEPAGES.get(modId), entry.homepage(), modId + " homepage");
            assertTrue(entry.issueTracker().contains("title=%5B"), modId + " issue title");
            assertTrue(entry.issueTracker().contains("labels=bug"), modId + " issue label");

            assertTrue(english.has(titleKey), modId + " English title");
            assertTrue(chinese.has(titleKey), modId + " Chinese title");
            assertTrue(chinese.get(titleKey).getAsString().contains("（"), modId + " bilingual Chinese title");

            String descriptionKey = "fml.menu.mods.info.description." + modId;
            assertStructuredDescription(english.get(descriptionKey).getAsString(), modId + " English description");
            assertStructuredDescription(chinese.get(descriptionKey).getAsString(), modId + " Chinese description");
        }
    }

    @Test
    void mouseTweaksDescriptionExplainsConcreteWheelAndDragBehavior() {
        String english = readLanguage("en_us")
            .get("fml.menu.mods.info.description.mousetweaks")
            .getAsString();
        String chinese = readLanguage("zh_cn")
            .get("fml.menu.mods.info.description.mousetweaks")
            .getAsString();

        assertTrue(english.contains("scroll"));
        assertTrue(english.contains("drag"));
        assertTrue(english.contains("container"));
        assertTrue(chinese.contains("滚轮"));
        assertTrue(chinese.contains("拖拽"));
        assertTrue(chinese.contains("放入"));
        assertTrue(chinese.contains("取出"));
    }

    @Test
    void localizedDisplayNameUsesTranslationAndFallsBackToMetadataName() {
        assertEquals(
            "鼠标物品栏优化（Mouse Tweaks）",
            CatalogueOverrides.localizedDisplayName(
                "mousetweaks",
                "Mouse Tweaks",
                key -> "鼠标物品栏优化（Mouse Tweaks）"
            )
        );
        assertEquals(
            "Mouse Tweaks",
            CatalogueOverrides.localizedDisplayName("mousetweaks", "Mouse Tweaks", key -> key)
        );
        assertEquals(
            "Unknown Mod",
            CatalogueOverrides.localizedDisplayName("unknown", "Unknown Mod", key -> "Unexpected")
        );
    }

    @Test
    void unknownModsDoNotReceiveOverrides() {
        assertTrue(CatalogueOverrides.forMod("minecraft").isEmpty());
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

    private static void assertStructuredDescription(String description, String message) {
        assertTrue(description.contains("\n\n"), message + " should contain paragraph breaks");
        assertTrue(description.length() >= 140, message + " should explain operation and result");
    }
}
