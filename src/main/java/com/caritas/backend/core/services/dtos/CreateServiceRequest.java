package com.caritas.backend.core.services.dtos;

import jakarta.validation.constraints.NotNull;

public record CreateServiceRequest(
        @NotNull Float price,
        @NotNull String type) {
}
