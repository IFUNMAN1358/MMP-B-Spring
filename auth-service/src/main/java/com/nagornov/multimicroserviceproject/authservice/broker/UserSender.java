package com.nagornov.multimicroserviceproject.authservice.broker;

import com.nagornov.multimicroserviceproject.authservice.model.User;
import com.nagornov.multimicroserviceproject.authservice.util.CustomLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class UserSender {

    private final StreamBridge streamBridge;
    private final Map<String, CompletableFuture<String>> responseMap = new ConcurrentHashMap<>();

    public String sendUserRequest(User user, String operation) {
        CompletableFuture<String> future = new CompletableFuture<>();

        String messageId = UUID.randomUUID().toString();
        responseMap.put(messageId, future);

        String request = "%s:%s:%s".formatted(messageId, operation, user);
        try {
            streamBridge.send("sendUserRequest-out-0", request);
            return future.get(5, TimeUnit.SECONDS);

        } catch (TimeoutException e) {
            CustomLogger.error("Timeout error sending user request: " + e.getMessage());
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            CustomLogger.error("Error sending user request: " + e.getMessage());
        } finally {
            responseMap.remove(messageId);
        }
        return "";
    }

    @Bean
    public Consumer<Message<String>> processUserResponse() {
        return message -> {
            String payload = message.getPayload();
            String[] parts = payload.split(":", 3);

            String messageId = parts[0];
            String operation = parts[1];
            String user = parts[2];

            CompletableFuture<String> future = responseMap.get(messageId);
            if (future != null) {
                future.complete(user);
            }
        };
    }

}
