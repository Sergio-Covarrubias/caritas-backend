package com.caritas.backend.services.mentals;

import java.time.LocalDate;

public record MentalReservationRequest(LocalDate orderDate, Integer count) {}
