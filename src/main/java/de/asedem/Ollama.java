package de.asedem;

import de.asedem.exception.OllamaConnectionException;
import de.asedem.model.Model;
import de.asedem.model.GenerationRequest;
import de.asedem.model.GenerationResponse;
import de.asedem.services.GenerateService;
import de.asedem.services.ListModelsService;
import org.jetbrains.annotations.NotNull;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;

public record Ollama(
        @NotNull String host,
        int port
) implements ListModelsService, GenerateService {

    public static Ollama initDefault() {
        return new Ollama("http://127.0.0.1", 11434);
    }

    public static Ollama init(@NotNull String host, int port) {
        return new Ollama(host, port);
    }

    public URL buildUrl(@NotNull String path) throws MalformedURLException {
        return URI.create(String.format("%s:%d%s", this.host(), this.port(), path)).toURL();
    }

    @NotNull
    @Override
    public List<Model> listModels() throws OllamaConnectionException {
        return ListModelsService.super.listModels(this);
    }

    @NotNull
    @Override
    public GenerationResponse generate(@NotNull GenerationRequest prompt) throws OllamaConnectionException {
        return GenerateService.super.generate(this, prompt);
    }
}
