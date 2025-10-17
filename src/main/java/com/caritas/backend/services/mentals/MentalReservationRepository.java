package com.caritas.backend.services.mentals;

import java.util.UUID;

import com.caritas.backend.common.BaseRepository;

public interface MentalReservationRepository extends BaseRepository<MentalReservationEntity, UUID> {
    @Override
    default String entityName() {
        return "Mental Reservation";
    }
}
