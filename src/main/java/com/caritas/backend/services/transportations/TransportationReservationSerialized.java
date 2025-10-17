package com.caritas.backend.services.transportations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record TransportationReservationSerialized(UUID id, LocalDate orderDate, Integer count, String hostelName, String place, Boolean fromHostel, LocalTime pickupTime) {
    public TransportationReservationSerialized(TransportationReservationEntity reservation) {
        this(reservation.getId(), reservation.getOrderDate(), reservation.getCount(), reservation.getHostelName(), reservation.getPlace(), reservation.getFromHostel(), reservation.getPickupTime());
    }
}
