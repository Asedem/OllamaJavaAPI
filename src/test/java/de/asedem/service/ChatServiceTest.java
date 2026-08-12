package de.asedem.service;

import de.asedem.Ollama;
import de.asedem.exception.OllamaConnectionException;
import de.asedem.model.ChatRequest;
import de.asedem.model.ChatResponse;
import de.asedem.model.Message;
import de.asedem.rest.HttpMethode;
import de.asedem.rest.Rest;
import de.asedem.rest.RestResponse;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChatServiceTest {

    private final ChatRequest request = new ChatRequest(
            "llama3.2",
            List.of(new Message("user", "Why is the sky blue?", null, null, null, null))
    );

    @Test
    void testMethodCall() {

        final Ollama ollama = Ollama.initDefault();

        try (MockedStatic<Rest> utilities = Mockito.mockStatic(Rest.class)) {
            utilities.when(() -> Rest.requestSync(ollama.buildUrl("/api/chat"),
                            HttpMethode.POST, request, 10000, 30000))
                    .thenReturn(new RestResponse(200, """
                            {
                              "model": "llama3.2",
                              "created_at": "2023-12-12T14:13:43.416799Z",
                              "message": {
                                "role": "assistant",
                                "content": "Hello! How are you today?"
                              },
                              "done": true,
                              "total_duration": 5191566416,
                              "load_duration": 2154458,
                              "prompt_eval_count": 26,
                              "prompt_eval_duration": 383809000,
                              "eval_count": 298,
                              "eval_duration": 4799921000
                            }
                            """));

            final ChatResponse response = ollama.chat(request);

            assertEquals("llama3.2", response.model());
            assertEquals("assistant", response.message().role());
            assertEquals("Hello! How are you today?", response.message().content());
            assertTrue(response.done());
            assertEquals(5191566416L, response.totalDuration());
            assertEquals(4799921000L, response.evalDuration());
        }
    }

    @Test
    void testException() {

        final Ollama ollama = Ollama.initDefault();

        try (MockedStatic<Rest> utilities = Mockito.mockStatic(Rest.class)) {
            utilities.when(() -> Rest.requestSync(ollama.buildUrl("/api/chat"),
                            HttpMethode.POST, request, 10000, 30000))
                    .thenThrow(new IOException());

            assertThrows(OllamaConnectionException.class, () -> ollama.chat(request));
        }
    }
}
