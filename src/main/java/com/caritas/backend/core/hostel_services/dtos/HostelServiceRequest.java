package com.caritas.backend.core.hostel_services.dtos;

import java.util.UUID;

public record HostelServiceRequest(UUID hostelId, UUID serviceId) {}
