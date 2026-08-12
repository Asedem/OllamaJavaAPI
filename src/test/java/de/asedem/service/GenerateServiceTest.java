package de.asedem.service;

import de.asedem.HttpTestServer;
import de.asedem.Ollama;
import de.asedem.exception.OllamaConnectionException;
import de.asedem.model.GenerationRequest;
import de.asedem.model.GenerationResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GenerateServiceTest {

    private final GenerationRequest request = new GenerationRequest(
            "llama2:latest",
            "Why is the sky blue?"
    );

    @Test
    void testMethodCall() throws Exception {
        try (HttpTestServer server = new HttpTestServer()) {
            server.setResponse(200, """
                    {
                      "model": "llama2:7b",
                      "created_at": "2023-08-04T19:22:45.499127Z",
                      "response": "The sky is blue because it is the color of the sky.",
                      "context": [1, 2, 3],
                      "done": true,
                      "total_duration": 5589157167,
                      "load_duration": 3013701500,
                      "sample_count": 114,
                      "sample_duration": 81442000,
                      "prompt_eval_count": 46,
                      "prompt_eval_duration": 1160282000,
                      "eval_count": 13,
                      "eval_duration": 1325948000
                    }
                    """);

            final Ollama ollama = Ollama.init("http://127.0.0.1", server.getPort());
            final GenerationResponse response = ollama.generate(request);

            assertEquals("llama2:7b", response.model());
            assertEquals("The sky is blue because it is the color of the sky.", response.response());
            assertEquals(5589157167L, response.totalDuration());
            assertEquals(3013701500L, response.loadDuration());
            assertEquals(114L, response.sampleCount());
            assertEquals(81442000L, response.sampleDuration());
            assertEquals(46L, response.promptEvalCount());
            assertEquals(1160282000L, response.promptEvalDuration());
            assertEquals(13L, response.evalCount());
            assertEquals(1325948000L, response.evalDuration());

            assertEquals("POST", server.getLastMethod());
            assertEquals("/api/generate", server.getLastPath());
            assertTrue(server.getLastBody().contains("\"model\":\"llama2:latest\""));
            assertTrue(server.getLastBody().contains("\"prompt\":\"Why is the sky blue?\""));
            assertTrue(server.getLastBody().contains("\"stream\":false"));
        }
    }

    @Test
    void testExceptionOnConnectionFailure() throws Exception {
        try (HttpTestServer server = new HttpTestServer()) {
            final int port = server.getPort();
            server.close();

            final Ollama ollama = Ollama.init("http://127.0.0.1", port);

            assertThrows(OllamaConnectionException.class, () -> ollama.generate(request));
        }
    }
}
