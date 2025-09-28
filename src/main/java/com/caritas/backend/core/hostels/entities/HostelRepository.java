package com.caritas.backend.core.hostels.entities;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.caritas.backend.common.BaseRepository;

public interface HostelRepository extends BaseRepository<HostelEntity, UUID> {
    @Query("""
                SELECT h AS hostel,
                       (h.maxCapacity - COALESCE(res.totalPeople, 0)) AS availableSpaces
                FROM HostelEntity h
                LEFT JOIN (
                    SELECT r.hostel.id AS hostelId, SUM(r.peopleCount) AS totalPeople
                    FROM ReservationEntity r
                    WHERE r.active = true
                      AND r.startDate <= :endDate
                      AND r.endDate >= :startDate
                    GROUP BY r.hostel.id
                ) res ON res.hostelId = h.id
                LEFT JOIN h.hostelServices hs
                LEFT JOIN hs.service s
                WHERE (:filtersSize = 0 OR s.type IN :filters)
                GROUP BY h, res.totalPeople
                HAVING (:filtersSize = 0 OR COUNT(DISTINCT s.type) = :filtersSize)
                ORDER BY availableSpaces DESC
            """)
    Page<Object[]> findPaginatedHostels(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("filters") List<String> filters,
            @Param("filtersSize") long filtersSize,
            Pageable pageable);
}
