package com.caritas.backend.services.laundries;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public record LaundryReservationRequest(
        @NotNull LocalDate orderDate,
        @NotNull Integer count) {
}
