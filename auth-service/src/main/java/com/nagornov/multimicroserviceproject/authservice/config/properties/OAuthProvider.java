package com.nagornov.multimicroserviceproject.authservice.config.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class OAuthProvider {

    private final String googleClientId;
    private final String googleClientSecret;
    private final String googleTokenUri;
    private final String googleRedirectUri;
    private final String googleUserInfoUri;

    private final String facebookClientId;
    private final String facebookClientSecret;
    private final String facebookTokenUri;
    private final String facebookRedirectUri;
    private final String facebookUserInfoUri;

    private final String yandexClientId;
    private final String yandexClientSecret;
    private final String yandexTokenUri;
    private final String yandexRedirectUri;
    private final String yandexUserInfoUri;

    private String clientId;
    private String clientSecret;
    private String tokenUri;
    private String redirectUri;
    private String userInfoUri;
    private String name;

    public OAuthProvider(
        @Value("${spring.security.oauth2.client.registration.google.client-id}") String googleClientId,
        @Value("${spring.security.oauth2.client.registration.google.client-secret}") String googleClientSecret,
        @Value("${spring.security.oauth2.client.provider.google.token-uri}") String googleTokenUri,
        @Value("${spring.security.oauth2.client.registration.google.redirect-uri}") String googleRedirectUri,
        @Value("${spring.security.oauth2.client.provider.google.user-info-uri}") String googleUserInfoUri,

        @Value("${spring.security.oauth2.client.registration.facebook.client-id}") String facebookClientId,
        @Value("${spring.security.oauth2.client.registration.facebook.client-secret}") String facebookClientSecret,
        @Value("${spring.security.oauth2.client.provider.facebook.token-uri}") String facebookTokenUri,
        @Value("${spring.security.oauth2.client.registration.facebook.redirect-uri}") String facebookRedirectUri,
        @Value("${spring.security.oauth2.client.provider.facebook.user-info-uri}") String facebookUserInfoUri,

        @Value("${spring.security.oauth2.client.registration.yandex.client-id}") String yandexClientId,
        @Value("${spring.security.oauth2.client.registration.yandex.client-secret}") String yandexClientSecret,
        @Value("${spring.security.oauth2.client.provider.yandex.token-uri}") String yandexTokenUri,
        @Value("${spring.security.oauth2.client.registration.yandex.redirect-uri}") String yandexRedirectUri,
        @Value("${spring.security.oauth2.client.provider.yandex.user-info-uri}") String yandexUserInfoUri
    ) {
        this.googleClientId = googleClientId;
        this.googleClientSecret = googleClientSecret;
        this.googleTokenUri = googleTokenUri;
        this.googleRedirectUri = googleRedirectUri;
        this.googleUserInfoUri = googleUserInfoUri;

        this.facebookClientId = facebookClientId;
        this.facebookClientSecret = facebookClientSecret;
        this.facebookTokenUri = facebookTokenUri;
        this.facebookRedirectUri = facebookRedirectUri;
        this.facebookUserInfoUri = facebookUserInfoUri;

        this.yandexClientId = yandexClientId;
        this.yandexClientSecret = yandexClientSecret;
        this.yandexTokenUri = yandexTokenUri;
        this.yandexRedirectUri = yandexRedirectUri;
        this.yandexUserInfoUri = yandexUserInfoUri;
    }

    public void setProviderData(String providerName) {
        switch (providerName) {
            case "google" -> setGoogleProviderData();
            case "facebook" -> setFacebookProviderData();
            case "yandex" -> setYandexProviderData();
        }
    }

    private void setGoogleProviderData() {
        this.clientId = this.googleClientId;
        this.clientSecret = this.googleClientSecret;
        this.tokenUri = this.googleTokenUri;
        this.redirectUri = this.googleRedirectUri;
        this.userInfoUri = this.googleUserInfoUri;
        this.name = "google";
    }

    private void setFacebookProviderData() {
        this.clientId = this.facebookClientId;
        this.clientSecret = this.facebookClientSecret;
        this.tokenUri = this.facebookTokenUri;
        this.redirectUri = this.facebookRedirectUri;
        this.userInfoUri = this.facebookUserInfoUri;
        this.name = "facebook";
    }

    private void setYandexProviderData() {
        this.clientId = this.yandexClientId;
        this.clientSecret = this.yandexClientSecret;
        this.tokenUri = this.yandexTokenUri;
        this.redirectUri = this.yandexRedirectUri;
        this.userInfoUri = this.yandexUserInfoUri;
        this.name = "yandex";
    }

}
