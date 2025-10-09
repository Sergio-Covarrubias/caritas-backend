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
import com.caritas.backend.core.reservations.dtos.CreateReservationResponse;
import com.caritas.backend.core.reservations.dtos.GetReservationResponse;
import com.caritas.backend.core.reservations.dtos.RepeatReservationRequest;
import com.caritas.backend.core.reservations.dtos.ReservationRequest;
import com.caritas.backend.core.reservations.dtos.ReservationResponse;
import com.caritas.backend.core.reservations.dtos.UserReservationsResponse;
import com.caritas.backend.core.reservations.entities.ReservationEntity;
import com.caritas.backend.core.reservations.entities.ReservationRepository;
import com.caritas.backend.core.service_interests.entities.ServiceInterestEntity;
import com.caritas.backend.core.services.entities.ServiceEntity;
import com.caritas.backend.core.services.entities.ServiceRepository;
import com.caritas.backend.core.users.entities.UserEntity;
import com.caritas.backend.core.users.entities.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final HostelRepository hostelRepository;
    private final PersonRepository personRepository;
    private final ServiceRepository serviceRepository;

    public ReservationService(ReservationRepository reservationRepository, UserRepository userRepository,
            HostelRepository hostelRepository, PersonRepository personRepository, ServiceRepository serviceRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.hostelRepository = hostelRepository;
        this.personRepository = personRepository;
        this.serviceRepository = serviceRepository;
    }

    public List<ReservationResponse> getAllReservations() {
        return reservationRepository.findAll()
                .stream()
                .map(reservation -> new ReservationResponse(reservation))
                .toList();
    }

    public GetReservationResponse getReservationById(UUID id) {
        ReservationEntity reservation = reservationRepository.findOneOrFail(id);

        List<PersonReservationEntity> personReservations = reservation.getPersonReservations();
        UUID[] personIds = personReservations.stream().map(personReservation -> personReservation.getPerson().getId())
                .toArray(UUID[]::new);

        List<ServiceInterestEntity> serviceInterests = reservation.getServiceInterests();
        UUID[] serviceIds = serviceInterests.stream().map(serviceInterest -> serviceInterest.getService().getId())
                .toArray(UUID[]::new);

        return new GetReservationResponse(reservation.getId(), reservation.getUser().getId(),
                reservation.getHostel().getId(), reservation.getStartDate(), reservation.getEndDate(), personIds,
                serviceIds);
    }

    public UserReservationsResponse getUserReservationHistory(String userId, int limit, int page) {
        // Get the active reservation if it exists
        Optional<ReservationEntity> activeReservationOpt = reservationRepository.findByUserIdAndActiveTrue(userId);
        UserReservationsResponse.UserReservation activeReservation = activeReservationOpt
                .map(UserReservationsResponse.UserReservation::new)
                .orElse(null);

        // Get the previous reservations
        List<UserReservationsResponse.UserReservation> previousReservations = reservationRepository
                .findAllByUserIdAndActiveFalse(userId)
                .stream()
                .map(UserReservationsResponse.UserReservation::new)
                .skip(limit * (page - 1)).limit(limit).toList();

        return new UserReservationsResponse(activeReservation, previousReservations);
    }

    @Transactional
    public CreateReservationResponse createReservation(CreateReservationRequest request) {
        boolean hasActiveReservation = reservationRepository.existsByUserIdAndActiveTrue(request.userId());
        if (hasActiveReservation) {
            throw new IllegalStateException("User already has an active reservation");
        }

        UserEntity user = userRepository.findOneOrFail(request.userId());
        HostelEntity hostel = hostelRepository.findOneOrFail(request.hostelId());

        ReservationEntity reservation = new ReservationEntity(user, hostel, request.startDate(), request.endDate());

        for (UUID personId : request.personIds()) {
            PersonEntity person = this.personRepository.findOneOrFail(personId);
            reservation.getPersonReservations().add(new PersonReservationEntity(person, reservation));
        }

        for (UUID serviceId : request.serviceIds()) {
            ServiceEntity service = this.serviceRepository.findOneOrFail(serviceId);
            reservation.getServiceInterests().add(new ServiceInterestEntity(reservation, service));
        }

        this.reservationRepository.save(reservation);

        return new CreateReservationResponse(reservation.getId(), user.getId(), hostel.getId(),
                reservation.getStartDate(), reservation.getEndDate(), request.personIds(), request.serviceIds());
    }

    public CreateReservationResponse repeatReservation(RepeatReservationRequest request) {
        ReservationEntity reservation = this.reservationRepository.findOneOrFail(request.reservationId());

        UUID[] personIds = reservation.getPersonReservations().stream()
                .map(personReservation -> personReservation.getPerson().getId())
                .toArray(UUID[]::new);

        UUID[] serviceIds = reservation.getServiceInterests().stream()
                .map(serviceInterest -> serviceInterest.getService().getId())
                .toArray(UUID[]::new);

        CreateReservationRequest reservationRequest = new CreateReservationRequest(reservation.getUser().getId(),
                reservation.getHostel().getId(), request.startDate(), request.endDate(), personIds, serviceIds);
        return this.createReservation(reservationRequest);
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
