package com.caritas.backend.core.person_reservations.entities;

import java.util.UUID;

import com.caritas.backend.core.persons.entities.PersonEntity;
import com.caritas.backend.core.reservations.entities.ReservationEntity;

import jakarta.persistence.*;

@Entity
@Table(name = "person_reservations")
public class PersonReservationEntity {
    
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = false)
    private PersonEntity person;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", nullable = false)
    private ReservationEntity reservation;

    public PersonReservationEntity() {
    }

    public PersonReservationEntity(PersonEntity person, ReservationEntity reservation) {
        this.person = person;
        this.reservation = reservation;
    }

    public UUID getId() {
        return id;
    }

    public PersonEntity getPerson() {
        return person;
    }

    public ReservationEntity getReservation() {
        return reservation;
    }

    public void detach() {
        if (this.person != null) {
            this.person.getPersonReservations().remove(this);
            this.person = null;
        }
        if (this.reservation != null) {
            this.reservation.getPersonReservations().remove(this);
            this.reservation = null;
        }
    }
}
