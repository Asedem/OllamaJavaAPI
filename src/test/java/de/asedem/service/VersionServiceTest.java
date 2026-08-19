package de.asedem.service;

import de.asedem.HttpTestServer;
import de.asedem.Ollama;
import de.asedem.exception.OllamaConnectionException;
import de.asedem.model.VersionResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VersionServiceTest {

    @Test
    void testMethodCall() throws Exception {
        try (HttpTestServer server = new HttpTestServer()) {
            server.setResponse(200, """
                    {
                      "version": "0.5.1"
                    }
                    """);

            final Ollama ollama = Ollama.init("http://127.0.0.1", server.getPort());
            final VersionResponse response = ollama.version();

            assertEquals("0.5.1", response.version());

            assertEquals("GET", server.getLastMethod());
            assertEquals("/api/version", server.getLastPath());
            assertNull(server.getLastBody());
        }
    }

    @Test
    void testExceptionOnConnectionFailure() throws Exception {
        try (HttpTestServer server = new HttpTestServer()) {
            final int port = server.getPort();
            server.close();

            final Ollama ollama = Ollama.init("http://127.0.0.1", port);

            assertThrows(OllamaConnectionException.class, ollama::version);
        }
    }
}
