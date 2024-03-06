package de.asedem.service;

import de.asedem.Ollama;
import de.asedem.exception.OllamaConnectionException;
import de.asedem.model.GenerationRequest;
import de.asedem.model.GenerationResponse;
import de.asedem.rest.HttpMethode;
import de.asedem.rest.Rest;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public interface GenerateService {

    @NotNull
    GenerationResponse generate(@NotNull GenerationRequest prompt);

    @NotNull
    default GenerationResponse generate(@NotNull Ollama ollama, @NotNull GenerationRequest prompt) {
        final GenerationResponse response;
        try {
            response = Rest.requestSync(ollama.buildUrl("/api/generate"),
                            HttpMethode.POST, new StreamGenerationRequest(prompt, false))
                    .asJavaObject(GenerationResponse.class);
        } catch (IOException exception) {
            throw new OllamaConnectionException(exception);
        }
        return response;
    }

    record StreamGenerationRequest(
            String model,
            String prompt,
            boolean stream
    ) {

        StreamGenerationRequest(GenerationRequest prompt, boolean stream) {
            this(
                    prompt.model(),
                    prompt.prompt(),
                    stream
            );
        }
    }
}
