package com.caritas.backend.services.dentals;

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
@RequestMapping("/" + ServiceNames.DENTAL)
public class DentalReservationController {

    private final DentalReservationService dentalReservationService;

    public DentalReservationController(DentalReservationService breakfastreservationService) {
        this.dentalReservationService = breakfastreservationService;
    }

    @GetMapping
    public List<DentalReservationSerialized> getAllDentalReservations() {
        return dentalReservationService.getAllDentalReservations();
    }

    @GetMapping("/{id}")
    public DentalReservationSerialized getDentalReservationById(@PathVariable UUID id) {
        return dentalReservationService.getDentalReservationById(id);
    }

    @PostMapping
    public DentalReservationSerialized createDentalReservation(@RequestBody DentalReservationRequest request) {
        return dentalReservationService.createDentalReservation(request);
    }

    @PutMapping("/{id}")
    public DentalReservationSerialized updateDentalReservation(@PathVariable UUID id, @RequestBody DentalReservationRequest request) {
        return dentalReservationService.updateDentalReservation(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteDentalReservation(@PathVariable UUID id) {
        dentalReservationService.deleteDentalReservation(id);
    }
}
