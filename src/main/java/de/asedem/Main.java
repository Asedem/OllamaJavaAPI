package de.asedem;

import de.asedem.exception.OllamaConnectionException;
import de.asedem.model.OllamaPrompt;

public class Main {
    public static void main(String[] args) throws OllamaConnectionException {
        System.out.println(Ollama.generate(new OllamaPrompt("llama2:latest", "")).response());
    }
}