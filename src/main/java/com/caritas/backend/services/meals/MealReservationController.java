package com.caritas.backend.services.meals;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.caritas.backend.common.ServiceNames;

@RestController
@RequestMapping("/" + ServiceNames.MEAL)
public class MealReservationController {

    private final MealReservationService mealReservationService;

    public MealReservationController(MealReservationService breakfastreservationService) {
        this.mealReservationService = breakfastreservationService;
    }

    @GetMapping
    public List<MealReservationSerialized> getAllMealReservations() {
        return mealReservationService.getAllMealReservations();
    }

    @GetMapping("/{id}")
    public MealReservationSerialized getMealReservationById(@PathVariable UUID id) {
        return mealReservationService.getMealReservationById(id);
    }

    @PostMapping
    public MealReservationSerialized createMealReservation(@RequestBody MealReservationRequest request) {
        return mealReservationService.createMealReservation(request);
    }

    @PutMapping("/{id}")
    public MealReservationSerialized updateMealReservation(@PathVariable UUID id, @RequestBody MealReservationRequest request) {
        return mealReservationService.updateMealReservation(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteMealReservation(@PathVariable UUID id) {
        mealReservationService.deleteMealReservation(id);
    }
}
