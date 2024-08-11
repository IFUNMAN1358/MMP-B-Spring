package com.nagornov.multimicroserviceproject.authservice.service;

import com.nagornov.multimicroserviceproject.authservice.repository.SmtpRepository;
import com.nagornov.multimicroserviceproject.authservice.util.CustomLogger;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import lombok.AllArgsConstructor;
import org.eclipse.angus.mail.util.MailConnectException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SmtpService {

    private final SmtpRepository smtpRepository;

    @Async
    public void sendEmail(String to, String subject, String text) {
        Session session = smtpRepository.getSession();
        Transport transport = smtpRepository.getTransport(session);
        try {
            smtpRepository.sendEmail(session, to, subject, text);
        } catch (MessagingException e) {
            CustomLogger.error("Error sending email: " + e.getMessage());
            if (e instanceof MailConnectException) {
                CustomLogger.error("Mail health check failed: " + e.getMessage());
            }
        } finally {
            if (transport != null) {
                try {
                    transport.close();
                } catch (MessagingException e) {
                    CustomLogger.error("Error closing SMTP transport: " + e.getMessage());
                }
            }
        }
    }
}
