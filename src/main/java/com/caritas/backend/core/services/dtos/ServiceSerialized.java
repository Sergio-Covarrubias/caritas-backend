package com.caritas.backend.core.services.dtos;

import java.util.UUID;

import com.caritas.backend.core.services.entities.ServiceEntity;

public record ServiceSerialized(UUID id, Float price, String type) {
    public ServiceSerialized(ServiceEntity service) {
        this(service.getId(), service.getPrice(), service.getType());
    }
}
