package com.caritas.backend.core.service_reservations.entities;

import java.util.UUID;

import com.caritas.backend.core.reservations.entities.ReservationEntity;
import com.caritas.backend.core.services.entities.ServiceEntity;

import jakarta.persistence.*;

@Entity
@Table(name = "service_reservations")
public class ServiceReservationEntity {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", nullable = false)
    private ReservationEntity reservation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", nullable = false)
    private ServiceEntity service;

    @Column(name = "external_reservation_id", nullable = false)
    private String externalReservationId;

    public ServiceReservationEntity() {
    }

    public ServiceReservationEntity(ReservationEntity reservation, ServiceEntity service, String externalReservationId) {
        this.reservation = reservation;
        this.service = service;
        this.externalReservationId = externalReservationId;
    }

    public UUID getId() {
        return id;
    }

    public ReservationEntity getReservation() {
        return reservation;
    }

    public ServiceEntity getService() {
        return service;
    }

    public String getExternalReservationId() {
        return externalReservationId;
    }

    public void detach() {
        if (this.reservation != null) {
            this.reservation.getServiceReservations().remove(this);
            this.reservation = null;
        }
        if (this.service != null) {
            this.service.getServiceReservations().remove(this);
            this.service = null;
        }
    }
}

