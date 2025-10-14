package com.caritas.backend.core.hostel_services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.caritas.backend.core.hostel_services.dtos.HostelServiceRequest;
import com.caritas.backend.core.hostel_services.dtos.HostelServiceSerialized;
import com.caritas.backend.core.hostel_services.entities.HostelServiceEntity;
import com.caritas.backend.core.hostel_services.entities.HostelServiceRepository;
import com.caritas.backend.core.hostels.entities.HostelEntity;
import com.caritas.backend.core.hostels.entities.HostelRepository;
import com.caritas.backend.core.services.entities.ServiceEntity;
import com.caritas.backend.core.services.entities.ServiceRepository;

@Service
public class HostelServiceService {

    private final HostelServiceRepository hostelServiceRepository;
    private final HostelRepository hostelRepository;
    private final ServiceRepository serviceRepository;

    public HostelServiceService(HostelServiceRepository hostelServiceRepository, ServiceRepository serviceRepository,
            HostelRepository hostelRepository) {
        this.hostelServiceRepository = hostelServiceRepository;
        this.serviceRepository = serviceRepository;
        this.hostelRepository = hostelRepository;
    }

    public List<HostelServiceSerialized> getAllHostelServices() {
        return hostelServiceRepository.findAll()
                .stream()
                .map(hostelService -> new HostelServiceSerialized(hostelService, hostelService.getHostel(), hostelService.getService()))
                .toList();
    }

    public HostelServiceSerialized getHostelServiceById(UUID id) {
        HostelServiceEntity hostelService = hostelServiceRepository.findOneOrFail(id);

        return new HostelServiceSerialized(hostelService, hostelService.getHostel(), hostelService.getService());
    }

    public HostelServiceSerialized createHostelService(HostelServiceRequest request) {
        HostelEntity hostel = hostelRepository.findOneOrFail(request.hostelId());
        ServiceEntity service = serviceRepository.findOneOrFail(request.serviceId());

        HostelServiceEntity hostelService = new HostelServiceEntity(hostel, service);
        HostelServiceEntity saved = hostelServiceRepository.save(hostelService);

        return new HostelServiceSerialized(saved, hostel, service);
    }

    public HostelServiceSerialized updateHostelService(UUID id, HostelServiceRequest request) {
        HostelServiceEntity hostelService = hostelServiceRepository.findOneOrFail(id);

        HostelServiceEntity updated = hostelServiceRepository.save(hostelService);

        return new HostelServiceSerialized(updated, updated.getHostel(), updated.getService());
    }

    public void deleteHostelService(UUID id) {
        HostelServiceEntity hostelService = hostelServiceRepository.findOneOrFail(id);

        hostelService.detach();
        hostelServiceRepository.delete(hostelService);
    }
}
