package com.caritas.backend.core.hostels;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.caritas.backend.core.hostels.dtos.HostelPaginationResponse;
import com.caritas.backend.core.hostels.dtos.HostelRequest;
import com.caritas.backend.core.hostels.dtos.HostelResponse;

@RestController
@RequestMapping("/hostels")
public class HostelController {

    private final HostelService hostelService;

    public HostelController(HostelService hostelService) {
        this.hostelService = hostelService;
    }

    @GetMapping
    public List<HostelPaginationResponse> getAvailableHostels(
            @RequestParam(defaultValue = "5") int limit,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) List<String> filters,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        if (filters == null) {
            filters = List.of();
        }

        LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy/MM/dd"));

        return hostelService.getPaginatedHostels(limit, page, filters, start, end);
    }

    @GetMapping("/{id}")
    public HostelResponse getHostelById(@PathVariable UUID id) {
        return hostelService.getHostelById(id);
    }

    @PostMapping
    public HostelResponse createHostel(@RequestBody HostelRequest request) {
        return hostelService.createHostel(request);
    }

    @PutMapping("/{id}")
    public HostelResponse updateHostel(@PathVariable UUID id, @RequestBody HostelRequest request) {
        return hostelService.updateHostel(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteHostel(@PathVariable UUID id) {
        hostelService.deleteHostel(id);
    }
}
