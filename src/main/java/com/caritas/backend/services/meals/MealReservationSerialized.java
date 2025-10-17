package com.caritas.backend.services.meals;

import java.time.LocalDate;
import java.util.UUID;

public record MealReservationSerialized(UUID id, LocalDate orderDate, Integer count) {
    public MealReservationSerialized(MealReservationEntity reservation) {
        this(reservation.getId(), reservation.getOrderDate(), reservation.getCount());
    }
}
