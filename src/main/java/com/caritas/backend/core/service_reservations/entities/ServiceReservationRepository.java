package com.caritas.backend.core.service_reservations.entities;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.caritas.backend.common.BaseRepository;

public interface ServiceReservationRepository extends BaseRepository<ServiceReservationEntity, UUID> {
    @Override
    default String entityName() {
        return "Service Reservation";
    }

    @Query("""
                SELECT s.type, COUNT(sr)
                FROM ServiceReservationEntity sr
                JOIN sr.service s
                WHERE YEAR(sr.orderDate) = :year
                GROUP BY s.type
            """)
    List<Object[]> countServiceReservationsByType(@Param("year") int year);
}
