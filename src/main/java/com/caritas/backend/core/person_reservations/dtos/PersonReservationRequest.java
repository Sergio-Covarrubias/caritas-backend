package com.caritas.backend.core.person_reservations.dtos;

import java.util.UUID;

public record PersonReservationRequest(UUID personId, UUID reservationId) {}
