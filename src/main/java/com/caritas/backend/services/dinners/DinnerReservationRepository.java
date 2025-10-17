package com.caritas.backend.services.dinners;

import java.util.UUID;

import com.caritas.backend.common.BaseRepository;

public interface DinnerReservationRepository extends BaseRepository<DinnerReservationEntity, UUID> {
    @Override
    default String entityName() {
        return "Dinner Reservation";
    }
}
