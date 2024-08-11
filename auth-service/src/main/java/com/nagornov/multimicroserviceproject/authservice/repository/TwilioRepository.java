package com.nagornov.multimicroserviceproject.authservice.repository;

import com.nagornov.multimicroserviceproject.authservice.config.TwilioConfig;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import com.twilio.type.PhoneNumber;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class TwilioRepository {

    private final TwilioConfig twilioConfig;

    public void startVerification(String toPhoneNumber, String channel) {
        Verification.creator(
                twilioConfig.getVerifyServiceSid(),
                toPhoneNumber,
                channel)
            .create();
    }

    public Boolean checkVerification(String toPhoneNumber, String code) {
        VerificationCheck verificationCheck = VerificationCheck.creator(
                twilioConfig.getVerifyServiceSid(),
                code)
            .setTo(toPhoneNumber)
            .create();
        return "approved".equals(verificationCheck.getStatus());
    }

    public void sendSms(String toPhoneNumber, String messageBody) {
        Message.creator(
                new PhoneNumber(toPhoneNumber),
                new PhoneNumber(twilioConfig.getFromPhoneNumber()),
                messageBody
        )
        .create();
    }

}
