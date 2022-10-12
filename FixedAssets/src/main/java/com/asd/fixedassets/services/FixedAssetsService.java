package com.asd.fixedassets.services;

import com.asd.fixedassets.controllers.dtos.requests.AssignFixedToAreaRequestDto;
import com.asd.fixedassets.controllers.dtos.requests.AssignFixedToPersonRequestDto;
import com.asd.fixedassets.controllers.dtos.requests.FixedAssetsRequestDto;
import com.asd.fixedassets.controllers.dtos.responses.FixedResponseDto;
import com.asd.fixedassets.exceptions.BusinessException;
import com.asd.fixedassets.exceptions.messages.BusinessExceptionMessage;
import com.asd.fixedassets.repositories.IFixedAssetsRepository;
import com.asd.fixedassets.services.mappers.IFixedAssetsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class FixedAssetsService {

    private final IFixedAssetsRepository fixedAssetsRepository;
    private final IFixedAssetsMapper mapper;

    private final AreaService areaService;
    private final PersonService personService;


    public List<FixedResponseDto> getAllFixedAssets() {
        return mapper.toDataList(fixedAssetsRepository.findAll());
    }

    public FixedResponseDto getFixedById(Long inventoryId) {

        return mapper.toData(fixedAssetsRepository.findById(inventoryId)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.FIXED_ASSET_NOT_FOUND)));
    }

    public List<FixedResponseDto> getFixedByType(String type){
        return mapper.toDataList(fixedAssetsRepository.findByType(type));
    }

    public List<FixedResponseDto> getFixedByPurchaseDate(LocalDate date) {

        return mapper.toDataList(fixedAssetsRepository.findByPurchaseDate(date));
    }

    public FixedResponseDto getFixedBySerial(String serial) {

        return mapper.toData(fixedAssetsRepository.findBySerial(serial)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.FIXED_ASSET_NOT_FOUND)));
    }

    @Transactional(rollbackFor = Exception.class)
    public FixedResponseDto saveFixedAsset(FixedAssetsRequestDto fixedAssetsDto) {
        var fixedSaved = fixedAssetsRepository.save(mapper.toEntity(fixedAssetsDto));
        return mapper.toData(fixedSaved);
    }

    @Transactional(rollbackFor = Exception.class)
    public FixedResponseDto updateFixedAsset(Long inventoryId, FixedAssetsRequestDto fixedAssetsDto) {
        var fixed = fixedAssetsRepository.findById(inventoryId)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.FIXED_ASSET_NOT_FOUND));
        mapper.updateFixedAsset(fixedAssetsDto, fixed);
        var fixedUpdated = fixedAssetsRepository.save(fixed);
        return mapper.toData(fixedUpdated);
    }

    @Transactional(rollbackFor = Exception.class)
    public FixedResponseDto assignFixedToArea(AssignFixedToAreaRequestDto assignFixedToAreaDto) {

        var fixed = fixedAssetsRepository.findById(assignFixedToAreaDto.getFixedId())
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.FIXED_ASSET_NOT_FOUND));
        var personIsNull = (fixed.getPerson() == null);
        if (personIsNull || Boolean.TRUE.equals(assignFixedToAreaDto.getForceReassign())) {
            areaService.assignFixed(assignFixedToAreaDto.getAreaId(), fixed);
            if (Boolean.FALSE.equals(personIsNull)) {
                personService.removeFixed(fixed.getPerson().getPersonId(), fixed);
            }
        } else {
            throw new BusinessException(BusinessExceptionMessage.PERSON_NOT_NULL);
        }
        return mapper.toData(fixed);
    }

    @Transactional(rollbackFor = Exception.class)
    public FixedResponseDto assignFixedToPerson(AssignFixedToPersonRequestDto assignFixedToPersonDto) {

        var fixed = fixedAssetsRepository.findById(assignFixedToPersonDto.getFixedId())
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.FIXED_ASSET_NOT_FOUND));
        var areaIsNull = (fixed.getArea() == null);
        if (areaIsNull || Boolean.TRUE.equals(assignFixedToPersonDto.getForceReassign())) {
            personService.assignFixed(assignFixedToPersonDto.getPersonId(), fixed);
            if (Boolean.FALSE.equals(areaIsNull)) {
                areaService.removeFixed(fixed.getArea().getAreaId(), fixed);
            }
        } else {
            throw new BusinessException(BusinessExceptionMessage.AREA_NOT_NULL);
        }
        return mapper.toData(fixed);
    }

}
