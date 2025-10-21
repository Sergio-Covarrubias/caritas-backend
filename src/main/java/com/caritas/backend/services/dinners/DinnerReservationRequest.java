package com.caritas.backend.services.dinners;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public record DinnerReservationRequest(
        @NotNull LocalDate orderDate,
        @NotNull Integer count) {
}
