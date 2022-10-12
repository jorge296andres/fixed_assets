package com.asd.fixedassets.controllers.dtos.responses;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
public class PersonResponseDto extends PersonDto {
    private Set<FixedDto> fixedAssets;
}
