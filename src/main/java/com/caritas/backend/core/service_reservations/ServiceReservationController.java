package com.caritas.backend.core.service_reservations;

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

import com.caritas.backend.core.service_reservations.dtos.ServiceReservationRequest;
import com.caritas.backend.core.service_reservations.dtos.ServiceReservationSerialized;
import com.fasterxml.jackson.databind.JsonNode;

@RestController
@RequestMapping("/service-reservations")
public class ServiceReservationController {

    private final ServiceReservationService serviceReservationService;

    public ServiceReservationController(ServiceReservationService serviceReservationService) {
        this.serviceReservationService = serviceReservationService;
    }

    @GetMapping
    public List<ServiceReservationSerialized> getAllServiceReservations() {
        return serviceReservationService.getAllServiceReservations();
    }

    @GetMapping("/{id}")
    public ServiceReservationSerialized getServiceReservationById(@PathVariable UUID id) {
        return serviceReservationService.getServiceReservationById(id);
    }

    @PostMapping
    public ServiceReservationSerialized createServiceReservation(@RequestBody JsonNode request) {
        return serviceReservationService.createServiceReservation(request);
    }

    @PostMapping("/confirm/{id}")
    public ServiceReservationSerialized confirmServiceReservation(@PathVariable UUID id) {
        return serviceReservationService.confirmServiceReservation(id);
    }

    @PutMapping("/{id}")
    public ServiceReservationSerialized updateServiceReservation(@PathVariable UUID id,
            @RequestBody ServiceReservationRequest request) {
        return serviceReservationService.updateServiceReservation(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteServiceReservation(@PathVariable UUID id) {
        serviceReservationService.deleteServiceReservation(id);
    }
}
