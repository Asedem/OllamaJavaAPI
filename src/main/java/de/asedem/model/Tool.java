package de.asedem.model;

import java.util.Map;

public record Tool(
        String type,
        ToolDefinition function
) {
}

record ToolDefinition(
        String name,
        String description,
        Map<String, Object> parameters
) {
}
