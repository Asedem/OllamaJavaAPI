package de.asedem.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PushRequest(
        String model,
        Boolean insecure,
        boolean stream
) {

    public PushRequest(String model) {
        this(model, null, false);
    }
}
