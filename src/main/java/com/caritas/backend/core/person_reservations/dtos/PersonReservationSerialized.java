package com.caritas.backend.core.person_reservations.dtos;

import java.util.UUID;

import com.caritas.backend.core.person_reservations.entities.PersonReservationEntity;
import com.caritas.backend.core.persons.dtos.PersonSerialized;
import com.caritas.backend.core.persons.entities.PersonEntity;
import com.caritas.backend.core.reservations.dtos.ReservationSerialized;
import com.caritas.backend.core.reservations.entities.ReservationEntity;

public record PersonReservationSerialized(UUID id, PersonSerialized person, ReservationSerialized reservation) {
    public PersonReservationSerialized(PersonReservationEntity personReservation, PersonEntity person, ReservationEntity reservation) {
        this(
            personReservation.getId(), 
            person != null ? new PersonSerialized(person, null) : null, 
            reservation != null ? new ReservationSerialized(reservation, null, null, null, null, false, false): null
        );
    }
}
