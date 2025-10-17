package com.caritas.backend.services.mentals;

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
@RequestMapping("/" + ServiceNames.MENTAL)
public class MentalReservationController {

    private final MentalReservationService mentalReservationService;

    public MentalReservationController(MentalReservationService breakfastreservationService) {
        this.mentalReservationService = breakfastreservationService;
    }

    @GetMapping
    public List<MentalReservationSerialized> getAllMentalReservations() {
        return mentalReservationService.getAllMentalReservations();
    }

    @GetMapping("/{id}")
    public MentalReservationSerialized getMentalReservationById(@PathVariable UUID id) {
        return mentalReservationService.getMentalReservationById(id);
    }

    @PostMapping
    public MentalReservationSerialized createMentalReservation(@RequestBody MentalReservationRequest request) {
        return mentalReservationService.createMentalReservation(request);
    }

    @PutMapping("/{id}")
    public MentalReservationSerialized updateMentalReservation(@PathVariable UUID id, @RequestBody MentalReservationRequest request) {
        return mentalReservationService.updateMentalReservation(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteMentalReservation(@PathVariable UUID id) {
        mentalReservationService.deleteMentalReservation(id);
    }
}
