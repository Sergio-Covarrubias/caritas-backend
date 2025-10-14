package com.caritas.backend.core.reservations.dtos;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.caritas.backend.core.reservations.entities.ReservationEntity;
import com.caritas.backend.core.reservations.entities.ReservationState;

public record UserReservationsResponse(UserReservation activeReservation, List<UserReservation> previousReservations) {
    public record UserReservation(UUID id, String hostelName, LocalDate startDate, LocalDate endDate, ReservationState state) {
        public UserReservation(ReservationEntity reservation) {
            this(reservation.getId(), reservation.getHostel().getName(), reservation.getStartDate(), reservation.getEndDate(), reservation.getState());
        }
    }
}
