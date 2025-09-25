package com.caritas.backend.core.service_interests;

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

import com.caritas.backend.core.service_interests.dtos.ServiceInterestRequest;
import com.caritas.backend.core.service_interests.dtos.ServiceInterestResponse;

@RestController
@RequestMapping("/service-interests")
public class ServiceInterestController {

    private final ServiceInterestService serviceInterestService;

    public ServiceInterestController(ServiceInterestService serviceInterestService) {
        this.serviceInterestService = serviceInterestService;
    }

    @GetMapping
    public List<ServiceInterestResponse> getAllServiceInterests() {
        return serviceInterestService.getAllServiceInterests();
    }

    @GetMapping("/{id}")
    public ServiceInterestResponse getServiceInterestById(@PathVariable UUID id) {
        return serviceInterestService.getServiceInterestById(id);
    }

    @PostMapping
    public ServiceInterestResponse createServiceInterest(@RequestBody ServiceInterestRequest request) {
        return serviceInterestService.createServiceInterest(request);
    }

    @PutMapping("/{id}")
    public ServiceInterestResponse updateServiceInterest(@PathVariable UUID id, @RequestBody ServiceInterestRequest request) {
        return serviceInterestService.updateServiceInterest(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteServiceInterest(@PathVariable UUID id) {
        serviceInterestService.deleteServiceInterest(id);
    }
}
