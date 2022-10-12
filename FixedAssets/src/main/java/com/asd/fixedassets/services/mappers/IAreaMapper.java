package com.asd.fixedassets.services.mappers;

import com.asd.fixedassets.controllers.dtos.requests.AreaRequestDto;
import com.asd.fixedassets.controllers.dtos.responses.AreaResponseDto;
import com.asd.fixedassets.domain.Area;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IAreaMapper {

    @Mapping(target = "areaId", ignore = true)
    @Mapping(target = "fixedAssets", ignore = true)
    Area toEntity(AreaRequestDto areaDto);

    AreaResponseDto toData(Area area);

    List<AreaResponseDto> toDataList(List<Area> listAreas);

    @Mapping(target = "areaId", ignore = true)
    @Mapping(target = "fixedAssets", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateArea(AreaRequestDto areaDto, @MappingTarget Area area);

}
