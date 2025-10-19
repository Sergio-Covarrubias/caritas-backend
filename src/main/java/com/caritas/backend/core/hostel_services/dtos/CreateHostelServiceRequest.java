package com.caritas.backend.core.hostel_services.dtos;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record CreateHostelServiceRequest(
        @NotNull UUID hostelId,
        @NotNull UUID serviceId) {
}
