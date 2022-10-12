package com.asd.fixedassets.controllers.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
@EqualsAndHashCode
@RequiredArgsConstructor
public class AreaRequestDto {

    @NotBlank
    @JsonProperty(value = "area_name")
    private String areaName;

    @NotBlank
    @JsonProperty(value = "city")
    private String city;
}
