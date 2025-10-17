package com.caritas.backend.services.dentals;

import java.util.UUID;

import com.caritas.backend.common.BaseRepository;

public interface DentalReservationRepository extends BaseRepository<DentalReservationEntity, UUID> {
    @Override
    default String entityName() {
        return "Dental Reservation";
    }
}
