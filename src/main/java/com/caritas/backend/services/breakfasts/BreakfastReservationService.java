package com.caritas.backend.services.breakfasts;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class BreakfastReservationService {
    private final BreakfastReservationRepository breakfastReservationRepository;

    public BreakfastReservationService(BreakfastReservationRepository breakfastRepository) {
        this.breakfastReservationRepository = breakfastRepository;
    }

    public List<BreakfastReservationSerialized> getAllBreakfastReservations() {
        return breakfastReservationRepository.findAll()
                .stream()
                .map(reservation -> new BreakfastReservationSerialized(reservation))
                .toList();
    }

    public BreakfastReservationSerialized getBreakfastReservationById(UUID id) {
        BreakfastReservationEntity reservation = breakfastReservationRepository.findOneOrFail(id);

        return new BreakfastReservationSerialized(reservation);
    }

    public BreakfastReservationSerialized createBreakfastReservation(BreakfastReservationRequest request) {
        BreakfastReservationEntity reservation = new BreakfastReservationEntity(request.orderDate(), request.count());
        BreakfastReservationEntity saved = breakfastReservationRepository.save(reservation);

        return new BreakfastReservationSerialized(saved);
    }

    public BreakfastReservationSerialized updateBreakfastReservation(UUID id, BreakfastReservationRequest request) {
        BreakfastReservationEntity reservation = breakfastReservationRepository.findOneOrFail(id);

        if (request.orderDate() != null) reservation.setOrderDate(request.orderDate());
        if (request.count() != null) reservation.setCount(request.count());

        BreakfastReservationEntity updated = breakfastReservationRepository.save(reservation);

        return new BreakfastReservationSerialized(updated);
    }

    public void deleteBreakfastReservation(UUID id) {
        BreakfastReservationEntity reservation = breakfastReservationRepository.findOneOrFail(id);
        breakfastReservationRepository.delete(reservation);
    }
}
