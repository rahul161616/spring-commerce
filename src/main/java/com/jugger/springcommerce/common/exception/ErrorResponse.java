package com.jugger.springcommerce.common.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Getter
@Builder
public class ErrorResponse {
    private final String message;
    private final HttpStatus status;
    private final Instant timestamp;
    private final String path;
    private final ErrorCode code;
}
