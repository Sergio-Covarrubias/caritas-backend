package com.caritas.backend.core.reservations.dtos;

import java.time.LocalDate;
import java.util.UUID;

import com.caritas.backend.core.reservations.entities.ReservationState;

public record GetReservationResponse(
        UUID id,
        String userId,
        UUID hostelId,
        LocalDate startDate,
        LocalDate endDate,
        ReservationState state,
        UUID[] personIds,
        UUID[] serviceIds) {
}
