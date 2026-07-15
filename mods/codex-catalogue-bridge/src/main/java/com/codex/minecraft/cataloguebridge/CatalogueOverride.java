package com.codex.minecraft.cataloguebridge;

import java.util.Optional;

public record CatalogueOverride(Optional<String> alias, String homepage, String issueTracker) {
    public CatalogueOverride {
        alias = alias.filter(value -> !value.isBlank());
    }
}
