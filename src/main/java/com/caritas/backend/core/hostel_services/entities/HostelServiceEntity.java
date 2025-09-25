package com.caritas.backend.core.hostel_services.entities;

import java.util.UUID;

import com.caritas.backend.core.hostels.entities.HostelEntity;
import com.caritas.backend.core.services.entities.ServiceEntity;

import jakarta.persistence.*;

@Entity
@Table(name = "hostel_services")
public class HostelServiceEntity {
    
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hostel_id", nullable = false)
    private HostelEntity hostel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", nullable = false)
    private ServiceEntity service;

    public HostelServiceEntity() {
    }

    public HostelServiceEntity(HostelEntity hostel, ServiceEntity service) {
        this.hostel = hostel;
        this.service = service;
    }

    public UUID getId() {
        return id;
    }

    public HostelEntity getHostel() {
        return hostel;
    }

    public ServiceEntity getService() {
        return service;
    }

    public void detach() {
        if (this.hostel != null) {
            this.hostel.getHostelServices().remove(this);
            this.hostel = null;
        }
        if (this.service != null) {
            this.service.getHostelServices().remove(this);
            this.service = null;
        }
    }
}
