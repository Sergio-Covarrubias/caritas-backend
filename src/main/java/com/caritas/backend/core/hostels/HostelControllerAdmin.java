package com.caritas.backend.core.hostels;

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

import com.caritas.backend.core.hostels.dtos.CreateHostelRequest;
import com.caritas.backend.core.hostels.dtos.HostelSerialized;
import com.caritas.backend.core.hostels.dtos.UpdateHostelRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin/hostels")
public class HostelControllerAdmin {

    private final HostelService hostelService;

    public HostelControllerAdmin(HostelService hostelService) {
        this.hostelService = hostelService;
    }

    @GetMapping()
    public List<HostelSerialized> getAllHostels() {
        return hostelService.getAllHostels();
    }

    @GetMapping("/{id}")
    public HostelSerialized getHostelById(@PathVariable UUID id) {
        return hostelService.getHostelById(id);
    }

    @PostMapping
    public HostelSerialized createHostel(@Valid @RequestBody CreateHostelRequest request) {
        return hostelService.createHostel(request);
    }

    @PutMapping("/{id}")
    public HostelSerialized updateHostel(@PathVariable UUID id, @Valid @RequestBody UpdateHostelRequest request) {
        return hostelService.updateHostel(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteHostel(@PathVariable UUID id) {
        hostelService.deleteHostel(id);
    }
}
