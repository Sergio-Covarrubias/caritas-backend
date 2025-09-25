package com.caritas.backend.core.service_interests.entities;

import java.util.UUID;

import com.caritas.backend.core.reservations.entities.ReservationEntity;
import com.caritas.backend.core.services.entities.ServiceEntity;

import jakarta.persistence.*;

@Entity
@Table(name = "service_interests")
public class ServiceInterestEntity {
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

    public ServiceInterestEntity() {
    }

    public ServiceInterestEntity(ReservationEntity reservation, ServiceEntity service) {
        this.reservation = reservation;
        this.service = service;
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

    public void detach() {
        if (this.reservation != null) {
            this.reservation.getServiceInterests().remove(this);
            this.reservation = null;
        }
        if (this.service != null) {
            this.service.getServiceInterests().remove(this);
            this.service = null;
        }
    }
}
