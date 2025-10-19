package com.caritas.backend.services.baths;

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
@RequestMapping("/internal/" + ServiceNames.BATH)
public class BathReservationController {

    private final BathReservationService bathReservationService;

    public BathReservationController(BathReservationService breakfastreservationService) {
        this.bathReservationService = breakfastreservationService;
    }

    @GetMapping
    public List<BathReservationSerialized> getAllBathReservations() {
        return bathReservationService.getAllBathReservations();
    }

    @GetMapping("/{id}")
    public BathReservationSerialized getBathReservationById(@PathVariable UUID id) {
        return bathReservationService.getBathReservationById(id);
    }

    @PostMapping
    public BathReservationSerialized createBathReservation(@RequestBody BathReservationRequest request) {
        return bathReservationService.createBathReservation(request);
    }

    @PutMapping("/{id}")
    public BathReservationSerialized updateBathReservation(@PathVariable UUID id, @RequestBody BathReservationRequest request) {
        return bathReservationService.updateBathReservation(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteBathReservation(@PathVariable UUID id) {
        bathReservationService.deleteBathReservation(id);
    }
}
