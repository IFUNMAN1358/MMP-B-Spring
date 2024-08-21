package com.nagornov.multimicroserviceproject.userprofileservice.util;

import com.nagornov.multimicroserviceproject.userprofileservice.config.security.jwt.JwtAuthentication;
import com.nagornov.multimicroserviceproject.userprofileservice.model.Role;
import io.jsonwebtoken.Claims;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JwtUtil {

    public static JwtAuthentication generate(Claims claims) {
        final JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setRoles(getRoles(claims));
        jwtInfoToken.setUserId(claims.getSubject());
        return jwtInfoToken;
    }

    private static Set<Role> getRoles(Claims claims) {
        List<Map<String, String>> roles = claims.get("roles", List.class);
        return roles.stream()
                .map(roleMap -> new Role(roleMap.get("authority")))
                .collect(Collectors.toSet());
    }

}
