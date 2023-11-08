package de.asedem.model;

public record GenerationRequest(
        String model,
        String prompt
) {
}
