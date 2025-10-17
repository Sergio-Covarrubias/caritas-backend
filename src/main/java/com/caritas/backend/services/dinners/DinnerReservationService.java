package com.caritas.backend.services.dinners;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class DinnerReservationService {
    private final DinnerReservationRepository dinnerReservationRepository;

    public DinnerReservationService(DinnerReservationRepository breakfastRepository) {
        this.dinnerReservationRepository = breakfastRepository;
    }

    public List<DinnerReservationSerialized> getAllDinnerReservations() {
        return dinnerReservationRepository.findAll()
                .stream()
                .map(reservation -> new DinnerReservationSerialized(reservation))
                .toList();
    }

    public DinnerReservationSerialized getDinnerReservationById(UUID id) {
        DinnerReservationEntity reservation = dinnerReservationRepository.findOneOrFail(id);

        return new DinnerReservationSerialized(reservation);
    }

    public DinnerReservationSerialized createDinnerReservation(DinnerReservationRequest request) {
        DinnerReservationEntity reservation = new DinnerReservationEntity(request.orderDate(), request.count());
        DinnerReservationEntity saved = dinnerReservationRepository.save(reservation);

        return new DinnerReservationSerialized(saved);
    }

    public DinnerReservationSerialized updateDinnerReservation(UUID id, DinnerReservationRequest request) {
        DinnerReservationEntity reservation = dinnerReservationRepository.findOneOrFail(id);

        if (request.orderDate() != null) reservation.setOrderDate(request.orderDate());
        if (request.count() != null) reservation.setCount(request.count());

        DinnerReservationEntity updated = dinnerReservationRepository.save(reservation);

        return new DinnerReservationSerialized(updated);
    }

    public void deleteDinnerReservation(UUID id) {
        DinnerReservationEntity reservation = dinnerReservationRepository.findOneOrFail(id);
        dinnerReservationRepository.delete(reservation);
    }
}
