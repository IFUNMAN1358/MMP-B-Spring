package com.nagornov.multimicroserviceproject.authservice.service;

import com.nagornov.multimicroserviceproject.authservice.dto.jwt.JwtAuthentication;
import com.nagornov.multimicroserviceproject.authservice.dto.auth.AuthResponse;
import com.nagornov.multimicroserviceproject.authservice.dto.auth.LoginFormRequest;
import com.nagornov.multimicroserviceproject.authservice.dto.registration.RegFormRequest;
import com.nagornov.multimicroserviceproject.authservice.dto.registration.RegVerifyRequest;
import com.nagornov.multimicroserviceproject.authservice.exception.user.GetUserException;
import com.nagornov.multimicroserviceproject.authservice.exception.user.UserNotFoundException;
import com.nagornov.multimicroserviceproject.authservice.exception.validation.IncorrectPasswordException;
import com.nagornov.multimicroserviceproject.authservice.exception.validation.IncorrectVerificationCodeException;
import com.nagornov.multimicroserviceproject.authservice.exception.user.SaveUserException;
import com.nagornov.multimicroserviceproject.authservice.exception.user.UserAlreadyExistsException;
import com.nagornov.multimicroserviceproject.authservice.mapper.UserMapper;
import com.nagornov.multimicroserviceproject.authservice.model.User;
import com.nagornov.multimicroserviceproject.authservice.util.CodeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final RedisService redisService;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final SmtpService smtpService;
    private final TwilioService twilioService;
    private final UserSenderService userSenderService;


    public AuthResponse register(RegFormRequest req)
            throws SaveUserException, UserAlreadyExistsException {

        String randomCodeInt6 = CodeUtils.generateRandomCodeDigit(6);
        User user = userMapper.toUser(req);
        user.setUserId(UUID.randomUUID());
        user.setVerificationCode(randomCodeInt6);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Optional<User> checkUserExists = userSenderService.getUser(user);

        if (checkUserExists.isPresent()) {
            throw new UserAlreadyExistsException();
        }

        if (user.getPhoneNumber() != null) {
            twilioService.sendSms(
                    user.getPhoneNumber(),
                    "Your verification code for registration: " + randomCodeInt6
            );
        } else if (user.getEmail() != null) {
            smtpService.sendEmail(
                    user.getEmail(),
                    "Verification code",
                    "Your verification code for registration: " + randomCodeInt6
            );
        }
        redisService.saveUser(user, 3);
        return new AuthResponse(
                userMapper.toUserResponse(user),
                jwtService.getAccessToken(user)
        );
    }

    public AuthResponse verifyRegister(RegVerifyRequest req, JwtAuthentication authInfo)
            throws GetUserException, IncorrectVerificationCodeException, UserAlreadyExistsException {

        User userFromRedis = redisService.getUser(authInfo.getUserId());

        if (!userFromRedis.getVerificationCode().equals(req.getVerificationCode())) {
            throw new IncorrectVerificationCodeException();
        }

        Optional<User> createdUser = userSenderService.createUser(userFromRedis);
        if (createdUser.isEmpty()) {
            throw new UserAlreadyExistsException();
        }

        return new AuthResponse(
            userMapper.toUserResponse(createdUser.get()),
            jwtService.getAuthTokens(createdUser.get())
        );
    }

    public AuthResponse login(LoginFormRequest req) throws UserNotFoundException, IncorrectPasswordException {

        User user = userMapper.toUser(req);
        Optional<User> userFromService = userSenderService.getUser(user);

        if (userFromService.isEmpty()) {
            throw new UserNotFoundException();
        }

        if (!passwordEncoder.matches(user.getPassword(), userFromService.get().getPassword())) {
            throw new IncorrectPasswordException();
        }

        return new AuthResponse(
                userMapper.toUserResponse(userFromService.get()),
                jwtService.getAuthTokens(userFromService.get())
        );
    }

}
