package com.caritas.backend.services.dentals;

import java.time.LocalDate;
import java.util.UUID;

public record DentalReservationSerialized(UUID id, LocalDate orderDate, Integer count) {
    public DentalReservationSerialized(DentalReservationEntity reservation) {
        this(reservation.getId(), reservation.getOrderDate(), reservation.getCount());
    }
}
