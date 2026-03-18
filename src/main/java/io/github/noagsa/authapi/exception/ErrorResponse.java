package io.github.noagsa.authapi.exceptions;

import org.springframework.http.HttpStatus;

public record ErrorResponse(String message, HttpStatus httpStatus) {
}
