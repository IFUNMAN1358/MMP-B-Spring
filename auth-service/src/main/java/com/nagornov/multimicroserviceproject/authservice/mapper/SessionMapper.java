package com.nagornov.multimicroserviceproject.authservice.mapper;

import com.nagornov.multimicroserviceproject.authservice.dto.session.SessionRequest;
import com.nagornov.multimicroserviceproject.authservice.model.Session;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SessionMapper {

    SessionMapper INSTANCE = Mappers.getMapper(SessionMapper.class);

    @Mapping(target = "sessionId", ignore = true)
    @Mapping(target = "lastActivity", ignore = true)
    Session toSession(SessionRequest request);
}
