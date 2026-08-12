package de.asedem.model;

import java.util.Map;

public record ToolCall(
        ToolFunction function
) {
}

record ToolFunction(
        String name,
        Map<String, Object> arguments
) {
}
