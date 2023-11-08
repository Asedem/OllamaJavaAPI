package de.asedem.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Model(
        String name,
        @JsonProperty("modified_at")
        String modifiedAt,
        String digest,
        long size
) {
}
