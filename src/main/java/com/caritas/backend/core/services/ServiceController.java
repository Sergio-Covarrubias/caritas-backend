package com.caritas.backend.core.services;

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

import com.caritas.backend.core.services.dtos.ServiceRequest;
import com.caritas.backend.core.services.dtos.ServiceSerialized;

@RestController
@RequestMapping("/services")
public class ServiceController {

    private final ServiceService serviceService;

    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @GetMapping
    public List<ServiceSerialized> getAllServices() {
        return serviceService.getAllServices();
    }

    @GetMapping("/{id}")
    public ServiceSerialized getServiceById(@PathVariable UUID id) {
        return serviceService.getServiceById(id);
    }

    @PostMapping
    public ServiceSerialized createService(@RequestBody ServiceRequest request) {
        return serviceService.createService(request);
    }

    @PutMapping("/{id}")
    public ServiceSerialized updateService(@PathVariable UUID id, @RequestBody ServiceRequest request) {
        return serviceService.updateService(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteService(@PathVariable UUID id) {
        serviceService.deleteService(id);
    }
}
