package com.caritas.backend.services.mentals;

import java.time.LocalDate;
import java.util.UUID;

public record MentalReservationSerialized(UUID id, LocalDate orderDate, Integer count) {
    public MentalReservationSerialized(MentalReservationEntity reservation) {
        this(reservation.getId(), reservation.getOrderDate(), reservation.getCount());
    }
}
