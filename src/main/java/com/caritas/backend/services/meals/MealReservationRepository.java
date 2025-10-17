package com.caritas.backend.services.meals;

import java.util.UUID;

import com.caritas.backend.common.BaseRepository;

public interface MealReservationRepository extends BaseRepository<MealReservationEntity, UUID> {
    @Override
    default String entityName() {
        return "Meal Reservation";
    }
}
