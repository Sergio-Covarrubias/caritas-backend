package com.caritas.backend.core.reservations.dtos;

import java.time.LocalDate;
import java.util.UUID;

import com.caritas.backend.core.reservations.entities.ReservationEntity;
import com.caritas.backend.core.reservations.entities.ReservationState;

public record ReservationResponse(UUID id, String userId, UUID hostelId, LocalDate startDate, LocalDate endDate, ReservationState state) {
    public ReservationResponse(ReservationEntity reservation) {
        this(reservation.getId(), reservation.getUser().getId(), reservation.getHostel().getId(), reservation.getStartDate(), reservation.getEndDate(), reservation.getState());
    }

    public ReservationResponse(ReservationEntity reservation, String userId, UUID hostelId) {
        this(reservation.getId(), userId, hostelId, reservation.getStartDate(), reservation.getEndDate(), reservation.getState());
    }
}
