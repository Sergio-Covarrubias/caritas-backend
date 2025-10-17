package com.caritas.backend.services.mentals;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class MentalReservationService {
    private final MentalReservationRepository mentalReservationRepository;

    public MentalReservationService(MentalReservationRepository breakfastRepository) {
        this.mentalReservationRepository = breakfastRepository;
    }

    public List<MentalReservationSerialized> getAllMentalReservations() {
        return mentalReservationRepository.findAll()
                .stream()
                .map(reservation -> new MentalReservationSerialized(reservation))
                .toList();
    }

    public MentalReservationSerialized getMentalReservationById(UUID id) {
        MentalReservationEntity reservation = mentalReservationRepository.findOneOrFail(id);

        return new MentalReservationSerialized(reservation);
    }

    public MentalReservationSerialized createMentalReservation(MentalReservationRequest request) {
        MentalReservationEntity reservation = new MentalReservationEntity(request.orderDate(), request.count());
        MentalReservationEntity saved = mentalReservationRepository.save(reservation);

        return new MentalReservationSerialized(saved);
    }

    public MentalReservationSerialized updateMentalReservation(UUID id, MentalReservationRequest request) {
        MentalReservationEntity reservation = mentalReservationRepository.findOneOrFail(id);

        if (request.orderDate() != null) reservation.setOrderDate(request.orderDate());
        if (request.count() != null) reservation.setCount(request.count());

        MentalReservationEntity updated = mentalReservationRepository.save(reservation);

        return new MentalReservationSerialized(updated);
    }

    public void deleteMentalReservation(UUID id) {
        MentalReservationEntity reservation = mentalReservationRepository.findOneOrFail(id);
        mentalReservationRepository.delete(reservation);
    }
}
