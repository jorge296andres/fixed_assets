package com.asd.fixedassets.exceptions.messages;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BusinessExceptionMessage {

    REQUEST_BODY("BUS001", "Error in request body", HttpStatus.BAD_REQUEST),
    FIXED_ASSET_NOT_FOUND("BUS002", "Fixed asset not found", HttpStatus.NOT_FOUND),
    AREA_NOT_FOUND("BUS003", "Area not found", HttpStatus.NOT_FOUND),
    PERSON_NOT_FOUND("BUS004", "Person not found", HttpStatus.NOT_FOUND),
    CITY_NOT_FOUND("BUS005", "City not found", HttpStatus.NOT_FOUND),
    AREA_ALREADY_EXISTS("BUS006", "Area already exists on this City", HttpStatus.BAD_REQUEST),
    CONSTRAINT_VIOLATION("BUS007", "Constraint violation exception", HttpStatus.BAD_REQUEST),
    AREA_NOT_NULL("BUS008", "Area is not null, to force the assignation add field " +
            "'force-reassign' to True", HttpStatus.BAD_REQUEST),
    PERSON_NOT_NULL("BUS009", "Person is not null, to force the assignation add field 'force-reassign'" +
            " to True", HttpStatus.BAD_REQUEST),
    FIXED_TYPE_NOT_EXIST("BUS010", "There are no articles with the requested TYPE",
            HttpStatus.NOT_FOUND);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
}
