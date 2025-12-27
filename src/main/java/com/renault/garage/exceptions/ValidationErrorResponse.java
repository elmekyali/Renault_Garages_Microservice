package com.renault.garage.exceptions;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ValidationErrorResponse {

    private int status;
    private String message;
    private LocalDateTime timestamp;
    private List<ValidationException.ValidationError> errors;

    public ValidationErrorResponse(int status, String message, List<ValidationException.ValidationError> errors) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.errors = errors;
    }
}
