package com.jugger.springcommerce.common.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorResponse> handleAppException(
            AppException exception,
            HttpServletRequest request
    ) {
        log.warn("Handled application exception for path {}: {}", request.getRequestURI(), exception.getMessage());
        return buildResponse(exception.getMessage(), exception.getStatus(), exception.getCode(), request.getRequestURI());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception,
            HttpServletRequest request
    ) {
        String message = exception.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .orElse("Validation failed");

        log.warn("Validation failed for path {}: {}", request.getRequestURI(), message);
        return buildResponse(message, HttpStatus.BAD_REQUEST, ErrorCode.VALIDATION_ERROR, request.getRequestURI());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException exception,
            HttpServletRequest request
    ) {
        String message = exception.getMessage() != null && exception.getMessage().contains("Required request body is missing")
                ? "Request body is required"
                : "Malformed request body";

        log.warn("Malformed request body for path {}", request.getRequestURI(), exception);
        return buildResponse(message, HttpStatus.BAD_REQUEST, ErrorCode.VALIDATION_ERROR, request.getRequestURI());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(
            Exception exception,
            HttpServletRequest request
    ) {
        log.error("Unhandled exception for path {}", request.getRequestURI(), exception);
        return buildResponse("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.INTERNAL_ERROR, request.getRequestURI());
    }

    private ResponseEntity<ErrorResponse> buildResponse(
            String message,
            HttpStatus status,
            ErrorCode code,
            String path
    ) {
        ErrorResponse response = ErrorResponse.builder()
                .message(message)
                .status(status)
                .timestamp(Instant.now())
                .path(path)
                .code(code)
                .build();

        return ResponseEntity.status(status).body(response);
    }
}
