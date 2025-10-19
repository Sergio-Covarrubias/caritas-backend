package com.caritas.backend.core.persons;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.caritas.backend.core.persons.dtos.CreatePersonRequest;
import com.caritas.backend.core.persons.dtos.PersonSerialized;
import com.caritas.backend.core.persons.dtos.UpdatePersonRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin/persons")
public class PersonControllerAdmin {

    private final PersonService personService;

    public PersonControllerAdmin(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public List<PersonSerialized> getAllPersons() {
        return personService.getAllPersons();
    }

    @GetMapping("/{id}")
    public PersonSerialized getPersonById(@PathVariable UUID id) {
        return personService.getPersonById(id);
    }

    @PostMapping
    public PersonSerialized createPerson(@Valid @RequestBody CreatePersonRequest request) {
        return personService.createPerson(request);
    }

    @PutMapping("/{id}")
    public PersonSerialized updatePerson(@PathVariable UUID id, @Valid @RequestBody UpdatePersonRequest request) {
        return personService.updatePerson(id, request);
    }

    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable UUID id) {
        personService.deletePerson(id);
    }
}
