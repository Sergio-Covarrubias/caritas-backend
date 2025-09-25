package com.caritas.backend.core.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.caritas.backend.core.services.dtos.ServiceRequest;
import com.caritas.backend.core.services.dtos.ServiceResponse;
import com.caritas.backend.core.services.entities.ServiceEntity;
import com.caritas.backend.core.services.entities.ServiceRepository;

@Service
public class ServiceService {

    private final ServiceRepository serviceRepository;

    public ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    public List<ServiceResponse> getAllServices() {
        return serviceRepository.findAll()
                .stream()
                .map(service -> new ServiceResponse(service))
                .toList();
    }

    public ServiceResponse getServiceById(UUID id) {
        ServiceEntity service = serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        return new ServiceResponse(service);
    }

    public ServiceResponse createService(ServiceRequest request) {
        ServiceEntity service = new ServiceEntity(request.displayName(), request.type());
        ServiceEntity saved = serviceRepository.save(service);

        return new ServiceResponse(saved);
    }

    public ServiceResponse updateService(UUID id, ServiceRequest request) {
        ServiceEntity service = serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        service.setDisplayName(request.displayName());
        service.setType(request.type());

        ServiceEntity updated = serviceRepository.save(service);

        return new ServiceResponse(updated);
    }

    public void deleteService(UUID id) {
        if (!serviceRepository.existsById(id)) {
            throw new RuntimeException("Service not found");
        }
        serviceRepository.deleteById(id);
    }
}
