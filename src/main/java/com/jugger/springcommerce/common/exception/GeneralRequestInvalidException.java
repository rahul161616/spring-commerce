package com.jugger.springcommerce.common.exception;

import org.springframework.http.HttpStatus;

public class GeneralRequestInvalidException extends AppException {
    public GeneralRequestInvalidException(String message) {
        super(message, HttpStatus.BAD_REQUEST, ErrorCode.VALIDATION_ERROR);
    }
}
