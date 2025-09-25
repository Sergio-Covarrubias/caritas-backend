package com.caritas.backend.core.users.dtos;

import com.caritas.backend.core.users.entities.UserEntity;

public record UserResponse(String id, String firstName, String lastName, String email, String phoneNumber) {
    public UserResponse(UserEntity user) {
        this(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPhoneNumber());
    }
}
