package com.caritas.backend.core.reservations;

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

import com.caritas.backend.core.reservations.dtos.CreateReservationRequest;
import com.caritas.backend.core.reservations.dtos.CreateReservationResponse;
import com.caritas.backend.core.reservations.dtos.GetReservationResponse;
import com.caritas.backend.core.reservations.dtos.RepeatReservationRequest;
import com.caritas.backend.core.reservations.dtos.ReservationRequest;
import com.caritas.backend.core.reservations.dtos.ReservationResponse;
import com.caritas.backend.core.reservations.dtos.UserReservationsResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public List<ReservationResponse> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @GetMapping("/{id}")
    public GetReservationResponse getReservationById(@PathVariable UUID id) {
        return reservationService.getReservationById(id);
    }

    @GetMapping("/user/{userId}")
    public UserReservationsResponse getUserReservationHistory(
            @PathVariable String userId,
            @RequestParam(defaultValue = "5") int limit,
            @RequestParam(defaultValue = "1") int page) {
        return reservationService.getUserReservationHistory(userId, limit, page);
    }

    @PostMapping
    public CreateReservationResponse createReservation(@Valid @RequestBody CreateReservationRequest request) {
        return reservationService.createReservation(request);
    }

    @PostMapping("/repeat/{id}")
    public CreateReservationResponse repeatReservation(@Valid @RequestBody RepeatReservationRequest request) {
        return reservationService.repeatReservation(request);
    }

    @PutMapping("/{id}")
    public ReservationResponse updateReservation(@PathVariable UUID id,
            @Valid @RequestBody ReservationRequest request) {
        return reservationService.updateReservation(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable UUID id) {
        reservationService.deleteReservation(id);
    }
}
