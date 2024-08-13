package com.nagornov.multimicroserviceproject.authservice.controller;

import com.nagornov.multimicroserviceproject.authservice.config.security.jwt.JwtAuthentication;
import com.nagornov.multimicroserviceproject.authservice.dto.auth.AuthResponse;
import com.nagornov.multimicroserviceproject.authservice.dto.registration.RegFormRequest;
import com.nagornov.multimicroserviceproject.authservice.dto.registration.RegVerifyRequest;
import com.nagornov.multimicroserviceproject.authservice.exception.user.GetUserException;
import com.nagornov.multimicroserviceproject.authservice.exception.validation.IncorrectVerificationCodeException;
import com.nagornov.multimicroserviceproject.authservice.exception.user.SaveUserException;
import com.nagornov.multimicroserviceproject.authservice.exception.user.UserAlreadyExistsException;
import com.nagornov.multimicroserviceproject.authservice.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class RegistrationController {

    private final ExceptionService exceptionService;
    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping("/api/registration")
    public ResponseEntity<?> registration(@Valid @RequestBody RegFormRequest request, BindingResult result) {
        try {
            exceptionService.fieldsValidation(result);

            AuthResponse data = userService.register(request);

            return ResponseEntity.status(HttpStatus.CREATED).body(data);
        }
        catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User already exists");
        }
        catch (SaveUserException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Redis save user error");
        }
    }

    @PostMapping("/api/registration/verify")
    public ResponseEntity<?> verifyRegistration(@RequestBody RegVerifyRequest request) {
        try {
            JwtAuthentication authInfo = jwtService.getAuthInfo();
            AuthResponse data = userService.verifyRegister(request, authInfo);

            return ResponseEntity.status(HttpStatus.CREATED).body(data);
        }
        catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User already exists");
        }
        catch (GetUserException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Redis get user error");
        }
        catch (IncorrectVerificationCodeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    "Entered verification code doesnt match saved verification code. Please, try write again."
            );
        }
    }

}
