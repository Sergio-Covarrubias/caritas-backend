package com.caritas.backend.core.hostels.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CreateHostelRequest(
        @NotNull String name,
        @NotNull String description,
        @NotNull Float price,
        @NotNull Integer maxCapacity,
        @NotNull String locationUrl,
        @NotNull @NotEmpty String[] imageUrls) {
}
