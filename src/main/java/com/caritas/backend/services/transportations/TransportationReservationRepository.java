package com.caritas.backend.services.transportations;

import java.util.UUID;

import com.caritas.backend.common.BaseRepository;

public interface TransportationReservationRepository extends BaseRepository<TransportationReservationEntity, UUID> {
    @Override
    default String entityName() {
        return "Transportation Reservation";
    }
}
