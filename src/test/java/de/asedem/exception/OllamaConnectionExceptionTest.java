package de.asedem.exception;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class OllamaConnectionExceptionTest {

    @Test
    void testDefaultInit() {
        final OllamaConnectionException ollamaConnectionException = new OllamaConnectionException(new IOException("Test"));

        assertInstanceOf(IOException.class, ollamaConnectionException.getBaseException());
        assertEquals("Test", ollamaConnectionException.getBaseException().getMessage());
    }
}
