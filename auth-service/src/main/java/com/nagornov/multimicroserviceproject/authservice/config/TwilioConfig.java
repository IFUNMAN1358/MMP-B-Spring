package com.nagornov.multimicroserviceproject.authservice.config;

import com.nagornov.multimicroserviceproject.authservice.util.CustomLogger;
import com.twilio.Twilio;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class TwilioConfig {

    @Value("${twilio.accountSid}")
    private String accountSid;

    @Value("${twilio.authToken}")
    private String authToken;

    @Value("${twilio.verifyServiceSid}")
    private String verifyServiceSid;

    @Value("${twilio.phoneNumber}")
    private String fromPhoneNumber;

    @PostConstruct
    private void twilioInit() {
        try {
            Twilio.init(this.accountSid, this.authToken);
            CustomLogger.info("Twilio successfully initialized");
        } catch (Exception e) {
            CustomLogger.error("Exception when connecting to Twilio: " + e.getMessage());
        }
    }

}