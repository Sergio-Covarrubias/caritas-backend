package com.caritas.backend.core.reservations.dtos;

import java.time.LocalDate;

import com.caritas.backend.core.reservations.entities.ReservationState;

public record UpdateReservationRequest(
        LocalDate startDate,
        LocalDate endDate,
        ReservationState state) {
}
