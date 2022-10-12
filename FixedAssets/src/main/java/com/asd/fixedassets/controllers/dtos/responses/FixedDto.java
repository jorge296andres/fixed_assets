package com.asd.fixedassets.controllers.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@RequiredArgsConstructor
public class FixedDto {
    @JsonProperty(value = "inventory_id")
    private Long inventoryId;
    private String description;
    private String name;
    private String serial;
    private String type;
    private Double weight;
    private Double height;
    private Double width;
    private Double length;
    @JsonProperty(value = "purchase_value")
    private Double purchaseValue;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonProperty(value = "purchase_date")
    private LocalDate purchaseDate;

}
