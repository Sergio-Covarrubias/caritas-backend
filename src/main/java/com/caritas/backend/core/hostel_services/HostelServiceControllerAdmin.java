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

import com.caritas.backend.core.hostel_services.dtos.CreateHostelServiceRequest;
import com.caritas.backend.core.hostel_services.dtos.HostelServiceSerialized;

@RestController
@RequestMapping("/admin/hostel-services")
public class HostelServiceControllerAdmin {

    private final HostelServiceService hostelServiceService;

    public HostelServiceControllerAdmin(HostelServiceService hostelServiceService) {
        this.hostelServiceService = hostelServiceService;
    }

    @GetMapping
    public List<HostelServiceSerialized> getAllHostelServices() {
        return hostelServiceService.getAllHostelServices();
    }

    @GetMapping("/{id}")
    public HostelServiceSerialized getHostelServiceById(@PathVariable UUID id) {
        return hostelServiceService.getHostelServiceById(id);
    }

    @PostMapping
    public HostelServiceSerialized createHostelService(@RequestBody CreateHostelServiceRequest request) {
        return hostelServiceService.createHostelService(request);
    }

    @PutMapping("/{id}")
    public HostelServiceSerialized updateHostelService(@PathVariable UUID id, @RequestBody CreateHostelServiceRequest request) {
        return hostelServiceService.updateHostelService(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteHostelService(@PathVariable UUID id) {
        hostelServiceService.deleteHostelService(id);
    }
}
