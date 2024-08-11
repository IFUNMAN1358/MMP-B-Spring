package com.nagornov.multimicroserviceproject.authservice.repository;

import com.nagornov.multimicroserviceproject.authservice.config.SmtpConfig;
import com.nagornov.multimicroserviceproject.authservice.util.CustomLogger;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Properties;

@Repository
@AllArgsConstructor
public class SmtpRepository {

    private final SmtpConfig smtpConfig;

    public Session getSession() {
        Properties props = smtpConfig.getProperties();
        try {
            return Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(smtpConfig.getUsername(), smtpConfig.getPassword());
                }
            });
        } catch (Exception e) {
            CustomLogger.error("Failed to create SMTP session: " + e.getMessage());
            return null;
        }
    }

    public Transport getTransport(Session session) {
        try {
            Transport transport = session.getTransport(smtpConfig.getProtocol());
            transport.connect(smtpConfig.getHost(), smtpConfig.getPort(), smtpConfig.getUsername(), smtpConfig.getPassword());
            return transport;
        } catch (MessagingException e) {
            CustomLogger.error("Failed to create SMTP transport: " + e.getMessage());
            return null;
        }
    }

    public void sendEmail(Session session, String to, String subject, String text) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(smtpConfig.getUsername()));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setText(text);

        Transport.send(message);
    }
}
