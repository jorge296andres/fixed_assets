package com.asd.fixedassets.exceptions;

import com.asd.fixedassets.exceptions.messages.BusinessExceptionMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException {

    private final String message;
    private final String code;
    private final HttpStatus httpStatus;

    public BusinessException(BusinessExceptionMessage businessExceptionMessage) {
        this.message = businessExceptionMessage.getMessage();
        this.code = businessExceptionMessage.getCode();
        this.httpStatus = businessExceptionMessage.getHttpStatus();
    }

}
