package com.nagornov.multimicroserviceproject.authservice.dto.user.request;


import com.nagornov.multimicroserviceproject.authservice.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRequest {

    private User user;

}
