package de.asedem.service;

import de.asedem.HttpTestServer;
import de.asedem.Ollama;
import de.asedem.exception.OllamaConnectionException;
import de.asedem.model.CreateRequest;
import de.asedem.model.CreateResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreateServiceTest {

    private final CreateRequest request = new CreateRequest("mario", "llama3.2");

    @Test
    void testMethodCall() throws Exception {
        try (HttpTestServer server = new HttpTestServer()) {
            server.setResponse(200, """
                    {
                      "status": "success"
                    }
                    """);

            final Ollama ollama = Ollama.init("http://127.0.0.1", server.getPort());
            final CreateResponse response = ollama.create(request);

            assertEquals("success", response.status());

            assertEquals("POST", server.getLastMethod());
            assertEquals("/api/create", server.getLastPath());
            assertTrue(server.getLastBody().contains("\"model\":\"mario\""));
            assertTrue(server.getLastBody().contains("\"from\":\"llama3.2\""));
            assertTrue(server.getLastBody().contains("\"stream\":false"));
        }
    }

    @Test
    void testExceptionOnConnectionFailure() throws Exception {
        try (HttpTestServer server = new HttpTestServer()) {
            final int port = server.getPort();
            server.close();

            final Ollama ollama = Ollama.init("http://127.0.0.1", port);

            assertThrows(OllamaConnectionException.class, () -> ollama.create(request));
        }
    }
}
