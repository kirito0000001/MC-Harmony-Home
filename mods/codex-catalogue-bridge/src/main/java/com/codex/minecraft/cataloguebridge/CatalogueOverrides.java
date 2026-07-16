package com.codex.minecraft.cataloguebridge;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public final class CatalogueOverrides {
    private static final String RESOURCE = "/data/codex_catalogue_bridge/catalogue_overrides.json";
    private static final Map<String, CatalogueOverride> OVERRIDES = load();

    private CatalogueOverrides() {
    }

    public static Optional<CatalogueOverride> forMod(String modId) {
        return Optional.ofNullable(OVERRIDES.get(modId));
    }

    public static String localizedDisplayName(
        String modId,
        String fallback,
        Function<String, String> translator
    ) {
        return forMod(modId)
            .flatMap(CatalogueOverride::alias)
            .map(key -> translatedOrFallback(key, fallback, translator))
            .orElse(fallback);
    }

    private static String translatedOrFallback(
        String key,
        String fallback,
        Function<String, String> translator
    ) {
        String translated = translator.apply(key);
        return translated == null || translated.isBlank() || translated.equals(key) ? fallback : translated;
    }

    private static Map<String, CatalogueOverride> load() {
        try (InputStream stream = CatalogueOverrides.class.getResourceAsStream(RESOURCE)) {
            if (stream == null) {
                return Map.of();
            }

            try (InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
                JsonObject entries = JsonParser.parseReader(reader)
                    .getAsJsonObject()
                    .getAsJsonObject("overrides");
                if (entries == null) {
                    return Map.of();
                }

                Map<String, CatalogueOverride> parsed = new HashMap<>();
                for (Map.Entry<String, JsonElement> entry : entries.entrySet()) {
                    if (!entry.getValue().isJsonObject()) {
                        continue;
                    }
                    JsonObject value = entry.getValue().getAsJsonObject();
                    if (!value.has("homepage") || !value.has("issueTracker")) {
                        continue;
                    }

                    Optional<String> alias = value.has("alias") && !value.get("alias").isJsonNull()
                        ? Optional.of(value.get("alias").getAsString())
                        : Optional.empty();
                    parsed.put(entry.getKey(), new CatalogueOverride(
                        alias,
                        value.get("homepage").getAsString(),
                        value.get("issueTracker").getAsString()
                    ));
                }
                return Map.copyOf(parsed);
            }
        } catch (Exception ignored) {
            return Map.of();
        }
    }
}
