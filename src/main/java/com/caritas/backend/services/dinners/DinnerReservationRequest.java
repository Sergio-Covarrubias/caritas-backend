package com.caritas.backend.services.dinners;

import java.time.LocalDate;

public record DinnerReservationRequest(LocalDate orderDate, Integer count) {}
