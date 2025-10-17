package com.caritas.backend.services.meals;

import java.time.LocalDate;

public record MealReservationRequest(LocalDate orderDate, Integer count) {}
