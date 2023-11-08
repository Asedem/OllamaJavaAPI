package de.asedem.service;

import de.asedem.model.GenerationRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class GenerateServiceTest {

    @Test
    void testStreamGenerationRequestConstruction() {
        final GenerationRequest request = new GenerationRequest(
                "llama:latest",
                "A Test Prompt"
        );

        assertTrue(new GenerateService.StreamGenerationRequest(request, true).stream());
        assertFalse(new GenerateService.StreamGenerationRequest(request, false).stream());
    }
}
