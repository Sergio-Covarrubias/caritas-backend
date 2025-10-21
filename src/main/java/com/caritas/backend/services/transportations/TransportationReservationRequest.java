package com.caritas.backend.services.transportations;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.NotNull;

public record TransportationReservationRequest(
        @NotNull LocalDate orderDate,
        @NotNull Integer count,
        @NotNull String hostelName,
        @NotNull String place,
        @NotNull Boolean fromHostel,
        @NotNull LocalTime pickupTime) {
}
