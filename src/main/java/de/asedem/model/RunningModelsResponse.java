package de.asedem.model;

import java.util.List;

public record RunningModelsResponse(
        List<ProcessModel> models
) {
}
