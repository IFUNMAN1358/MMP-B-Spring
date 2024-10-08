package com.nagornov.multimicroserviceproject.authservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nagornov.multimicroserviceproject.authservice.dto.auth.AuthResponse;
import com.nagornov.multimicroserviceproject.authservice.dto.auth.OAuthTokenResponse;
import com.nagornov.multimicroserviceproject.authservice.dto.auth.OAuthUserInfoResponse;
import com.nagornov.multimicroserviceproject.authservice.mapper.UserMapper;
import com.nagornov.multimicroserviceproject.authservice.model.User;
import com.nagornov.multimicroserviceproject.authservice.util.CodeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OAuthService {

    private final UserSenderService userSenderService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    public OAuthTokenResponse exchangeCodeForToken(String code, String clientId, String clientSecret, String tokenUri, String redirectUri)
            throws JsonProcessingException {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("code", code);
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUri);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(tokenUri, request, String.class);
        return OAuthTokenResponse.fromString(response.getBody());
    }

    public OAuthUserInfoResponse getUserInfo(String accessToken, String userInfoUri)
            throws JsonProcessingException {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(userInfoUri, HttpMethod.GET, entity, String.class);
        return OAuthUserInfoResponse.fromString(response.getBody());
    }

    public AuthResponse userAuth(OAuthUserInfoResponse userInfo) {
        User user = userMapper.toUser(userInfo);
        user.setPassword(passwordEncoder.encode(CodeUtils.generateRandomCodeDigitChars(6, 6)));

        Optional<User> userFromService = userSenderService.getUser(user);

        AuthResponse data = new AuthResponse();

        if (userFromService.isPresent()) {
            User svUser = userFromService.get();

            data.setUser(userMapper.toUserResponse(svUser));
            data.setTokens(jwtService.getAuthTokens(svUser));
        } else {
            Optional<User> createdUser = userSenderService.createUser(user);
            User crUser = createdUser.get();

            data.setUser(userMapper.toUserResponse(crUser));
            data.setTokens(jwtService.getAuthTokens(crUser));
        }
        return data;
    }

}
