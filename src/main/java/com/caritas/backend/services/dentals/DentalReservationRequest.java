package com.caritas.backend.services.dentals;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public record DentalReservationRequest(
        @NotNull LocalDate orderDate,
        @NotNull Integer count) {
}
