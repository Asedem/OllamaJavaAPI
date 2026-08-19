package de.asedem.service;

import de.asedem.HttpTestServer;
import de.asedem.Ollama;
import de.asedem.exception.OllamaConnectionException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CopyServiceTest {

    @Test
    void testMethodCall() throws Exception {
        try (HttpTestServer server = new HttpTestServer()) {
            server.setResponse(200, "");

            final Ollama ollama = Ollama.init("http://127.0.0.1", server.getPort());

            assertTrue(ollama.copy("llama2:latest", "llama2:copy"));

            assertEquals("POST", server.getLastMethod());
            assertEquals("/api/copy", server.getLastPath());
            assertTrue(server.getLastBody().contains("\"source\":\"llama2:latest\""));
            assertTrue(server.getLastBody().contains("\"destination\":\"llama2:copy\""));
        }
    }

    @Test
    void testFalseIfNotSuccessful() throws Exception {
        try (HttpTestServer server = new HttpTestServer()) {
            server.setResponse(404, "");

            final Ollama ollama = Ollama.init("http://127.0.0.1", server.getPort());

            assertFalse(ollama.copy("llama2:latest", "llama2:copy"));
        }
    }

    @Test
    void testExceptionOnConnectionFailure() throws Exception {
        try (HttpTestServer server = new HttpTestServer()) {
            final int port = server.getPort();
            server.close();

            final Ollama ollama = Ollama.init("http://127.0.0.1", port);

            assertThrows(OllamaConnectionException.class, () -> ollama.copy("llama2:latest", "llama2:copy"));
        }
    }
}
