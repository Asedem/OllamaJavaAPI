package de.asedem.service;

import de.asedem.Ollama;
import de.asedem.exception.OllamaConnectionException;
import de.asedem.model.PushRequest;
import de.asedem.model.PushResponse;
import de.asedem.rest.HttpMethode;
import de.asedem.rest.Rest;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public interface PushService {

    @NotNull
    PushResponse push(@NotNull PushRequest request);

    @NotNull
    default PushResponse push(@NotNull Ollama ollama, @NotNull PushRequest request) {
        final PushResponse response;
        try {
            response = Rest.requestSync(ollama.buildUrl("/api/push"),
                            HttpMethode.POST, request, 10000, 30000)
                    .asJavaObject(PushResponse.class);
        } catch (IOException exception) {
            throw new OllamaConnectionException(exception);
        }
        return response;
    }
}
