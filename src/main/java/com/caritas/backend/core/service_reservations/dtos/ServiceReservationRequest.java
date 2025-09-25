package com.caritas.backend.core.service_reservations.dtos;

import java.util.UUID;

public record ServiceReservationRequest(UUID reservationId, UUID serviceId, String externalReservationId) {}
