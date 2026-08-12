package de.asedem.service;

import de.asedem.HttpTestServer;
import de.asedem.Ollama;
import de.asedem.exception.OllamaConnectionException;
import de.asedem.model.ProcessModel;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RunningModelsServiceTest {

    @Test
    void testMethodCall() throws Exception {
        try (HttpTestServer server = new HttpTestServer()) {
            server.setResponse(200, """
                    {
                      "models": [
                        {
                          "name": "mistral:latest",
                          "model": "mistral:latest",
                          "size": 5137025024,
                          "digest": "2ae6f6dd7a3dd734790bbbf58b8909a606e0e7e97e94b7604e0aa7ae4490e6d8",
                          "details": {
                            "parent_model": "",
                            "format": "gguf",
                            "family": "llama",
                            "families": ["llama"],
                            "parameter_size": "7.2B",
                            "quantization_level": "Q4_0"
                          },
                          "expires_at": "2024-06-04T14:38:31.83753-07:00",
                          "size_vram": 5137025024
                        }
                      ]
                    }
                    """);

            final Ollama ollama = Ollama.init("http://127.0.0.1", server.getPort());
            final List<ProcessModel> models = ollama.runningModels();

            assertEquals(1, models.size());
            assertEquals("mistral:latest", models.getFirst().name());
            assertEquals(5137025024L, models.getFirst().sizeVram());
            assertNotNull(models.getFirst().details());
            assertEquals("Q4_0", models.getFirst().details().quantizationLevel());

            assertEquals("GET", server.getLastMethod());
            assertEquals("/api/ps", server.getLastPath());
            assertNull(server.getLastBody());
        }
    }

    @Test
    void testEmptyListIfNoModelRunning() throws Exception {
        try (HttpTestServer server = new HttpTestServer()) {
            server.setResponse(200, """
                    {
                      "models": []
                    }
                    """);

            final Ollama ollama = Ollama.init("http://127.0.0.1", server.getPort());
            final List<ProcessModel> models = ollama.runningModels();

            assertEquals(0, models.size());
        }
    }

    @Test
    void testExceptionOnConnectionFailure() throws Exception {
        try (HttpTestServer server = new HttpTestServer()) {
            final int port = server.getPort();
            server.close();

            final Ollama ollama = Ollama.init("http://127.0.0.1", port);

            assertThrows(OllamaConnectionException.class, ollama::runningModels);
        }
    }
}
