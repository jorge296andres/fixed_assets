package com.asd.fixedassets.exceptions;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.ZonedDateTime;

@Data
@RequiredArgsConstructor
@Builder(toBuilder = true)
public class ErrorResponse {

    private final String reason;
    private final String code;
    private final String domain;
    private final ZonedDateTime timestamp;
    private final String description;

}
