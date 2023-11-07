package de.asedem.services;

import de.asedem.model.OllamaPrompt;

record OllamaPromptExtended(
        String model,
        String prompt,
        boolean stream
) {

    OllamaPromptExtended(OllamaPrompt prompt) {
        this(
                prompt.model(),
                prompt.prompt(),
                false
        );
    }
}

