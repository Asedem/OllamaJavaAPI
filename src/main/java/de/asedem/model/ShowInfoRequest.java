package de.asedem.model;

public record ShowInfoRequest(
        String model,
        boolean verbose
) {
}
