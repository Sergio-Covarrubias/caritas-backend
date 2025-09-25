package com.caritas.backend.common;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class HealthCheckController {

    public HealthCheckController() {
    }

    public record HealthCheckResponse(String status) {
    }

    @GetMapping("/health")
    public HealthCheckResponse healthCheck() {
        return new HealthCheckResponse("UP");
    }
}
