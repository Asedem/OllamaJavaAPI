package de.asedem.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PullRequest(
        String model,
        Boolean insecure,
        boolean stream
) {

    public PullRequest(String model) {
        this(model, null, false);
    }
}
