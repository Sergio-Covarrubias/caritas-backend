package com.caritas.backend.core.reservations.dtos;

import java.time.LocalDate;
import java.util.UUID;

public record GetReservationResponse(
        UUID id,
        String userId,
        UUID hostelId,
        LocalDate startDate,
        LocalDate endDate,
        UUID[] personIds,
        UUID[] serviceIds) {
}
