package com.caritas.backend.core.persons;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.caritas.backend.core.persons.dtos.CreatePersonRequest;
import com.caritas.backend.core.persons.dtos.PersonSerialized;
import com.caritas.backend.core.persons.dtos.UpdatePersonRequest;
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

    public List<PersonSerialized> getAllPersons() {
        return personRepository.findAll()
                .stream()
                .map(person -> new PersonSerialized(person, person.getUser()))
                .toList();
    }

    public PersonSerialized getPersonById(UUID id) {
        PersonEntity person = personRepository.findOneOrFail(id);

        return new PersonSerialized(person, person.getUser());
    }

    public PersonSerialized createPerson(CreatePersonRequest request) {
        UserEntity user = this.userRepository.findOneOrFail(request.userId());

        PersonEntity person = new PersonEntity(user, request.firstName(), request.lastName(), request.birthDate(),
                request.alergies(), request.discapacities(), request.medicines());
        
        PersonEntity saved = personRepository.save(person);

        return new PersonSerialized(saved, person.getUser());
    }

    public PersonSerialized updatePerson(UUID id, UpdatePersonRequest request) {
        PersonEntity person = personRepository.findOneOrFail(id);

        if (request.firstName() != null) person.setFirstName(request.firstName());
        if (request.lastName() != null) person.setLastName(request.lastName());
        if (request.birthDate() != null) person.setBirthDate(request.birthDate());
        if (request.alergies() != null) person.setAlergies(request.alergies());
        if (request.discapacities() != null) person.setDiscapacities(request.discapacities());
        if (request.medicines() != null) person.setMedicines(request.medicines());

        PersonEntity updated = personRepository.save(person);

        return new PersonSerialized(updated, person.getUser());
    }

    public void deletePerson(UUID id) {
        PersonEntity person = personRepository.findOneOrFail(id);

        person.detach();
        personRepository.delete(person);
    }
}
