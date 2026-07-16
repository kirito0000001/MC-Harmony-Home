package com.liuyu.passivesearchbar;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

class ModResourcesTest {
    @Test
    void declaresTheClientMixinInNeoForgeMetadata() throws IOException {
        var stream = getClass().getClassLoader().getResourceAsStream("META-INF/neoforge.mods.toml");
        assertNotNull(stream);

        var metadata = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        assertTrue(metadata.contains("modId=\"passivesearchbar\""));
        assertTrue(metadata.contains("version=\"1.0.2-2026Reset\""));
        assertTrue(metadata.contains("config=\"passivesearchbar.mixins.json\""));
    }
}
