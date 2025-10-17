package com.caritas.backend.services.laundries;

import java.time.LocalDate;

public record LaundryReservationRequest(LocalDate orderDate, Integer count) {}
