package com.caritas.backend.core.reservations.dtos;

import java.time.LocalDate;
import java.util.UUID;

public record CreateReservationResponse(
        UUID id,
        String userId,
        UUID hostelId,
        LocalDate startDate,
        LocalDate endDate,
        UUID[] personReservationIds,
        UUID[] serviceInterestsIds) {
}
