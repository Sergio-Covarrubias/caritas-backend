package com.caritas.backend.core.persons.dtos;

import java.time.LocalDate;
import java.util.UUID;

import com.caritas.backend.core.persons.entities.PersonEntity;

public record PersonResponse(UUID id, String userId, String firstName, String lastName, LocalDate birthDate, String[] alergies, String[] discapacities, String[] medicines) {
    public PersonResponse(PersonEntity person) {
        this(person.getId(), person.getUser().getId(), person.getFirstName(), person.getLastName(), person.getBirthDate(), person.getAlergies(), person.getDiscapacities(), person.getMedicines());
    }

    public PersonResponse(PersonEntity person, String userId) {
        this(person.getId(), userId, person.getFirstName(), person.getLastName(), person.getBirthDate(), person.getAlergies(), person.getDiscapacities(), person.getMedicines());
    }
}
