package de.asedem.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ChatResponse(
        String model,
        String created_at,
        Message message,
        boolean done,
        @JsonProperty("done_reason")
        String doneReason,
        int[] context,
        @JsonProperty("total_duration")
        long totalDuration,
        @JsonProperty("load_duration")
        long loadDuration,
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
