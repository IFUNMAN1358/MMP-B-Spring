package com.nagornov.multimicroserviceproject.authservice.service;

import com.nagornov.multimicroserviceproject.authservice.config.security.jwt.JwtAuthentication;
import com.nagornov.multimicroserviceproject.authservice.dto.auth.AuthResponse;
import com.nagornov.multimicroserviceproject.authservice.dto.auth.LoginFormRequest;
import com.nagornov.multimicroserviceproject.authservice.dto.registration.RegFormRequest;
import com.nagornov.multimicroserviceproject.authservice.dto.registration.RegVerifyRequest;
import com.nagornov.multimicroserviceproject.authservice.dto.user.response.UserResponse;
import com.nagornov.multimicroserviceproject.authservice.exception.GetUserException;
import com.nagornov.multimicroserviceproject.authservice.exception.IncorrectVerificationCodeException;
import com.nagornov.multimicroserviceproject.authservice.exception.SaveUserException;
import com.nagornov.multimicroserviceproject.authservice.exception.UserAlreadyExistsException;
import com.nagornov.multimicroserviceproject.authservice.mapper.UserMapper;
import com.nagornov.multimicroserviceproject.authservice.model.User;
import com.nagornov.multimicroserviceproject.authservice.util.CodeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final RedisService redisService;
    private final UserProfileService userProfileService;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final SmtpService smtpService;
    private final TwilioService twilioService;


    public AuthResponse register(RegFormRequest req)
            throws SaveUserException, UserAlreadyExistsException {

        String randomCodeInt6 = CodeUtils.generateRandomCodeDigit(6);
        User user = userMapper.toUser(req);
        user.setUserId(UUID.randomUUID());
        user.setVerificationCode(randomCodeInt6);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        UserResponse checkUserExists = userProfileService.getUser(user);

        if (checkUserExists != null) {
            throw new UserAlreadyExistsException();
        }

        if (!user.getPhoneNumber().isEmpty()) {
            twilioService.sendSms(
                    user.getPhoneNumber(),
                    "Your verification code for registration: " + randomCodeInt6
            );
        } else if (!user.getEmail().isEmpty()) {
            smtpService.sendEmail(
                    user.getEmail(),
                    "Verification code",
                    "Your verification code for registration: " + randomCodeInt6
            );
        }
        redisService.saveUser(user, 3);
        return new AuthResponse(userMapper.toUserResponse(user), jwtService.getAccessToken(user));
    }

    public AuthResponse verifyRegister(RegVerifyRequest req, JwtAuthentication authInfo)
            throws GetUserException, IncorrectVerificationCodeException {

        User userFromRedis = redisService.getUser(authInfo.getUserId());

        if (!userFromRedis.getVerificationCode().equals(req.getVerificationCode())) {
            throw new IncorrectVerificationCodeException();
        }

        UserResponse userResponse = userProfileService.createUser(userFromRedis);
        User userFromService = userMapper.toUser(userResponse);

        return new AuthResponse(
            userResponse, jwtService.getAuthTokens(userFromService)
        );
    }

    public AuthResponse login(LoginFormRequest req) {
        User user = userMapper.toUser(req);
        UserResponse userFromService = userProfileService.verifyUser(user);

        return new AuthResponse(
                userFromService, jwtService.getAuthTokens(userMapper.toUser(userFromService))
        );
    }

}
