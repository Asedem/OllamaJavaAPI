package de.asedem.service;

import de.asedem.Ollama;
import de.asedem.exception.OllamaConnectionException;
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
                                  "modified_at": "2023-11-06T17:27:55.025369326+01:00",
                                  "name": "llama2:latest",
                                  "model": "llama2:latest",
                                  "digest": "fe938a131f40e6f6d40083c9f0f430a515233eb2edaa6d72eb85c50d64f2300e",
                                  "size": 3825819519,
                                  "details": {
                                    "parent_model": "",
                                    "parameter_size": "7B",
                                    "quantization_level": "Q4_0",
                                    "format": "gguf",
                                    "family": "llama",
                                    "families": null
                                  }
                                }
                              ]
                            }
                            """));

            final List<Model> models = ollama.listModels();

            assertEquals(1, models.size());
            assertEquals("2023-11-06T17:27:55.025369326+01:00", models.getFirst().modifiedAt());
            assertEquals("llama2:latest", models.getFirst().name());
            assertEquals("llama2:latest", models.getFirst().model());
            assertEquals("fe938a131f40e6f6d40083c9f0f430a515233eb2edaa6d72eb85c50d64f2300e", models.getFirst().digest());
            assertEquals(3825819519L, models.getFirst().size());
            assertNotNull(models.getFirst().details());
            assertEquals("", models.getFirst().details().parentModel());
            assertEquals("7B", models.getFirst().details().parameterSize());
            assertEquals("Q4_0", models.getFirst().details().quantizationLevel());
            assertEquals("gguf", models.getFirst().details().format());
            assertEquals("llama", models.getFirst().details().family());
            assertNull(models.getFirst().details().families());
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
