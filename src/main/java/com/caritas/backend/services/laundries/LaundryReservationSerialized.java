package com.caritas.backend.services.laundries;

import java.time.LocalDate;
import java.util.UUID;

public record LaundryReservationSerialized(UUID id, LocalDate orderDate, Integer count) {
    public LaundryReservationSerialized(LaundryReservationEntity reservation) {
        this(reservation.getId(), reservation.getOrderDate(), reservation.getCount());
    }
}
