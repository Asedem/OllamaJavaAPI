package de.asedem.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record EmbedRequest(
        String model,
        Object input,
        Boolean truncate,
        Integer dimensions,
        Object keep_alive,
        Map<String, Object> options
) {

    public EmbedRequest(String model, Object input) {
        this(model, input, null, null, null, null);
    }
}
