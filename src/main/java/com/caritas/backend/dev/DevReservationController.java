package com.caritas.backend.dev;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.caritas.backend.core.reservations.ReservationService;
import com.caritas.backend.core.reservations.dtos.CreateReservationRequest;
import com.caritas.backend.core.reservations.dtos.ReservationSerialized;
import com.caritas.backend.core.reservations.entities.ReservationState;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/dev/reservations")
public class DevReservationController {
    public record DevCreateReservationRequest(
            @NotNull String userId,
            @NotNull UUID hostelId,
            @NotNull LocalDate startDate,
            LocalDate endDate,
            @NotEmpty UUID[] personIds,
            @NotNull ReservationState state) {
    }

    private final ReservationService reservationService;

    public DevReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping()
    public ReservationSerialized createReservationWithState(@Valid @RequestBody DevCreateReservationRequest request) {
        return this.reservationService.createReservation(
                request.userId,
                new CreateReservationRequest(request.userId(), request.hostelId(),
                        request.startDate(), request.endDate(), request.personIds()),
                request.state());
    }
}
