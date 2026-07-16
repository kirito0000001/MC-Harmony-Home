package com.liuyu.passivesearchbar;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class SearchBoxFocusPolicyTest {
    @Test
    void clearsFocusAfterAPrimaryClickOutsideTheSearchBox() {
        assertTrue(SearchBoxFocusPolicy.shouldClearFocus(0, false));
    }

    @Test
    void retainsFocusAfterAPrimaryClickInsideTheSearchBox() {
        assertFalse(SearchBoxFocusPolicy.shouldClearFocus(0, true));
    }

    @Test
    void retainsFocusAfterANonPrimaryClickOutsideTheSearchBox() {
        assertFalse(SearchBoxFocusPolicy.shouldClearFocus(1, false));
    }
}
