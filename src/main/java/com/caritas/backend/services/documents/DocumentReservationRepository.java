package com.caritas.backend.services.documents;

import java.util.UUID;

import com.caritas.backend.common.BaseRepository;

public interface DocumentReservationRepository extends BaseRepository<DocumentReservationEntity, UUID> {
    @Override
    default String entityName() {
        return "Document Reservation";
    }
}
