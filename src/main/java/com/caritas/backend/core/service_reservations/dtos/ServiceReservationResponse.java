package com.caritas.backend.core.service_reservations.dtos;

import java.util.UUID;

import com.caritas.backend.core.service_reservations.entities.ServiceReservationEntity;

public record ServiceReservationResponse(UUID id, UUID reservationId, UUID serviceID, String externalReservationId) {
    public ServiceReservationResponse(ServiceReservationEntity serviceReservation) {
        this(serviceReservation.getId(), serviceReservation.getReservation().getId(),
                serviceReservation.getService().getId(), serviceReservation.getExternalReservationId());
    }

    public ServiceReservationResponse(ServiceReservationEntity serviceReservation, UUID reservationId, UUID serviceID) {
        this(serviceReservation.getId(), reservationId, serviceID, serviceReservation.getExternalReservationId());
    }
}
