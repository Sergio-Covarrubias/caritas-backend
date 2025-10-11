package com.caritas.backend.core.services.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.caritas.backend.core.hostel_services.entities.HostelServiceEntity;
import com.caritas.backend.core.service_interests.entities.ServiceInterestEntity;
import com.caritas.backend.core.service_reservations.entities.ServiceReservationEntity;

import jakarta.persistence.*;

@Entity
@Table(name = "services")
public class ServiceEntity {
    
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "price", nullable = false)
    private Float price;

    @Column(name = "type", nullable = false)
    private String type;

    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HostelServiceEntity> hostelServices = new ArrayList<>();

    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServiceInterestEntity> serviceInterests = new ArrayList<>();

    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServiceReservationEntity> serviceReservations = new ArrayList<>();

    public ServiceEntity() {
    }

    public ServiceEntity(Float price, String type) {
        this.price = price;
        this.type = type;
    }

    public UUID getId() {
        return id;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<HostelServiceEntity> getHostelServices() {
        return hostelServices;
    }

    public List<ServiceInterestEntity> getServiceInterests() {
        return serviceInterests;
    }

    public List<ServiceReservationEntity> getServiceReservations() {
        return serviceReservations;
    }
}
