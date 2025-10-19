package com.caritas.backend.common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.caritas.backend.common.errors.NotFoundException;

@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaRepository<T, ID> {
    default T findOneOrFail(ID id) {
        return findById(id).orElseThrow(() -> new NotFoundException(entityName() + " not found"));
    }

    String entityName();
}
