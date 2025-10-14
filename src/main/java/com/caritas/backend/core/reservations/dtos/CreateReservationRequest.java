package com.caritas.backend.core.reservations.dtos;

import java.time.LocalDate;
import java.util.UUID;

import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record CreateReservationRequest(
        @NotBlank String userId,
        @NonNull UUID hostelId,
        @NonNull LocalDate startDate,
        @NonNull LocalDate endDate,
        @NotEmpty UUID[] personIds) {
}
