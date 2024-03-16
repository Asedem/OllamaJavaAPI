package de.asedem.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record Model(
        String name,
        String model,
        @JsonProperty("modified_at")
        String modifiedAt,
        String digest,
        long size,
        ModelDetails details
) {

        public record ModelDetails(
                @JsonProperty("parent_model")
                String parentModel,
                String format,
                String family,
                List<String> families,
                @JsonProperty("parameter_size")
                String parameterSize,
                @JsonProperty("quantization_level")
                String quantizationLevel
        ) {
        }
}
