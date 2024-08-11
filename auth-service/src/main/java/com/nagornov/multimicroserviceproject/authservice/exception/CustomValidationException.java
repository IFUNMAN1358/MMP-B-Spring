package com.nagornov.multimicroserviceproject.authservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.BindingResult;

@Getter
@AllArgsConstructor
public class CustomValidationException extends RuntimeException {
    private final BindingResult bindingResult;
}
