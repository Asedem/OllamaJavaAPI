package de.asedem.service;

import de.asedem.HttpTestServer;
import de.asedem.Ollama;
import de.asedem.exception.OllamaConnectionException;
import de.asedem.model.ChatRequest;
import de.asedem.model.ChatResponse;
import de.asedem.model.Message;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChatServiceTest {

    private final ChatRequest request = new ChatRequest(
            "llama3.2",
            List.of(new Message("user", "Why is the sky blue?", null, null, null, null))
    );

    @Test
    void testMethodCall() throws Exception {
        try (HttpTestServer server = new HttpTestServer()) {
            server.setResponse(200, """
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
                    """);

            final Ollama ollama = Ollama.init("http://127.0.0.1", server.getPort());
            final ChatResponse response = ollama.chat(request);

            assertEquals("llama3.2", response.model());
            assertEquals("assistant", response.message().role());
            assertEquals("Hello! How are you today?", response.message().content());
            assertTrue(response.done());
            assertEquals(5191566416L, response.totalDuration());
            assertEquals(4799921000L, response.evalDuration());

            assertEquals("POST", server.getLastMethod());
            assertEquals("/api/chat", server.getLastPath());
            assertTrue(server.getLastBody().contains("\"model\":\"llama3.2\""));
            assertTrue(server.getLastBody().contains("\"stream\":false"));
        }
    }

    @Test
    void testExceptionOnConnectionFailure() throws Exception {
        try (HttpTestServer server = new HttpTestServer()) {
            final int port = server.getPort();
            server.close();

            final Ollama ollama = Ollama.init("http://127.0.0.1", port);

            assertThrows(OllamaConnectionException.class, () -> ollama.chat(request));
        }
    }
}
