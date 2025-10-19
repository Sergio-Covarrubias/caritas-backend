package com.caritas.backend.core.reservations.dtos;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record RepeatReservationRequest(
    @NotNull UUID reservationId,
    @NotNull LocalDate startDate,
    @NotNull LocalDate endDate) { 
}
