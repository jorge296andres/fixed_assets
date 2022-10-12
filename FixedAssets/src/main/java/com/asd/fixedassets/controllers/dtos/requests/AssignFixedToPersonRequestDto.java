package com.asd.fixedassets.controllers.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@EqualsAndHashCode
@RequiredArgsConstructor
public class AssignFixedToPersonRequestDto {

    @NotNull
    @Positive
    @JsonProperty(value = "fixed_id")
    private Long fixedId;

    @NotNull
    @Positive
    @JsonProperty(value = "person_id")
    private Long personId;

    @JsonProperty(value = "force_reassign")
    private Boolean forceReassign;
}
