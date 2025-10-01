package com.caritas.backend.core.hostel_services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.caritas.backend.core.hostel_services.dtos.HostelServiceRequest;
import com.caritas.backend.core.hostel_services.dtos.HostelServiceResponse;
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

    public List<HostelServiceResponse> getAllHostelServices() {
        return hostelServiceRepository.findAll()
                .stream()
                .map(hostelService -> new HostelServiceResponse(hostelService))
                .toList();
    }

    public HostelServiceResponse getHostelServiceById(UUID id) {
        HostelServiceEntity hostelService = hostelServiceRepository.findOneOrFail(id);

        return new HostelServiceResponse(hostelService);
    }

    public HostelServiceResponse createHostelService(HostelServiceRequest request) {
        HostelEntity hostel = hostelRepository.findOneOrFail(request.hostelId());
        ServiceEntity service = serviceRepository.findOneOrFail(request.serviceId());

        HostelServiceEntity hostelService = new HostelServiceEntity(hostel, service);

        HostelServiceEntity saved = hostelServiceRepository.save(hostelService);

        return new HostelServiceResponse(saved, hostel.getId(), service.getId());
    }

    public HostelServiceResponse updateHostelService(UUID id, HostelServiceRequest request) {
        HostelServiceEntity hostelService = hostelServiceRepository.findOneOrFail(id);

        HostelServiceEntity updated = hostelServiceRepository.save(hostelService);

        return new HostelServiceResponse(updated);
    }

    public void deleteHostelService(UUID id) {
        HostelServiceEntity hostelService = hostelServiceRepository.findOneOrFail(id);

        hostelService.detach();
        hostelServiceRepository.deleteById(id);
    }
}
