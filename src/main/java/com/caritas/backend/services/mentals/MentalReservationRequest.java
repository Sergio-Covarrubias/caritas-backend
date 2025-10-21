package com.caritas.backend.services.mentals;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public record MentalReservationRequest(
        @NotNull LocalDate orderDate,
        @NotNull Integer count) {
}
