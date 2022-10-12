package com.asd.fixedassets.services;

import com.asd.fixedassets.controllers.dtos.requests.AreaRequestDto;
import com.asd.fixedassets.controllers.dtos.responses.AreaResponseDto;
import com.asd.fixedassets.domain.Area;
import com.asd.fixedassets.domain.FixedAssets;
import com.asd.fixedassets.exceptions.BusinessException;
import com.asd.fixedassets.exceptions.messages.BusinessExceptionMessage;
import com.asd.fixedassets.repositories.IAreaRepository;
import com.asd.fixedassets.services.mappers.IAreaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AreaService {

    private final IAreaRepository areaRepository;
    private final IAreaMapper mapper;


    public List<AreaResponseDto> getAllAreas() {
        return mapper.toDataList(areaRepository.findAll());
    }

    public AreaResponseDto getAreaById(Long areaId) {

        return mapper.toData(areaRepository.findById(areaId)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.AREA_NOT_FOUND)));
    }

    @Transactional
    public AreaResponseDto saveArea(AreaRequestDto areaDto) {

        areaDto.setAreaName(areaDto.getAreaName().toUpperCase());
        if (areaRepository.findByAreaNameAndCity(areaDto.getAreaName(), areaDto.getCity()).isPresent()) {
            throw new BusinessException(BusinessExceptionMessage.AREA_ALREADY_EXISTS);
        }
        Area areaSaved = areaRepository.save(mapper.toEntity(areaDto));
        return mapper.toData(areaSaved);

    }

    @Transactional(rollbackFor = Exception.class)
    public AreaResponseDto updateArea(Long areaId, AreaRequestDto areaDto) {
        var area = areaRepository.findById(areaId)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.AREA_NOT_FOUND));
        mapper.updateArea(areaDto, area);
        var areaUpdated = areaRepository.save(area);
        return mapper.toData(areaUpdated);
    }

    @Transactional(rollbackFor = Exception.class)
    public void assignFixed(Long areaId, FixedAssets fixedAsset) {
        var area = areaRepository.findById(areaId)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.AREA_NOT_FOUND));

        area.addFixedAssets(fixedAsset);
        areaRepository.save(area);
    }

    @Transactional(rollbackFor = Exception.class)
    public void removeFixed(Long areaId, FixedAssets fixedAsset) {
        var area = areaRepository.findById(areaId)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.AREA_NOT_FOUND));

        area.removeFixedAssets(fixedAsset);
        areaRepository.save(area);
    }

}
