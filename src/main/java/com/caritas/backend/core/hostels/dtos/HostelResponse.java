package com.caritas.backend.core.hostels.dtos;

import java.util.UUID;

import com.caritas.backend.core.hostels.entities.HostelEntity;

public record HostelResponse(UUID id, String name, String description, Integer maxCapacity, String locationUrl, String[] imageUrls) {
    public HostelResponse(HostelEntity hostel) {
        this(hostel.getId(), hostel.getName(), hostel.getDescription(), hostel.getMaxCapacity(), hostel.getLocationUrl(), hostel.getImageUrls());
    }
}
