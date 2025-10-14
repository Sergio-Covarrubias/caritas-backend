package com.caritas.backend.core.service_reservations.dtos;

import java.time.LocalDate;
import java.util.UUID;

import com.caritas.backend.core.service_reservations.entities.ServiceReservationState;

public record ServiceReservationRequest(UUID reservationId, UUID serviceId, LocalDate orderDate, Integer costCount, ServiceReservationState state, String externalReservationId) {}
