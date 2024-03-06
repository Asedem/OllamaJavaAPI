package de.asedem.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GenerationResponse(
        String model,
        String created_at,
        String response,
        int[] context,
        boolean done,
        @JsonProperty("total_duration")
        long totalDuration,
        @JsonProperty("load_duration")
        long loadDuration,
        @JsonProperty("sample_count")
        long sampleCount,
        @JsonProperty("sample_duration")
        long sampleDuration,
        @JsonProperty("prompt_eval_count")
        long promptEvalCount,
        @JsonProperty("prompt_eval_duration")
        long promptEvalDuration,
        @JsonProperty("eval_count")
        long evalCount,
        @JsonProperty("eval_duration")
        long evalDuration
) {
}
