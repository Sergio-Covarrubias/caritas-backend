package com.caritas.backend.services.documents;

import java.time.LocalDate;
import java.util.UUID;

public record DocumentReservationSerialized(UUID id, LocalDate orderDate, Integer count) {
    public DocumentReservationSerialized(DocumentReservationEntity reservation) {
        this(reservation.getId(), reservation.getOrderDate(), reservation.getCount());
    }
}
