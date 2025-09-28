package com.caritas.backend.core.hostels.dtos;

public record HostelRequest(String name, String description, Integer maxCapacity, String locationUrl, String[] imageUrls) {}
