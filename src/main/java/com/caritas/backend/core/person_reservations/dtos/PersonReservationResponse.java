package com.caritas.backend.core.person_reservations.dtos;

import java.util.UUID;

import com.caritas.backend.core.person_reservations.entities.PersonReservationEntity;

public record PersonReservationResponse(UUID id, UUID personId, UUID reservationId) {
    public PersonReservationResponse(PersonReservationEntity personReservation) {
        this(personReservation.getId(), personReservation.getPerson().getId(), personReservation.getReservation().getId());
    }

    public PersonReservationResponse(PersonReservationEntity personReservation, UUID personId, UUID reservationId) {
        this(personReservation.getId(), personId, reservationId);
    }
}
