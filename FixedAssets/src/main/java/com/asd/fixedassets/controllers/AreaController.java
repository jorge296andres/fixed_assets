package com.asd.fixedassets.controllers;

import com.asd.fixedassets.controllers.dtos.requests.AreaRequestDto;
import com.asd.fixedassets.controllers.dtos.responses.AreaResponseDto;
import com.asd.fixedassets.services.AreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RequiredArgsConstructor
@RestController
@RequestMapping(path = "api/v1/area", produces = APPLICATION_JSON_VALUE)
public class AreaController {

    private final AreaService areaService;

    @GetMapping
    public List<AreaResponseDto> getAllAreas() {
        return areaService.getAllAreas();
    }

    @GetMapping(value = "/{area-id}")
    public AreaResponseDto getAreaById(@PathVariable("area-id") Long areaId) {
        return areaService.getAreaById(areaId);
    }

    @PostMapping
    public AreaResponseDto saveArea(@Valid @RequestBody AreaRequestDto areaDto) {
        return areaService.saveArea(areaDto);
    }

    @PutMapping(value = "/{area-id}")
    public AreaResponseDto updateArea(@PathVariable("area-id") Long areaId, @RequestBody AreaRequestDto areaDto) {
        return areaService.updateArea(areaId, areaDto);
    }

}
