package com.caritas.backend.core.person_reservations;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.caritas.backend.core.person_reservations.dtos.PersonReservationRequest;
import com.caritas.backend.core.person_reservations.dtos.PersonReservationResponse;
import com.caritas.backend.core.person_reservations.entities.PersonReservationEntity;
import com.caritas.backend.core.person_reservations.entities.PersonReservationRepository;
import com.caritas.backend.core.persons.entities.PersonEntity;
import com.caritas.backend.core.persons.entities.PersonRepository;
import com.caritas.backend.core.reservations.entities.ReservationEntity;
import com.caritas.backend.core.reservations.entities.ReservationRepository;

@Service
public class PersonReservationService {

    private final PersonReservationRepository personReservationRepository;
    private final PersonRepository personRepository;
    private final ReservationRepository reservationRepository;

    public PersonReservationService(PersonReservationRepository personReservationRepository,
            PersonRepository personRepository,
            ReservationRepository reservationRepository) {
        this.personReservationRepository = personReservationRepository;
        this.personRepository = personRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<PersonReservationResponse> getAllPersonReservations() {
        return personReservationRepository.findAll()
                .stream()
                .map(personReservation -> new PersonReservationResponse(personReservation))
                .toList();
    }

    public PersonReservationResponse getPersonReservationById(UUID id) {
        PersonReservationEntity personReservation = personReservationRepository.findOneOrFail(id);

        return new PersonReservationResponse(personReservation);
    }

    public PersonReservationResponse createPersonReservation(PersonReservationRequest request) {
        PersonEntity person = personRepository.findOneOrFail(request.personId());
        ReservationEntity reservation = reservationRepository.findOneOrFail(request.reservationId());

        PersonReservationEntity personReservation = new PersonReservationEntity(person, reservation);

        PersonReservationEntity saved = personReservationRepository.save(personReservation);

        return new PersonReservationResponse(saved, person.getId(), reservation.getId());
    }

    public PersonReservationResponse updatePersonReservation(UUID id, PersonReservationRequest request) {
        PersonReservationEntity personReservation = personReservationRepository.findOneOrFail(id);

        PersonReservationEntity updated = personReservationRepository.save(personReservation);

        return new PersonReservationResponse(updated);
    }

    public void deletePersonReservation(UUID id) {
        PersonReservationEntity personReservation = personReservationRepository.findOneOrFail(id);

        personReservation.detach();
        personReservationRepository.delete(personReservation);
    }
}
