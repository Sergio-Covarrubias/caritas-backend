package com.caritas.backend.core.service_interests.dtos;

import java.util.UUID;

import com.caritas.backend.core.service_interests.entities.ServiceInterestEntity;

public record ServiceInterestResponse(UUID id, UUID reservationId, UUID serviceId) {
    public ServiceInterestResponse(ServiceInterestEntity serviceInterest) {
        this(serviceInterest.getId(), serviceInterest.getReservation().getId(), serviceInterest.getService().getId());
    }

    public ServiceInterestResponse(ServiceInterestEntity serviceInterest, UUID reservationId, UUID serviceId) {
        this(serviceInterest.getId(), reservationId, serviceId);
    }
}
