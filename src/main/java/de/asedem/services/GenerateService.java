package de.asedem.services;

import de.asedem.exception.OllamaConnectionException;
import de.asedem.model.OllamaPrompt;
import de.asedem.model.PromptResponse;
import de.asedem.rest.HttpMethode;
import de.asedem.rest.Rest;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;

public class GenerateService {

    @NotNull
    public PromptResponse execute(final OllamaPrompt prompt) throws OllamaConnectionException {
        final PromptResponse response;
        try {
            response = Rest.requestSync(new URL("http://localhost:11434/api/generate"),
                            HttpMethode.POST, new OllamaPromptExtended(prompt))
                    .asJavaObject(PromptResponse.class);
        } catch (IOException exception) {
            throw new OllamaConnectionException(exception);
        }
        return response;
    }
}
