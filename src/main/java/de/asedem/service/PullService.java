package de.asedem.service;

import de.asedem.Ollama;
import de.asedem.exception.OllamaConnectionException;
import de.asedem.model.PullRequest;
import de.asedem.model.PullResponse;
import de.asedem.rest.HttpMethode;
import de.asedem.rest.Rest;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public interface PullService {

    @NotNull
    PullResponse pull(@NotNull PullRequest request);

    @NotNull
    default PullResponse pull(@NotNull Ollama ollama, @NotNull PullRequest request) {
        final PullResponse response;
        try {
            response = Rest.requestSync(ollama.buildUrl("/api/pull"),
                            HttpMethode.POST, request, 10000, 30000)
                    .asJavaObject(PullResponse.class);
        } catch (IOException exception) {
            throw new OllamaConnectionException(exception);
        }
        return response;
    }
}
