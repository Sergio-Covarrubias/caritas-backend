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

import com.caritas.backend.core.persons.dtos.PersonRequest;
import com.caritas.backend.core.persons.dtos.PersonResponse;

@RestController
@RequestMapping("/persons")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public List<PersonResponse> getAllPersons() {
        return personService.getAllPersons();
    }

    @GetMapping("/{id}")
    public PersonResponse getPersonById(@PathVariable UUID id) {
        return personService.getPersonById(id);
    }

    @PostMapping
    public PersonResponse createPerson(@RequestBody PersonRequest request) {
        return personService.createPerson(request);
    }

    @PutMapping("/{id}")
    public PersonResponse updatePerson(@PathVariable UUID id, @RequestBody PersonRequest request) {
        return personService.updatePerson(id, request);
    }

    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable UUID id) {
        personService.deletePerson(id);
    }
}
