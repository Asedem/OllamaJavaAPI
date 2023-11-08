package de.asedem.services;

import de.asedem.Ollama;
import de.asedem.exception.OllamaConnectionException;
import de.asedem.rest.HttpMethode;
import de.asedem.rest.Rest;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public interface DeleteService {

    boolean delete(@NotNull String modelName) throws OllamaConnectionException;

    default boolean delete(@NotNull Ollama ollama, @NotNull String modelName) throws OllamaConnectionException {
        final int statusCode;
        try {
            statusCode = Rest.requestSync(ollama.buildUrl("/api/delete"),
                            HttpMethode.POST, new DeleteRequest(modelName))
                    .getStatusCode();
        } catch (IOException exception) {
            throw new OllamaConnectionException(exception);
        }
        return statusCode == 200;
    }

    record DeleteRequest(
            @NotNull String name
    ) {
    }
}
