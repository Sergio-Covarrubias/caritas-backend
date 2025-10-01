package com.caritas.backend.core.persons;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.caritas.backend.core.persons.dtos.PersonRequest;
import com.caritas.backend.core.persons.dtos.PersonResponse;
import com.caritas.backend.core.persons.entities.PersonEntity;
import com.caritas.backend.core.persons.entities.PersonRepository;
import com.caritas.backend.core.users.entities.UserEntity;
import com.caritas.backend.core.users.entities.UserRepository;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final UserRepository userRepository;

    public PersonService(PersonRepository personRepository, UserRepository userRepository) {
        this.personRepository = personRepository;
        this.userRepository = userRepository;
    }

    public List<PersonResponse> getAllPersons() {
        return personRepository.findAll()
                .stream()
                .map(person -> new PersonResponse(person))
                .toList();
    }

    public PersonResponse getPersonById(UUID id) {
        PersonEntity person = personRepository.findOneOrFail(id);

        return new PersonResponse(person);
    }

    public PersonResponse createPerson(PersonRequest request) {
        UserEntity user = this.userRepository.findOneOrFail(request.userId());

        PersonEntity person = new PersonEntity(user, request.firstName(), request.lastName(), request.age(),
                request.alergies(), request.discapacities(), request.medicines());

        PersonEntity saved = personRepository.save(person);

        return new PersonResponse(saved, user.getId());
    }

    public PersonResponse updatePerson(UUID id, PersonRequest request) {
        PersonEntity person = personRepository.findOneOrFail(id);

        person.setFirstName(request.firstName());
        person.setLastName(request.lastName());
        person.setAge(request.age());
        person.setAlergies(request.alergies());
        person.setDiscapacities(request.discapacities());
        person.setMedicines(request.medicines());

        PersonEntity updated = personRepository.save(person);

        return new PersonResponse(updated);
    }

    public void deletePerson(UUID id) {
        PersonEntity person = personRepository.findOneOrFail(id);

        person.detach();
        personRepository.deleteById(id);
    }
}
