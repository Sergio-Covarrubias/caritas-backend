package com.caritas.backend.core.hostels;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.caritas.backend.core.hostels.dtos.HostelPaginationResponse;
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

    public List<HostelPaginationResponse> getPaginatedHostels(
            int limit,
            int page,
            List<String> filters,
            LocalDate startDate,
            LocalDate endDate) {
        Pageable pageable = PageRequest.of(page - 1, limit);

        Page<Object[]> results = hostelRepository.findPaginatedHostels(
                startDate, endDate, filters, filters.size(), pageable);

        return results.stream()
                .map(row -> {
                    HostelEntity hostel = (HostelEntity) row[0];
                    Integer availableSpaces = ((Number) row[1]).intValue();
                    return new HostelPaginationResponse(hostel, availableSpaces);
                })
                .toList();
    }

    public HostelResponse getHostelById(UUID id) {
        HostelEntity hostel = hostelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hostel not found"));

        return new HostelResponse(hostel);
    }

    public HostelResponse createHostel(HostelRequest request) {
        HostelEntity hostel = new HostelEntity(request.name(), request.description(), request.maxCapacity(),
                request.locationUrl(), request.imageUrls());
        HostelEntity saved = hostelRepository.save(hostel);

        return new HostelResponse(saved);
    }

    public HostelResponse updateHostel(UUID id, HostelRequest request) {
        HostelEntity hostel = hostelRepository.findById(id).orElseThrow(() -> new RuntimeException("Hostel not found"));

        hostel.setName(request.name());
        hostel.setDescription(request.description());
        hostel.setMaxCapacity(request.maxCapacity());
        hostel.setLocationUrl(request.locationUrl());
        hostel.setImageUrls(request.imageUrls());

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
