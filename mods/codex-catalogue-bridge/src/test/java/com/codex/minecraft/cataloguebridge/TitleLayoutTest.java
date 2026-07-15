package com.codex.minecraft.cataloguebridge;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class TitleLayoutTest {
    @Test
    void fittingTitleKeepsCatalogueDefaultScale() {
        assertEquals(new TitleLayout.Result(2.0F, false, 0), TitleLayout.fit(300, 800));
    }

    @Test
    void longTitleScalesBeforeItTrims() {
        assertEquals(new TitleLayout.Result(1.25F, false, 0), TitleLayout.fit(640, 800));
    }

    @Test
    void extremeTitleUsesMinimumScaleAndTrimWidth() {
        assertEquals(new TitleLayout.Result(0.75F, true, 1066), TitleLayout.fit(2000, 800));
    }
}
