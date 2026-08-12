package de.asedem;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Minimal in-process HTTP server used to exercise the real {@link de.asedem.rest.Rest}
 * client end-to-end without mocking it. Each test configures the response body/status
 * and can inspect the captured request.
 */
public class HttpTestServer implements AutoCloseable {

    private final HttpServer server;
    private volatile Response response = new Response(200, "");
    private final AtomicReference<String> lastMethod = new AtomicReference<>();
    private final AtomicReference<String> lastPath = new AtomicReference<>();
    private final AtomicReference<String> lastBody = new AtomicReference<>();

    public HttpTestServer() throws IOException {
        server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        server.createContext("/", exchange -> {
            lastMethod.set(exchange.getRequestMethod());
            lastPath.set(exchange.getRequestURI().getPath());
            final String method = exchange.getRequestMethod();
            if ("POST".equals(method) || "PUT".equals(method) || "DELETE".equals(method) || "PATCH".equals(method)) {
                try (var stream = exchange.getRequestBody()) {
                    lastBody.set(new String(stream.readAllBytes(), StandardCharsets.UTF_8));
                }
            }
            final byte[] payload = response.body().getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(response.status(), payload.length);
            try (var stream = exchange.getResponseBody()) {
                stream.write(payload);
            }
        });
        server.start();
    }

    public int getPort() {
        return server.getAddress().getPort();
    }

    public void setResponse(int status, String body) {
        this.response = new Response(status, body);
    }

    public String getLastMethod() {
        return lastMethod.get();
    }

    public String getLastPath() {
        return lastPath.get();
    }

    public String getLastBody() {
        return lastBody.get();
    }

    @Override
    public void close() {
        server.stop(0);
    }

    private record Response(int status, String body) {
    }
}
