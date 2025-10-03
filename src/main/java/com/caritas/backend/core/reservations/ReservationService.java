package com.caritas.backend.core.reservations;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.caritas.backend.core.hostels.entities.HostelEntity;
import com.caritas.backend.core.hostels.entities.HostelRepository;
import com.caritas.backend.core.person_reservations.entities.PersonReservationEntity;
import com.caritas.backend.core.person_reservations.entities.PersonReservationRepository;
import com.caritas.backend.core.persons.entities.PersonEntity;
import com.caritas.backend.core.persons.entities.PersonRepository;
import com.caritas.backend.core.reservations.dtos.CreateReservationRequest;
import com.caritas.backend.core.reservations.dtos.CreateReservationResponse;
import com.caritas.backend.core.reservations.dtos.ReservationRequest;
import com.caritas.backend.core.reservations.dtos.ReservationResponse;
import com.caritas.backend.core.reservations.entities.ReservationEntity;
import com.caritas.backend.core.reservations.entities.ReservationRepository;
import com.caritas.backend.core.service_interests.entities.ServiceInterestEntity;
import com.caritas.backend.core.service_interests.entities.ServiceInterestRepository;
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
    private final PersonReservationRepository personReservationRepository;
    private final ServiceInterestRepository serviceInterestRepository;

    public ReservationService(ReservationRepository reservationRepository, UserRepository userRepository,
            HostelRepository hostelRepository, PersonRepository personRepository, ServiceRepository serviceRepository,
            PersonReservationRepository personReservationRepository,
            ServiceInterestRepository serviceInterestRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.hostelRepository = hostelRepository;
        this.personRepository = personRepository;
        this.serviceRepository = serviceRepository;
        this.personReservationRepository = personReservationRepository;
        this.serviceInterestRepository = serviceInterestRepository;
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

    @Transactional
    public CreateReservationResponse createReservation(CreateReservationRequest request) {
        boolean hasActiveReservation = reservationRepository.existsByUserIdAndActiveTrue(request.userId());
        if (hasActiveReservation) {
            throw new IllegalStateException("User already has an active reservation");
        }

        UserEntity user = userRepository.findOneOrFail(request.userId());
        HostelEntity hostel = hostelRepository.findOneOrFail(request.hostelId());

        PersonEntity[] persons = Arrays.stream(request.personIds())
                .map(personRepository::findOneOrFail).toArray(PersonEntity[]::new);
        ServiceEntity[] services = Arrays.stream(request.serviceIds())
                .map(serviceRepository::findOneOrFail).toArray(ServiceEntity[]::new);

        ReservationEntity reservation = reservationRepository
                .save(new ReservationEntity(user, hostel, request.startDate(), request.endDate()));

        UUID[] personReservationIds = Arrays.stream(persons).map(
                person -> personReservationRepository.save(new PersonReservationEntity(person, reservation)).getId())
                .toArray(UUID[]::new);
        UUID[] serviceInterestIds = Arrays.stream(services)
                .map(service -> serviceInterestRepository.save(new ServiceInterestEntity(reservation, service)).getId())
                .toArray(UUID[]::new);

        return new CreateReservationResponse(reservation.getId(), user.getId(), hostel.getId(),
                reservation.getStartDate(), reservation.getEndDate(), personReservationIds, serviceInterestIds);
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
