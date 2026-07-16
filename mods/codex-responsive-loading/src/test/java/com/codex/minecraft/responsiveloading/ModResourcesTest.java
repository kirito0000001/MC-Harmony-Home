package com.codex.minecraft.responsiveloading;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class ModResourcesTest {
    @Test
    void declaresClientOnlyModAndMixinMetadata() throws IOException {
        var metadataStream = getClass().getClassLoader().getResourceAsStream("META-INF/neoforge.mods.toml");
        var mixinStream = getClass().getClassLoader().getResourceAsStream("codex_responsive_loading.mixins.json");
        assertNotNull(metadataStream);
        assertNotNull(mixinStream);

        String metadata = new String(metadataStream.readAllBytes(), StandardCharsets.UTF_8);
        String mixins = new String(mixinStream.readAllBytes(), StandardCharsets.UTF_8);
        assertTrue(metadata.contains("modId=\"codex_responsive_loading\""));
        assertTrue(metadata.contains("version=\"1.0.0-2026Reset\""));
        assertTrue(metadata.contains("side=\"CLIENT\""));
        assertTrue(metadata.contains("config=\"codex_responsive_loading.mixins.json\""));
        assertTrue(mixins.contains("ReloadableResourceManagerMixin"));
    }

    @Test
    void limitsTheModEntryPointToThePhysicalClient() throws IOException {
        String source = Files.readString(Path.of(
            "src/main/java/com/codex/minecraft/responsiveloading/CodexResponsiveLoading.java"
        ));

        assertTrue(source.contains("dist = Dist.CLIENT"));
    }
}
