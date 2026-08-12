package de.asedem.service;

import de.asedem.Ollama;
import de.asedem.exception.OllamaConnectionException;
import de.asedem.model.EmbedRequest;
import de.asedem.model.EmbedResponse;
import de.asedem.rest.HttpMethode;
import de.asedem.rest.Rest;
import de.asedem.rest.RestResponse;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class EmbedServiceTest {

    private final EmbedRequest request = new EmbedRequest("all-minilm", "Why is the sky blue?");

    @Test
    void testMethodCall() {

        final Ollama ollama = Ollama.initDefault();

        try (MockedStatic<Rest> utilities = Mockito.mockStatic(Rest.class)) {
            utilities.when(() -> Rest.requestSync(ollama.buildUrl("/api/embed"),
                            HttpMethode.POST, request, 10000, 30000))
                    .thenReturn(new RestResponse(200, """
                            {
                              "model": "all-minilm",
                              "embeddings": [
                                [0.010071029, -0.0017594862, 0.05007221]
                              ],
                              "total_duration": 14143917,
                              "load_duration": 1019500,
                              "prompt_eval_count": 8
                            }
                            """));

            final EmbedResponse response = ollama.embed(request);

            assertEquals("all-minilm", response.model());
            assertEquals(1, response.embeddings().size());
            assertEquals(3, response.embeddings().getFirst().size());
            assertEquals(14143917L, response.totalDuration());
            assertEquals(8L, response.promptEvalCount());
        }
    }

    @Test
    void testException() {

        final Ollama ollama = Ollama.initDefault();

        try (MockedStatic<Rest> utilities = Mockito.mockStatic(Rest.class)) {
            utilities.when(() -> Rest.requestSync(ollama.buildUrl("/api/embed"),
                            HttpMethode.POST, request, 10000, 30000))
                    .thenThrow(new IOException());

            assertThrows(OllamaConnectionException.class, () -> ollama.embed(request));
        }
    }
}
