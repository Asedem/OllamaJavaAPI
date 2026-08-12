package de.asedem.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RestResponse {

    private final int statusCode;
    private final String value;
    static final ObjectMapper mapper = new ObjectMapper();

    public RestResponse(int statusCode, @Nullable String value) {
        this.statusCode = statusCode;
        this.value = value;
    }

    /**
     * Gets the statusCode of the response
     *
     * @return the statusCode
     */
    public int getStatusCode() {
        return this.statusCode;
    }

    /**
     * Get the JSON value the is saved in the response as a String
     *
     * @return the JSON value the is saved in the response as a String
     */
    public String asValueString() {
        return this.value;
    }

    /**
     * Get the JSON value the is saved in the response as a java object
     *
     * @param targetClass The class of the java object
     * @param <T>         The class where the response should be mapped to
     * @return The java object if the response can be mapped
     * @throws JsonProcessingException if the response can't be mapped to the class
     */
    public <T> T asJavaObject(Class<T> targetClass) throws JsonProcessingException {

        return this.value == null ? null : mapper.readValue(this.value, targetClass);
    }
}
