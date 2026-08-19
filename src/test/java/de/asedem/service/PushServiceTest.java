package de.asedem.service;

import de.asedem.HttpTestServer;
import de.asedem.Ollama;
import de.asedem.exception.OllamaConnectionException;
import de.asedem.model.PushRequest;
import de.asedem.model.PushResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PushServiceTest {

    private final PushRequest request = new PushRequest("mattw/pygmalion:latest");

    @Test
    void testMethodCall() throws Exception {
        try (HttpTestServer server = new HttpTestServer()) {
            server.setResponse(200, """
                    {
                      "status": "success"
                    }
                    """);

            final Ollama ollama = Ollama.init("http://127.0.0.1", server.getPort());
            final PushResponse response = ollama.push(request);

            assertEquals("success", response.status());

            assertEquals("POST", server.getLastMethod());
            assertEquals("/api/push", server.getLastPath());
            assertTrue(server.getLastBody().contains("\"model\":\"mattw/pygmalion:latest\""));
            assertTrue(server.getLastBody().contains("\"stream\":false"));
        }
    }

    @Test
    void testExceptionOnConnectionFailure() throws Exception {
        try (HttpTestServer server = new HttpTestServer()) {
            final int port = server.getPort();
            server.close();

            final Ollama ollama = Ollama.init("http://127.0.0.1", port);

            assertThrows(OllamaConnectionException.class, () -> ollama.push(request));
        }
    }
}
