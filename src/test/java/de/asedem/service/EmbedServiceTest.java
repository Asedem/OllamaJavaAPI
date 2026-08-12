package de.asedem.service;

import de.asedem.HttpTestServer;
import de.asedem.Ollama;
import de.asedem.exception.OllamaConnectionException;
import de.asedem.model.EmbedRequest;
import de.asedem.model.EmbedResponse;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmbedServiceTest {

    private final EmbedRequest request = new EmbedRequest("all-minilm", "Why is the sky blue?");

    @Test
    void testMethodCall() throws Exception {
        try (HttpTestServer server = new HttpTestServer()) {
            server.setResponse(200, """
                    {
                      "model": "all-minilm",
                      "embeddings": [
                        [0.010071029, -0.0017594862, 0.05007221]
                      ],
                      "total_duration": 14143917,
                      "load_duration": 1019500,
                      "prompt_eval_count": 8
                    }
                    """);

            final Ollama ollama = Ollama.init("http://127.0.0.1", server.getPort());
            final EmbedResponse response = ollama.embed(request);

            assertEquals("all-minilm", response.model());
            assertEquals(1, response.embeddings().size());
            assertEquals(3, response.embeddings().getFirst().size());
            assertEquals(14143917L, response.totalDuration());
            assertEquals(8L, response.promptEvalCount());

            assertEquals("POST", server.getLastMethod());
            assertEquals("/api/embed", server.getLastPath());
            assertTrue(server.getLastBody().contains("\"model\":\"all-minilm\""));
            assertTrue(server.getLastBody().contains("\"input\":\"Why is the sky blue?\""));
        }
    }

    @Test
    void testExceptionOnConnectionFailure() throws Exception {
        try (HttpTestServer server = new HttpTestServer()) {
            final int port = server.getPort();
            server.close();

            final Ollama ollama = Ollama.init("http://127.0.0.1", port);

            assertThrows(OllamaConnectionException.class, () -> ollama.embed(request));
        }
    }
}
