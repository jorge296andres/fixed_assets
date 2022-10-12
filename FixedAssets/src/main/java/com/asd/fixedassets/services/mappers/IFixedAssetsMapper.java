package com.asd.fixedassets.services.mappers;

import com.asd.fixedassets.controllers.dtos.requests.FixedAssetsRequestDto;
import com.asd.fixedassets.controllers.dtos.responses.FixedResponseDto;
import com.asd.fixedassets.domain.FixedAssets;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IFixedAssetsMapper {


    @Mapping(target = "inventoryId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "person", ignore = true)
    @Mapping(target = "area", ignore = true)
    FixedAssets toEntity(FixedAssetsRequestDto fixedAssetsDto);

    FixedResponseDto toData(FixedAssets fixedAssets);

    List<FixedResponseDto> toDataList(List<FixedAssets> listFixedAssets);

    @Mapping(target = "inventoryId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "person", ignore = true)
    @Mapping(target = "area", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFixedAsset(FixedAssetsRequestDto fixedAssetsDto, @MappingTarget FixedAssets fixedAssets);

}
