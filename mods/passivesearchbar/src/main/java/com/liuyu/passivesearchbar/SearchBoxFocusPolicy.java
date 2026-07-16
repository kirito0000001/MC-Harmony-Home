package com.liuyu.passivesearchbar;

public final class SearchBoxFocusPolicy {
    public enum FocusChange {
        FOCUS_SEARCH,
        CLEAR_SEARCH,
        KEEP
    }

    private SearchBoxFocusPolicy() {
    }

    public static FocusChange changeAfterClick(int button, boolean pointerOverSearchBox) {
        if (button != 0) {
            return FocusChange.KEEP;
        }
        return pointerOverSearchBox ? FocusChange.FOCUS_SEARCH : FocusChange.CLEAR_SEARCH;
    }
}
