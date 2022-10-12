package com.asd.fixedassets.services;

import com.asd.fixedassets.controllers.dtos.requests.PersonRequestDto;
import com.asd.fixedassets.controllers.dtos.responses.PersonResponseDto;
import com.asd.fixedassets.domain.FixedAssets;
import com.asd.fixedassets.domain.Person;
import com.asd.fixedassets.exceptions.BusinessException;
import com.asd.fixedassets.exceptions.messages.BusinessExceptionMessage;
import com.asd.fixedassets.repositories.IPersonRepository;
import com.asd.fixedassets.services.mappers.IPersonMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @Mock
    private IPersonRepository personRepository;

    @Mock
    private IPersonMapper mapper;

    @InjectMocks
    private PersonService personService;

    private Person person;
    private static final String FIXED_DESCRIPTION = "FIXED CREATED IN UNIT TEST";

    @BeforeEach
    void setUp() {
        person = new Person();
        person.setPersonId(1L);
        person.setPersonName("Pedro");
        person.setDocumentNumber("12345");
        person.setDocumentType("CC");
    }


    @Test
    void shouldReturnAListOfPersonsWhenConsultAll() {
        when(personRepository.findAll()).thenReturn(List.of(person));
        assertNotNull(personService.getAllPersons());
    }

    @Test
    void ShouldReturnAPersonDtoWhenConsulById() {
        when(personRepository.findById(person.getPersonId())).thenReturn(Optional.of(person));
        when(mapper.toData(person)).thenReturn(new PersonResponseDto());
        assertNotNull(personService.getPersonById(person.getPersonId()));
    }

    @Test
    void shouldThrowABusinessExceptionWhenNotFoundPersonById() {
        when(personRepository.findById(person.getPersonId())).thenReturn(Optional.empty());

        assertEquals(assertThrowsExactly(BusinessException.class, () -> personService.getPersonById(1L))
                .getMessage(), BusinessExceptionMessage.PERSON_NOT_FOUND.getMessage());
    }

    @Test
    void shouldSaveAPerson() {
        PersonRequestDto personRequestDto = new PersonRequestDto();
        when(mapper.toEntity(personRequestDto)).thenReturn(person);
        when(mapper.toData(person)).thenReturn(new PersonResponseDto());
        when(personRepository.save(person)).thenReturn(person);
        assertNotNull(personService.savePerson(personRequestDto));
    }

    @Test
    void shouldUpdateAPerson() {
        PersonRequestDto personRequestDto = new PersonRequestDto();
        when(personRepository.findById(person.getPersonId())).thenReturn(Optional.of(person));
        when(mapper.toData(person)).thenReturn(new PersonResponseDto());
        when(personRepository.save(person)).thenReturn(person);
        assertNotNull(personService.updatePerson(person.getPersonId(), personRequestDto));
    }

    @Test
    void shouldThrowABusinessExceptionWhenNotFoundPersonByIdOnMethodUpdatePerson() {
        PersonRequestDto personRequestDto = new PersonRequestDto();
        when(personRepository.findById(person.getPersonId())).thenReturn(Optional.empty());
        assertEquals(assertThrowsExactly(BusinessException.class,
                () -> personService.updatePerson(1L, personRequestDto))
                .getMessage(), BusinessExceptionMessage.PERSON_NOT_FOUND.getMessage());
    }

    @Test
    void shouldAssignAFixedOfThePerson() {
        when(personRepository.findById(person.getPersonId())).thenReturn(Optional.of(person));
        when(personRepository.save(person)).thenReturn(person);
        FixedAssets fixed = new FixedAssets();
        fixed.setDescription(FIXED_DESCRIPTION);
        personService.assignFixed(person.getPersonId(), fixed);
        assertTrue(person.getFixedAssets().contains(fixed));

    }

    @Test
    void shouldThrowABusinessExceptionWhenNotFoundPersonByIdOnMethodAssignFixed() {
        PersonRequestDto personRequestDto = new PersonRequestDto();
        FixedAssets fixed = new FixedAssets();
        when(personRepository.findById(person.getPersonId())).thenReturn(Optional.empty());
        assertEquals(assertThrowsExactly(BusinessException.class,
                () -> personService.assignFixed(1L, fixed))
                .getMessage(), BusinessExceptionMessage.PERSON_NOT_FOUND.getMessage());
    }

    @Test
    void shouldRemoveAFixedOfThePerson() {
        FixedAssets fixed = new FixedAssets();
        fixed.setDescription(FIXED_DESCRIPTION);
        person.addFixedAssets(fixed);
        when(personRepository.findById(person.getPersonId())).thenReturn(Optional.of(person));
        when(personRepository.save(person)).thenReturn(person);
        personService.removeFixed(person.getPersonId(), fixed);
        assertTrue(person.getFixedAssets().isEmpty());

    }

    @Test
    void shouldThrowABusinessExceptionWhenNotFoundPersonByIdOnMethodRemoveFixed() {
        PersonRequestDto personRequestDto = new PersonRequestDto();
        FixedAssets fixed = new FixedAssets();
        when(personRepository.findById(person.getPersonId())).thenReturn(Optional.empty());
        assertEquals(assertThrowsExactly(BusinessException.class,
                () -> personService.removeFixed(1L, fixed))
                .getMessage(), BusinessExceptionMessage.PERSON_NOT_FOUND.getMessage());
    }
}