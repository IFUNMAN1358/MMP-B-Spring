package com.nagornov.multimicroserviceproject.authservice.util;

import com.nagornov.multimicroserviceproject.authservice.config.security.jwt.JwtAuthentication;
import com.nagornov.multimicroserviceproject.authservice.model.Role;
import io.jsonwebtoken.Claims;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JwtUtils {

    public static JwtAuthentication generateAccessInfo(Claims claims) {
        final JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setRoles(getRoles(claims));
        jwtInfoToken.setUserId(claims.getSubject());
        return jwtInfoToken;
    }

    public static JwtAuthentication generateRefreshInfo(Claims claims) {
        final JwtAuthentication jwtInfoToken = new JwtAuthentication();
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
