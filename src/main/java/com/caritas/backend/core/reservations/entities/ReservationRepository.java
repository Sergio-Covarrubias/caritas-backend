package com.caritas.backend.core.reservations.entities;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.caritas.backend.common.BaseRepository;
import com.caritas.backend.core.service_reservations.entities.ServiceReservationEntity;
import com.caritas.backend.core.service_reservations.entities.ServiceReservationState;

public interface ReservationRepository extends BaseRepository<ReservationEntity, UUID> {
    @Override
    default String entityName() {
        return "Reservation";
    }

    List<ReservationEntity> findAllByState(ReservationState state);

    boolean existsByUserIdAndState(String userId, ReservationState state);

    Optional<ReservationEntity> findByUserIdAndState(String userId, ReservationState state);

    List<ReservationEntity> findAllByUserIdAndStateNot(String userId, ReservationState state);

    @Query("""
                SELECT MONTH(r.startDate), COUNT(r)
                FROM ReservationEntity r
                WHERE YEAR(r.startDate) = :year
                GROUP BY MONTH(r.startDate)
                ORDER BY MONTH(r.startDate)
            """)
    List<Object[]> countReservationsByMonth(@Param("year") int year);

    @Query("""
                SELECT MONTH(r.startDate), COUNT(pr)
                FROM ReservationEntity r
                JOIN r.personReservations pr
                WHERE YEAR(r.startDate) = :year
                GROUP BY MONTH(r.startDate)
                ORDER BY MONTH(r.startDate)
            """)
    List<Object[]> countPersonsByMonth(@Param("year") int year);

    @Query("""
                SELECT MONTH(r.startDate), r.state, COUNT(r)
                FROM ReservationEntity r
                WHERE YEAR(r.startDate) = :year
                GROUP BY MONTH(r.startDate), r.state
                ORDER BY MONTH(r.startDate)
            """)
    List<Object[]> countReservationsByMonthAndState(@Param("year") int year);

    @Query("""
                SELECT sr
                FROM ReservationEntity r
                JOIN r.serviceReservations sr
                JOIN sr.service s
                WHERE r.user.id = :userId
                  AND r.state = :reservationState
                  AND s.type = :serviceName
                  AND sr.state = :serviceState
            """)
    Optional<ServiceReservationEntity> findPendingServiceReservationByUserIdAndType(
            @Param("userId") String userId, String serviceName,  ReservationState reservationState, ServiceReservationState serviceState);

}
