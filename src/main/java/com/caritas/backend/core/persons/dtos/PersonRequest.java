package com.caritas.backend.core.persons.dtos;

public record PersonRequest(String firstName, String userId, String lastName, Integer age, String[] alergies, String[] discapacities, String[] medicines) {}
