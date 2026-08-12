package de.asedem.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record Message(
        String role,
        String content,
        List<String> images,
        String thinking,
        List<ToolCall> tool_calls,
        String tool_name
) {
}
