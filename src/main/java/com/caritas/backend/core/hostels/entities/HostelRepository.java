package com.caritas.backend.core.hostels.entities;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.caritas.backend.common.BaseRepository;

import com.caritas.backend.core.reservations.entities.ReservationState;

public interface HostelRepository extends BaseRepository<HostelEntity, UUID> {
    @Override
    default String entityName() {
        return "Hostel";
    }

    @Query("""
        SELECT h
        FROM HostelEntity h
        JOIN h.hostelServices hs
        JOIN hs.service s
        WHERE h IN :hostels
          AND s.type IN :serviceTypes
        GROUP BY h
        HAVING COUNT(DISTINCT s.type) = :#{#serviceTypes.size()}
    """)
    List<HostelEntity> filterHostelsByServices(
        @Param("hostels") List<HostelEntity> hostels,
        @Param("serviceTypes") List<String> serviceTypes
    );

    @Query("""
        SELECT h,
               (h.maxCapacity - COALESCE(COUNT(pr), 0)) AS availableSpaces
        FROM HostelEntity h
        LEFT JOIN h.reservations r
        LEFT JOIN r.personReservations pr
        WHERE h IN :hostels
          AND (r IS NULL OR (r.startDate <= :endDate AND (r.endDate IS NULL OR r.endDate >= :startDate) AND r.state = :activeState))
        GROUP BY h
        ORDER BY availableSpaces DESC
    """)
    List<Object[]> calculateAvailabilityForHostels(
        @Param("hostels") List<HostelEntity> hostels,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate,
        @Param("activeState") ReservationState activeState
    );
}
