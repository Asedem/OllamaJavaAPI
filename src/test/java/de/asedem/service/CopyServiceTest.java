package de.asedem.service;

import de.asedem.Ollama;
import de.asedem.exception.OllamaConnectionException;
import de.asedem.rest.HttpMethode;
import de.asedem.rest.Rest;
import de.asedem.rest.RestResponse;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class CopyServiceTest {

    @Test
    void testMethodCall() {

        final Ollama ollama = Ollama.initDefault();

        try (MockedStatic<Rest> utilities = Mockito.mockStatic(Rest.class)) {
            utilities.when(() -> Rest.requestSync(ollama.buildUrl("/api/copy"),
                            HttpMethode.POST, new CopyService.CopyRequest("llama2:latest", "llama2:copy")))
                    .thenReturn(new RestResponse(200, ""));

            assertTrue(ollama.copy("llama2:latest", "llama2:copy"));
        }
    }

    @Test
    void testFalseIfNotSuccessful() {

        final Ollama ollama = Ollama.initDefault();

        try (MockedStatic<Rest> utilities = Mockito.mockStatic(Rest.class)) {
            utilities.when(() -> Rest.requestSync(ollama.buildUrl("/api/copy"),
                            HttpMethode.POST, new CopyService.CopyRequest("llama2:latest", "llama2:copy")))
                    .thenReturn(new RestResponse(404, ""));

            assertFalse(ollama.copy("llama2:latest", "llama2:copy"));
        }
    }

    @Test
    void testException() {

        final Ollama ollama = Ollama.initDefault();

        try (MockedStatic<Rest> utilities = Mockito.mockStatic(Rest.class)) {
            utilities.when(() -> Rest.requestSync(ollama.buildUrl("/api/copy"),
                            HttpMethode.POST, new CopyService.CopyRequest("llama2:latest", "llama2:copy")))
                    .thenThrow(new IOException());

            assertThrows(OllamaConnectionException.class, () -> ollama.copy("llama2:latest", "llama2:copy"));
        }
    }
}
