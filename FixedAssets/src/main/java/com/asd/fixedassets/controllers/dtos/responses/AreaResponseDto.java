package com.asd.fixedassets.controllers.dtos.responses;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
public class AreaResponseDto extends AreaDto {
    private Set<FixedDto> fixedAssets;
}
