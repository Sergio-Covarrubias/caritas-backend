package com.caritas.backend.core.hostels;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

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

    public List<HostelPaginationResponse> getPaginatedHostels(List<String> filters,
            LocalDate startDate, LocalDate endDate, int limit, int page) {
        List<HostelEntity> allHostels = hostelRepository.findAll();

        List<HostelEntity> filtered = allHostels;
        if (filters != null && !filters.isEmpty()) {
            filtered = hostelRepository.filterHostelsByServices(allHostels, filters);
        }

        if (filtered.isEmpty()) {
            return List.of();
        }

        List<Object[]> availabilityRows = hostelRepository.calculateAvailabilityForHostels(filtered, startDate,
                endDate);

        return availabilityRows.stream()
                .map(row -> {
                    HostelEntity hostel = (HostelEntity) row[0];
                    Long available = (Long) row[1];
                    return new HostelPaginationResponse(hostel, available.intValue());
                })
                .skip(limit * (page - 1)).limit(limit).toList();
    }

    public HostelResponse getHostelById(UUID id) {
        HostelEntity hostel = hostelRepository.findOneOrFail(id);

        return new HostelResponse(hostel);
    }

    public HostelResponse createHostel(HostelRequest request) {
        HostelEntity hostel = new HostelEntity(request.name(), request.description(), request.maxCapacity(),
                request.locationUrl(), request.imageUrls());
        HostelEntity saved = hostelRepository.save(hostel);

        return new HostelResponse(saved);
    }

    public HostelResponse updateHostel(UUID id, HostelRequest request) {
        HostelEntity hostel = hostelRepository.findOneOrFail(id);

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
