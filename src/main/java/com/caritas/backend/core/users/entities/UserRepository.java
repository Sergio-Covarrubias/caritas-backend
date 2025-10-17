package com.caritas.backend.core.users.entities;

import java.util.Optional;

import com.caritas.backend.common.BaseRepository;

public interface UserRepository extends BaseRepository<UserEntity, String> {
    @Override
    default String entityName() {
        return "User";
    }

    Optional<UserEntity> findByPhoneNumber(String phoneNumber);
}
