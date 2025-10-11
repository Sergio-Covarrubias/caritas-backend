package com.caritas.backend.core.hostels.dtos;

import java.util.UUID;

import com.caritas.backend.core.hostels.entities.HostelEntity;

public record HostelGetResponse(UUID id, String name, String description, Integer maxCapacity, String locationUrl, String[] imageUrls, Service[] services) {
    public record Service(UUID id, float price, String type) {}
    
    public HostelGetResponse(HostelEntity hostel, Service[] services) {
        this(hostel.getId(), hostel.getName(), hostel.getDescription(), hostel.getMaxCapacity(), hostel.getLocationUrl(), hostel.getImageUrls(), services);
    }
}
