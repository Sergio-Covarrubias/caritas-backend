package com.caritas.backend.core.reservations.dtos;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CreateReservationRequest(
        @NotNull String userId,
        @NotNull UUID hostelId,
        @NotNull LocalDate startDate,
        LocalDate endDate,
        @NotEmpty UUID[] personIds) {
}
