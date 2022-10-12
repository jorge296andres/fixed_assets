package com.asd.fixedassets.controllers.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;


@Getter
@Setter
@EqualsAndHashCode
@RequiredArgsConstructor
public class FixedAssetsRequestDto {

    @NotBlank
    private String description;

    @NotBlank
    private String name;

    @NotNull
    private String serial;

    @NotBlank
    private String type;

    @NotNull
    @DecimalMin("0.0")
    private Double weight;

    @NotNull
    @DecimalMin("0.0")
    private Double height;

    @NotNull
    @DecimalMin("0.0")
    private Double width;

    @NotNull
    @DecimalMin("0.0")
    private Double length;

    @NotNull
    @DecimalMin("0.0")
    @JsonProperty(value = "purchase_value")
    private Double purchaseValue;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonProperty(value = "purchase_date")
    private LocalDate purchaseDate;
}
