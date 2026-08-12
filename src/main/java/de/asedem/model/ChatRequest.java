package de.asedem.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ChatRequest(
        String model,
        List<Message> messages,
        boolean stream,
        Object format,
        Map<String, Object> options,
        Object think,
        Object keep_alive,
        List<Tool> tools
) {

    public ChatRequest(String model, List<Message> messages) {
        this(model, messages, false, null, null, null, null, null);
    }
}
