package com.asd.fixedassets.services.mappers;

import com.asd.fixedassets.controllers.dtos.requests.PersonRequestDto;
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
class IPersonMapperTest {

    private final IPersonMapper mapper = Mappers.getMapper(IPersonMapper.class);

    @Test
    void shouldReturnADomainObjectFromDtoObject() {
        PersonRequestDto personRequestDto = new PersonRequestDto();
        personRequestDto.setPersonName("NAME");
        personRequestDto.setDocumentNumber("123456");
        personRequestDto.setDocumentType("CC");
        var person = mapper.toEntity(personRequestDto);
        assertEquals(personRequestDto.getPersonName(), person.getPersonName());
        assertEquals(personRequestDto.getDocumentNumber(), person.getDocumentNumber());
        assertEquals(personRequestDto.getDocumentType(), person.getDocumentType());

        var personToUpdate = buildPerson();
        mapper.updatePerson(personRequestDto, personToUpdate);
        assertEquals(personToUpdate.getPersonName(), personRequestDto.getPersonName());
        assertEquals(personToUpdate.getDocumentNumber(), personRequestDto.getDocumentNumber());
        assertEquals(personToUpdate.getDocumentType(), personRequestDto.getDocumentType());

        mapper.updatePerson(null, personToUpdate);
        assertEquals(personToUpdate.getPersonName(), personToUpdate.getPersonName());
    }

    @Test
    void shouldReturnNullValues() {
        var person = mapper.toEntity(null);
        assertNull(person);

        var personDto = mapper.toData(null);
        assertNull(personDto);

        var listPersonDto = mapper.toDataList(null);
        assertNull(listPersonDto);

        var personWithNullSetFixed = buildPerson();
        personWithNullSetFixed.setFixedAssets(null);

        var listPersonDtoWithNullSetFixed = mapper.toDataList(List.of(personWithNullSetFixed));
        assertNull(listPersonDtoWithNullSetFixed.get(0).getFixedAssets());

    }

    @Test
    void shouldReturnAObjectDtoFromADomainObject() {

        var person = buildPerson();

        var personDto = mapper.toData(person);

        assertEquals(personDto.getPersonId(), person.getPersonId());
        assertEquals(personDto.getPersonName(), person.getPersonName());
        assertEquals(personDto.getDocumentNumber(), person.getDocumentNumber());
        assertEquals(personDto.getDocumentType(), person.getDocumentType());
        assertNotNull(personDto.getFixedAssets());

        var listPersonDto = mapper.toDataList(List.of(person));

        assertEquals(listPersonDto.get(0).getPersonId(), person.getPersonId());
        assertEquals(listPersonDto.get(0).getDocumentType(), person.getDocumentType());
        assertEquals(listPersonDto.get(0).getPersonName(), person.getPersonName());
        assertEquals(listPersonDto.get(0).getDocumentNumber(), person.getDocumentNumber());
        assertNotNull(listPersonDto.get(0).getFixedAssets());

    }

    private Person buildPerson() {

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

        return person;
    }

}