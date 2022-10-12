package com.asd.fixedassets.exceptions;

import com.asd.fixedassets.exceptions.messages.TechnicalExceptionMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class TechnicalException extends RuntimeException {

    private final String message;
    private final String code;
    private final HttpStatus httpStatus;

    public TechnicalException(TechnicalExceptionMessage technicalExceptionMessage) {
        this.message = technicalExceptionMessage.getMessage();
        this.code = technicalExceptionMessage.getCode();
        this.httpStatus = technicalExceptionMessage.getHttpStatus();
    }

}
