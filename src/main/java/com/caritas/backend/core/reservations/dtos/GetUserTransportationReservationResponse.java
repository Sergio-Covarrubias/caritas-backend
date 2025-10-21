package com.caritas.backend.core.reservations.dtos;

import com.caritas.backend.services.transportations.TransportationReservationSerialized;

public record GetUserTransportationReservationResponse(TransportationReservationSerialized transportationReservation) {
}
