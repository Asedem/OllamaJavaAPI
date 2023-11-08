package de.asedem.service;

import de.asedem.Ollama;
import de.asedem.exception.OllamaConnectionException;
import de.asedem.model.GenerationRequest;
import de.asedem.rest.HttpMethode;
import de.asedem.rest.Rest;
import de.asedem.rest.RestResponse;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class GenerateServiceTest {

    private final GenerationRequest request = new GenerationRequest(
            "llama:latest",
            "Why is the sky blue?"
    );

    @Test
    void testStreamGenerationRequestConstruction() {

        assertTrue(new GenerateService.StreamGenerationRequest(this.request, true).stream());
        assertFalse(new GenerateService.StreamGenerationRequest(this.request, false).stream());
    }

    @Test
    void testMethodCall() throws OllamaConnectionException {

        final Ollama ollama = Ollama.initDefault();

        try (MockedStatic<Rest> utilities = Mockito.mockStatic(Rest.class)) {
            utilities.when(() -> Rest.requestSync(ollama.buildUrl("/api/generate"),
                            HttpMethode.POST, new GenerateService.StreamGenerationRequest(request, false)))
                    .thenReturn(new RestResponse(200, """
                            {
                              "model": "llama2:7b",
                              "created_at": "2023-08-04T19:22:45.499127Z",
                              "response": "The sky is blue because it is the color of the sky.",
                              "context": [1, 2, 3],
                              "done": true,
                              "total_duration": 5589157167,
                              "load_duration": 3013701500,
                              "sample_count": 114,
                              "sample_duration": 81442000,
                              "prompt_eval_count": 46,
                              "prompt_eval_duration": 1160282000,
                              "eval_count": 13,
                              "eval_duration": 1325948000
                            }"""));

            assertEquals("The sky is blue because it is the color of the sky.", ollama.generate(request).response());
        }
    }
}
