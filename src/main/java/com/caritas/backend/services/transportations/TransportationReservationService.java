package com.caritas.backend.services.transportations;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class TransportationReservationService {
    private final TransportationReservationRepository transportationReservationRepository;

    public TransportationReservationService(TransportationReservationRepository breakfastRepository) {
        this.transportationReservationRepository = breakfastRepository;
    }

    public List<TransportationReservationSerialized> getAllTransportationReservations() {
        return transportationReservationRepository.findAll()
                .stream()
                .map(reservation -> new TransportationReservationSerialized(reservation))
                .toList();
    }

    public TransportationReservationSerialized getTransportationReservationById(UUID id) {
        TransportationReservationEntity reservation = transportationReservationRepository.findOneOrFail(id);

        return new TransportationReservationSerialized(reservation);
    }

    public TransportationReservationSerialized createTransportationReservation(TransportationReservationRequest request) {
        TransportationReservationEntity reservation = new TransportationReservationEntity(request.orderDate(), request.count(), request.hostelName(), request.place(), request.fromHostel(), request.pickupTime());
        TransportationReservationEntity saved = transportationReservationRepository.save(reservation);

        return new TransportationReservationSerialized(saved);
    }

    public TransportationReservationSerialized updateTransportationReservation(UUID id, TransportationReservationRequest request) {
        TransportationReservationEntity reservation = transportationReservationRepository.findOneOrFail(id);

        if (request.orderDate() != null) reservation.setOrderDate(request.orderDate());
        if (request.count() != null) reservation.setCount(request.count());
        if (request.hostelName() != null) reservation.setHostelName(request.hostelName());
        if (request.place() != null) reservation.setPlace(request.place());
        if (request.fromHostel() != null) reservation.setFromHostel(request.fromHostel());
        if (request.pickupTime() != null) reservation.setPickupTime(request.pickupTime());

        TransportationReservationEntity updated = transportationReservationRepository.save(reservation);

        return new TransportationReservationSerialized(updated);
    }

    public void deleteTransportationReservation(UUID id) {
        TransportationReservationEntity reservation = transportationReservationRepository.findOneOrFail(id);
        transportationReservationRepository.delete(reservation);
    }
}
