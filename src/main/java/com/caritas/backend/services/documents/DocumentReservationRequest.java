package com.caritas.backend.services.documents;

import java.time.LocalDate;

public record DocumentReservationRequest(LocalDate orderDate, Integer count) {}
