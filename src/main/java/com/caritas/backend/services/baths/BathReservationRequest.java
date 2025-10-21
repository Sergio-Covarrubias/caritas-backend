package com.caritas.backend.services.baths;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public record BathReservationRequest(
        @NotNull LocalDate orderDate,
        @NotNull Integer count) {
}