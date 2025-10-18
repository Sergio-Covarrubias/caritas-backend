package com.caritas.backend.dashboard.dtos;

public record ServiceReservationsTypeCountResponse(
        Integer transportations,
        Integer breakfasts,
        Integer meals,
        Integer dinners,
        Integer laundries,
        Integer baths,
        Integer dentals,
        Integer mentals,
        Integer documents) {
}
