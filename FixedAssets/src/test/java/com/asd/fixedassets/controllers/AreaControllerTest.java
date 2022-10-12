package com.asd.fixedassets.controllers;

import com.asd.fixedassets.controllers.dtos.requests.AreaRequestDto;
import com.asd.fixedassets.controllers.dtos.responses.AreaResponseDto;
import com.asd.fixedassets.exceptions.FixedExceptionHandler;
import com.asd.fixedassets.services.AreaService;
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

import java.util.HashSet;
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
class AreaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private AreaController areaController;

    @Mock
    private AreaService areaService;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final Long ID = 1L;

    private AreaResponseDto areaResponseDto;


    @BeforeEach
    void setUp() {

        areaResponseDto = new AreaResponseDto();
        areaResponseDto.setAreaId(ID);
        areaResponseDto.setAreaName("NAME");
        areaResponseDto.setCity("CITY");
        areaResponseDto.setFixedAssets(new HashSet<>());

        mockMvc = MockMvcBuilders.standaloneSetup(areaController)
                .setControllerAdvice(new FixedExceptionHandler())
                .build();
    }


    @Test
    void shouldReturnAListOfAreasWhenConsultAll() throws Exception {
        when(areaService.getAllAreas()).thenReturn(List.of(areaResponseDto));

        String responseExpected = objectMapper.writeValueAsString(List.of(areaResponseDto));

        this.mockMvc.perform(
                        get("/api/v1/area"))
                .andExpect(status().isOk())
                .andExpect(content().string(responseExpected));

        verify(areaService, times(1)).getAllAreas();
    }

    @Test
    void shouldReturnAnAreaWhenConsultById() throws Exception {

        when(areaService.getAreaById(any(Long.class))).thenReturn(areaResponseDto);
        String responseExpected = objectMapper.writeValueAsString((areaResponseDto));

        this.mockMvc.perform(
                        get("/api/v1/area/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(responseExpected));

        verify(areaService, times(1)).getAreaById(areaResponseDto.getAreaId());
    }

    @Test
    void shouldSaveAnArea() throws Exception {
        AreaRequestDto areaRequestDto = new AreaRequestDto();
        areaRequestDto.setAreaName("NAME");
        areaRequestDto.setCity("CITY");

        when(areaService.saveArea(any(AreaRequestDto.class))).thenReturn(areaResponseDto);
        String responseExpected = objectMapper.writeValueAsString((areaResponseDto));
        String request = objectMapper.writeValueAsString((areaRequestDto));

        this.mockMvc.perform(
                        post("/api/v1/area/")
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(responseExpected));

        verify(areaService, times(1)).saveArea(areaRequestDto);
    }

    @Test
    void shouldUpdateAnArea() throws Exception {

        AreaRequestDto areaRequestDto = new AreaRequestDto();
        areaRequestDto.setAreaName("NAME");
        areaRequestDto.setCity("CITY");

        when(areaService.updateArea(any(Long.class), any(AreaRequestDto.class))).thenReturn(areaResponseDto);
        String responseExpected = objectMapper.writeValueAsString((areaResponseDto));
        String request = objectMapper.writeValueAsString((areaRequestDto));

        this.mockMvc.perform(
                        put("/api/v1/area/1")
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(responseExpected));

        verify(areaService, times(1)).updateArea(ID, areaRequestDto);
    }

}