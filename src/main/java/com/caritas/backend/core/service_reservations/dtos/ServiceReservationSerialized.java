package com.caritas.backend.core.service_reservations.dtos;

import java.time.LocalDate;
import java.util.UUID;

import com.caritas.backend.core.reservations.dtos.ReservationSerialized;
import com.caritas.backend.core.reservations.entities.ReservationEntity;
import com.caritas.backend.core.service_reservations.entities.ServiceReservationEntity;
import com.caritas.backend.core.service_reservations.entities.ServiceReservationState;
import com.caritas.backend.core.services.dtos.ServiceSerialized;
import com.caritas.backend.core.services.entities.ServiceEntity;

public record ServiceReservationSerialized(UUID id, ReservationSerialized reservation, ServiceSerialized service,
        LocalDate orderDate, Integer costCount, ServiceReservationState state, String externalReservationId) {
    public ServiceReservationSerialized(ServiceReservationEntity serviceReservation, ReservationEntity reservation, ServiceEntity service) {
        this(
            serviceReservation.getId(), 
            reservation != null ? new ReservationSerialized(reservation, null, null, null, null, false, false) : null,
            service != null ? new ServiceSerialized(service) : null,
            serviceReservation.getOrderDate(),
            serviceReservation.getCostCount(),
            serviceReservation.getState(),
            serviceReservation.getExternalReservationId());
    }
}
