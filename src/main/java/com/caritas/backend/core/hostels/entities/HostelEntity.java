package com.caritas.backend.core.hostels.entities;

import java.util.List;
import java.util.UUID;

import com.caritas.backend.core.hostel_services.entities.HostelServiceEntity;
import com.caritas.backend.core.reservations.entities.ReservationEntity;

import jakarta.persistence.*;

@Entity
@Table(name = "hostels")
public class HostelEntity {
    
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    private String description;

    @OneToMany(mappedBy = "hostel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReservationEntity> reservations;

    @OneToMany(mappedBy = "hostel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HostelServiceEntity> hostelServices;

    public HostelEntity() {
    }

    public HostelEntity(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ReservationEntity> getReservations() {
        return reservations;
    }

    public List<HostelServiceEntity> getHostelServices() {
        return hostelServices;
    }
}
