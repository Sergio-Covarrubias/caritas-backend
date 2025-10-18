package com.caritas.backend.dashboard.dtos;

public record ReservationsStateCountResponse(
        Integer[] pending,
        Integer[] active,
        Integer[] inactive,
        Integer[] cancelled) {
}
