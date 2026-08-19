package de.asedem.service;

import de.asedem.HttpTestServer;
import de.asedem.Ollama;
import de.asedem.exception.OllamaConnectionException;
import de.asedem.model.Model;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ListModelsServiceTest {

    @Test
    void testMethodCall() throws Exception {
        try (HttpTestServer server = new HttpTestServer()) {
            server.setResponse(200, """
                    {
                      "models": [
                        {
                          "modified_at": "2023-11-06T17:27:55.025369326+01:00",
                          "name": "llama2:latest",
                          "model": "llama2:latest",
                          "digest": "fe938a131f40e6f6d40083c9f0f430a515233eb2edaa6d72eb85c50d64f2300e",
                          "size": 3825819519,
                          "details": {
                            "parent_model": "",
                            "parameter_size": "7B",
                            "quantization_level": "Q4_0",
                            "format": "gguf",
                            "family": "llama",
                            "families": null
                          }
                        }
                      ]
                    }
                    """);

            final Ollama ollama = Ollama.init("http://127.0.0.1", server.getPort());
            final List<Model> models = ollama.listModels();

            assertEquals(1, models.size());
            assertEquals("2023-11-06T17:27:55.025369326+01:00", models.getFirst().modifiedAt());
            assertEquals("llama2:latest", models.getFirst().name());
            assertEquals("llama2:latest", models.getFirst().model());
            assertEquals("fe938a131f40e6f6d40083c9f0f430a515233eb2edaa6d72eb85c50d64f2300e", models.getFirst().digest());
            assertEquals(3825819519L, models.getFirst().size());
            assertNotNull(models.getFirst().details());
            assertEquals("", models.getFirst().details().parentModel());
            assertEquals("7B", models.getFirst().details().parameterSize());
            assertEquals("Q4_0", models.getFirst().details().quantizationLevel());
            assertEquals("gguf", models.getFirst().details().format());
            assertEquals("llama", models.getFirst().details().family());
            assertNull(models.getFirst().details().families());

            assertEquals("GET", server.getLastMethod());
            assertEquals("/api/tags", server.getLastPath());
            assertNull(server.getLastBody());
        }
    }

    @Test
    void testEmptyListIfNoModelInstalled() throws Exception {
        try (HttpTestServer server = new HttpTestServer()) {
            server.setResponse(200, """
                    {
                      "models": []
                    }
                    """);

            final Ollama ollama = Ollama.init("http://127.0.0.1", server.getPort());
            final List<Model> models = ollama.listModels();

            assertEquals(0, models.size());
        }
    }

    @Test
    void testExceptionOnConnectionFailure() throws Exception {
        try (HttpTestServer server = new HttpTestServer()) {
            final int port = server.getPort();
            server.close();

            final Ollama ollama = Ollama.init("http://127.0.0.1", port);

            assertThrows(OllamaConnectionException.class, ollama::listModels);
        }
    }
}
