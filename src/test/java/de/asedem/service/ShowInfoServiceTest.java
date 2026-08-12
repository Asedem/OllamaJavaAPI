package de.asedem.service;

import de.asedem.HttpTestServer;
import de.asedem.Ollama;
import de.asedem.exception.OllamaConnectionException;
import de.asedem.model.ModelInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShowInfoServiceTest {

    @Test
    void testMethodCall() throws Exception {
        try (HttpTestServer server = new HttpTestServer()) {
            server.setResponse(200, """
                    {
                      "license": "MIT",
                      "modelfile": "# Modelfile",
                      "parameters": "num_ctx 4096",
                      "template": "{{ .Prompt }}",
                      "details": {
                        "parent_model": "",
                        "format": "gguf",
                        "family": "llama",
                        "families": ["llama"],
                        "parameter_size": "8B",
                        "quantization_level": "Q4_0"
                      },
                      "model_info": {
                        "general.architecture": "llama"
                      },
                      "capabilities": ["completion", "vision"]
                    }
                    """);

            final Ollama ollama = Ollama.init("http://127.0.0.1", server.getPort());
            final ModelInfo modelInfo = ollama.showInfo("llama2:latest");

            assertEquals("MIT", modelInfo.license());
            assertEquals("# Modelfile", modelInfo.modelFile());
            assertEquals("num_ctx 4096", modelInfo.parameters());
            assertEquals("{{ .Prompt }}", modelInfo.template());
            assertNotNull(modelInfo.details());
            assertEquals("8B", modelInfo.details().parameterSize());
            assertEquals("llama", modelInfo.modelInfo().get("general.architecture"));
            assertEquals(2, modelInfo.capabilities().size());

            assertEquals("POST", server.getLastMethod());
            assertEquals("/api/show", server.getLastPath());
            assertTrue(server.getLastBody().contains("\"model\":\"llama2:latest\""));
            assertTrue(server.getLastBody().contains("\"verbose\":false"));
        }
    }

    @Test
    void testExceptionOnConnectionFailure() throws Exception {
        try (HttpTestServer server = new HttpTestServer()) {
            final int port = server.getPort();
            server.close();

            final Ollama ollama = Ollama.init("http://127.0.0.1", port);

            assertThrows(OllamaConnectionException.class, () -> ollama.showInfo("llama2:latest"));
        }
    }
}
