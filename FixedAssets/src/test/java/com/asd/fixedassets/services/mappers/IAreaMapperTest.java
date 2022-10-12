package com.asd.fixedassets.services.mappers;

import com.asd.fixedassets.controllers.dtos.requests.AreaRequestDto;
import com.asd.fixedassets.domain.Area;
import com.asd.fixedassets.domain.FixedAssets;
import com.asd.fixedassets.domain.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class IAreaMapperTest {

    private final IAreaMapper mapper = Mappers.getMapper(IAreaMapper.class);

    @Test
    void shouldReturnADomainObjectFromDtoObject() {
        AreaRequestDto areaRequestDto = new AreaRequestDto();
        areaRequestDto.setAreaName("NAME");
        areaRequestDto.setCity("CITY");
        var area = mapper.toEntity(areaRequestDto);
        assertEquals(areaRequestDto.getAreaName(), area.getAreaName());

        var areaToUpdate = buildArea();
        mapper.updateArea(areaRequestDto, areaToUpdate);
        assertEquals(areaToUpdate.getAreaName(), areaRequestDto.getAreaName());

        mapper.updateArea(null, areaToUpdate);
        assertEquals(areaToUpdate.getAreaName(), areaToUpdate.getAreaName());
    }

    @Test
    void shouldReturnNullValues() {
        var area = mapper.toEntity(null);
        assertNull(area);

        var areaDto = mapper.toData(null);
        assertNull(areaDto);

        var listAreaDto = mapper.toDataList(null);
        assertNull(listAreaDto);

        var areaWithNullSetFixed = buildArea();
        areaWithNullSetFixed.setFixedAssets(null);

        var listAreaDtoWithNullSetFixed = mapper.toDataList(List.of(areaWithNullSetFixed));
        assertNull(listAreaDtoWithNullSetFixed.get(0).getFixedAssets());

    }

    @Test
    void shouldReturnAObjectDtoFromADomainObject() {

        var area = buildArea();

        var areaDto = mapper.toData(area);

        assertEquals(areaDto.getAreaName(), area.getAreaName());
        assertEquals(areaDto.getAreaId(), area.getAreaId());
        assertNotNull(areaDto.getFixedAssets());

        var listAreasDto = mapper.toDataList(List.of(area));

        assertEquals(listAreasDto.get(0).getAreaId(), area.getAreaId());
        assertEquals(listAreasDto.get(0).getAreaName(), area.getAreaName());
        assertNotNull(listAreasDto.get(0).getFixedAssets());

    }

    private Area buildArea() {

        FixedAssets fixedAssets = new FixedAssets();
        fixedAssets.setInventoryId(1L);
        fixedAssets.setDescription("DESCRIPTION");
        fixedAssets.setName("NAME");
        fixedAssets.setCreatedAt(LocalDateTime.now());
        fixedAssets.setHeight(1.0);
        fixedAssets.setLength(1.0);
        fixedAssets.setWeight(1.0);
        fixedAssets.setWidth(1.0);
        fixedAssets.setUpdatedAt(LocalDateTime.now());
        fixedAssets.setPurchaseDate(LocalDate.now());
        fixedAssets.setPurchaseValue(1.0);
        fixedAssets.setSerial(UUID.randomUUID().toString());
        fixedAssets.setType("TYPE");

        Person person = new Person();
        person.setPersonId(1L);
        person.setPersonName("PERSON_NAME");
        person.setDocumentNumber("123456789");
        person.setDocumentType("CC");
        person.addFixedAssets(fixedAssets);

        Area area = new Area();
        area.setAreaId(1L);
        area.setAreaName("NAME");
        area.setCity("CITY");
        area.addFixedAssets(fixedAssets);

        return area;
    }

}