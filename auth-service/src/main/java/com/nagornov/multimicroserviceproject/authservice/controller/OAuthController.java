package com.nagornov.multimicroserviceproject.authservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nagornov.multimicroserviceproject.authservice.dto.auth.AuthResponse;
import com.nagornov.multimicroserviceproject.authservice.dto.auth.OAuthTokenResponse;
import com.nagornov.multimicroserviceproject.authservice.dto.auth.OAuthUserInfoResponse;
import com.nagornov.multimicroserviceproject.authservice.config.properties.OAuthProvider;
import com.nagornov.multimicroserviceproject.authservice.service.OAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OAuthController {

    private final OAuthService oAuthService;
    private final OAuthProvider oAuthProvider;


    @PostMapping("/api/auth/{providerName}/code")
    public ResponseEntity<?> handleAuthCode(@PathVariable String providerName, @RequestParam String code) {
        try {
            oAuthProvider.setProviderData(providerName);

            OAuthTokenResponse tokenResponse = oAuthService.exchangeCodeForToken(
                    code,
                    oAuthProvider.getClientId(),
                    oAuthProvider.getClientSecret(),
                    oAuthProvider.getTokenUri(),
                    oAuthProvider.getRedirectUri()
            );
            OAuthUserInfoResponse userInfo = oAuthService.getUserInfo(
                    tokenResponse.getAccessToken(),
                    oAuthProvider.getUserInfoUri()
            );

            AuthResponse data = oAuthService.userAuth(userInfo);

            return ResponseEntity.status(HttpStatus.OK).body(data);
        }
        catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("OAuth DTO JSON parsing error");
        }
    }

}
