package de.asedem.model;

public record Model(
        String name,
        String modified_at,
        String digest,
        long size
) {
}
