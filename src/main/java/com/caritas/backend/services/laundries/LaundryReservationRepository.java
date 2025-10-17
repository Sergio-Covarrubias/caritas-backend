package com.caritas.backend.services.laundries;

import java.util.UUID;

import com.caritas.backend.common.BaseRepository;

public interface LaundryReservationRepository extends BaseRepository<LaundryReservationEntity, UUID> {
    @Override
    default String entityName() {
        return "Laundry Reservation";
    }
}
