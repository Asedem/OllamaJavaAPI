package de.asedem.service;

import de.asedem.HttpTestServer;
import de.asedem.Ollama;
import de.asedem.exception.OllamaConnectionException;
import de.asedem.model.PullRequest;
import de.asedem.model.PullResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PullServiceTest {

    private final PullRequest request = new PullRequest("llama3.2");

    @Test
    void testMethodCall() throws Exception {
        try (HttpTestServer server = new HttpTestServer()) {
            server.setResponse(200, """
                    {
                      "status": "success"
                    }
                    """);

            final Ollama ollama = Ollama.init("http://127.0.0.1", server.getPort());
            final PullResponse response = ollama.pull(request);

            assertEquals("success", response.status());

            assertEquals("POST", server.getLastMethod());
            assertEquals("/api/pull", server.getLastPath());
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

            assertThrows(OllamaConnectionException.class, () -> ollama.pull(request));
        }
    }
}
