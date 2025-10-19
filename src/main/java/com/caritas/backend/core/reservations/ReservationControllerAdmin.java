package com.caritas.backend.core.reservations;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.caritas.backend.core.reservations.dtos.ReservationSerialized;
import com.caritas.backend.core.reservations.dtos.UpdateReservationRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin/reservations")
public class ReservationControllerAdmin {

    private final ReservationService reservationService;

    public ReservationControllerAdmin(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public List<ReservationSerialized> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @GetMapping("/{id}")
    public ReservationSerialized getReservationById(@PathVariable UUID id) {
        return reservationService.getReservationById(id);
    }

    @PutMapping("/{id}")
    public ReservationSerialized updateReservation(@PathVariable UUID id,
            @Valid @RequestBody UpdateReservationRequest request) {
        return reservationService.updateReservation(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable UUID id) {
        reservationService.deleteReservation(id);
    }
}
