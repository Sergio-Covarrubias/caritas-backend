package com.caritas.backend.core.person_reservations.entities;

import java.util.UUID;

import com.caritas.backend.common.BaseRepository;

public interface PersonReservationRepository extends BaseRepository<PersonReservationEntity, UUID> {
    @Override
    default String entityName() {
        return "Person Reservation";
    }
}
