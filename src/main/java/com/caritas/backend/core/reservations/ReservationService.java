package com.caritas.backend.core.reservations;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.caritas.backend.core.hostels.entities.HostelEntity;
import com.caritas.backend.core.hostels.entities.HostelRepository;
import com.caritas.backend.core.reservations.dtos.ReservationRequest;
import com.caritas.backend.core.reservations.dtos.ReservationResponse;
import com.caritas.backend.core.reservations.entities.ReservationEntity;
import com.caritas.backend.core.reservations.entities.ReservationRepository;
import com.caritas.backend.core.users.entities.UserEntity;
import com.caritas.backend.core.users.entities.UserRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final HostelRepository hostelRepository;

    public ReservationService(ReservationRepository reservationRepository, UserRepository userRepository,
            HostelRepository hostelRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.hostelRepository = hostelRepository;
    }

    public List<ReservationResponse> getAllReservations() {
        return reservationRepository.findAll()
                .stream()
                .map(reservation -> new ReservationResponse(reservation))
                .toList();
    }

    public ReservationResponse getReservationById(UUID id) {
        ReservationEntity reservation = reservationRepository.findOneOrFail(id);

        return new ReservationResponse(reservation);
    }

    public ReservationResponse createReservation(ReservationRequest request) {
        UserEntity user = userRepository.findOneOrFail(request.userId());
        HostelEntity hostel = hostelRepository.findOneOrFail(request.hostelId());

        ReservationEntity reservation = new ReservationEntity(user, hostel, request.startDate(), request.endDate());
        ReservationEntity saved = reservationRepository.save(reservation);

        return new ReservationResponse(saved, user.getId(), hostel.getId());
    }

    public ReservationResponse updateReservation(UUID id, ReservationRequest request) {
        ReservationEntity reservation = reservationRepository.findOneOrFail(id);

        reservation.setStartDate(request.startDate());
        reservation.setEndDate(request.endDate());
        reservation.setActive(request.active());

        ReservationEntity updated = reservationRepository.save(reservation);

        return new ReservationResponse(updated);
    }

    public void deleteReservation(UUID id) {
        ReservationEntity reservation = reservationRepository.findOneOrFail(id);

        reservation.detach();
        reservationRepository.deleteById(id);
    }
}
