package de.asedem;

import de.asedem.model.ChatRequest;
import de.asedem.model.ChatResponse;
import de.asedem.model.CreateRequest;
import de.asedem.model.CreateResponse;
import de.asedem.model.EmbedRequest;
import de.asedem.model.EmbedResponse;
import de.asedem.model.GenerationRequest;
import de.asedem.model.GenerationResponse;
import de.asedem.model.Model;
import de.asedem.model.ModelInfo;
import de.asedem.model.ProcessModel;
import de.asedem.model.PullRequest;
import de.asedem.model.PullResponse;
import de.asedem.model.PushRequest;
import de.asedem.model.PushResponse;
import de.asedem.model.VersionResponse;
import de.asedem.service.*;
import org.jetbrains.annotations.NotNull;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;

public record Ollama(
        @NotNull String host,
        int port
) implements
        ListModelsService,
        GenerateService,
        ShowInfoService,
        CopyService,
        DeleteService,
        ChatService,
        CreateService,
        PullService,
        PushService,
        EmbedService,
        RunningModelsService,
        VersionService {

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
    public List<Model> listModels() {
        return ListModelsService.super.listModels(this);
    }

    @NotNull
    @Override
    public GenerationResponse generate(@NotNull GenerationRequest prompt) {
        return GenerateService.super.generate(this, prompt);
    }

    @NotNull
    @Override
    public ModelInfo showInfo(@NotNull String modelName) {
        return ShowInfoService.super.showInfo(this, modelName);
    }

    @Override
    public boolean copy(@NotNull String source, @NotNull String destination) {
        return CopyService.super.copy(this, source, destination);
    }

    @Override
    public boolean delete(@NotNull String modelName) {
        return DeleteService.super.delete(this, modelName);
    }

    @NotNull
    @Override
    public ChatResponse chat(@NotNull ChatRequest request) {
        return ChatService.super.chat(this, request);
    }

    @NotNull
    @Override
    public CreateResponse create(@NotNull CreateRequest request) {
        return CreateService.super.create(this, request);
    }

    @NotNull
    @Override
    public PullResponse pull(@NotNull PullRequest request) {
        return PullService.super.pull(this, request);
    }

    @NotNull
    @Override
    public PushResponse push(@NotNull PushRequest request) {
        return PushService.super.push(this, request);
    }

    @NotNull
    @Override
    public EmbedResponse embed(@NotNull EmbedRequest request) {
        return EmbedService.super.embed(this, request);
    }

    @NotNull
    @Override
    public List<ProcessModel> runningModels() {
        return RunningModelsService.super.runningModels(this);
    }

    @NotNull
    @Override
    public VersionResponse version() {
        return VersionService.super.version(this);
    }
}
