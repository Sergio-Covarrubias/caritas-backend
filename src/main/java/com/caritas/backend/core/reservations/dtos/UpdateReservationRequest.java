package com.caritas.backend.core.reservations.dtos;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateReservationRequest(
        @NotBlank String userId,
        UUID hostelId,
        LocalDate startDate,
        LocalDate endDate,
        @Size(min = 1) UUID[] personIds,
        UUID[] serviceIds) {
}
