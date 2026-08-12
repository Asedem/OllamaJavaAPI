package de.asedem.service;

import de.asedem.Ollama;
import de.asedem.exception.OllamaConnectionException;
import de.asedem.model.ModelInfo;
import de.asedem.rest.HttpMethode;
import de.asedem.rest.Rest;
import de.asedem.rest.RestResponse;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ShowInfoServiceTest {

    @Test
    void testMethodCall() {

        final Ollama ollama = Ollama.initDefault();

        try (MockedStatic<Rest> utilities = Mockito.mockStatic(Rest.class)) {
            utilities.when(() -> Rest.requestSync(ollama.buildUrl("/api/show"), HttpMethode.POST, "llama2:latest"))
                    .thenReturn(new RestResponse(200, """
                            {
                              "license": "MIT",
                              "modelfile": "# Modelfile",
                              "parameters": "num_ctx 4096",
                              "template": "{{ .Prompt }}"
                            }
                            """));

            final ModelInfo modelInfo = ollama.showInfo("llama2:latest");

            assertEquals("MIT", modelInfo.license());
            assertEquals("# Modelfile", modelInfo.modelFile());
            assertEquals("num_ctx 4096", modelInfo.parameters());
            assertEquals("{{ .Prompt }}", modelInfo.template());
        }
    }

    @Test
    void testException() {

        final Ollama ollama = Ollama.initDefault();

        try (MockedStatic<Rest> utilities = Mockito.mockStatic(Rest.class)) {
            utilities.when(() -> Rest.requestSync(ollama.buildUrl("/api/show"), HttpMethode.POST, "llama2:latest"))
                    .thenThrow(new IOException());

            assertThrows(OllamaConnectionException.class, () -> ollama.showInfo("llama2:latest"));
        }
    }
}
