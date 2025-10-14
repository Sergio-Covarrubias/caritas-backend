package com.caritas.backend.core.reservations.dtos;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.caritas.backend.core.hostels.dtos.HostelSerialized;
import com.caritas.backend.core.hostels.entities.HostelEntity;
import com.caritas.backend.core.person_reservations.dtos.PersonReservationSerialized;
import com.caritas.backend.core.person_reservations.entities.PersonReservationEntity;
import com.caritas.backend.core.reservations.entities.ReservationEntity;
import com.caritas.backend.core.reservations.entities.ReservationState;
import com.caritas.backend.core.service_reservations.dtos.ServiceReservationSerialized;
import com.caritas.backend.core.service_reservations.entities.ServiceReservationEntity;
import com.caritas.backend.core.users.dtos.UserSerialized;
import com.caritas.backend.core.users.entities.UserEntity;

public record ReservationSerialized(
        UUID id,
        UserSerialized user,
        HostelSerialized hostel,
        LocalDate startDate,
        LocalDate endDate,
        ReservationState state,
        PersonReservationSerialized[] personReservations,
        ServiceReservationSerialized[] serviceReservations) {
    public ReservationSerialized(ReservationEntity reservation, UserEntity user, HostelEntity hostel, List<PersonReservationEntity> personsReservations,
            List<ServiceReservationEntity> serviceReservations, Boolean withPersons, Boolean withServices) {
        this(
                reservation.getId(),
                user != null ? new UserSerialized(user) : null,
                hostel != null ? new HostelSerialized(hostel, null, null) : null,
                reservation.getStartDate(),
                reservation.getEndDate(),
                reservation.getState(),
                personsReservations != null ? personsReservations.stream().map(personsReservation -> new PersonReservationSerialized(personsReservation, withPersons ? personsReservation.getPerson() : null, null))
                        .toArray(PersonReservationSerialized[]::new) : null,
                serviceReservations != null ? serviceReservations.stream().map(serviceReservation -> new ServiceReservationSerialized(serviceReservation, null, withServices ? serviceReservation.getService() : null))
                        .toArray(ServiceReservationSerialized[]::new) : null);
    }
}
