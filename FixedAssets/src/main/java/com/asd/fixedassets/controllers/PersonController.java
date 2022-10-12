package com.asd.fixedassets.controllers;

import com.asd.fixedassets.controllers.dtos.requests.PersonRequestDto;
import com.asd.fixedassets.controllers.dtos.responses.PersonResponseDto;
import com.asd.fixedassets.services.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RequiredArgsConstructor
@RestController
@RequestMapping(path = "api/v1/person", produces = APPLICATION_JSON_VALUE)
//@CrossOrigin(origins = {"http://localhost:4200"})
public class PersonController {

    private final PersonService personService;

    @GetMapping
    public List<PersonResponseDto> getAllPersons() {
        return personService.getAllPersons();
    }

    @GetMapping(value = "/{person-id}")
    public PersonResponseDto getPersonById(@PathVariable("person-id") Long personId) {
        return personService.getPersonById(personId);
    }

    @PostMapping
    public PersonResponseDto savePerson(@Valid @RequestBody PersonRequestDto personDto) {
        return personService.savePerson(personDto);
    }

    @PutMapping(value = "/{person-id}")
    public PersonResponseDto updatePerson(@PathVariable("person-id") Long personId, @RequestBody PersonRequestDto personDto) {
        return personService.updatePerson(personId, personDto);
    }

}
