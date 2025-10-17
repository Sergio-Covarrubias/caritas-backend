package com.caritas.backend.core.hostels.dtos;

import java.util.List;
import java.util.UUID;

import com.caritas.backend.core.hostel_services.dtos.HostelServiceSerialized;
import com.caritas.backend.core.hostel_services.entities.HostelServiceEntity;
import com.caritas.backend.core.hostels.entities.HostelEntity;
import com.caritas.backend.core.reservations.dtos.ReservationSerialized;
import com.caritas.backend.core.reservations.entities.ReservationEntity;

public record HostelSerialized(UUID id, String name, String description, Float price, Integer maxCapacity, String locationUrl,
        String[] imageUrls, HostelServiceSerialized[] hostelServices, ReservationSerialized[] reservations) {
    public HostelSerialized(HostelEntity hostel, List<HostelServiceEntity> hostelServices,
            List<ReservationEntity> reservations) {
        this(
                hostel.getId(),
                hostel.getName(),
                hostel.getDescription(),
                hostel.getPrice(),
                hostel.getMaxCapacity(),
                hostel.getLocationUrl(),
                hostel.getImageUrls(),
                hostelServices != null ? hostelServices.stream().map(hostelService -> new HostelServiceSerialized(hostelService, null, hostelService.getService()))
                        .toArray(HostelServiceSerialized[]::new) : null,
                reservations != null ? reservations.stream().map(reservation -> new ReservationSerialized(reservation, null, null, null, null, false, false))
                        .toArray(ReservationSerialized[]::new) : null);
    }
}
