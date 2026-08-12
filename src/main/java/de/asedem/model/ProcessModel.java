package de.asedem.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ProcessModel(
        String name,
        String model,
        long size,
        String digest,
        Model.ModelDetails details,
        @JsonProperty("expires_at")
        String expiresAt,
        @JsonProperty("size_vram")
        long sizeVram
) {
}
