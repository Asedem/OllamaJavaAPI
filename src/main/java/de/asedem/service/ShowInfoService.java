package de.asedem.service;

import de.asedem.Ollama;
import de.asedem.exception.OllamaConnectionException;
import de.asedem.model.ModelInfo;
import de.asedem.rest.HttpMethode;
import de.asedem.rest.Rest;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public interface ShowInfoService {

    @NotNull
    ModelInfo showInfo(@NotNull String modelName);

    @NotNull
    default ModelInfo showInfo(@NotNull Ollama ollama, @NotNull String modelName) {
        final ModelInfo modelInfo;
        try {
            modelInfo = Rest.requestSync(ollama.buildUrl("/api/show"), HttpMethode.POST, modelName)
                    .asJavaObject(ModelInfo.class);
        } catch (IOException exception) {
            throw new OllamaConnectionException(exception);
        }
        return modelInfo;
    }
}
