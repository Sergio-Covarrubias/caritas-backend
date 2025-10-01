package com.caritas.backend.core.persons.entities;

import java.util.UUID;

import com.caritas.backend.common.BaseRepository;

public interface PersonRepository extends BaseRepository<PersonEntity, UUID> {
    @Override
    default String entityName() {
        return "Person";
    }
}
