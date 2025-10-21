package com.caritas.backend.services.documents;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public record DocumentReservationRequest(
        @NotNull LocalDate orderDate,
        @NotNull Integer count) {
}
