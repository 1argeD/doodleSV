package com.example.doodle.Redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisUtil {
    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisTemplate<String, Object> penListTemplate;

    private void set(String key, Object o,int minutes ) {
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.opsForValue().set(key, o, minutes, TimeUnit.MINUTES);
    }

    private Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    private boolean delete(String key) {
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

    private boolean hashKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    private void setPenListTemplate(String key, Object o, Long milliSecond) {
        penListTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        penListTemplate.opsForValue().set(key, o, milliSecond);
    }

    public Object getPenListTemplate(String key) {
        return penListTemplate.opsForValue().get(key);
    }

    public boolean deletePenListTemplate(String key) {
        return Boolean.TRUE.equals(penListTemplate.delete(key));
    }

    public boolean hasKeyPenList(String key) {
        return Boolean.TRUE.equals(penListTemplate.hasKey(key));
    }


}
