package com.caritas.backend.core.service_reservations.entities;

import java.util.UUID;

import com.caritas.backend.common.BaseRepository;

public interface ServiceReservationRepository extends BaseRepository<ServiceReservationEntity, UUID> {
    @Override
    default String entityName() {
        return "Service Reservation";
    }
}
