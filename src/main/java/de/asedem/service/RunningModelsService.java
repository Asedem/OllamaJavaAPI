package de.asedem.service;

import de.asedem.Ollama;
import de.asedem.exception.OllamaConnectionException;
import de.asedem.model.ProcessModel;
import de.asedem.model.RunningModelsResponse;
import de.asedem.rest.HttpMethode;
import de.asedem.rest.Rest;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public interface RunningModelsService {

    List<ProcessModel> runningModels();

    @NotNull
    default List<ProcessModel> runningModels(@NotNull Ollama ollama) {
        final RunningModelsResponse response;
        try {
            response = Rest.requestSync(ollama.buildUrl("/api/ps"), HttpMethode.GET)
                    .asJavaObject(RunningModelsResponse.class);
        } catch (IOException exception) {
            throw new OllamaConnectionException(exception);
        }
        if (response == null || response.models() == null) return Collections.emptyList();
        return response.models();
    }
}
