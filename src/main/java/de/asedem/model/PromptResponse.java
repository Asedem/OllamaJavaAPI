package de.asedem.model;

public record PromptResponse(
        String model,
        String created_at,
        String response,
        int[] context,
        boolean done,
        long total_duration,
        long load_duration,
        long sample_count,
        long sample_duration,
        long prompt_eval_count,
        long prompt_eval_duration,
        long eval_count,
        long eval_duration
) {
}
