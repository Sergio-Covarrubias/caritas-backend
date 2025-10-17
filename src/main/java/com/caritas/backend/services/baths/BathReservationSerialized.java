package com.caritas.backend.services.baths;

import java.time.LocalDate;
import java.util.UUID;

public record BathReservationSerialized(UUID id, LocalDate orderDate, Integer count) {
    public BathReservationSerialized(BathReservationEntity reservation) {
        this(reservation.getId(), reservation.getOrderDate(), reservation.getCount());
    }
}
