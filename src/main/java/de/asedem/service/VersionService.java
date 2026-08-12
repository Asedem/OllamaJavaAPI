package de.asedem.service;

import de.asedem.Ollama;
import de.asedem.exception.OllamaConnectionException;
import de.asedem.model.VersionResponse;
import de.asedem.rest.HttpMethode;
import de.asedem.rest.Rest;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public interface VersionService {

    @NotNull
    VersionResponse version();

    @NotNull
    default VersionResponse version(@NotNull Ollama ollama) {
        final VersionResponse response;
        try {
            response = Rest.requestSync(ollama.buildUrl("/api/version"), HttpMethode.GET)
                    .asJavaObject(VersionResponse.class);
        } catch (IOException exception) {
            throw new OllamaConnectionException(exception);
        }
        return response;
    }
}
