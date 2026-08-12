package de.asedem.rest;

import org.jetbrains.annotations.NotNull;

public enum HttpMethode {

    GET("GET"),
    POST("POST"),
    DELETE("DELETE");

    private final String methode;

    HttpMethode(@NotNull final String methode) {
        this.methode = methode;
    }

    /**
     * Get the string from the request methode
     *
     * @return the string from the request methode
     */
    @NotNull
    public String get() {
        return methode;
    }
}
