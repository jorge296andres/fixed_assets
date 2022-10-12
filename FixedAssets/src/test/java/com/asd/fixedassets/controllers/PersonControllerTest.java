package com.asd.fixedassets.controllers;

import com.asd.fixedassets.controllers.dtos.requests.PersonRequestDto;
import com.asd.fixedassets.controllers.dtos.responses.PersonResponseDto;
import com.asd.fixedassets.exceptions.FixedExceptionHandler;
import com.asd.fixedassets.services.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private PersonController personController;

    @Mock
    private PersonService personService;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final Long ID = 1L;

    private PersonResponseDto personResponseDto;


    @BeforeEach
    void setUp() {

        personResponseDto = new PersonResponseDto();
        personResponseDto.setPersonId(ID);
        personResponseDto.setPersonName("NAME");
        personResponseDto.setDocumentNumber("CC");

        mockMvc = MockMvcBuilders.standaloneSetup(personController)
                .setControllerAdvice(new FixedExceptionHandler())
                .build();
    }


    @Test
    void shouldReturnAListOfPersonsWhenConsultAll() throws Exception {
        when(personService.getAllPersons()).thenReturn(List.of(personResponseDto));

        String responseExpected = objectMapper.writeValueAsString(List.of(personResponseDto));

        this.mockMvc.perform(
                        get("/api/v1/person"))
                .andExpect(status().isOk())
                .andExpect(content().string(responseExpected));

        verify(personService, times(1)).getAllPersons();
    }

    @Test
    void shouldReturnAPersonWhenConsultById() throws Exception {

        when(personService.getPersonById(any(Long.class))).thenReturn(personResponseDto);
        String responseExpected = objectMapper.writeValueAsString((personResponseDto));

        this.mockMvc.perform(
                        get("/api/v1/person/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(responseExpected));

        verify(personService, times(1)).getPersonById(personResponseDto.getPersonId());
    }

    @Test
    void shouldSaveAPerson() throws Exception {
        PersonRequestDto personRequestDto = new PersonRequestDto();
        personRequestDto.setDocumentType("CC");
        personRequestDto.setPersonName("NAME");
        personRequestDto.setDocumentNumber("123456789");

        when(personService.savePerson(any(PersonRequestDto.class))).thenReturn(personResponseDto);
        String responseExpected = objectMapper.writeValueAsString((personResponseDto));
        String request = objectMapper.writeValueAsString((personRequestDto));

        this.mockMvc.perform(
                        post("/api/v1/person/")
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(responseExpected));

        verify(personService, times(1)).savePerson(personRequestDto);
    }

    @Test
    void shouldUpdateAPerson() throws Exception {

        PersonRequestDto personRequestDto = new PersonRequestDto();
        personRequestDto.setDocumentType("CC");
        personRequestDto.setPersonName("NAME");
        personRequestDto.setDocumentNumber("123456789");

        when(personService.updatePerson(any(Long.class), any(PersonRequestDto.class))).thenReturn(personResponseDto);
        String responseExpected = objectMapper.writeValueAsString((personResponseDto));
        String request = objectMapper.writeValueAsString((personRequestDto));

        this.mockMvc.perform(
                        put("/api/v1/person/1")
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(responseExpected));

        verify(personService, times(1)).updatePerson(ID, personRequestDto);
    }

}