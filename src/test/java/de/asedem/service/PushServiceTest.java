package de.asedem.service;

import de.asedem.Ollama;
import de.asedem.exception.OllamaConnectionException;
import de.asedem.model.PushRequest;
import de.asedem.model.PushResponse;
import de.asedem.rest.HttpMethode;
import de.asedem.rest.Rest;
import de.asedem.rest.RestResponse;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class PushServiceTest {

    private final PushRequest request = new PushRequest("mattw/pygmalion:latest");

    @Test
    void testMethodCall() {

        final Ollama ollama = Ollama.initDefault();

        try (MockedStatic<Rest> utilities = Mockito.mockStatic(Rest.class)) {
            utilities.when(() -> Rest.requestSync(ollama.buildUrl("/api/push"),
                            HttpMethode.POST, request, 10000, 30000))
                    .thenReturn(new RestResponse(200, """
                            {
                              "status": "success"
                            }
                            """));

            final PushResponse response = ollama.push(request);

            assertEquals("success", response.status());
        }
    }

    @Test
    void testException() {

        final Ollama ollama = Ollama.initDefault();

        try (MockedStatic<Rest> utilities = Mockito.mockStatic(Rest.class)) {
            utilities.when(() -> Rest.requestSync(ollama.buildUrl("/api/push"),
                            HttpMethode.POST, request, 10000, 30000))
                    .thenThrow(new IOException());

            assertThrows(OllamaConnectionException.class, () -> ollama.push(request));
        }
    }
}
