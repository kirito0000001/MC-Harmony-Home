package com.codex.minecraft.cataloguebridge;

import static org.junit.jupiter.api.Assertions.assertTrue;

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
}
