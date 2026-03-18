package io.github.noagsa.authapi.exception;

import org.springframework.http.HttpStatus;

public record ErrorResponse(String message, HttpStatus httpStatus) {
}
