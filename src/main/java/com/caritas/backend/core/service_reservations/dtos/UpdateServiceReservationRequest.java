package com.caritas.backend.core.service_reservations.dtos;

import com.caritas.backend.core.service_reservations.entities.ServiceReservationState;

public record UpdateServiceReservationRequest(ServiceReservationState state) {
}
