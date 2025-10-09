package com.caritas.backend.core.reservations.dtos;

import java.time.LocalDate;
import java.util.List;

import com.caritas.backend.core.reservations.entities.ReservationEntity;

public record UserReservationsResponse(UserReservation activeReservation, List<UserReservation> previousReservations) {
    public record UserReservation(String hostelName, LocalDate startDate, LocalDate endDate) {
        public UserReservation(ReservationEntity reservation) {
            this(reservation.getHostel().getName(), reservation.getStartDate(), reservation.getEndDate());
        }
    }
}
