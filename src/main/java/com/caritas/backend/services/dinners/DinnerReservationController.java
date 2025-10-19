package com.caritas.backend.services.dinners;

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
@RequestMapping("/internal/" + ServiceNames.DINNER)
public class DinnerReservationController {

    private final DinnerReservationService dinnerReservationService;

    public DinnerReservationController(DinnerReservationService breakfastreservationService) {
        this.dinnerReservationService = breakfastreservationService;
    }

    @GetMapping
    public List<DinnerReservationSerialized> getAllDinnerReservations() {
        return dinnerReservationService.getAllDinnerReservations();
    }

    @GetMapping("/{id}")
    public DinnerReservationSerialized getDinnerReservationById(@PathVariable UUID id) {
        return dinnerReservationService.getDinnerReservationById(id);
    }

    @PostMapping
    public DinnerReservationSerialized createDinnerReservation(@RequestBody DinnerReservationRequest request) {
        return dinnerReservationService.createDinnerReservation(request);
    }

    @PutMapping("/{id}")
    public DinnerReservationSerialized updateDinnerReservation(@PathVariable UUID id, @RequestBody DinnerReservationRequest request) {
        return dinnerReservationService.updateDinnerReservation(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteDinnerReservation(@PathVariable UUID id) {
        dinnerReservationService.deleteDinnerReservation(id);
    }
}
