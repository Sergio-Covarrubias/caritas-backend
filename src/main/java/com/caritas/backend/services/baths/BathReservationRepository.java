package com.caritas.backend.services.baths;

import java.util.UUID;

import com.caritas.backend.common.BaseRepository;

public interface BathReservationRepository extends BaseRepository<BathReservationEntity, UUID> {
    @Override
    default String entityName() {
        return "Bath Reservation";
    }
}
