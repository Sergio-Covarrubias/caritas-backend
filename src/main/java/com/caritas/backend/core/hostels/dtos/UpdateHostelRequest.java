package com.caritas.backend.core.hostels.dtos;

import jakarta.validation.constraints.Size;

public record UpdateHostelRequest(
        String name,
        String description,
        Float price,
        Integer maxCapacity,
        String locationUrl,
        @Size(min = 1, message = "imageUrls must contain at least one URL if provided") String[] imageUrls) {
}
