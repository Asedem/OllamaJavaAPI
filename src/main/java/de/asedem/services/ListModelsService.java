package de.asedem.services;

import de.asedem.exception.OllamaConnectionException;
import de.asedem.model.Model;
import de.asedem.rest.HttpMethode;
import de.asedem.rest.Rest;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

public class ListModelsService {

    @NotNull
    public List<Model> execute() throws OllamaConnectionException {
        final Models models;
        try {
            models = Rest.requestSync(new URL("http://localhost:11434/api/tags"), HttpMethode.GET)
                    .asJavaObject(Models.class);
        } catch (IOException exception) {
            throw new OllamaConnectionException(exception);
        }
        if (models == null) return Collections.emptyList();
        return models.models();
    }
}
