package com.caritas.backend.core.services.dtos;

import java.util.UUID;

import com.caritas.backend.core.services.entities.ServiceEntity;

public record ServiceResponse(UUID id, Float price, String type) {
    public ServiceResponse(ServiceEntity service) {
        this(service.getId(), service.getPrice(), service.getType());
    }
}
