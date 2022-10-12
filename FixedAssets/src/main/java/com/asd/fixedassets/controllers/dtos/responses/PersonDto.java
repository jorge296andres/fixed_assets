package com.asd.fixedassets.controllers.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class PersonDto {
    @JsonProperty("person_id")
    private Long personId;

    @JsonProperty(value = "person_name")
    private String personName;

    @JsonProperty(value = "document_type")
    private String documentType;

    @JsonProperty(value = "document_number")
    private String documentNumber;
}
