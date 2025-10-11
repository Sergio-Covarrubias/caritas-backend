package com.caritas.backend.core.persons.dtos;

import java.time.LocalDate;

public record PersonRequest(String firstName, String userId, String lastName, LocalDate birthDate, String[] alergies, String[] discapacities, String[] medicines) {}
