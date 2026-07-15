package com.codex.minecraft.cataloguebridge;

public final class TitleLayout {
    public static final float DEFAULT_SCALE = 2.0F;
    public static final float MIN_SCALE = 0.75F;

    private TitleLayout() {
    }

    public static Result fit(int titleWidth, int availablePixels) {
        if (titleWidth <= 0 || titleWidth * DEFAULT_SCALE <= availablePixels) {
            return new Result(DEFAULT_SCALE, false, 0);
        }

        float requestedScale = (float) availablePixels / titleWidth;
        if (requestedScale >= MIN_SCALE) {
            return new Result(requestedScale, false, 0);
        }

        return new Result(MIN_SCALE, true, (int) (availablePixels / MIN_SCALE));
    }

    public record Result(float scale, boolean trim, int trimWidth) {
    }
}
