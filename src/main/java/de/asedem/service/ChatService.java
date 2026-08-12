package de.asedem.service;

import de.asedem.Ollama;
import de.asedem.exception.OllamaConnectionException;
import de.asedem.model.ChatRequest;
import de.asedem.model.ChatResponse;
import de.asedem.rest.HttpMethode;
import de.asedem.rest.Rest;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public interface ChatService {

    @NotNull
    ChatResponse chat(@NotNull ChatRequest request);

    @NotNull
    default ChatResponse chat(@NotNull Ollama ollama, @NotNull ChatRequest request) {
        final ChatResponse response;
        try {
            response = Rest.requestSync(ollama.buildUrl("/api/chat"),
                            HttpMethode.POST, request, 10000, 30000)
                    .asJavaObject(ChatResponse.class);
        } catch (IOException exception) {
            throw new OllamaConnectionException(exception);
        }
        return response;
    }
}
