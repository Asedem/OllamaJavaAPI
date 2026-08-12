package de.asedem.service;

import de.asedem.Ollama;
import de.asedem.exception.OllamaConnectionException;
import de.asedem.model.EmbedRequest;
import de.asedem.model.EmbedResponse;
import de.asedem.rest.HttpMethode;
import de.asedem.rest.Rest;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public interface EmbedService {

    @NotNull
    EmbedResponse embed(@NotNull EmbedRequest request);

    @NotNull
    default EmbedResponse embed(@NotNull Ollama ollama, @NotNull EmbedRequest request) {
        final EmbedResponse response;
        try {
            response = Rest.requestSync(ollama.buildUrl("/api/embed"),
                            HttpMethode.POST, request, 10000, 30000)
                    .asJavaObject(EmbedResponse.class);
        } catch (IOException exception) {
            throw new OllamaConnectionException(exception);
        }
        return response;
    }
}
