package com.caritas.backend.core.person_reservations.dtos;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record CreatePersonReservationRequest(
        @NotNull UUID personId,
        @NotNull UUID reservationId) {
}
