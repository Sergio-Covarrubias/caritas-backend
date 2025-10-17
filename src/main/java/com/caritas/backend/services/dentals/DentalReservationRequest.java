package com.caritas.backend.services.dentals;

import java.time.LocalDate;

public record DentalReservationRequest(LocalDate orderDate, Integer count) {}
