package de.asedem.service;

import de.asedem.HttpTestServer;
import de.asedem.Ollama;
import de.asedem.exception.OllamaConnectionException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeleteServiceTest {

    @Test
    void testMethodCall() throws Exception {
        try (HttpTestServer server = new HttpTestServer()) {
            server.setResponse(200, "");

            final Ollama ollama = Ollama.init("http://127.0.0.1", server.getPort());

            assertTrue(ollama.delete("llama2:latest"));

            assertEquals("DELETE", server.getLastMethod());
            assertEquals("/api/delete", server.getLastPath());
            assertTrue(server.getLastBody().contains("\"name\":\"llama2:latest\""));
        }
    }

    @Test
    void testFalseIfNotSuccessful() throws Exception {
        try (HttpTestServer server = new HttpTestServer()) {
            server.setResponse(404, "");

            final Ollama ollama = Ollama.init("http://127.0.0.1", server.getPort());

            assertFalse(ollama.delete("llama2:latest"));
        }
    }

    @Test
    void testExceptionOnConnectionFailure() throws Exception {
        try (HttpTestServer server = new HttpTestServer()) {
            final int port = server.getPort();
            server.close();

            final Ollama ollama = Ollama.init("http://127.0.0.1", port);

            assertThrows(OllamaConnectionException.class, () -> ollama.delete("llama2:latest"));
        }
    }
}
