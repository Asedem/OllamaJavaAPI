package de.asedem.service;

import de.asedem.Ollama;
import de.asedem.exception.OllamaConnectionException;
import de.asedem.model.CreateRequest;
import de.asedem.model.CreateResponse;
import de.asedem.rest.HttpMethode;
import de.asedem.rest.Rest;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public interface CreateService {

    @NotNull
    CreateResponse create(@NotNull CreateRequest request);

    @NotNull
    default CreateResponse create(@NotNull Ollama ollama, @NotNull CreateRequest request) {
        final CreateResponse response;
        try {
            response = Rest.requestSync(ollama.buildUrl("/api/create"),
                            HttpMethode.POST, request, 10000, 30000)
                    .asJavaObject(CreateResponse.class);
        } catch (IOException exception) {
            throw new OllamaConnectionException(exception);
        }
        return response;
    }
}
