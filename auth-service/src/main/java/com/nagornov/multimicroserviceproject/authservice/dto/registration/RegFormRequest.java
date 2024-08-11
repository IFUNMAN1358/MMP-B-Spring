package com.nagornov.multimicroserviceproject.authservice.dto.registration;


import jakarta.validation.constraints.*;
import lombok.Data;


@Data
public class RegFormRequest {

    @NotBlank(message = "Firstname is required")
    @Size(min = 2, max = 30, message = "Firstname must be between 2 and 30 characters")
    @Pattern(regexp = "^\\p{L}+$", message = "Firstname must contain only letters")
    private String firstName;

    @NotBlank(message = "Lastname is required")
    @Size(min = 2, max = 30, message = "Lastname must be between 2 and 30 characters")
    @Pattern(regexp = "^\\p{L}+$", message = "Lastname must contain only letters")
    private String lastName;

    @Pattern(regexp = "^$|^\\+[0-9]{1,3}[0-9]{7,15}$", message = "Phone number must be contain a country code starting with a plus and contain no spaces")
    private String phoneNumber;

    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Username is required")
    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 60, message = "Password must be between 6 and 60 characters")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,60}$", message = "Password must contain at least one digit, one lowercase and one uppercase letter")
    private String password;

    @NotBlank(message = "Confirm Password is required")
    @Size(min = 6, max = 60, message = "Confirm Password must be between 6 and 60 characters")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,60}$", message = "Confirm Password must contain at least one digit, one lowercase and one uppercase letter")
    private String confirmPassword;

    @AssertTrue(message = "Password and Confirm Password do not match")
    public boolean isPasswordMatching() {
        return password != null && password.equals(confirmPassword);
    }

    @AssertTrue(message = "Phone number or email is required")
    public boolean isPhoneAndEmail() {
        return (!phoneNumber.isEmpty() || !email.isEmpty());
    }

}
