package com.caritas.backend.core.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.caritas.backend.core.services.dtos.CreateServiceRequest;
import com.caritas.backend.core.services.dtos.ServiceSerialized;
import com.caritas.backend.core.services.dtos.UpdateServiceRequest;
import com.caritas.backend.core.services.entities.ServiceEntity;
import com.caritas.backend.core.services.entities.ServiceRepository;

@Service
public class ServiceService {

    private final ServiceRepository serviceRepository;

    public ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    public List<ServiceSerialized> getAllServices() {
        return serviceRepository.findAll()
                .stream()
                .map(service -> new ServiceSerialized(service))
                .toList();
    }

    public ServiceSerialized getServiceById(UUID id) {
        ServiceEntity service = serviceRepository.findOneOrFail(id);

        return new ServiceSerialized(service);
    }

    public ServiceSerialized createService(CreateServiceRequest request) {
        ServiceEntity service = new ServiceEntity(request.price(), request.type());
        ServiceEntity saved = serviceRepository.save(service);

        return new ServiceSerialized(saved);
    }

    public ServiceSerialized updateService(UUID id, UpdateServiceRequest request) {
        ServiceEntity service = serviceRepository.findOneOrFail(id);

        if (request.price() != null) service.setPrice(request.price());

        ServiceEntity updated = serviceRepository.save(service);

        return new ServiceSerialized(updated);
    }

    public void deleteService(UUID id) {
        ServiceEntity service = serviceRepository.findOneOrFail(id);
        serviceRepository.delete(service);
    }
}
