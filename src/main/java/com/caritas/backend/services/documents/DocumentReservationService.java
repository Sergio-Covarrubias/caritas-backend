package com.caritas.backend.services.documents;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class DocumentReservationService {
    private final DocumentReservationRepository documentReservationRepository;

    public DocumentReservationService(DocumentReservationRepository breakfastRepository) {
        this.documentReservationRepository = breakfastRepository;
    }

    public List<DocumentReservationSerialized> getAllDocumentReservations() {
        return documentReservationRepository.findAll()
                .stream()
                .map(reservation -> new DocumentReservationSerialized(reservation))
                .toList();
    }

    public DocumentReservationSerialized getDocumentReservationById(UUID id) {
        DocumentReservationEntity reservation = documentReservationRepository.findOneOrFail(id);

        return new DocumentReservationSerialized(reservation);
    }

    public DocumentReservationSerialized createDocumentReservation(DocumentReservationRequest request) {
        DocumentReservationEntity reservation = new DocumentReservationEntity(request.orderDate(), request.count());
        DocumentReservationEntity saved = documentReservationRepository.save(reservation);

        return new DocumentReservationSerialized(saved);
    }

    public DocumentReservationSerialized updateDocumentReservation(UUID id, DocumentReservationRequest request) {
        DocumentReservationEntity reservation = documentReservationRepository.findOneOrFail(id);

        if (request.orderDate() != null) reservation.setOrderDate(request.orderDate());
        if (request.count() != null) reservation.setCount(request.count());

        DocumentReservationEntity updated = documentReservationRepository.save(reservation);

        return new DocumentReservationSerialized(updated);
    }

    public void deleteDocumentReservation(UUID id) {
        DocumentReservationEntity reservation = documentReservationRepository.findOneOrFail(id);
        documentReservationRepository.delete(reservation);
    }
}
