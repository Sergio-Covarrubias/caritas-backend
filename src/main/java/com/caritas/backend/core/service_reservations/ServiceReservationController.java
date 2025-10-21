package com.caritas.backend.core.service_reservations;

import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.caritas.backend.core.service_reservations.dtos.ServiceReservationSerialized;
import com.fasterxml.jackson.databind.JsonNode;

@RestController
@RequestMapping("/service-reservations")
public class ServiceReservationController {

    private final ServiceReservationService serviceReservationService;

    public ServiceReservationController(ServiceReservationService serviceReservationService) {
        this.serviceReservationService = serviceReservationService;
    }

    @GetMapping("/{id}")
    public ServiceReservationSerialized getServiceReservationById(@PathVariable UUID id) {
        return serviceReservationService.getServiceReservationById(id);
    }

    @GetMapping("/{id}/details")
    public JsonNode getServiceReservationByIdWithDetails(@PathVariable UUID id) {
        return serviceReservationService.getServiceReservationByIdWithDetails(id);
    }

    @PostMapping
    public ServiceReservationSerialized createServiceReservation(@RequestBody JsonNode request) {
        return serviceReservationService.createServiceReservation(request);
    }
}
