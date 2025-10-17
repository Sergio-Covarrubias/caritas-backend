package com.caritas.backend.services.transportations;

import java.time.LocalDate;
import java.time.LocalTime;

public record TransportationReservationRequest(LocalDate orderDate, Integer count, String hostelName, String place, Boolean fromHostel, LocalTime pickupTime) {}
