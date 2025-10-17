package com.caritas.backend.services.dentals;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class DentalReservationService {
    private final DentalReservationRepository dentalReservationRepository;

    public DentalReservationService(DentalReservationRepository breakfastRepository) {
        this.dentalReservationRepository = breakfastRepository;
    }

    public List<DentalReservationSerialized> getAllDentalReservations() {
        return dentalReservationRepository.findAll()
                .stream()
                .map(reservation -> new DentalReservationSerialized(reservation))
                .toList();
    }

    public DentalReservationSerialized getDentalReservationById(UUID id) {
        DentalReservationEntity reservation = dentalReservationRepository.findOneOrFail(id);

        return new DentalReservationSerialized(reservation);
    }

    public DentalReservationSerialized createDentalReservation(DentalReservationRequest request) {
        DentalReservationEntity reservation = new DentalReservationEntity(request.orderDate(), request.count());
        DentalReservationEntity saved = dentalReservationRepository.save(reservation);

        return new DentalReservationSerialized(saved);
    }

    public DentalReservationSerialized updateDentalReservation(UUID id, DentalReservationRequest request) {
        DentalReservationEntity reservation = dentalReservationRepository.findOneOrFail(id);

        if (request.orderDate() != null) reservation.setOrderDate(request.orderDate());
        if (request.count() != null) reservation.setCount(request.count());

        DentalReservationEntity updated = dentalReservationRepository.save(reservation);

        return new DentalReservationSerialized(updated);
    }

    public void deleteDentalReservation(UUID id) {
        DentalReservationEntity reservation = dentalReservationRepository.findOneOrFail(id);
        dentalReservationRepository.delete(reservation);
    }
}
