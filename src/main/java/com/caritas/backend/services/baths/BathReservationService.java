package com.caritas.backend.services.baths;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class BathReservationService {
    private final BathReservationRepository bathReservationRepository;

    public BathReservationService(BathReservationRepository breakfastRepository) {
        this.bathReservationRepository = breakfastRepository;
    }

    public List<BathReservationSerialized> getAllBathReservations() {
        return bathReservationRepository.findAll()
                .stream()
                .map(reservation -> new BathReservationSerialized(reservation))
                .toList();
    }

    public BathReservationSerialized getBathReservationById(UUID id) {
        BathReservationEntity reservation = bathReservationRepository.findOneOrFail(id);

        return new BathReservationSerialized(reservation);
    }

    public BathReservationSerialized createBathReservation(BathReservationRequest request) {
        BathReservationEntity reservation = new BathReservationEntity(request.orderDate(), request.count());
        BathReservationEntity saved = bathReservationRepository.save(reservation);

        return new BathReservationSerialized(saved);
    }

    public BathReservationSerialized updateBathReservation(UUID id, BathReservationRequest request) {
        BathReservationEntity reservation = bathReservationRepository.findOneOrFail(id);

        if (request.orderDate() != null) reservation.setOrderDate(request.orderDate());
        if (request.count() != null) reservation.setCount(request.count());

        BathReservationEntity updated = bathReservationRepository.save(reservation);

        return new BathReservationSerialized(updated);
    }

    public void deleteBathReservation(UUID id) {
        BathReservationEntity reservation = bathReservationRepository.findOneOrFail(id);
        bathReservationRepository.delete(reservation);
    }
}

