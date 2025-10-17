package com.caritas.backend.services.laundries;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class LaundryReservationService {
    private final LaundryReservationRepository laundryReservationRepository;

    public LaundryReservationService(LaundryReservationRepository breakfastRepository) {
        this.laundryReservationRepository = breakfastRepository;
    }

    public List<LaundryReservationSerialized> getAllLaundryReservations() {
        return laundryReservationRepository.findAll()
                .stream()
                .map(reservation -> new LaundryReservationSerialized(reservation))
                .toList();
    }

    public LaundryReservationSerialized getLaundryReservationById(UUID id) {
        LaundryReservationEntity reservation = laundryReservationRepository.findOneOrFail(id);

        return new LaundryReservationSerialized(reservation);
    }

    public LaundryReservationSerialized createLaundryReservation(LaundryReservationRequest request) {
        LaundryReservationEntity reservation = new LaundryReservationEntity(request.orderDate(), request.count());
        LaundryReservationEntity saved = laundryReservationRepository.save(reservation);

        return new LaundryReservationSerialized(saved);
    }

    public LaundryReservationSerialized updateLaundryReservation(UUID id, LaundryReservationRequest request) {
        LaundryReservationEntity reservation = laundryReservationRepository.findOneOrFail(id);

        if (request.orderDate() != null) reservation.setOrderDate(request.orderDate());
        if (request.count() != null) reservation.setCount(request.count());

        LaundryReservationEntity updated = laundryReservationRepository.save(reservation);

        return new LaundryReservationSerialized(updated);
    }

    public void deleteLaundryReservation(UUID id) {
        LaundryReservationEntity reservation = laundryReservationRepository.findOneOrFail(id);
        laundryReservationRepository.delete(reservation);
    }
}
