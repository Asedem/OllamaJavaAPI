package de.asedem.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ModelInfo(
        String license,
        @JsonProperty("modelfile")
        String modelFile,
        String parameters,
        String template
) {
}
