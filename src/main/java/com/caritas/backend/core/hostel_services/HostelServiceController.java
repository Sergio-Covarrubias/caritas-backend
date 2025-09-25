package com.caritas.backend.core.hostel_services;

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

import com.caritas.backend.core.hostel_services.dtos.HostelServiceRequest;
import com.caritas.backend.core.hostel_services.dtos.HostelServiceResponse;

@RestController
@RequestMapping("/hostel-services")
public class HostelServiceController {

    private final HostelServiceService hostelServiceService;

    public HostelServiceController(HostelServiceService hostelServiceService) {
        this.hostelServiceService = hostelServiceService;
    }

    @GetMapping
    public List<HostelServiceResponse> getAllHostelServices() {
        return hostelServiceService.getAllHostelServices();
    }

    @GetMapping("/{id}")
    public HostelServiceResponse getHostelServiceById(@PathVariable UUID id) {
        return hostelServiceService.getHostelServiceById(id);
    }

    @PostMapping
    public HostelServiceResponse createHostelService(@RequestBody HostelServiceRequest request) {
        return hostelServiceService.createHostelService(request);
    }

    @PutMapping("/{id}")
    public HostelServiceResponse updateHostelService(@PathVariable UUID id, @RequestBody HostelServiceRequest request) {
        return hostelServiceService.updateHostelService(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteHostelService(@PathVariable UUID id) {
        hostelServiceService.deleteHostelService(id);
    }
}
