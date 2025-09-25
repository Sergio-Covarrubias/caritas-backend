package com.caritas.backend.core.hostel_services.dtos;

import java.util.UUID;

import com.caritas.backend.core.hostel_services.entities.HostelServiceEntity;

public record HostelServiceResponse(UUID id, UUID hostelId, UUID serviceId) {
    public HostelServiceResponse(HostelServiceEntity hostelService) {
        this(hostelService.getId(), hostelService.getHostel().getId(), hostelService.getService().getId());
    }

    public HostelServiceResponse(HostelServiceEntity hostelService, UUID hostelId, UUID serviceId) {
        this(hostelService.getId(), hostelId, serviceId);
    }
}
