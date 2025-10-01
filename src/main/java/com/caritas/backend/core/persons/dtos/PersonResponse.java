package com.caritas.backend.core.persons.dtos;

import java.util.UUID;

import com.caritas.backend.core.persons.entities.PersonEntity;

public record PersonResponse(UUID id, String userId, String firstName, String lastName, Integer age, String[] alergies, String[] discapacities, String[] medicines) {
    public PersonResponse(PersonEntity person) {
        this(person.getId(), person.getUser().getId(), person.getFirstName(), person.getLastName(), person.getAge(), person.getAlergies(), person.getDiscapacities(), person.getMedicines());
    }

    public PersonResponse(PersonEntity person, String userId) {
        this(person.getId(), userId, person.getFirstName(), person.getLastName(), person.getAge(), person.getAlergies(), person.getDiscapacities(), person.getMedicines());
    }
}
