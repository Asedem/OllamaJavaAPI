package de.asedem;

import de.asedem.exception.OllamaConnectionException;
import de.asedem.model.Model;
import de.asedem.model.OllamaPrompt;
import de.asedem.model.PromptResponse;
import de.asedem.services.GenerateService;
import de.asedem.services.ListModelsService;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Ollama {

    private static final GenerateService GENERATE_SERVICE = new GenerateService();
    private static final ListModelsService LIST_MODELS_SERVICE = new ListModelsService();

    @NotNull
    public static PromptResponse generate(@NotNull final OllamaPrompt prompt) throws OllamaConnectionException {
        return GENERATE_SERVICE.execute(prompt);
    }

    @NotNull
    public static List<Model> listModels() throws OllamaConnectionException {
        return LIST_MODELS_SERVICE.execute();
    }
}
