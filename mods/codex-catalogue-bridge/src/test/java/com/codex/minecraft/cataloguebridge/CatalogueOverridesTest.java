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
    void configuredCatalogueOverrideExposesProjectLinksButNoAlias() {
        CatalogueOverride entry = CatalogueOverrides.forMod("catalogue").orElseThrow();

        assertTrue(entry.alias().isEmpty());
        assertTrue(entry.homepage().endsWith("/mods/catalogue"));
        assertTrue(entry.issueTracker().contains("title=%5BCatalogue%5D%20"));
    }

    @Test
    void configuredJeedOverrideExposesProjectLinksAndBilingualDescription() {
        CatalogueOverride entry = CatalogueOverrides.forMod("jeed").orElseThrow();

        assertTrue(entry.alias().isEmpty());
        assertEquals(
            "https://www.curseforge.com/minecraft/mc-mods/just-enough-effect-descriptions-jeed",
            entry.homepage()
        );
        assertTrue(entry.issueTracker().contains("title=%5BJEED%5D%20"));
        assertEquals(
            "Adds JEI, REI, and EMI pages that explain status effects, their sources, and their behavior.",
            readLanguage("en_us").get("fml.menu.mods.info.description.jeed").getAsString()
        );
        assertEquals(
            "为 JEI、REI 和 EMI 补充状态效果说明页，\n展示效果来源、作用与相关信息。",
            readLanguage("zh_cn").get("fml.menu.mods.info.description.jeed").getAsString()
        );
    }

    @Test
    void configuredJeiOverrideExposesOfficialPageBugTemplateAndBilingualDescription() {
        CatalogueOverride entry = CatalogueOverrides.forMod("jei").orElseThrow();

        assertTrue(entry.alias().isEmpty());
        assertEquals("https://www.curseforge.com/minecraft/mc-mods/jei", entry.homepage());
        assertTrue(entry.issueTracker().contains("title=%5BJEI%5D%20"));
        assertEquals(
            "Provides searchable item, block, recipe, and usage lookup for Minecraft and installed mods. Supports recipe transfer, bookmarks, and configurable item management tools.",
            readLanguage("en_us").get("fml.menu.mods.info.description.jei").getAsString()
        );
        assertEquals(
            "为原版与已安装模组提供物品、方块、配方和用途的搜索查询，\n支持配方转移、书签与可配置的物品管理功能。",
            readLanguage("zh_cn").get("fml.menu.mods.info.description.jei").getAsString()
        );
    }

    @Test
    void configuredJerOverrideExposesOfficialPageBugTemplateAndBilingualDescription() {
        CatalogueOverride entry = CatalogueOverrides.forMod("jeresources").orElseThrow();

        assertTrue(entry.alias().isEmpty());
        assertEquals(
            "https://www.curseforge.com/minecraft/mc-mods/just-enough-resources-jer",
            entry.homepage()
        );
        assertTrue(entry.issueTracker().contains("title=%5BJER%5D%20"));
        assertEquals(
            "Adds JEI pages for resource generation, mob drops, chest loot, village trades, and world-generation information. It only displays information and does not modify the world or loot.",
            readLanguage("en_us").get("fml.menu.mods.info.description.jeresources").getAsString()
        );
        assertEquals(
            "为 JEI 补充资源生成、生物掉落、战利品箱、村民交易和世界生成信息页，\n仅用于查询展示，不会修改世界或战利品。",
            readLanguage("zh_cn").get("fml.menu.mods.info.description.jeresources").getAsString()
        );
    }

    @Test
    void configuredJecOverrideExposesOfficialPageBugTemplateAndBilingualDescription() {
        CatalogueOverride entry = CatalogueOverrides.forMod("jecharacters").orElseThrow();

        assertTrue(entry.alias().isEmpty());
        assertEquals("https://github.com/Towdium/JustEnoughCharacters", entry.homepage());
        assertTrue(entry.issueTracker().contains("title=%5BJEC%5D%20"));
        assertEquals(
            "Adds Pinyin and Pinyin-initial search matching to JEI, and extends it to supported search interfaces from other installed mods. It is a client-side search enhancement.",
            readLanguage("en_us").get("fml.menu.mods.info.description.jecharacters").getAsString()
        );
        assertEquals(
            "为 JEI 提供全拼与拼音首字母检索，并为已兼容的其他模组搜索界面扩展同样的匹配方式。\n仅客户端生效。",
            readLanguage("zh_cn").get("fml.menu.mods.info.description.jecharacters").getAsString()
        );
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
            "在JEI中加入村民职业浏览页，\n展示每种职业及其对应的工作方块。",
            readLanguage("zh_cn").get("fml.menu.mods.info.description.justenoughprofessions").getAsString()
        );
    }

    @Test
    void catalogueDescriptionExistsInBothLanguages() {
        assertEquals(
            "Replaces the NeoForge mod list with a searchable catalogue for viewing mod details, links, filters, favorites, and update status.",
            readLanguage("en_us").get("fml.menu.mods.info.description.catalogue").getAsString()
        );
        assertEquals(
            "将 NeoForge 原版模组列表替换为可搜索的目录，\n集中查看模组信息、链接、筛选、收藏和更新状态。",
            readLanguage("zh_cn").get("fml.menu.mods.info.description.catalogue").getAsString()
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
