package com.asd.fixedassets.services.mappers;

import com.asd.fixedassets.controllers.dtos.requests.FixedAssetsRequestDto;
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
class IFixedAssetsMapperTest {

    private final IFixedAssetsMapper mapper = Mappers.getMapper(IFixedAssetsMapper.class);

    @Test
    void shouldReturnADomainObjectFromDtoObject() {
        FixedAssetsRequestDto fixedRequestDto = new FixedAssetsRequestDto();
        fixedRequestDto.setDescription("DESCRIPTION");
        fixedRequestDto.setName("NAME");
        fixedRequestDto.setHeight(1.0);
        fixedRequestDto.setLength(1.0);
        fixedRequestDto.setWeight(1.0);
        fixedRequestDto.setWidth(1.0);
        fixedRequestDto.setPurchaseDate(LocalDate.now());
        fixedRequestDto.setPurchaseValue(1.0);
        fixedRequestDto.setSerial(UUID.randomUUID().toString());
        fixedRequestDto.setType("TYPE");

        var fixedAssets = mapper.toEntity(fixedRequestDto);
        assertEquals(fixedRequestDto.getDescription(), fixedAssets.getDescription());
        assertEquals(fixedRequestDto.getName(), fixedAssets.getName());
        assertEquals(fixedRequestDto.getHeight(), fixedAssets.getHeight());
        assertEquals(fixedRequestDto.getLength(), fixedAssets.getLength());
        assertEquals(fixedRequestDto.getWeight(), fixedAssets.getWeight());
        assertEquals(fixedRequestDto.getWidth(), fixedAssets.getWidth());
        assertEquals(fixedRequestDto.getPurchaseDate(), fixedAssets.getPurchaseDate());
        assertEquals(fixedRequestDto.getPurchaseValue(), fixedAssets.getPurchaseValue());
        assertEquals(fixedRequestDto.getSerial(), fixedAssets.getSerial());
        assertEquals(fixedRequestDto.getType(), fixedAssets.getType());


        var fixedToUpdate = buildFixedAssets();
        mapper.updateFixedAsset(fixedRequestDto, fixedToUpdate);
        assertEquals(fixedToUpdate.getDescription(), fixedRequestDto.getDescription());
        assertEquals(fixedToUpdate.getName(), fixedRequestDto.getName());
        assertEquals(fixedToUpdate.getHeight(), fixedRequestDto.getHeight());
        assertEquals(fixedToUpdate.getLength(), fixedRequestDto.getLength());
        assertEquals(fixedToUpdate.getWeight(), fixedRequestDto.getWeight());
        assertEquals(fixedToUpdate.getWidth(), fixedRequestDto.getWidth());
        assertEquals(fixedToUpdate.getPurchaseDate(), fixedRequestDto.getPurchaseDate());
        assertEquals(fixedToUpdate.getPurchaseValue(), fixedRequestDto.getPurchaseValue());
        assertEquals(fixedToUpdate.getSerial(), fixedRequestDto.getSerial());
        assertEquals(fixedToUpdate.getType(), fixedRequestDto.getType());

        mapper.updateFixedAsset(null, fixedToUpdate);
        assertEquals(fixedToUpdate.getDescription(), fixedToUpdate.getDescription());
        assertEquals(fixedToUpdate.getName(), fixedToUpdate.getName());
        assertEquals(fixedToUpdate.getHeight(), fixedToUpdate.getHeight());
        assertEquals(fixedToUpdate.getLength(), fixedToUpdate.getLength());
        assertEquals(fixedToUpdate.getWeight(), fixedToUpdate.getWeight());
        assertEquals(fixedToUpdate.getWidth(), fixedToUpdate.getWidth());
        assertEquals(fixedToUpdate.getPurchaseDate(), fixedToUpdate.getPurchaseDate());
        assertEquals(fixedToUpdate.getPurchaseValue(), fixedToUpdate.getPurchaseValue());
        assertEquals(fixedToUpdate.getSerial(), fixedToUpdate.getSerial());
        assertEquals(fixedToUpdate.getType(), fixedToUpdate.getType());
    }

    @Test
    void shouldReturnNullValues() {
        var fixedAssets = mapper.toEntity(null);
        assertNull(fixedAssets);

        var fixedResponseDto = mapper.toData(null);
        assertNull(fixedResponseDto);

        var fixedResponseDtoList = mapper.toDataList(null);
        assertNull(fixedResponseDtoList);

        var fixedWithNullSetAreaAndPerson = buildFixedAssets();
        fixedWithNullSetAreaAndPerson.setArea(null);
        fixedWithNullSetAreaAndPerson.setPerson(null);

        var listFixedDtoWithNullSetAreaAndPerson =
                mapper.toDataList(List.of(fixedWithNullSetAreaAndPerson));
        assertNull(listFixedDtoWithNullSetAreaAndPerson.get(0).getArea());
        assertNull(listFixedDtoWithNullSetAreaAndPerson.get(0).getPerson());


    }

    @Test
    void shouldReturnAObjectDtoFromADomainObject() {

        var fixedAssets = buildFixedAssets();

        var fixedResponseDto = mapper.toData(fixedAssets);

        assertEquals(fixedAssets.getDescription(), fixedResponseDto.getDescription());
        assertEquals(fixedAssets.getName(), fixedResponseDto.getName());
        assertEquals(fixedAssets.getHeight(), fixedResponseDto.getHeight());
        assertEquals(fixedAssets.getLength(), fixedResponseDto.getLength());
        assertEquals(fixedAssets.getWeight(), fixedResponseDto.getWeight());
        assertEquals(fixedAssets.getWidth(), fixedResponseDto.getWidth());
        assertEquals(fixedAssets.getPurchaseDate(), fixedResponseDto.getPurchaseDate());
        assertEquals(fixedAssets.getPurchaseValue(), fixedResponseDto.getPurchaseValue());
        assertEquals(fixedAssets.getSerial(), fixedResponseDto.getSerial());
        assertEquals(fixedAssets.getType(), fixedResponseDto.getType());
        assertNotNull(fixedResponseDto.getPerson());
        assertNotNull(fixedResponseDto.getArea());

        var listFixedDto = mapper.toDataList(List.of(fixedAssets));

        assertEquals(listFixedDto.get(0).getDescription(), fixedAssets.getDescription());
        assertEquals(listFixedDto.get(0).getName(), fixedAssets.getName());
        assertEquals(listFixedDto.get(0).getHeight(), fixedAssets.getHeight());
        assertEquals(listFixedDto.get(0).getLength(), fixedAssets.getLength());
        assertEquals(listFixedDto.get(0).getWeight(), fixedAssets.getWeight());
        assertEquals(listFixedDto.get(0).getWidth(), fixedAssets.getWidth());
        assertEquals(listFixedDto.get(0).getPurchaseDate(), fixedAssets.getPurchaseDate());
        assertEquals(listFixedDto.get(0).getPurchaseValue(), fixedAssets.getPurchaseValue());
        assertEquals(listFixedDto.get(0).getSerial(), fixedAssets.getSerial());
        assertEquals(listFixedDto.get(0).getType(), fixedAssets.getType());
        assertNotNull(listFixedDto.get(0).getArea());
        assertNotNull(listFixedDto.get(0).getPerson());

    }

    private FixedAssets buildFixedAssets() {

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

        return fixedAssets;
    }

}