package com.nagornov.multimicroserviceproject.authservice.service;


import com.nagornov.multimicroserviceproject.authservice.repository.TwilioRepository;
import com.nagornov.multimicroserviceproject.authservice.util.CustomLogger;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TwilioService {

    private final TwilioRepository twilioRepository;

    @Async
    public void sendSms(String toPhoneNumber, String messageBody) {
        try {
            twilioRepository.sendSms(toPhoneNumber, messageBody);
        } catch (Exception e) {
            CustomLogger.error("Error when sending sms to user phone number: " + e.getMessage());
        }
    }

}
