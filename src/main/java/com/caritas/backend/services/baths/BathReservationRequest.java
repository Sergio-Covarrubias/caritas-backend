package com.caritas.backend.services.baths;

import java.time.LocalDate;

public record BathReservationRequest(LocalDate orderDate, Integer count) {   
}
