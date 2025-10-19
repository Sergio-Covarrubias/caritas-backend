package com.caritas.backend.core.persons.dtos;

import java.time.LocalDate;

public record UpdatePersonRequest(
        String firstName,
        String lastName,
        LocalDate birthDate,
        String[] alergies,
        String[] discapacities,
        String[] medicines) {
}
