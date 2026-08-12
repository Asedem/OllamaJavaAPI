package de.asedem.rest;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HttpMethodeTest {

    @Test
    void testGetString() {
        assertEquals("GET", HttpMethode.GET.get());
    }

    @Test
    void testPostString() {
        assertEquals("POST", HttpMethode.POST.get());
    }

    @Test
    void testDeleteString() {
        assertEquals("DELETE", HttpMethode.DELETE.get());
    }
}
