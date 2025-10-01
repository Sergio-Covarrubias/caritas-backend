package com.caritas.backend.core.reservations.dtos;

import java.time.LocalDate;
import java.util.UUID;

import com.caritas.backend.core.reservations.entities.ReservationEntity;

public record ReservationResponse(UUID id, String userId, UUID hostelId, LocalDate startDate, LocalDate endDate, Boolean active) {
    public ReservationResponse(ReservationEntity reservation) {
        this(reservation.getId(), reservation.getUser().getId(), reservation.getHostel().getId(), reservation.getStartDate(), reservation.getEndDate(), reservation.getActive());
    }

    public ReservationResponse(ReservationEntity reservation, String userId, UUID hostelId) {
        this(reservation.getId(), userId, hostelId, reservation.getStartDate(), reservation.getEndDate(), reservation.getActive());
    }
}
