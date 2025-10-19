package com.caritas.backend.core.service_reservations.dtos;

import java.time.LocalDate;
import java.util.UUID;

import com.caritas.backend.core.service_reservations.entities.ServiceReservationState;

import jakarta.validation.constraints.NotNull;

public record CreateServiceReservationRequest(
        @NotNull UUID reservationId,
        @NotNull UUID serviceId,
        @NotNull LocalDate orderDate,
        @NotNull Integer costCount,
        @NotNull ServiceReservationState state,
        @NotNull String externalReservationId) {
}
