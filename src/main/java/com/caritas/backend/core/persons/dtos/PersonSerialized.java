package com.caritas.backend.core.persons.dtos;

import java.time.LocalDate;
import java.util.UUID;

import com.caritas.backend.core.persons.entities.PersonEntity;
import com.caritas.backend.core.users.dtos.UserSerialized;
import com.caritas.backend.core.users.entities.UserEntity;

public record PersonSerialized(UUID id, UserSerialized user, String firstName, String lastName, LocalDate birthDate,
        String[] alergies, String[] discapacities, String[] medicines) {
    public PersonSerialized(PersonEntity person, UserEntity user) {
        this(
                person.getId(),
                user != null ? new UserSerialized(user) : null,
                person.getFirstName(),
                person.getLastName(),
                person.getBirthDate(),
                person.getAlergies(),
                person.getDiscapacities(),
                person.getMedicines());
    }
}
