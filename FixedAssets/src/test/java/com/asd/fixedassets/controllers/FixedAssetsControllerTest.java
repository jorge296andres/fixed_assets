package com.asd.fixedassets.controllers;

import com.asd.fixedassets.controllers.dtos.requests.AssignFixedToAreaRequestDto;
import com.asd.fixedassets.controllers.dtos.requests.AssignFixedToPersonRequestDto;
import com.asd.fixedassets.controllers.dtos.requests.FixedAssetsRequestDto;
import com.asd.fixedassets.controllers.dtos.responses.FixedResponseDto;
import com.asd.fixedassets.exceptions.BusinessException;
import com.asd.fixedassets.exceptions.FixedExceptionHandler;
import com.asd.fixedassets.exceptions.TechnicalException;
import com.asd.fixedassets.exceptions.messages.BusinessExceptionMessage;
import com.asd.fixedassets.exceptions.messages.TechnicalExceptionMessage;
import com.asd.fixedassets.services.FixedAssetsService;
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

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
class FixedAssetsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private FixedAssetsController fixedAssetsController;

    @Mock
    private FixedAssetsService fixedAssetsService;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final Long ID = 1L;

    private static final String SERIAL = UUID.randomUUID().toString();

    private FixedResponseDto fixedResponseDto;


    @BeforeEach
    void setUp() {

        fixedResponseDto = new FixedResponseDto();
        fixedResponseDto.setInventoryId(ID);
        fixedResponseDto.setDescription("DESCRIPTION");
        fixedResponseDto.setName("NAME");
        fixedResponseDto.setHeight(1.0);
        fixedResponseDto.setLength(1.0);
        fixedResponseDto.setWeight(1.0);
        fixedResponseDto.setWidth(1.0);
        fixedResponseDto.setPurchaseValue(1.0);
        fixedResponseDto.setSerial(SERIAL);
        fixedResponseDto.setType("TYPE");

        mockMvc = MockMvcBuilders.standaloneSetup(fixedAssetsController)
                .setControllerAdvice(new FixedExceptionHandler())
                .build();
    }


    @Test
    void shouldReturnAListOfFixedAssetsWhenConsultAll() throws Exception {
        when(fixedAssetsService.getAllFixedAssets()).thenReturn(List.of(fixedResponseDto));

        String responseExpected = objectMapper.writeValueAsString(List.of(fixedResponseDto));

        this.mockMvc.perform(
                        get("/api/v1/fixed-assets/"))
                .andExpect(status().isOk())
                .andExpect(content().string(responseExpected));

        verify(fixedAssetsService, times(1)).getAllFixedAssets();
    }

    @Test
    void shouldReturnAFixedWhenConsultById() throws Exception {

        when(fixedAssetsService.getFixedById(any(Long.class))).thenReturn(fixedResponseDto);
        String responseExpected = objectMapper.writeValueAsString((fixedResponseDto));

        this.mockMvc.perform(
                        get("/api/v1/fixed-assets/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(responseExpected));

        verify(fixedAssetsService, times(1)).getFixedById(fixedResponseDto.getInventoryId());
    }

    @Test
    void shouldReturnAListOfFixedAssetsWhenConsultByType() throws Exception {
        fixedResponseDto.setType("ELECTRIC");
        var response = List.of(fixedResponseDto);
        when(fixedAssetsService.getFixedByType(any(String.class))).thenReturn(response);
        String responseExpected = objectMapper.writeValueAsString((response));

        this.mockMvc.perform(
                        get("/api/v1/fixed-assets/get-by-type/".concat(fixedResponseDto.getType())))
                .andExpect(status().isOk())
                .andExpect(content().string(responseExpected));

        verify(fixedAssetsService, times(1)).getFixedByType(fixedResponseDto.getType());
    }

    @Test
    void shouldReturnAFixedAssetWhenBySerial() throws Exception {
        fixedResponseDto.setSerial(UUID.randomUUID().toString());
        var response = fixedResponseDto;
        when(fixedAssetsService.getFixedBySerial(any(String.class))).thenReturn(response);
        String responseExpected = objectMapper.writeValueAsString((response));

        this.mockMvc.perform(
                        get("/api/v1/fixed-assets/get-by-serial/".concat(fixedResponseDto.getSerial())))
                .andExpect(status().isOk())
                .andExpect(content().string(responseExpected));

        verify(fixedAssetsService, times(1)).getFixedBySerial(fixedResponseDto.getSerial());
    }

    @Test
    void shouldReturnAListOfFixedAssetsWhenByPurchaseDate() throws Exception {
        var response = List.of(fixedResponseDto);
        when(fixedAssetsService.getFixedByPurchaseDate(any(LocalDate.class))).thenReturn(response);
        String responseExpected = objectMapper.writeValueAsString((response));
        var date = LocalDate.of(2022, 10, 6);
        this.mockMvc.perform(
                        get("/api/v1/fixed-assets/get-by-purchase-date/"
                                .concat(date.toString())))
                .andExpect(status().isOk())
                .andExpect(content().string(responseExpected));

        verify(fixedAssetsService, times(1))
                .getFixedByPurchaseDate(date);
    }

    @Test
    void shouldSaveAFixedAsset() throws Exception {
        FixedAssetsRequestDto fixedAssetsRequestDto = new FixedAssetsRequestDto();
        fixedAssetsRequestDto.setDescription("DESCRIPTION");
        fixedAssetsRequestDto.setName("NAME");
        fixedAssetsRequestDto.setHeight(1.0);
        fixedAssetsRequestDto.setLength(1.0);
        fixedAssetsRequestDto.setWeight(1.0);
        fixedAssetsRequestDto.setWidth(1.0);
        fixedAssetsRequestDto.setPurchaseValue(1.0);
        fixedAssetsRequestDto.setSerial(SERIAL);
        fixedAssetsRequestDto.setType("TYPE");

        when(fixedAssetsService.saveFixedAsset(any(FixedAssetsRequestDto.class))).thenReturn(fixedResponseDto);
        String responseExpected = objectMapper.writeValueAsString((fixedResponseDto));
        String request = objectMapper.writeValueAsString((fixedAssetsRequestDto));

        this.mockMvc.perform(
                        post("/api/v1/fixed-assets/")
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(responseExpected));

        verify(fixedAssetsService, times(1)).saveFixedAsset(fixedAssetsRequestDto);
    }

    @Test
    void shouldUpdateAFixedAsset() throws Exception {
        FixedAssetsRequestDto fixedAssetsRequestDto = new FixedAssetsRequestDto();
        fixedAssetsRequestDto.setDescription("DESCRIPTION");
        fixedAssetsRequestDto.setName("NAME");
        fixedAssetsRequestDto.setHeight(1.0);
        fixedAssetsRequestDto.setLength(1.0);
        fixedAssetsRequestDto.setWeight(1.0);
        fixedAssetsRequestDto.setWidth(1.0);
        fixedAssetsRequestDto.setPurchaseValue(1.0);
        fixedAssetsRequestDto.setSerial(SERIAL);
        fixedAssetsRequestDto.setType("TYPE");

        when(fixedAssetsService.updateFixedAsset(any(Long.class), any(FixedAssetsRequestDto.class)))
                .thenReturn(fixedResponseDto);
        String responseExpected = objectMapper.writeValueAsString((fixedResponseDto));
        String request = objectMapper.writeValueAsString((fixedAssetsRequestDto));

        this.mockMvc.perform(
                        put("/api/v1/fixed-assets/1")
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(responseExpected));

        verify(fixedAssetsService, times(1)).updateFixedAsset(ID, fixedAssetsRequestDto);
    }

    @Test
    void shouldThrowAExceptionForMalformedPayload() throws Exception {
        FixedAssetsRequestDto fixedAssetsRequestDto = new FixedAssetsRequestDto();
        fixedAssetsRequestDto.setDescription("DESCRIPTION");
        fixedAssetsRequestDto.setName("NAME");
        fixedAssetsRequestDto.setHeight(1.0);
        fixedAssetsRequestDto.setLength(1.0);
        fixedAssetsRequestDto.setWeight(1.0);
        fixedAssetsRequestDto.setWidth(1.0);
        fixedAssetsRequestDto.setPurchaseValue(1.0);
        fixedAssetsRequestDto.setSerial(SERIAL);
        fixedAssetsRequestDto.setType("TYPE");

        String request = objectMapper.writeValueAsString((fixedAssetsRequestDto))
                .replace("type", "\"");

        this.mockMvc.perform(
                        post("/api/v1/fixed-assets/")
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());

        verify(fixedAssetsService, times(0)).saveFixedAsset(fixedAssetsRequestDto);
    }


    @Test
    void shouldThrowABusinessExceptionForFixedNotFound() throws Exception {

        FixedAssetsRequestDto fixedAssetsRequestDto = new FixedAssetsRequestDto();
        fixedAssetsRequestDto.setDescription("DESCRIPTION");
        fixedAssetsRequestDto.setName("NAME");
        fixedAssetsRequestDto.setHeight(1.0);
        fixedAssetsRequestDto.setLength(1.0);
        fixedAssetsRequestDto.setWeight(1.0);
        fixedAssetsRequestDto.setWidth(1.0);
        fixedAssetsRequestDto.setPurchaseValue(1.0);
        fixedAssetsRequestDto.setSerial(SERIAL);
        fixedAssetsRequestDto.setType("TYPE");

        when(fixedAssetsService.updateFixedAsset(any(Long.class), any(FixedAssetsRequestDto.class)))
                .thenThrow(new BusinessException(BusinessExceptionMessage.FIXED_ASSET_NOT_FOUND));
        String request = objectMapper.writeValueAsString((fixedAssetsRequestDto));
        this.mockMvc.perform(
                        put("/api/v1/fixed-assets/1")
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
        verify(fixedAssetsService, times(1))
                .updateFixedAsset(ID, fixedAssetsRequestDto);
    }


    @Test
    void shouldAssignAnAreaToAFixedAsset() throws Exception {
        AssignFixedToAreaRequestDto areaRequestDto = new AssignFixedToAreaRequestDto();
        areaRequestDto.setAreaId(ID);
        areaRequestDto.setFixedId(ID);

        when(fixedAssetsService.assignFixedToArea(any(AssignFixedToAreaRequestDto.class)))
                .thenReturn(fixedResponseDto);
        String responseExpected = objectMapper.writeValueAsString((fixedResponseDto));
        String request = objectMapper.writeValueAsString((areaRequestDto));

        this.mockMvc.perform(
                        post("/api/v1/fixed-assets/assign-fixed-to-area")
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(responseExpected));

        verify(fixedAssetsService, times(1)).assignFixedToArea(areaRequestDto);
    }

    @Test
    void shouldThrowABusinessExceptionWhenAssignArea() throws Exception {
        AssignFixedToAreaRequestDto areaRequestDto = new AssignFixedToAreaRequestDto();
        areaRequestDto.setAreaId(ID);

        String request = objectMapper.writeValueAsString((areaRequestDto));

        this.mockMvc.perform(
                        post("/api/v1/fixed-assets/assign-fixed-to-area")
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());

        verify(fixedAssetsService, times(0)).assignFixedToArea(areaRequestDto);
    }

    @Test
    void shouldAssignAPersonToAFixedAsset() throws Exception {
        AssignFixedToPersonRequestDto areaRequestDto = new AssignFixedToPersonRequestDto();
        areaRequestDto.setPersonId(ID);
        areaRequestDto.setFixedId(ID);

        when(fixedAssetsService.assignFixedToPerson(any(AssignFixedToPersonRequestDto.class)))
                .thenReturn(fixedResponseDto);
        String responseExpected = objectMapper.writeValueAsString((fixedResponseDto));
        String request = objectMapper.writeValueAsString((areaRequestDto));

        this.mockMvc.perform(
                        post("/api/v1/fixed-assets/assign-fixed-to-person")
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(responseExpected));

        verify(fixedAssetsService, times(1)).assignFixedToPerson(areaRequestDto);
    }

    @Test
    void shouldThrowABusinessExceptionWhenAssignPerson() throws Exception {
        AssignFixedToPersonRequestDto areaRequestDto = new AssignFixedToPersonRequestDto();
        areaRequestDto.setPersonId(ID);

        String request = objectMapper.writeValueAsString((areaRequestDto));

        this.mockMvc.perform(
                        post("/api/v1/fixed-assets/assign-fixed-to-person")
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());

        verify(fixedAssetsService, times(0)).assignFixedToPerson(areaRequestDto);
    }

    @Test
    void shouldThrowAnUnexpectedException() throws Exception {

        FixedAssetsRequestDto fixedAssetsRequestDto = new FixedAssetsRequestDto();

        when(fixedAssetsService.updateFixedAsset(any(Long.class), any(FixedAssetsRequestDto.class)))
                .thenThrow(new RuntimeException("UNEXPECTED ERROR DESCRIPTION"));
        String request = objectMapper.writeValueAsString((fixedAssetsRequestDto));

        var response = this.mockMvc.perform(
                        put("/api/v1/fixed-assets/1")
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError()).andReturn();


        var exx = new TechnicalException(TechnicalExceptionMessage.UNEXPECTED_ERROR);


        assertTrue(response.getResponse().getContentAsString().contains(exx.getCode()));
        assertTrue(response.getResponse().getContentAsString().contains(exx.getMessage()));
        assertEquals(response.getResponse().getStatus(), exx.getHttpStatus().value());


        verify(fixedAssetsService, times(1))
                .updateFixedAsset(ID, fixedAssetsRequestDto);
    }

    @Test
    void shouldThrowAnConstraintViolationException() throws Exception {
        FixedAssetsRequestDto fixedAssetsRequestDto = new FixedAssetsRequestDto();
        Set<ConstraintViolation<?>> violations = new HashSet<>();
        when(fixedAssetsService.updateFixedAsset(any(Long.class), any(FixedAssetsRequestDto.class)))
                .thenThrow(new ConstraintViolationException(violations));
        String request = objectMapper.writeValueAsString((fixedAssetsRequestDto));

        this.mockMvc.perform(
                        put("/api/v1/fixed-assets/1")
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError()).andReturn();
        verify(fixedAssetsService, times(1))
                .updateFixedAsset(ID, fixedAssetsRequestDto);
    }


}