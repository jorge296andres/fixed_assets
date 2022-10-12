package com.asd.fixedassets.exceptions;


import com.asd.fixedassets.exceptions.messages.BusinessExceptionMessage;
import com.asd.fixedassets.exceptions.messages.TechnicalExceptionMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@ControllerAdvice
public class FixedExceptionHandler {

    Logger logger = LoggerFactory.getLogger(FixedExceptionHandler.class);


    private static final String DELIMITED = ", ";
    private static final String COLON = ": ";
    private static final String ZONED_ID_Z = "Z";

    private String getPath(HttpServletRequest request) {
        String method = "METHOD";
        String uri = "URI";
        return Optional.ofNullable(request.getMethod()).orElse(method)
                .concat(COLON.concat(Optional.ofNullable(request.getRequestURI()).orElse(uri)));
    }

    private ErrorResponse buildApiExceptionForBusinessException(String description, HttpServletRequest request,
                                                                BusinessException businessException) {
        return ErrorResponse.builder()
                .reason(businessException.getMessage())
                .code(businessException.getCode())
                .domain(getPath(request))
                .description(description)
                .timestamp(ZonedDateTime.now(ZoneId.of(ZONED_ID_Z)))
                .build();
    }

    @ExceptionHandler(value = {BusinessException.class})
    public ResponseEntity<Object> handleBusinessException(BusinessException businessException,
                                                          HttpServletRequest request) {
        ErrorResponse badRequest = buildApiExceptionForBusinessException(
                businessException.getMessage(), request, businessException);

        logger.error(badRequest.toString());

        return new ResponseEntity<>(badRequest, businessException.getHttpStatus());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> onConstraintValidationException(ConstraintViolationException e,
                                                                  HttpServletRequest request) {
        String violations = e.getConstraintViolations().stream()
                .map(constraintViolation -> constraintViolation.getPropertyPath().toString().concat(COLON)
                        .concat(constraintViolation.getMessage()))
                .collect(Collectors.joining(DELIMITED));

        BusinessException businessException = new BusinessException(BusinessExceptionMessage.REQUEST_BODY);
        ErrorResponse validationError = buildApiExceptionForBusinessException(violations,
                request, businessException);
        logger.error(validationError.toString());
        return new ResponseEntity<>(validationError, businessException.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> onMethodArgumentNotValidException(MethodArgumentNotValidException e,
                                                                    HttpServletRequest request) {
        var violations = e.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField().concat(COLON)
                        .concat(Objects.requireNonNull(fieldError.getDefaultMessage())))
                .collect(Collectors.joining(DELIMITED));

        BusinessException businessException = new BusinessException(BusinessExceptionMessage.REQUEST_BODY);
        ErrorResponse validationError = buildApiExceptionForBusinessException(
                violations, request, businessException);
        logger.error(validationError.toString());
        return new ResponseEntity<>(validationError, businessException.getHttpStatus());
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentTypeMismatchException.class,
            MissingPathVariableException.class})
    public ResponseEntity<Object> handleBadRequest(RuntimeException e, HttpServletRequest request) {

        BusinessException businessException = new BusinessException(BusinessExceptionMessage.REQUEST_BODY);
        ErrorResponse badRequest = buildApiExceptionForBusinessException(e.getMessage(),
                request, businessException);
        logger.error(badRequest.toString());
        return new ResponseEntity<>(badRequest, businessException.getHttpStatus());
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<Object> handleConstraintValidation(DataIntegrityViolationException exception,
                                                             HttpServletRequest request) {

        String message = Optional.ofNullable(exception.getCause().getCause().toString())
                .orElse(exception.getCause().toString());

        BusinessException businessException = new BusinessException(BusinessExceptionMessage.CONSTRAINT_VIOLATION);
        ErrorResponse badRequest = buildApiExceptionForBusinessException(message, request, businessException);
        logger.error(badRequest.toString());
        return new ResponseEntity<>(badRequest, businessException.getHttpStatus());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleUnexpectedError(Exception e, HttpServletRequest request) {

        ErrorResponse unexpectedError = ErrorResponse.builder()
                .reason(TechnicalExceptionMessage.UNEXPECTED_ERROR.getMessage())
                .code(TechnicalExceptionMessage.UNEXPECTED_ERROR.getCode())
                .domain(getPath(request))
                .description(e.getMessage())
                .timestamp(ZonedDateTime.now(ZoneId.of(ZONED_ID_Z)))
                .build();
        logger.error(unexpectedError.toString());
        return new ResponseEntity<>(unexpectedError, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
