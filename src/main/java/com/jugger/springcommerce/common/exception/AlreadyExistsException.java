package com.jugger.springcommerce.common.exception;

import org.springframework.http.HttpStatus;

public class AlreadyExistsException extends AppException {
    public AlreadyExistsException(String message) {
        super(message, HttpStatus.CONFLICT, ErrorCode.RESOURCE_ALREADY_EXISTS);
    }
}
