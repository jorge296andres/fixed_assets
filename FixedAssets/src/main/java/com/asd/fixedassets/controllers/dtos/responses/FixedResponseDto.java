package com.asd.fixedassets.controllers.dtos.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class FixedResponseDto extends FixedDto {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private AreaDto area;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private PersonDto person;
}
