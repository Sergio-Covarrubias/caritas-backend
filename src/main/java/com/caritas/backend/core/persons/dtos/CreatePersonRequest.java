package com.caritas.backend.core.persons.dtos;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public record CreatePersonRequest(
        @NotNull String userId,
        @NotNull String firstName,
        @NotNull String lastName,
        @NotNull LocalDate birthDate,
        @NotNull String[] alergies,
        @NotNull String[] discapacities,
        @NotNull String[] medicines) {
}
