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

import io.micrometer.common.lang.NonNull;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

@RestController
@RequestMapping("/dev/reservations")
public class DevReservationController {
    public record DevCreateReservationRequest(
            @NonNull String userId,
            @NonNull UUID hostelId,
            @NonNull LocalDate startDate,
            @NonNull LocalDate endDate,
            @NotEmpty UUID[] personIds,
            @NonNull ReservationState state) {
    }

    private final ReservationService reservationService;

    public DevReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping()
    public ReservationSerialized createReservationWithState(@Valid @RequestBody DevCreateReservationRequest request) {
        return this.reservationService.createReservation(
                request.userId,
                new CreateReservationRequest(request.hostelId(),
                        request.startDate(), request.endDate(), request.personIds()),
                request.state());
    }
}
