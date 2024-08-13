package com.nagornov.multimicroserviceproject.authservice.service;

import com.nagornov.multimicroserviceproject.authservice.exception.validation.CustomValidationException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
public class ExceptionService {

    public void fieldsValidation(BindingResult result) {
        if (result.hasErrors()) {
            throw new CustomValidationException(result);
        }
    }

}
