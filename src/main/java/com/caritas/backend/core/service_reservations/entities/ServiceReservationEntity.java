package com.caritas.backend.core.service_reservations.entities;

import java.time.LocalDate;
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

    @Column(name = "order_date", nullable = false)
    private LocalDate orderDate;

    @Column(name = "cost_count")
    private Integer costCount;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private ServiceReservationState state = ServiceReservationState.PENDING;

    @Column(name = "external_reservation_id", nullable = false)
    private String externalReservationId;

    public ServiceReservationEntity() {
    }

    public ServiceReservationEntity(ReservationEntity reservation, ServiceEntity service, LocalDate orderDate, Integer costCount, ServiceReservationState state, String externalReservationId) {
        this.reservation = reservation;
        this.service = service;
        this.orderDate = orderDate;
        this.costCount = costCount;
        this.state = state;
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

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public Integer getCostCount() {
        return costCount;
    }

    public void setCostCount(Integer costCount) {
        this.costCount = costCount;
    }

    public ServiceReservationState getState() {
        return state;
    }

    public void setState(ServiceReservationState state) {
        this.state = state;
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

