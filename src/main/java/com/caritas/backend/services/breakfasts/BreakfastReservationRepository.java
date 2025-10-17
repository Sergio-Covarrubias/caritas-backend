package com.caritas.backend.services.breakfasts;

import java.util.UUID;

import com.caritas.backend.common.BaseRepository;

public interface BreakfastReservationRepository extends BaseRepository<BreakfastReservationEntity, UUID> {
    @Override
    default String entityName() {
        return "Breakfast Reservation";
    }
}
