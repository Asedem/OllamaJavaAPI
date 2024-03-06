package de.asedem.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.Objects;

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

    @Override
    public String toString() {
        return "GenerationResponse{" +
                "model='" + model + '\'' +
                ", created_at='" + created_at + '\'' +
                ", response='" + response + '\'' +
                ", context=" + Arrays.toString(context) +
                ", done=" + done +
                ", totalDuration=" + totalDuration +
                ", loadDuration=" + loadDuration +
                ", sampleCount=" + sampleCount +
                ", sampleDuration=" + sampleDuration +
                ", promptEvalCount=" + promptEvalCount +
                ", promptEvalDuration=" + promptEvalDuration +
                ", evalCount=" + evalCount +
                ", evalDuration=" + evalDuration +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenerationResponse response1 = (GenerationResponse) o;
        return done == response1.done &&
                totalDuration == response1.totalDuration &&
                loadDuration == response1.loadDuration &&
                sampleCount == response1.sampleCount &&
                sampleDuration == response1.sampleDuration &&
                promptEvalCount == response1.promptEvalCount &&
                promptEvalDuration == response1.promptEvalDuration &&
                evalCount == response1.evalCount &&
                evalDuration == response1.evalDuration &&
                Objects.equals(model, response1.model) &&
                Objects.equals(created_at, response1.created_at) &&
                Objects.equals(response, response1.response) &&
                Arrays.equals(context, response1.context);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(model, created_at, response, done, totalDuration, loadDuration, sampleCount,
                sampleDuration, promptEvalCount, promptEvalDuration, evalCount, evalDuration);
        result = 31 * result + Arrays.hashCode(context);
        return result;
    }
}
