package de.asedem.services;

import de.asedem.Ollama;
import de.asedem.exception.OllamaConnectionException;
import de.asedem.model.Model;
import de.asedem.rest.HttpMethode;
import de.asedem.rest.Rest;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public interface ListModelsService {

    List<Model> listModels() throws OllamaConnectionException;

    @NotNull
    default List<Model> listModels(@NotNull Ollama ollama) throws OllamaConnectionException {
        final Models models;
        try {
            models = Rest.requestSync(ollama.buildUrl("/api/tags"), HttpMethode.GET)
                    .asJavaObject(Models.class);
        } catch (IOException exception) {
            throw new OllamaConnectionException(exception);
        }
        if (models == null) return Collections.emptyList();
        return models.models();
    }

    record Models(
            List<Model> models
    ) {
    }
}
