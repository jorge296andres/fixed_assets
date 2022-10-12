package com.asd.fixedassets.services;

import com.asd.fixedassets.controllers.dtos.requests.AssignFixedToAreaRequestDto;
import com.asd.fixedassets.controllers.dtos.requests.AssignFixedToPersonRequestDto;
import com.asd.fixedassets.controllers.dtos.requests.FixedAssetsRequestDto;
import com.asd.fixedassets.controllers.dtos.responses.FixedResponseDto;
import com.asd.fixedassets.domain.Area;
import com.asd.fixedassets.domain.FixedAssets;
import com.asd.fixedassets.domain.Person;
import com.asd.fixedassets.exceptions.BusinessException;
import com.asd.fixedassets.exceptions.messages.BusinessExceptionMessage;
import com.asd.fixedassets.repositories.IAreaRepository;
import com.asd.fixedassets.repositories.IFixedAssetsRepository;
import com.asd.fixedassets.repositories.IPersonRepository;
import com.asd.fixedassets.services.mappers.IFixedAssetsMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FixedAssetsServiceTest {

    @InjectMocks
    private FixedAssetsService fixedAssetsService;

    @Mock
    private IFixedAssetsRepository fixedAssetsRepository;
    @Mock
    private IFixedAssetsMapper mapper;

    @Mock
    private AreaService areaServiceMock;

    @Mock
    private PersonService personServiceMock;

    @InjectMocks
    private AreaService areaService;
    @Mock
    private IAreaRepository areaRepository;

    @InjectMocks
    private PersonService personService;
    @Mock
    private IPersonRepository personRepository;

    private FixedAssets fixedAssets;

    private final String PERSON_NAME = "PERSON_WITH_FIXED";
    private final String AREA_NAME = "AREA_WITH_FIXED";
    private final String CITY = "CITY";


    @BeforeEach
    void setUp() {
        fixedAssets = new FixedAssets();
    }

    @Test
    void shouldReturnAListOfFixedAssetsWhenConsultAll() {
        when(fixedAssetsRepository.findAll()).thenReturn(List.of(fixedAssets));
        assertNotNull(fixedAssetsService.getAllFixedAssets());
    }

    @Test
    void shouldReturnAFixedAssetWhenConsultById() {
        when(fixedAssetsRepository.findById(fixedAssets.getInventoryId())).thenReturn(Optional.of(fixedAssets));
        when(mapper.toData(fixedAssets)).thenReturn(new FixedResponseDto());
        assertNotNull(fixedAssetsService.getFixedById(fixedAssets.getInventoryId()));
    }

    @Test
    void shouldReturnAListOfFixedWhenConsultByType() {
        when(fixedAssetsRepository.findByType(fixedAssets.getType())).thenReturn(List.of(fixedAssets));
        assertNotNull(fixedAssetsService.getFixedByType(fixedAssets.getType()));
    }

    @Test
    void shouldReturnAFixedWhenConsultBySerial() {
        fixedAssets.setSerial(UUID.randomUUID().toString());
        when(mapper.toData(fixedAssets)).thenReturn(new FixedResponseDto());
        when(fixedAssetsRepository.findBySerial(fixedAssets.getSerial())).thenReturn(Optional.of(fixedAssets));
        assertNotNull(fixedAssetsService.getFixedBySerial(fixedAssets.getSerial()));
    }

    @Test
    void shouldThrowABusinessExceptionWhenFixedNotFoundBySerial() {
        when(fixedAssetsRepository.findBySerial("SERIAL")).thenReturn(Optional.empty());
        assertEquals(assertThrowsExactly(BusinessException.class, () -> fixedAssetsService.getFixedBySerial("SERIAL"))
                .getMessage(), BusinessExceptionMessage.FIXED_ASSET_NOT_FOUND.getMessage());
    }

    @Test
    void shouldReturnAListOfFixedAssetsWhenConsultByPurchaseDate() {
        when(fixedAssetsRepository.findByPurchaseDate(fixedAssets.getPurchaseDate())).thenReturn(List.of(fixedAssets));
        assertNotNull(fixedAssetsService.getFixedByPurchaseDate(fixedAssets.getPurchaseDate()));
    }

    @Test
    void shouldThrowABusinessExceptionWhenNotFoundFixedAssetById() {
        when(fixedAssetsRepository.findById(1L)).thenReturn(Optional.empty());
        assertEquals(assertThrowsExactly(BusinessException.class, () -> fixedAssetsService.getFixedById(1L))
                .getMessage(), BusinessExceptionMessage.FIXED_ASSET_NOT_FOUND.getMessage());
    }

    @Test
    void shouldSaveAFixedAsset() {
        FixedAssetsRequestDto fixedAssetsRequestDto = new FixedAssetsRequestDto();
        when(mapper.toEntity(fixedAssetsRequestDto)).thenReturn(fixedAssets);
        when(mapper.toData(fixedAssets)).thenReturn(new FixedResponseDto());
        when(fixedAssetsRepository.save(fixedAssets)).thenReturn(fixedAssets);
        assertNotNull(fixedAssetsService.saveFixedAsset(fixedAssetsRequestDto));
    }

    @Test
    void shouldUpdateAFixedAsset() {
        FixedAssetsRequestDto fixedAssetsRequestDto = new FixedAssetsRequestDto();
        when(fixedAssetsRepository.findById(fixedAssets.getInventoryId())).thenReturn(Optional.of(fixedAssets));
        when(mapper.toData(fixedAssets)).thenReturn(new FixedResponseDto());
        when(fixedAssetsRepository.save(fixedAssets)).thenReturn(fixedAssets);
        assertNotNull(fixedAssetsService.updateFixedAsset(fixedAssets.getInventoryId(), fixedAssetsRequestDto));
    }

    @Test
    void shouldThrowABusinessExceptionWhenNotFoundFixedAssetByIdOnMethodUpdateFixedAsset() {
        FixedAssetsRequestDto fixedAssetsRequestDto = new FixedAssetsRequestDto();
        when(fixedAssetsRepository.findById(1L)).thenReturn(Optional.empty());
        assertEquals(assertThrowsExactly(BusinessException.class,
                () -> fixedAssetsService.updateFixedAsset(1L, fixedAssetsRequestDto))
                .getMessage(), BusinessExceptionMessage.FIXED_ASSET_NOT_FOUND.getMessage());
    }


    @Test
    void ShouldAssignAnAreaToAnFixed() {

        AssignFixedToAreaRequestDto areaRequestDto = new AssignFixedToAreaRequestDto();
        areaRequestDto.setAreaId(1L);
        areaRequestDto.setFixedId(fixedAssets.getInventoryId());
        areaRequestDto.setForceReassign(Boolean.TRUE);

        Area area = new Area();
        area.setAreaName(AREA_NAME);
        area.setCity(CITY);
        area.setAreaId(areaRequestDto.getAreaId());

        executeAssignArea(area);

        when(fixedAssetsRepository.findById(fixedAssets.getInventoryId())).thenReturn(Optional.of(fixedAssets));
        fixedAssetsService.assignFixedToArea(areaRequestDto);
        assertEquals(areaRequestDto.getAreaId(), fixedAssets.getArea().getAreaId());
        assertEquals(AREA_NAME, fixedAssets.getArea().getAreaName());
        verify(areaServiceMock, times(1)).assignFixed(areaRequestDto.getAreaId(), fixedAssets);

    }

    @Test
    void shouldAssignAAreaToTheFixedAndCallTheMethodForRemoveTheAssignationInPerson() {

        fixedAssets.setInventoryId(1L);
        Person person = new Person();
        person.setPersonId(1L);

        AssignFixedToAreaRequestDto areaRequestDto = new AssignFixedToAreaRequestDto();
        areaRequestDto.setFixedId(1L);
        areaRequestDto.setAreaId(fixedAssets.getInventoryId());
        areaRequestDto.setForceReassign(Boolean.TRUE);

        Area area = new Area();
        area.setAreaName(AREA_NAME);
        area.setCity(CITY);
        area.setAreaId(1L);
        executeAssignArea(area);

        fixedAssets.setPerson(person);

        when(fixedAssetsRepository.findById(fixedAssets.getInventoryId())).thenReturn(Optional.of(fixedAssets));
        fixedAssetsService.assignFixedToArea(areaRequestDto);
        assertNotNull(fixedAssets.getPerson());
        assertEquals(areaRequestDto.getAreaId(), fixedAssets.getArea().getAreaId());
        assertEquals(AREA_NAME, fixedAssets.getArea().getAreaName());
        assertNotNull(fixedAssets.getPerson());
        verify(areaServiceMock, times(1)).assignFixed(areaRequestDto.getAreaId(), fixedAssets);
        verify(personServiceMock, times(1)).removeFixed(person.getPersonId(), fixedAssets);

    }

    @Test
    void shouldThrowABusinessExceptionWhenNotFoundFixedAssetByIdOnMethodAssignFixedToArea() {
        AssignFixedToAreaRequestDto areaRequestDto = new AssignFixedToAreaRequestDto();
        areaRequestDto.setFixedId(1L);
        when(fixedAssetsRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        assertEquals(assertThrowsExactly(BusinessException.class,
                () -> fixedAssetsService.assignFixedToArea(areaRequestDto))
                .getMessage(), BusinessExceptionMessage.FIXED_ASSET_NOT_FOUND.getMessage());
    }

    @Test
    void shouldThrowABusinessExceptionWhenFixedHaveAPersonAndTheParameterForceReassignIsInFalse() {

        fixedAssets.setInventoryId(1L);
        Person person = new Person();
        person.setPersonId(1L);

        AssignFixedToAreaRequestDto areaRequestDto = new AssignFixedToAreaRequestDto();
        areaRequestDto.setFixedId(1L);

        fixedAssets.setPerson(person);
        when(fixedAssetsRepository.findById(fixedAssets.getInventoryId())).thenReturn(Optional.of(fixedAssets));

        assertEquals(assertThrowsExactly(BusinessException.class,
                () -> fixedAssetsService.assignFixedToArea(areaRequestDto))
                .getMessage(), BusinessExceptionMessage.PERSON_NOT_NULL.getMessage());
    }


    @Test
    void shouldAssignAPersonToAFixed() {


        AssignFixedToPersonRequestDto personRequestDto = new AssignFixedToPersonRequestDto();
        personRequestDto.setPersonId(1L);
        personRequestDto.setFixedId(fixedAssets.getInventoryId());
        personRequestDto.setForceReassign(Boolean.TRUE);

        Person person = new Person();
        person.setPersonName(PERSON_NAME);
        person.setPersonId(personRequestDto.getPersonId());
        person.setDocumentType("CC");
        person.setDocumentNumber("123456");

        executeAssignPerson(person);

        when(fixedAssetsRepository.findById(fixedAssets.getInventoryId())).thenReturn(Optional.of(fixedAssets));
        fixedAssetsService.assignFixedToPerson(personRequestDto);
        assertEquals(personRequestDto.getPersonId(), fixedAssets.getPerson().getPersonId());
        assertEquals(PERSON_NAME, fixedAssets.getPerson().getPersonName());
        verify(personServiceMock, times(1)).assignFixed(personRequestDto.getPersonId(), fixedAssets);
    }

    @Test
    void shouldThrowABusinessExceptionWhenNotFoundFixedAssetByIdOnMethodAssignFixedToPerson() {
        AssignFixedToPersonRequestDto personRequestDto = new AssignFixedToPersonRequestDto();
        personRequestDto.setFixedId(1L);
        when(fixedAssetsRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        assertEquals(assertThrowsExactly(BusinessException.class,
                () -> fixedAssetsService.assignFixedToPerson(personRequestDto))
                .getMessage(), BusinessExceptionMessage.FIXED_ASSET_NOT_FOUND.getMessage());
    }

    @Test
    void shouldAssignAPersonToTheFixedAndCallTheMethodForRemoveTheAssignationInArea() {

        Area area = new Area();
        area.setAreaId(1L);

        AssignFixedToPersonRequestDto personRequestDto = new AssignFixedToPersonRequestDto();
        personRequestDto.setPersonId(1L);
        personRequestDto.setFixedId(fixedAssets.getInventoryId());
        personRequestDto.setForceReassign(Boolean.TRUE);

        Person person = new Person();
        person.setPersonName(PERSON_NAME);
        person.setPersonId(personRequestDto.getPersonId());
        person.setDocumentType("CC");
        person.setDocumentNumber("123456");
        executeAssignPerson(person);

        fixedAssets.setArea(area);

        when(fixedAssetsRepository.findById(fixedAssets.getInventoryId())).thenReturn(Optional.of(fixedAssets));
        fixedAssetsService.assignFixedToPerson(personRequestDto);
        assertNotNull(fixedAssets.getPerson());
        assertEquals(personRequestDto.getPersonId(), fixedAssets.getPerson().getPersonId());
        assertEquals(PERSON_NAME, fixedAssets.getPerson().getPersonName());
        assertNotNull(fixedAssets.getArea());
        verify(personServiceMock, times(1)).assignFixed(personRequestDto.getPersonId(), fixedAssets);
        verify(areaServiceMock, times(1)).removeFixed(area.getAreaId(), fixedAssets);

    }

    @Test
    void shouldThrowABusinessExceptionWhenFixedHaveAnAreaAndTheParameterForceReassignIsInFalse() {

        Area area = new Area();
        area.setAreaId(1L);

        AssignFixedToPersonRequestDto personRequestDto = new AssignFixedToPersonRequestDto();
        personRequestDto.setPersonId(1L);
        personRequestDto.setFixedId(fixedAssets.getInventoryId());
        personRequestDto.setForceReassign(Boolean.FALSE);

        fixedAssets.setArea(area);
        when(fixedAssetsRepository.findById(fixedAssets.getInventoryId())).thenReturn(Optional.of(fixedAssets));

        assertEquals(assertThrowsExactly(BusinessException.class,
                () -> fixedAssetsService.assignFixedToPerson(personRequestDto))
                .getMessage(), BusinessExceptionMessage.AREA_NOT_NULL.getMessage());
    }

    private void executeAssignArea(Area area) {
        when(areaRepository.findById(area.getAreaId())).thenReturn(Optional.of(area));
        when(areaRepository.save(area)).thenReturn(area);
        areaService.assignFixed(area.getAreaId(), fixedAssets);

    }

    private void executeAssignPerson(Person person) {
        when(personRepository.findById(person.getPersonId())).thenReturn(Optional.of(person));
        when(personRepository.save(person)).thenReturn(person);
        personService.assignFixed(person.getPersonId(), fixedAssets);

    }


}