package com.caritas.backend.core.reservations.dtos;

import java.time.LocalDate;
import java.util.UUID;

public record ReservationRequest(String userId, UUID hostelId, LocalDate startDate, LocalDate endDate, Boolean active) {}
