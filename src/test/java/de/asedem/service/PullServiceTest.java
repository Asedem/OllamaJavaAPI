package de.asedem.service;

import de.asedem.Ollama;
import de.asedem.exception.OllamaConnectionException;
import de.asedem.model.PullRequest;
import de.asedem.model.PullResponse;
import de.asedem.rest.HttpMethode;
import de.asedem.rest.Rest;
import de.asedem.rest.RestResponse;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class PullServiceTest {

    private final PullRequest request = new PullRequest("llama3.2");

    @Test
    void testMethodCall() {

        final Ollama ollama = Ollama.initDefault();

        try (MockedStatic<Rest> utilities = Mockito.mockStatic(Rest.class)) {
            utilities.when(() -> Rest.requestSync(ollama.buildUrl("/api/pull"),
                            HttpMethode.POST, request, 10000, 30000))
                    .thenReturn(new RestResponse(200, """
                            {
                              "status": "success"
                            }
                            """));

            final PullResponse response = ollama.pull(request);

            assertEquals("success", response.status());
        }
    }

    @Test
    void testException() {

        final Ollama ollama = Ollama.initDefault();

        try (MockedStatic<Rest> utilities = Mockito.mockStatic(Rest.class)) {
            utilities.when(() -> Rest.requestSync(ollama.buildUrl("/api/pull"),
                            HttpMethode.POST, request, 10000, 30000))
                    .thenThrow(new IOException());

            assertThrows(OllamaConnectionException.class, () -> ollama.pull(request));
        }
    }
}
