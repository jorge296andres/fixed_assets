package com.asd.fixedassets.exceptions.messages;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum TechnicalExceptionMessage {

    UNEXPECTED_ERROR("TEC001", "Unexpected Error", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
}
