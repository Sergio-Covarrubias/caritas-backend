package com.caritas.backend.core.hostels;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.caritas.backend.core.hostels.dtos.HostelPaginationResponse;
import com.caritas.backend.core.hostels.dtos.CreateHostelRequest;
import com.caritas.backend.core.hostels.dtos.HostelSerialized;
import com.caritas.backend.core.hostels.dtos.UpdateHostelRequest;
import com.caritas.backend.core.hostels.entities.HostelEntity;
import com.caritas.backend.core.hostels.entities.HostelRepository;
import com.caritas.backend.core.reservations.entities.ReservationState;

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
                endDate, ReservationState.ACTIVE);

        return availabilityRows.stream()
                .map(row -> {
                    HostelEntity hostel = (HostelEntity) row[0];
                    Long available = (Long) row[1];
                    return new HostelPaginationResponse(hostel, available.intValue());
                })
                .skip(limit * (page - 1)).limit(limit).toList();
    }

    public List<HostelSerialized> getAllHostels() {
        return hostelRepository.findAll()
                .stream()
                .map(hostel -> new HostelSerialized(hostel, hostel.getHostelServices(), null))
                .toList();
    }

    public HostelSerialized getHostelById(UUID id) {
        HostelEntity hostel = hostelRepository.findOneOrFail(id);

        return new HostelSerialized(hostel, hostel.getHostelServices(), null);
    }

    public HostelSerialized createHostel(CreateHostelRequest request) {
        HostelEntity hostel = new HostelEntity(request.name(), request.description(), request.price(),
                request.maxCapacity(), request.locationUrl(), request.imageUrls());
        HostelEntity saved = hostelRepository.save(hostel);

        return new HostelSerialized(saved, null, null);
    }

    public HostelSerialized updateHostel(UUID id, UpdateHostelRequest request) {
        HostelEntity hostel = hostelRepository.findOneOrFail(id);


        if (request.name() != null) hostel.setName(request.name());
        if (request.description() != null) hostel.setDescription(request.description());
        if (request.price() != null) hostel.setPrice(request.price());
        if (request.maxCapacity() != null) hostel.setMaxCapacity(request.maxCapacity());
        if (request.locationUrl() != null) hostel.setLocationUrl(request.locationUrl());
        if (request.imageUrls() != null) hostel.setImageUrls(request.imageUrls());

        HostelEntity updated = hostelRepository.save(hostel);

        return new HostelSerialized(updated, null, null);
    }

    public void deleteHostel(UUID id) {
        HostelEntity hostel = hostelRepository.findOneOrFail(id);
        hostelRepository.delete(hostel);
    }
}
