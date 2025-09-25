package com.caritas.backend.core.hostels;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.caritas.backend.core.hostels.dtos.HostelRequest;
import com.caritas.backend.core.hostels.dtos.HostelResponse;
import com.caritas.backend.core.hostels.entities.HostelEntity;
import com.caritas.backend.core.hostels.entities.HostelRepository;

@Service
public class HostelService {

    private final HostelRepository hostelRepository;

    public HostelService(HostelRepository hostelRepository) {
        this.hostelRepository = hostelRepository;
    }

    public List<HostelResponse> getAllHostels() {
        return hostelRepository.findAll()
                .stream()
                .map(hostel -> new HostelResponse(hostel))
                .toList();
    }

    public HostelResponse getHostelById(UUID id) {
        HostelEntity hostel = hostelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hostel not found"));

        return new HostelResponse(hostel);
    }

    public HostelResponse createHostel(HostelRequest request) {
        HostelEntity hostel = new HostelEntity(request.name(), request.description());
        HostelEntity saved = hostelRepository.save(hostel);

        return new HostelResponse(saved);
    }

    public HostelResponse updateHostel(UUID id, HostelRequest request) {
        HostelEntity hostel = hostelRepository.findById(id).orElseThrow(() -> new RuntimeException("Hostel not found"));

        hostel.setName(request.name());
        hostel.setDescription(request.description());

        HostelEntity updated = hostelRepository.save(hostel);

        return new HostelResponse(updated);
    }

    public void deleteHostel(UUID id) {
        if (!hostelRepository.existsById(id)) {
            throw new RuntimeException("Hostel not found");
        }
        hostelRepository.deleteById(id);
    }
}
