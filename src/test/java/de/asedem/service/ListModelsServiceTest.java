package de.asedem.service;

import de.asedem.Ollama;
import de.asedem.exception.OllamaConnectionException;
import de.asedem.model.GenerationRequest;
import de.asedem.model.GenerationResponse;
import de.asedem.model.Model;
import de.asedem.rest.HttpMethode;
import de.asedem.rest.Rest;
import de.asedem.rest.RestResponse;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ListModelsServiceTest {

    @Test
    void testMethodCall() {

        final Ollama ollama = Ollama.initDefault();

        try (MockedStatic<Rest> utilities = Mockito.mockStatic(Rest.class)) {
            utilities.when(() -> Rest.requestSync(ollama.buildUrl("/api/tags"), HttpMethode.GET))
                    .thenReturn(new RestResponse(200, """
                            {
                              "models": [
                                {
                                  "name": "codellama:13b",
                                  "modified_at": "2023-11-04T14:56:49.277302595-07:00",
                                  "size": 7365960935,
                                  "digest": "9f438cb9cd581fc025612d27f7c1a6669ff83a8bb0ed86c94fcf4c5440555697"
                                }
                              ]
                            }
                            """));

            final List<Model> models = ollama.listModels();

            assertEquals(1, models.size());
            assertEquals("codellama:13b", models.getFirst().name());
            assertEquals("2023-11-04T14:56:49.277302595-07:00", models.getFirst().modifiedAt());
            assertEquals(7365960935L, models.getFirst().size());
            assertEquals("9f438cb9cd581fc025612d27f7c1a6669ff83a8bb0ed86c94fcf4c5440555697", models.getFirst().digest());
        }
    }

    @Test
    void testEmptyListIfNoModelInstalled() {

        final Ollama ollama = Ollama.initDefault();

        try (MockedStatic<Rest> utilities = Mockito.mockStatic(Rest.class)) {
            utilities.when(() -> Rest.requestSync(ollama.buildUrl("/api/tags"), HttpMethode.GET))
                    .thenReturn(new RestResponse(200, """
                            {
                              "models": []
                            }
                            """));

            final List<Model> models = ollama.listModels();

            assertEquals(0, models.size());
        }
    }

    @Test
    void testException() {

        final Ollama ollama = Ollama.initDefault();

        try (MockedStatic<Rest> utilities = Mockito.mockStatic(Rest.class)) {
            utilities.when(() -> Rest.requestSync(ollama.buildUrl("/api/tags"), HttpMethode.GET))
                    .thenThrow(new IOException());

            assertThrows(OllamaConnectionException.class, ollama::listModels);
        }
    }
}
