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
public class PersonRequestDto {
    @NotBlank
    @JsonProperty(value = "person_name")
    private String personName;

    @JsonProperty(value = "document_type")
    private String documentType;

    @JsonProperty(value = "document_number")
    private String documentNumber;
}
