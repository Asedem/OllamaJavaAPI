package de.asedem.rest;

import de.asedem.HttpTestServer;
import org.junit.jupiter.api.Test;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

class RestTest {

    @Test
    void testGetRequest() throws Exception {
        try (HttpTestServer server = new HttpTestServer()) {
            server.setResponse(200, "{\"ok\":true}");

            final RestResponse response = Rest.requestSync(
                    new URL("http://127.0.0.1:" + server.getPort() + "/api/tags"), HttpMethode.GET);

            assertEquals(200, response.getStatusCode());
            assertEquals("{\"ok\":true}", response.asValueString());
            assertEquals("GET", server.getLastMethod());
            assertNull(server.getLastBody());
        }
    }

    @Test
    void testPostRequestSendsBody() throws Exception {
        try (HttpTestServer server = new HttpTestServer()) {
            server.setResponse(200, "{\"ok\":true}");

            final RestResponse response = Rest.requestSync(
                    new URL("http://127.0.0.1:" + server.getPort() + "/api/generate"),
                    HttpMethode.POST, new GenerateBody("llama2", "hi"));

            assertEquals(200, response.getStatusCode());
            assertTrue(server.getLastBody().contains("\"model\":\"llama2\""));
        }
    }

    @Test
    void testDeleteRequest() throws Exception {
        try (HttpTestServer server = new HttpTestServer()) {
            server.setResponse(200, "");

            final RestResponse response = Rest.requestSync(
                    new URL("http://127.0.0.1:" + server.getPort() + "/api/delete"),
                    HttpMethode.DELETE, new DeleteBody("llama2"));

            assertEquals(200, response.getStatusCode());
            assertEquals("DELETE", server.getLastMethod());
        }
    }

    @Test
    void testErrorStatusReturnsStatusCodeAndNoBody() throws Exception {
        try (HttpTestServer server = new HttpTestServer()) {
            server.setResponse(404, "not found");

            final RestResponse response = Rest.requestSync(
                    new URL("http://127.0.0.1:" + server.getPort() + "/api/copy"),
                    HttpMethode.POST, new CopyBody("a", "b"));

            assertEquals(404, response.getStatusCode());
            assertNull(response.asValueString());
        }
    }

    @Test
    void testThrowsOnConnectionFailure() throws Exception {
        try (HttpTestServer server = new HttpTestServer()) {
            final int port = server.getPort();
            server.close();

            assertThrows(java.io.IOException.class, () -> Rest.requestSync(
                    new URL("http://127.0.0.1:" + port + "/api/tags"), HttpMethode.GET));
        }
    }

    record GenerateBody(String model, String prompt) {
    }

    record DeleteBody(String name) {
    }

    record CopyBody(String source, String destination) {
    }
}
