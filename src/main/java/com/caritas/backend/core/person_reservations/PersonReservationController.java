package com.caritas.backend.core.person_reservations;

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

import com.caritas.backend.core.person_reservations.dtos.PersonReservationRequest;
import com.caritas.backend.core.person_reservations.dtos.PersonReservationSerialized;

@RestController
@RequestMapping("/person-reservations")
public class PersonReservationController {

    private final PersonReservationService personReservationService;

    public PersonReservationController(PersonReservationService personReservationService) {
        this.personReservationService = personReservationService;
    }

    @GetMapping
    public List<PersonReservationSerialized> getAllPersonReservations() {
        return personReservationService.getAllPersonReservations();
    }

    @GetMapping("/{id}")
    public PersonReservationSerialized getPersonReservationById(@PathVariable UUID id) {
        return personReservationService.getPersonReservationById(id);
    }

    @PostMapping
    public PersonReservationSerialized createPersonReservation(@RequestBody PersonReservationRequest request) {
        return personReservationService.createPersonReservation(request);
    }

    @PutMapping("/{id}")
    public PersonReservationSerialized updatePersonReservation(@PathVariable UUID id, @RequestBody PersonReservationRequest request) {
        return personReservationService.updatePersonReservation(id, request);
    }

    @DeleteMapping("/{id}")
    public void deletePersonReservation(@PathVariable UUID id) {
        personReservationService.deletePersonReservation(id);
    }
}
