package de.asedem.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CreateRequest(
        String model,
        String from,
        Map<String, String> files,
        Map<String, String> adapters,
        String template,
        String renderer,
        String parser,
        Object license,
        String system,
        Map<String, Object> parameters,
        List<Message> messages,
        boolean stream,
        String quantize
) {

    public CreateRequest(String model, String from) {
        this(model, from, null, null, null, null, null, null, null, null, null, false, null);
    }
}
