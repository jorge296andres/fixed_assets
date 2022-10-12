package com.asd.fixedassets.services;

import com.asd.fixedassets.controllers.dtos.requests.PersonRequestDto;
import com.asd.fixedassets.controllers.dtos.responses.PersonResponseDto;
import com.asd.fixedassets.domain.FixedAssets;
import com.asd.fixedassets.domain.Person;
import com.asd.fixedassets.exceptions.BusinessException;
import com.asd.fixedassets.exceptions.messages.BusinessExceptionMessage;
import com.asd.fixedassets.repositories.IPersonRepository;
import com.asd.fixedassets.services.mappers.IPersonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PersonService {

    private final IPersonRepository personRepository;
    private final IPersonMapper mapper;

    public List<PersonResponseDto> getAllPersons() {
        return mapper.toDataList(personRepository.findAll());
    }

    public PersonResponseDto getPersonById(Long personId) {

        return mapper.toData(personRepository.findById(personId)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.PERSON_NOT_FOUND)));
    }

    @Transactional(rollbackFor = Exception.class)
    public PersonResponseDto savePerson(PersonRequestDto personDto) {
        Person personSaved = personRepository.save(mapper.toEntity(personDto));
        return mapper.toData(personSaved);
    }

    @Transactional(rollbackFor = Exception.class)
    public PersonResponseDto updatePerson(Long personId, PersonRequestDto personDto) {
        var person = personRepository.findById(personId)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.PERSON_NOT_FOUND));
        mapper.updatePerson(personDto, person);
        var personUpdated = personRepository.save(person);
        return mapper.toData(personUpdated);
    }

    @Transactional(rollbackFor = Exception.class)
    public void assignFixed(Long personId, FixedAssets fixedAssets) {
        var person = personRepository.findById(personId)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.PERSON_NOT_FOUND));
        person.addFixedAssets(fixedAssets);
        personRepository.save(person);
    }

    @Transactional(rollbackFor = Exception.class)
    public void removeFixed(Long personId, FixedAssets fixedAsset) {
        var person = personRepository.findById(personId)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.PERSON_NOT_FOUND));

        person.removeFixedAssets(fixedAsset);
        personRepository.save(person);
    }

}
