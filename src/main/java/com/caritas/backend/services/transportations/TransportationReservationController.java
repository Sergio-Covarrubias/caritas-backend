package com.caritas.backend.services.transportations;

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
@RequestMapping("/internal/" + ServiceNames.TRANSPORTATION)
public class TransportationReservationController {

    private final TransportationReservationService transportationReservationService;

    public TransportationReservationController(TransportationReservationService breakfastreservationService) {
        this.transportationReservationService = breakfastreservationService;
    }

    @GetMapping
    public List<TransportationReservationSerialized> getAllTransportationReservations() {
        return transportationReservationService.getAllTransportationReservations();
    }

    @GetMapping("/{id}")
    public TransportationReservationSerialized getTransportationReservationById(@PathVariable UUID id) {
        return transportationReservationService.getTransportationReservationById(id);
    }

    @PostMapping
    public TransportationReservationSerialized createTransportationReservation(@RequestBody TransportationReservationRequest request) {
        return transportationReservationService.createTransportationReservation(request);
    }

    @PutMapping("/{id}")
    public TransportationReservationSerialized updateTransportationReservation(@PathVariable UUID id, @RequestBody TransportationReservationRequest request) {
        return transportationReservationService.updateTransportationReservation(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteTransportationReservation(@PathVariable UUID id) {
        transportationReservationService.deleteTransportationReservation(id);
    }
}
