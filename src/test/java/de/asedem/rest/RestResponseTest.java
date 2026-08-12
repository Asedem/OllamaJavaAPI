package de.asedem.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RestResponseTest {

    @Test
    void testStatusCodeAndBody() {
        final RestResponse response = new RestResponse(200, "hello");

        assertEquals(200, response.getStatusCode());
        assertEquals("hello", response.asValueString());
    }

    @Test
    void testAsJavaObjectParsesJson() throws JsonProcessingException {
        final RestResponse response = new RestResponse(200, "{\"value\":42}");

        final Map<?, ?> map = response.asJavaObject(Map.class);

        assertEquals(42, map.get("value"));
    }

    @Test
    void testAsJavaObjectReturnsNullForNullBody() throws JsonProcessingException {
        final RestResponse response = new RestResponse(200, null);

        assertNull(response.asJavaObject(Map.class));
    }

    @Test
    void testAsJavaObjectThrowsOnInvalidJson() {
        final RestResponse response = new RestResponse(200, "not json");

        assertThrows(JsonProcessingException.class, () -> response.asJavaObject(Map.class));
    }
}
