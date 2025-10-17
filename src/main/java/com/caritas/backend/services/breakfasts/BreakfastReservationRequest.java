package com.caritas.backend.services.breakfasts;

import java.time.LocalDate;

public record BreakfastReservationRequest(LocalDate orderDate, Integer count) {}
