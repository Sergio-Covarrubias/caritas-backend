package com.caritas.backend.core.users.dtos;

import jakarta.validation.constraints.NotNull;

public record CreateUserRequest(
        @NotNull String id,
        @NotNull String firstName,
        @NotNull String lastName,
        @NotNull String phoneNumber) {
}
