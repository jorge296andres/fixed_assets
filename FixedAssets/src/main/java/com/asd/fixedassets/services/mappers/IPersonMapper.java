package com.asd.fixedassets.services.mappers;

import com.asd.fixedassets.controllers.dtos.requests.PersonRequestDto;
import com.asd.fixedassets.controllers.dtos.responses.PersonResponseDto;
import com.asd.fixedassets.domain.Person;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IPersonMapper {
    @Mapping(target = "personId", ignore = true)
    @Mapping(target = "fixedAssets", ignore = true)
    Person toEntity(PersonRequestDto personDto);

    PersonResponseDto toData(Person person);

    List<PersonResponseDto> toDataList(List<Person> listPersons);

    @Mapping(target = "personId", ignore = true)
    @Mapping(target = "fixedAssets", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePerson(PersonRequestDto personDto, @MappingTarget Person person);

}
