package de.asedem.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public record ModelInfo(
        String license,
        @JsonProperty("modelfile")
        String modelFile,
        String parameters,
        String template,
        Model.ModelDetails details,
        @JsonProperty("model_info")
        Map<String, Object> modelInfo,
        List<String> capabilities,
        @JsonProperty("projector_info")
        Map<String, Object> projectorInfo
) {
}
