package com.asd.fixedassets.controllers;

import com.asd.fixedassets.controllers.dtos.requests.AssignFixedToAreaRequestDto;
import com.asd.fixedassets.controllers.dtos.requests.AssignFixedToPersonRequestDto;
import com.asd.fixedassets.controllers.dtos.requests.FixedAssetsRequestDto;
import com.asd.fixedassets.controllers.dtos.responses.FixedResponseDto;
import com.asd.fixedassets.services.FixedAssetsService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/v1/fixed-assets/", produces = APPLICATION_JSON_VALUE)
public class FixedAssetsController {


    private final FixedAssetsService fixedAssetsService;

    @GetMapping
    public List<FixedResponseDto> getAllFixedAssets() {
        return fixedAssetsService.getAllFixedAssets();
    }

    @GetMapping(value = "{inventory-id}")
    public FixedResponseDto getFixedById(@PathVariable("inventory-id") Long inventoryId) {

        return fixedAssetsService.getFixedById(inventoryId);
    }

    @GetMapping(value = "get-by-type/{type}")
    public List<FixedResponseDto> getAllByType(@PathVariable("type") @NotBlank String type) {
        return fixedAssetsService.getFixedByType(type);
    }

    @GetMapping(value = "get-by-purchase-date/{date}")
    public List<FixedResponseDto> getAllByPurchaseDate(
            @PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return fixedAssetsService.getFixedByPurchaseDate(date);
    }

    @GetMapping(value = "get-by-serial/{serial}")
    public FixedResponseDto getAllBySerial(
            @PathVariable("serial") @NotBlank String serial) {
        return fixedAssetsService.getFixedBySerial(serial);
    }

    @PostMapping
    public FixedResponseDto saveFixedAsset(@Valid @RequestBody FixedAssetsRequestDto fixedAssetsDto) {
        return fixedAssetsService.saveFixedAsset(fixedAssetsDto);
    }

    @PutMapping(value = "{inventory-id}")
    public FixedResponseDto updateFixedAsset(
            @PathVariable("inventory-id") Long inventoryId, @RequestBody FixedAssetsRequestDto fixedAssetsDto) {
        return fixedAssetsService.updateFixedAsset(inventoryId, fixedAssetsDto);
    }

    @PostMapping(path = "assign-fixed-to-area")
    public FixedResponseDto assignFixedAssetToArea(
            @Valid @RequestBody AssignFixedToAreaRequestDto assignFixedToAreaDto) {
        return fixedAssetsService.assignFixedToArea(assignFixedToAreaDto);
    }

    @PostMapping(path = "assign-fixed-to-person")
    public FixedResponseDto assignFixedAssetToPerson(
            @Valid @RequestBody AssignFixedToPersonRequestDto assignFixedToPersonDto) {
        return fixedAssetsService.assignFixedToPerson(assignFixedToPersonDto);
    }

}
