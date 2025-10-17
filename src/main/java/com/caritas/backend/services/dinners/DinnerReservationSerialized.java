package com.caritas.backend.services.dinners;

import java.time.LocalDate;
import java.util.UUID;

public record DinnerReservationSerialized(UUID id, LocalDate orderDate, Integer count) {
    public DinnerReservationSerialized(DinnerReservationEntity reservation) {
        this(reservation.getId(), reservation.getOrderDate(), reservation.getCount());
    }
}
