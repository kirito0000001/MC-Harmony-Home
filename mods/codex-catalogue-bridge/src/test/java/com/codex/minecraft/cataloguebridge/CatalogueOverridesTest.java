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
    void configuredAppleSkinOverrideExposesOfficialPageBugTemplateAndBilingualDescription() {
        CatalogueOverride entry = CatalogueOverrides.forMod("appleskin").orElseThrow();

        assertTrue(entry.alias().isEmpty());
        assertEquals("https://github.com/squeek502/AppleSkin", entry.homepage());
        assertTrue(entry.issueTracker().contains("title=%5BAppleSkin%5D%20"));
        assertEquals(
            "Displays food hunger, saturation, exhaustion, and estimated health recovery in tooltips and HUD overlays. Optional server synchronization can provide exact food-state values.",
            readLanguage("en_us").get("fml.menu.mods.info.description.appleskin").getAsString()
        );
        assertEquals(
            "在物品提示和 HUD 叠加层中显示食物恢复的饥饿值、饱和度、疲劳值及预计生命恢复。\n可选服务器同步可提供更精确的食物状态。",
            readLanguage("zh_cn").get("fml.menu.mods.info.description.appleskin").getAsString()
        );
    }

    @Test
    void configuredMouseTweaksOverrideExposesOfficialPageBugTemplateAndBilingualDescription() {
        CatalogueOverride entry = CatalogueOverrides.forMod("mousetweaks").orElseThrow();

        assertTrue(entry.alias().isEmpty());
        assertEquals("https://www.curseforge.com/minecraft/mc-mods/mouse-tweaks", entry.homepage());
        assertTrue(entry.issueTracker().contains("title=%5BMouse%20Tweaks%5D%20"));
        assertEquals(
            "Adds mouse-driven inventory controls including drag distribution, quick movement, and scroll-wheel transfers. It uses the normal container click protocol and is client-side only.",
            readLanguage("en_us").get("fml.menu.mods.info.description.mousetweaks").getAsString()
        );
        assertEquals(
            "为背包和容器提供鼠标拖拽分配、快速转移和滚轮移动等操作。\n使用原版容器点击协议，仅客户端需要。",
            readLanguage("zh_cn").get("fml.menu.mods.info.description.mousetweaks").getAsString()
        );
    }

    @Test
    void configuredPassiveSearchBarOverrideExposesSourcePageBugTemplateAndBilingualDescription() {
        CatalogueOverride entry = CatalogueOverrides.forMod("passivesearchbar").orElseThrow();

        assertTrue(entry.alias().isEmpty());
        assertEquals(
            "https://github.com/kirito0000001/MC-Harmony-Home/tree/main/mods/passivesearchbar",
            entry.homepage()
        );
        assertTrue(entry.issueTracker().contains("title=%5BPassive%20Search%20Bar%5D%20"));
        assertEquals(
            "Prevents the Creative inventory search box from taking keyboard focus automatically. Click it when you want to search; clicking elsewhere returns focus to normal inventory controls.",
            readLanguage("en_us").get("fml.menu.mods.info.description.passivesearchbar").getAsString()
        );
        assertEquals(
            "防止创造模式物品栏打开时搜索框自动抢占键盘焦点。\n需要检索时点击搜索框输入；点击其他位置后，键盘输入会回到正常物品栏操作。",
            readLanguage("zh_cn").get("fml.menu.mods.info.description.passivesearchbar").getAsString()
        );
    }

    @Test
    void configuredDurabilityTooltipOverrideExposesOfficialPageBugTemplateAndBilingualDescription() {
        CatalogueOverride entry = CatalogueOverrides.forMod("durabilitytooltip").orElseThrow();

        assertTrue(entry.alias().isEmpty());
        assertEquals("https://www.curseforge.com/minecraft/mc-mods/durability-tooltip", entry.homepage());
        assertTrue(entry.issueTracker().contains("title=%5BDurability%20Tooltip%5D%20"));
        assertEquals(
            "Adds configurable durability values, status text, or a bar to damageable item tooltips. Runs on the client and can limit display to vanilla tools or selected mod namespaces.",
            readLanguage("en_us").get("fml.menu.mods.info.description.durabilitytooltip").getAsString()
        );
        assertEquals(
            "为可损坏物品的提示补充可配置的耐久数值、状态文字或耐久条。\n仅客户端运行，可限制为原版工具或按模组命名空间隐藏。",
            readLanguage("zh_cn").get("fml.menu.mods.info.description.durabilitytooltip").getAsString()
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
