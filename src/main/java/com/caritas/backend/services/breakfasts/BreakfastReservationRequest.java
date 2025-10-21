package com.caritas.backend.services.breakfasts;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public record BreakfastReservationRequest(
        @NotNull LocalDate orderDate,
        @NotNull Integer count) {
}
