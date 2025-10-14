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

    List<ReservationEntity> findAllByState(ReservationState state);

    boolean existsByUserIdAndState(String userId, ReservationState state);

    Optional<ReservationEntity> findByUserIdAndState(String userId, ReservationState state);
    List<ReservationEntity> findAllByUserIdAndStateNot(String userId, ReservationState state);
}
