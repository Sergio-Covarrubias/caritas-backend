package com.caritas.backend.core.reservations;

import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.caritas.backend.common.ValidateHeaderUserId;
import com.caritas.backend.core.reservations.dtos.CreateReservationRequest;
import com.caritas.backend.core.reservations.dtos.GetActiveReservationResponse;
import com.caritas.backend.core.reservations.dtos.GetUserTransportationReservationResponse;
import com.caritas.backend.core.reservations.dtos.RepeatReservationRequest;
import com.caritas.backend.core.reservations.dtos.ReservationSerialized;
import com.caritas.backend.core.reservations.dtos.UserReservationsResponse;
import com.caritas.backend.core.reservations.entities.ReservationState;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/{id}")
    public ReservationSerialized getReservationById(@PathVariable UUID id) {
        return reservationService.getReservationById(id);
    }

    @GetMapping("/user/{userId}")
    public GetActiveReservationResponse getUserActiveReservation(
            @RequestHeader(value = "x-user-id", required = false) String headerUserId,
            @PathVariable String userId) {
        ValidateHeaderUserId.validateOrThrow(headerUserId, userId);
        return reservationService.getUserActiveReservation(userId);
    }

    @GetMapping("/user/history/{userId}")
    public UserReservationsResponse getUserReservationHistory(
            @RequestHeader(value = "x-user-id", required = false) String headerUserId,
            @PathVariable String userId,
            @RequestParam(defaultValue = "5") int limit,
            @RequestParam(defaultValue = "1") int page) {
        ValidateHeaderUserId.validateOrThrow(headerUserId, userId);
        return reservationService.getUserReservationHistory(userId, limit, page);
    }

    @GetMapping("/user/transportation/{userId}")
    public GetUserTransportationReservationResponse getUserTransportationReservation(
            @RequestHeader(value = "x-user-id", required = false) String headerUserId,
            @PathVariable String userId) {
        ValidateHeaderUserId.validateOrThrow(headerUserId, userId);
        return reservationService.getUserTransportationReservation(userId);
    }

    @PostMapping
    public ReservationSerialized createReservation(
            @RequestHeader(value = "x-user-id", required = false) String headerUserId,
            @Valid @RequestBody CreateReservationRequest request) {
        ValidateHeaderUserId.validateOrThrow(headerUserId, request.userId());
        return reservationService.createReservation(request.userId(), request, ReservationState.PENDING);
    }

    @PostMapping("/repeat/{id}")
    public ReservationSerialized repeatReservation(@Valid @RequestBody RepeatReservationRequest request) {
        return reservationService.repeatReservation(request);
    }
}
