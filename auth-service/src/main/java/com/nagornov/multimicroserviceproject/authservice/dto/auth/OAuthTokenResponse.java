package com.nagornov.multimicroserviceproject.authservice.dto.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class OAuthTokenResponse {

    private String accessToken;
    private String refreshToken;
    private Integer expiresIn;
    private List<String> scope;
    private String tokenType;
    private String idToken;

    public static OAuthTokenResponse fromString(String object) throws JsonProcessingException {
        OAuthTokenResponse res = new OAuthTokenResponse();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(object);

        if (json.has("access_token")) {
            res.setAccessToken(json.get("access_token").asText());
        }
        if (json.has("refresh_token")) {
            res.setRefreshToken(json.get("refresh_token").asText());
        }
        if (json.has("expires_in")) {
            res.setExpiresIn(json.get("expires_in").asInt());
        }
        if (json.has("scope")) {
            res.setScope(Arrays.stream(json.get("scope").asText().split(" ")).collect(Collectors.toList()));
        }
        if (json.has("token_type")) {
            res.setTokenType(json.get("token_type").asText());
        }
        if (json.has("id_token")) {
            res.setIdToken(json.get("id_token").asText());
        }

        return res;
    }
}
