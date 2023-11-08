package de.asedem;

import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OllamaTest {

    @Test
    void testDefaultInit() throws MalformedURLException {
        final Ollama ollama = Ollama.initDefault();

        assertEquals(11434, ollama.port());
        assertEquals("http://127.0.0.1", ollama.host());

        final URL url = ollama.buildUrl("/api/test");

        assertEquals("http://127.0.0.1:11434/api/test", url.toString());
    }

    @Test
    void testCustomInit() throws MalformedURLException {
        final Ollama ollama = Ollama.init("http://localhost", 25565);

        assertEquals(25565, ollama.port());
        assertEquals("http://localhost", ollama.host());

        final URL url = ollama.buildUrl("/api/test");

        assertEquals("http://localhost:25565/api/test", url.toString());
    }
}
