package com.caritas.backend.core.reservations.entities;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.caritas.backend.common.BaseRepository;

public interface ReservationRepository extends BaseRepository<ReservationEntity, UUID> {
    @Override
    default String entityName() {
        return "Reservation";
    }

    boolean existsByUserIdAndActiveTrue(String userId);

    Optional<ReservationEntity> findByUserIdAndActiveTrue(String userId);
    List<ReservationEntity> findAllByUserIdAndActiveFalse(String userId);
}
