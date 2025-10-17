package com.caritas.backend.services.meals;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class MealReservationService {
    private final MealReservationRepository mealReservationRepository;

    public MealReservationService(MealReservationRepository breakfastRepository) {
        this.mealReservationRepository = breakfastRepository;
    }

    public List<MealReservationSerialized> getAllMealReservations() {
        return mealReservationRepository.findAll()
                .stream()
                .map(reservation -> new MealReservationSerialized(reservation))
                .toList();
    }

    public MealReservationSerialized getMealReservationById(UUID id) {
        MealReservationEntity reservation = mealReservationRepository.findOneOrFail(id);

        return new MealReservationSerialized(reservation);
    }

    public MealReservationSerialized createMealReservation(MealReservationRequest request) {
        MealReservationEntity reservation = new MealReservationEntity(request.orderDate(), request.count());
        MealReservationEntity saved = mealReservationRepository.save(reservation);

        return new MealReservationSerialized(saved);
    }

    public MealReservationSerialized updateMealReservation(UUID id, MealReservationRequest request) {
        MealReservationEntity reservation = mealReservationRepository.findOneOrFail(id);

        if (request.orderDate() != null) reservation.setOrderDate(request.orderDate());
        if (request.count() != null) reservation.setCount(request.count());

        MealReservationEntity updated = mealReservationRepository.save(reservation);

        return new MealReservationSerialized(updated);
    }

    public void deleteMealReservation(UUID id) {
        MealReservationEntity reservation = mealReservationRepository.findOneOrFail(id);
        mealReservationRepository.delete(reservation);
    }
}
