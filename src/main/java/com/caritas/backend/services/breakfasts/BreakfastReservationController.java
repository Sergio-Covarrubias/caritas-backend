package com.caritas.backend.services.breakfasts;

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
@RequestMapping("/" + ServiceNames.BREAKFAST)
public class BreakfastReservationController {

    private final BreakfastReservationService breakfastReservationService;

    public BreakfastReservationController(BreakfastReservationService breakfastreservationService) {
        this.breakfastReservationService = breakfastreservationService;
    }

    @GetMapping
    public List<BreakfastReservationSerialized> getAllBreakfastReservations() {
        return breakfastReservationService.getAllBreakfastReservations();
    }

    @GetMapping("/{id}")
    public BreakfastReservationSerialized getBreakfastReservationById(@PathVariable UUID id) {
        return breakfastReservationService.getBreakfastReservationById(id);
    }

    @PostMapping
    public BreakfastReservationSerialized createBreakfastReservation(@RequestBody BreakfastReservationRequest request) {
        return breakfastReservationService.createBreakfastReservation(request);
    }

    @PutMapping("/{id}")
    public BreakfastReservationSerialized updateBreakfastReservation(@PathVariable UUID id, @RequestBody BreakfastReservationRequest request) {
        return breakfastReservationService.updateBreakfastReservation(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteBreakfastReservation(@PathVariable UUID id) {
        breakfastReservationService.deleteBreakfastReservation(id);
    }
}
