package com.caritas.backend.core.reservations.entities;

import java.util.UUID;

import com.caritas.backend.common.BaseRepository;

public interface ReservationRepository extends BaseRepository<ReservationEntity, UUID> {
    @Override
    default String entityName() {
        return "Reservation";
    }
}
