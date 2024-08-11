package com.nagornov.multimicroserviceproject.authservice.repository;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Repository
@AllArgsConstructor
public class RedisRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    public void saveKeyHashExpire(String key, Map<Object, Object> hash, Integer ttl, TimeUnit unit) {
         redisTemplate.opsForHash().putAll(key, hash);
         redisTemplate.expire(key, ttl, unit);
    }

    public void saveKeyValue(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void saveKeyHash(String key, Map<Object, Object> hash) {
        redisTemplate.opsForHash().putAll(key, hash);
    }

    public Object findValueByKey(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public Map<Object, Object> getHashByKey(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    public void setExpire(String key, Integer ttl, TimeUnit unit) {
        redisTemplate.expire(key, ttl, unit);
    }

    public void deleteByKey(String key) {
        redisTemplate.delete(key);
    }

}
