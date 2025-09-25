package com.caritas.backend.core.services.dtos;

import java.util.UUID;

import com.caritas.backend.core.services.entities.ServiceEntity;

public record ServiceResponse(UUID id, String displayName, String type) {
    public ServiceResponse(ServiceEntity service) {
        this(service.getId(), service.getDisplayName(), service.getType());
    }
}
