package com.nagornov.multimicroserviceproject.authservice.service;

import com.nagornov.multimicroserviceproject.authservice.exception.GetUserException;
import com.nagornov.multimicroserviceproject.authservice.exception.SaveUserException;
import com.nagornov.multimicroserviceproject.authservice.model.User;
import com.nagornov.multimicroserviceproject.authservice.repository.RedisRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class RedisService {

    private final RedisRepository redisRepository;

    public void saveUser(User user, Integer expireMinutes) throws SaveUserException {
        try {
            redisRepository.saveKeyHashExpire(String.valueOf(user.getUserId()), user.toMap(), expireMinutes, TimeUnit.MINUTES);
        } catch (Exception e) {
            throw new SaveUserException();
        }
    }

    public User getUser(String key) throws GetUserException {
        try {
            Map<Object, Object> map = redisRepository.getHashByKey(key);
            return User.fromMap(map);
        } catch (Exception e) {
            throw new GetUserException();
        }
    }

}
