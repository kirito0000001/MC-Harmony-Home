package com.liuyu.passivesearchbar;

public final class SearchBoxFocusPolicy {
    private SearchBoxFocusPolicy() {
    }

    public static boolean shouldClearFocus(int button, boolean pointerOverSearchBox) {
        return button == 0 && !pointerOverSearchBox;
    }
}
