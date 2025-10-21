package com.caritas.backend.services.meals;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public record MealReservationRequest(
        @NotNull LocalDate orderDate,
        @NotNull Integer count) {
}
