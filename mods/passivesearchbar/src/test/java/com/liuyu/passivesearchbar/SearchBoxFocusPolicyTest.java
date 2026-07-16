package com.liuyu.passivesearchbar;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.liuyu.passivesearchbar.SearchBoxFocusPolicy.FocusChange;

import org.junit.jupiter.api.Test;

class SearchBoxFocusPolicyTest {
    @Test
    void focusesSearchAfterAPrimaryClickInsideTheSearchBox() {
        assertEquals(FocusChange.FOCUS_SEARCH, SearchBoxFocusPolicy.changeAfterClick(0, true));
    }

    @Test
    void clearsSearchAfterAPrimaryClickOutsideTheSearchBox() {
        assertEquals(FocusChange.CLEAR_SEARCH, SearchBoxFocusPolicy.changeAfterClick(0, false));
    }

    @Test
    void keepsCurrentFocusAfterANonPrimaryClick() {
        assertEquals(FocusChange.KEEP, SearchBoxFocusPolicy.changeAfterClick(1, false));
        assertEquals(FocusChange.KEEP, SearchBoxFocusPolicy.changeAfterClick(1, true));
    }

    @Test
    void supportsOpenFocusClearAndRefocusSequence() {
        boolean focused = false;
        focused = apply(focused, SearchBoxFocusPolicy.changeAfterClick(0, true));
        assertEquals(true, focused);

        focused = apply(focused, SearchBoxFocusPolicy.changeAfterClick(0, false));
        assertEquals(false, focused);

        focused = apply(focused, SearchBoxFocusPolicy.changeAfterClick(0, true));
        assertEquals(true, focused);
    }

    private static boolean apply(boolean focused, FocusChange change) {
        return switch (change) {
            case FOCUS_SEARCH -> true;
            case CLEAR_SEARCH -> false;
            case KEEP -> focused;
        };
    }
}
