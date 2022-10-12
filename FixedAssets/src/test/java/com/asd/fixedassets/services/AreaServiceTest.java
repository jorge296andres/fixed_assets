package com.asd.fixedassets.services;

import com.asd.fixedassets.controllers.dtos.requests.AreaRequestDto;
import com.asd.fixedassets.controllers.dtos.responses.AreaResponseDto;
import com.asd.fixedassets.domain.Area;
import com.asd.fixedassets.domain.FixedAssets;
import com.asd.fixedassets.exceptions.BusinessException;
import com.asd.fixedassets.exceptions.messages.BusinessExceptionMessage;
import com.asd.fixedassets.repositories.IAreaRepository;
import com.asd.fixedassets.services.mappers.IAreaMapper;
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
class AreaServiceTest {

    @InjectMocks
    private AreaService areaService;

    @Mock
    private IAreaRepository areaRepository;
    @Mock
    private IAreaMapper mapper;

    private Area area;

    private static final String FIXED_DESCRIPTION = "FIXED CREATED IN UNIT TEST";

    @BeforeEach
    void setUp() {
        area = new Area();
    }


    @Test
    void shouldReturnAListOfAreasWhenConsultAll() {
        when(areaRepository.findAll()).thenReturn(List.of(area));
        assertNotNull(areaService.getAllAreas());
    }

    @Test
    void ShouldReturnAnAreaDtoWhenConsulById() {
        when(areaRepository.findById(area.getAreaId())).thenReturn(Optional.of(area));
        when(mapper.toData(area)).thenReturn(new AreaResponseDto());
        assertNotNull(areaService.getAreaById(area.getAreaId()));
    }

    @Test
    void shouldThrowABusinessExceptionWhenNotFoundAreaById() {
        when(areaRepository.findById(1L)).thenReturn(Optional.empty());
        assertEquals(assertThrowsExactly(BusinessException.class, () -> areaService.getAreaById(1L))
                .getMessage(), BusinessExceptionMessage.AREA_NOT_FOUND.getMessage());
    }

    @Test
    void shouldSaveAnArea() {
        AreaRequestDto areaRequestDto = new AreaRequestDto();
        areaRequestDto.setAreaName("AREA");
        areaRequestDto.setCity("CITY");
        when(areaRepository.findByAreaNameAndCity(areaRequestDto.getAreaName(), areaRequestDto.getCity()))
                .thenReturn(Optional.empty());
        when(mapper.toEntity(areaRequestDto)).thenReturn(area);
        when(mapper.toData(area)).thenReturn(new AreaResponseDto());
        when(areaRepository.save(area)).thenReturn(area);
        assertNotNull(areaService.saveArea(areaRequestDto));
    }

    @Test
    void ShouldThrowABusinessExceptionWhenAreaNameAlreadyExists() {
        AreaRequestDto areaRequestDto = new AreaRequestDto();
        areaRequestDto.setAreaName("AREA");
        areaRequestDto.setCity("CITY");
        when(areaRepository.findByAreaNameAndCity(areaRequestDto.getAreaName(), areaRequestDto.getCity()))
                .thenReturn(Optional.of(area));

        assertEquals(assertThrowsExactly(BusinessException.class, () -> areaService.saveArea(areaRequestDto))
                .getMessage(), BusinessExceptionMessage.AREA_ALREADY_EXISTS.getMessage());

    }


    @Test
    void shouldUpdateAnArea() {
        AreaRequestDto areaRequestDto = new AreaRequestDto();
        when(areaRepository.findById(area.getAreaId())).thenReturn(Optional.of(area));
        when(mapper.toData(area)).thenReturn(new AreaResponseDto());
        when(areaRepository.save(area)).thenReturn(area);
        assertNotNull(areaService.updateArea(area.getAreaId(), areaRequestDto));
    }

    @Test
    void shouldThrowABusinessExceptionWhenNotFoundAreaByIdOnMethodUpdateArea() {
        AreaRequestDto areaRequestDto = new AreaRequestDto();
        areaRequestDto.setAreaName("AREA");
        areaRequestDto.setCity("CITY");
        when(areaRepository.findById(1L)).thenReturn(Optional.empty());
        assertEquals(assertThrowsExactly(BusinessException.class, () -> areaService.updateArea(1L, areaRequestDto))
                .getMessage(), BusinessExceptionMessage.AREA_NOT_FOUND.getMessage());

    }

    @Test
    void shouldAssignAFixedOnTheArea() {
        when(areaRepository.findById(area.getAreaId())).thenReturn(Optional.of(area));
        when(areaRepository.save(area)).thenReturn(area);
        FixedAssets fixed = new FixedAssets();
        fixed.setDescription(FIXED_DESCRIPTION);
        areaService.assignFixed(area.getAreaId(), fixed);
        assertTrue(area.getFixedAssets().contains(fixed));
    }

    @Test
    void shouldThrowABusinessExceptionWhenNotFoundAreaByIdOnMethodAssignFixed() {
        FixedAssets fixed = new FixedAssets();
        fixed.setDescription(FIXED_DESCRIPTION);
        when(areaRepository.findById(1L)).thenReturn(Optional.empty());
        assertEquals(assertThrowsExactly(BusinessException.class, () -> areaService.assignFixed(1L, fixed))
                .getMessage(), BusinessExceptionMessage.AREA_NOT_FOUND.getMessage());

    }

    @Test
    void shouldRemoveAFixedOfTheArea() {
        FixedAssets fixed = new FixedAssets();
        fixed.setDescription(FIXED_DESCRIPTION);
        area.addFixedAssets(fixed);
        when(areaRepository.findById(area.getAreaId())).thenReturn(Optional.of(area));
        when(areaRepository.save(area)).thenReturn(area);
        areaService.removeFixed(area.getAreaId(), fixed);
        assertTrue(area.getFixedAssets().isEmpty());
    }

    @Test
    void shouldThrowABusinessExceptionWhenNotFoundAreaByIdOnMethodRemoveFixed() {
        FixedAssets fixed = new FixedAssets();
        fixed.setDescription(FIXED_DESCRIPTION);
        when(areaRepository.findById(1L)).thenReturn(Optional.empty());
        assertEquals(assertThrowsExactly(BusinessException.class, () -> areaService.removeFixed(1L, fixed))
                .getMessage(), BusinessExceptionMessage.AREA_NOT_FOUND.getMessage());

    }

}