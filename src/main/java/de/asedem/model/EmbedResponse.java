package de.asedem.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record EmbedResponse(
        String model,
        List<List<Double>> embeddings,
        @JsonProperty("total_duration")
        long totalDuration,
        @JsonProperty("load_duration")
        long loadDuration,
        @JsonProperty("prompt_eval_count")
        long promptEvalCount
) {
}
