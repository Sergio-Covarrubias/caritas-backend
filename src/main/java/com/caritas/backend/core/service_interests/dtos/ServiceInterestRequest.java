package com.caritas.backend.core.service_interests.dtos;

import java.util.UUID;

public record ServiceInterestRequest(UUID reservationId, UUID serviceId) {}
