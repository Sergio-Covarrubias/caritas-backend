package com.caritas.backend.core.reservations.dtos;

import java.time.LocalDate;
import java.util.UUID;

import com.caritas.backend.core.reservations.entities.ReservationEntity;

public record GetReservationsDashboard(ReservationBody[] pendingReservation, ReservationBody[] activeReservations) {
    public record ReservationBody(UUID reservationId, String userFullName, String hostelName, Integer peopleCount, LocalDate startDate, LocalDate endDate) {
        public ReservationBody(ReservationEntity reservation) {
            this(
                reservation.getId(), 
                reservation.getUser().getFirstName() + " " + reservation.getUser().getLastName(), 
                reservation.getHostel().getName(),
                reservation.getPersonReservations().size(),
                reservation.getStartDate(),
                reservation.getEndDate());
        }
    }
}
