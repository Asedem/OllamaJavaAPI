package de.asedem.service;

import de.asedem.Ollama;
import de.asedem.exception.OllamaConnectionException;
import de.asedem.rest.HttpMethode;
import de.asedem.rest.Rest;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public interface CopyService {

    boolean copy(@NotNull String source, @NotNull String destination) throws OllamaConnectionException;

    default boolean copy(@NotNull Ollama ollama, @NotNull String source, @NotNull String destination) throws OllamaConnectionException {
        final int statusCode;
        try {
            statusCode = Rest.requestSync(ollama.buildUrl("/api/copy"),
                            HttpMethode.POST, new CopyRequest(source, destination))
                    .getStatusCode();
        } catch (IOException exception) {
            throw new OllamaConnectionException(exception);
        }
        return statusCode == 200;
    }

    record CopyRequest(
            @NotNull String source,
            @NotNull String destination
    ) {
    }
}
