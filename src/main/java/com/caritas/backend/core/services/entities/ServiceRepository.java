package com.caritas.backend.core.services.entities;

import java.util.Optional;
import java.util.UUID;

import com.caritas.backend.common.BaseRepository;

public interface ServiceRepository extends BaseRepository<ServiceEntity, UUID> {
    @Override
    default String entityName() {
        return "Service";
    }

    Optional<ServiceEntity> findByType(String type);
}
