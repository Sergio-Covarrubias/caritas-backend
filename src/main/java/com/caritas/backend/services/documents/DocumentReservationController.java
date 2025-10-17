package com.caritas.backend.services.documents;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.caritas.backend.common.ServiceNames;

@RestController
@RequestMapping("/" + ServiceNames.DOCUMENT)
public class DocumentReservationController {

    private final DocumentReservationService documentReservationService;

    public DocumentReservationController(DocumentReservationService breakfastreservationService) {
        this.documentReservationService = breakfastreservationService;
    }

    @GetMapping
    public List<DocumentReservationSerialized> getAllDocumentReservations() {
        return documentReservationService.getAllDocumentReservations();
    }

    @GetMapping("/{id}")
    public DocumentReservationSerialized getDocumentReservationById(@PathVariable UUID id) {
        return documentReservationService.getDocumentReservationById(id);
    }

    @PostMapping
    public DocumentReservationSerialized createDocumentReservation(@RequestBody DocumentReservationRequest request) {
        return documentReservationService.createDocumentReservation(request);
    }

    @PutMapping("/{id}")
    public DocumentReservationSerialized updateDocumentReservation(@PathVariable UUID id, @RequestBody DocumentReservationRequest request) {
        return documentReservationService.updateDocumentReservation(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteDocumentReservation(@PathVariable UUID id) {
        documentReservationService.deleteDocumentReservation(id);
    }
}
