package com.caritas.backend.core.hostels.dtos;

public record HostelRequest(String name, String description, Float price, Integer maxCapacity, String locationUrl, String[] imageUrls) {}
