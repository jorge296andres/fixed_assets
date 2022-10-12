package com.asd.fixedassets.controllers.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class AreaDto {
    @JsonProperty(value = "area_id")
    private Long areaId;
    @JsonProperty(value = "area_name")
    private String areaName;
    @JsonProperty(value = "city")
    private String city;

}
