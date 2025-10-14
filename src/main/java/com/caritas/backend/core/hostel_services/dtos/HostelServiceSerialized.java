package com.caritas.backend.core.hostel_services.dtos;

import java.util.UUID;

import com.caritas.backend.core.hostel_services.entities.HostelServiceEntity;
import com.caritas.backend.core.hostels.dtos.HostelSerialized;
import com.caritas.backend.core.hostels.entities.HostelEntity;
import com.caritas.backend.core.services.dtos.ServiceSerialized;
import com.caritas.backend.core.services.entities.ServiceEntity;

public record HostelServiceSerialized(UUID id, HostelSerialized hostel, ServiceSerialized service) {
    public HostelServiceSerialized(HostelServiceEntity hostelService, HostelEntity hostel, ServiceEntity service) {
        this(
            hostelService.getId(), 
            hostel != null ? new HostelSerialized(hostel, null, null) : null, 
            service != null ? new ServiceSerialized(service) : null);
    }
}
