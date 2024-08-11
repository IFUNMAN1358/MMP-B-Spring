package com.nagornov.multimicroserviceproject.userprofileservice.mapper;

import com.nagornov.multimicroserviceproject.userprofileservice.dto.user.request.UserRegisterDataRequest;
import com.nagornov.multimicroserviceproject.userprofileservice.dto.user.response.UserResponse;
import com.nagornov.multimicroserviceproject.userprofileservice.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toUserResponse(User user);

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "isPhoneVerified", ignore = true)
    @Mapping(target = "isEmailVerified", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "dateOfBirth", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "lastLogin", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User toUser(UserRegisterDataRequest req);
    
}
