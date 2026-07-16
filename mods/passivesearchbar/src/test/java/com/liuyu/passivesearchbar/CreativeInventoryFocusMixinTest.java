package com.liuyu.passivesearchbar;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class CreativeInventoryFocusMixinTest {
    private static final Path MIXIN_SOURCE = Path.of(
        "src/main/java/com/liuyu/passivesearchbar/mixin/CreativeModeInventoryScreenMixin.java"
    );

    @Test
    void doesNotDispatchTheSearchBoxClickBeforeVanillaHandlesIt() throws IOException {
        String source = Files.readString(MIXIN_SOURCE);

        assertFalse(source.contains("this.searchBox.mouseClicked("));
    }

    @Test
    void clearsOutsideFocusOnlyAfterVanillaClickProcessing() throws IOException {
        String source = Files.readString(MIXIN_SOURCE);

        assertTrue(source.contains("@At(\"TAIL\")"));
        assertTrue(source.contains("this.searchBox.isMouseOver(mouseX, mouseY)"));
    }
}
