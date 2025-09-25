package com.caritas.backend.core.services.entities;

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

    @Column(name = "display_name", nullable = false)
    private String displayName;

    @Column(name = "type", nullable = false)
    private String type;

    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HostelServiceEntity> hostelServices;

    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServiceInterestEntity> serviceInterests;

    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServiceReservationEntity> serviceReservations;

    public ServiceEntity() {
    }

    public ServiceEntity(String displayName, String type) {
        this.displayName = displayName;
        this.type = type;
    }

    public UUID getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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
