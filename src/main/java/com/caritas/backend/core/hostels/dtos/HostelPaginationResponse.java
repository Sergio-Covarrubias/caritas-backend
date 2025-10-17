package com.caritas.backend.core.hostels.dtos;

import java.util.UUID;

import com.caritas.backend.core.hostels.entities.HostelEntity;

public record HostelPaginationResponse(UUID id, String name, String description, Integer maxCapacity, String locationUrl, String imageUrl, Integer availableSpaces) {
    public HostelPaginationResponse(HostelEntity hostel, Integer availableSpaces) {
        this(hostel.getId(), hostel.getName(), hostel.getDescription(), hostel.getMaxCapacity(), hostel.getLocationUrl(), hostel.getImageUrls()[0], availableSpaces);
    }
}
