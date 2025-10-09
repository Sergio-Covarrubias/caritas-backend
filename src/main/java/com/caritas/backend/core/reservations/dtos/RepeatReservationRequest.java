package com.caritas.backend.core.reservations.dtos;

import java.time.LocalDate;
import java.util.UUID;

public record RepeatReservationRequest(UUID reservationId, LocalDate startDate, LocalDate endDate) { 
}
