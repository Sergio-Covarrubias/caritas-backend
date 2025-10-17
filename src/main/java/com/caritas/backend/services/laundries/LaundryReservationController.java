package com.caritas.backend.services.laundries;

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
@RequestMapping("/" + ServiceNames.LAUNDRY)
public class LaundryReservationController {

    private final LaundryReservationService laundryReservationService;

    public LaundryReservationController(LaundryReservationService breakfastreservationService) {
        this.laundryReservationService = breakfastreservationService;
    }

    @GetMapping
    public List<LaundryReservationSerialized> getAllLaundryReservations() {
        return laundryReservationService.getAllLaundryReservations();
    }

    @GetMapping("/{id}")
    public LaundryReservationSerialized getLaundryReservationById(@PathVariable UUID id) {
        return laundryReservationService.getLaundryReservationById(id);
    }

    @PostMapping
    public LaundryReservationSerialized createLaundryReservation(@RequestBody LaundryReservationRequest request) {
        return laundryReservationService.createLaundryReservation(request);
    }

    @PutMapping("/{id}")
    public LaundryReservationSerialized updateLaundryReservation(@PathVariable UUID id, @RequestBody LaundryReservationRequest request) {
        return laundryReservationService.updateLaundryReservation(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteLaundryReservation(@PathVariable UUID id) {
        laundryReservationService.deleteLaundryReservation(id);
    }
}
