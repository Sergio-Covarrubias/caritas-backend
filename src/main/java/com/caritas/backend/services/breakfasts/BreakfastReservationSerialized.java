package com.caritas.backend.services.breakfasts;

import java.time.LocalDate;
import java.util.UUID;

public record BreakfastReservationSerialized(UUID id, LocalDate orderDate, Integer count) {
    public BreakfastReservationSerialized(BreakfastReservationEntity reservation) {
        this(reservation.getId(), reservation.getOrderDate(), reservation.getCount());
    }
}
