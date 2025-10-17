package com.caritas.backend.core.reservations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.caritas.backend.core.hostels.entities.HostelEntity;
import com.caritas.backend.core.hostels.entities.HostelRepository;
import com.caritas.backend.core.person_reservations.entities.PersonReservationEntity;
import com.caritas.backend.core.persons.entities.PersonEntity;
import com.caritas.backend.core.persons.entities.PersonRepository;
import com.caritas.backend.core.reservations.dtos.CreateReservationRequest;
import com.caritas.backend.core.reservations.dtos.GetReservationsDashboard;
import com.caritas.backend.core.reservations.dtos.RepeatReservationRequest;
import com.caritas.backend.core.reservations.dtos.ReservationRequest;
import com.caritas.backend.core.reservations.dtos.ReservationSerialized;
import com.caritas.backend.core.reservations.dtos.UserReservationsResponse;
import com.caritas.backend.core.reservations.entities.ReservationEntity;
import com.caritas.backend.core.reservations.entities.ReservationRepository;
import com.caritas.backend.core.reservations.entities.ReservationState;
import com.caritas.backend.core.users.entities.UserEntity;
import com.caritas.backend.core.users.entities.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final HostelRepository hostelRepository;
    private final PersonRepository personRepository;

    public ReservationService(ReservationRepository reservationRepository, UserRepository userRepository,
            HostelRepository hostelRepository, PersonRepository personRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.hostelRepository = hostelRepository;
        this.personRepository = personRepository;
    }

    public List<ReservationSerialized> getAllReservations() {
        return reservationRepository.findAll()
                .stream()
                .map(reservation -> new ReservationSerialized(reservation, reservation.getUser(),
                        reservation.getHostel(), null, null, false, false))
                .toList();
    }

    public ReservationSerialized getReservationById(UUID id) {
        ReservationEntity reservation = reservationRepository.findOneOrFail(id);

        return new ReservationSerialized(reservation, reservation.getUser(), reservation.getHostel(),
                reservation.getPersonReservations(), reservation.getServiceReservations(), true, true);
    }

    public UserReservationsResponse getUserReservationHistory(String userId, int limit, int page) {
        // Get the active reservation if it exists
        Optional<ReservationEntity> activeReservationOpt = reservationRepository.findByUserIdAndState(userId,
                ReservationState.ACTIVE);
        UserReservationsResponse.UserReservation activeReservation = activeReservationOpt
                .map(UserReservationsResponse.UserReservation::new)
                .orElse(null);

        // Get the previous reservations
        List<UserReservationsResponse.UserReservation> previousReservations = reservationRepository
                .findAllByUserIdAndStateNot(userId, ReservationState.ACTIVE)
                .stream()
                .map(UserReservationsResponse.UserReservation::new)
                .skip(limit * (page - 1)).limit(limit).toList();

        return new UserReservationsResponse(activeReservation, previousReservations);
    }

    public GetReservationsDashboard getReservationsDashboard() {
        GetReservationsDashboard.ReservationBody[] pendingReservations = this.reservationRepository
                .findAllByState(ReservationState.PENDING).stream()
                .map(reservation -> new GetReservationsDashboard.ReservationBody(reservation))
                .toArray(GetReservationsDashboard.ReservationBody[]::new);
        GetReservationsDashboard.ReservationBody[] activeReservations = this.reservationRepository
                .findAllByState(ReservationState.ACTIVE).stream()
                .map(reservation -> new GetReservationsDashboard.ReservationBody(reservation))
                .toArray(GetReservationsDashboard.ReservationBody[]::new);

        return new GetReservationsDashboard(pendingReservations, activeReservations);
    }

    @Transactional
    public ReservationSerialized createReservation(String userId, CreateReservationRequest request,
            ReservationState state) {
        if (state == ReservationState.PENDING || state == ReservationState.ACTIVE) {
            boolean hasActiveReservation = reservationRepository.existsByUserIdAndState(userId,
                    ReservationState.ACTIVE);
            if (hasActiveReservation) {
                throw new IllegalStateException("User already has an active reservation");
            }
        }

        UserEntity user = userRepository.findOneOrFail(userId);
        HostelEntity hostel = hostelRepository.findOneOrFail(request.hostelId());

        ReservationEntity reservation = new ReservationEntity(user, hostel, request.startDate(), request.endDate(), state);

        for (UUID personId : request.personIds()) {
            PersonEntity person = this.personRepository.findOneOrFail(personId);
            reservation.getPersonReservations().add(new PersonReservationEntity(person, reservation));
        }

        ReservationEntity saved = this.reservationRepository.save(reservation);

        return new ReservationSerialized(saved, user, hostel, saved.getPersonReservations(), null, true, false);
    }

    public ReservationSerialized repeatReservation(RepeatReservationRequest request) {
        ReservationEntity reservation = this.reservationRepository.findOneOrFail(request.reservationId());

        UUID[] personIds = reservation.getPersonReservations().stream()
                .map(personReservation -> personReservation.getPerson().getId())
                .toArray(UUID[]::new);

        CreateReservationRequest reservationRequest = new CreateReservationRequest(reservation.getHostel().getId(),
                request.startDate(), request.endDate(), personIds);
        return this.createReservation(reservation.getUser().getId(), reservationRequest, ReservationState.PENDING);
    }

    public ReservationSerialized updateReservation(UUID id, ReservationRequest request) {
        ReservationEntity reservation = reservationRepository.findOneOrFail(id);

        if (request.startDate() != null)
            reservation.setStartDate(request.startDate());
        if (request.endDate() != null)
            reservation.setEndDate(request.endDate());
        if (request.state() != null)
            reservation.setState(request.state());

        ReservationEntity updated = reservationRepository.save(reservation);

        return new ReservationSerialized(updated, null, null, null, null, false, false);
    }

    public void deleteReservation(UUID id) {
        ReservationEntity reservation = reservationRepository.findOneOrFail(id);

        reservation.detach();
        reservationRepository.delete(reservation);
    }
}
