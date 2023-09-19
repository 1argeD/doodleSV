package com.example.doodle.Redis;

import com.example.doodle.Pen.Pen;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate<String, String> redisTemplate;
    private final RedisTemplate<String, Object> redisPenTemplate;

    private static final String PEN_KEY = "pen_id";
    private static String penKeyGenerate(String penId) {
        return PEN_KEY+":"+penId;
    }

    public void setValue(String key, String data) {
        ValueOperations<String, String> value =  redisTemplate.opsForValue();
        value.set(key, data);
    }

    public void setValue(String key,String data, Duration duration) {
        ValueOperations<String, String> value = redisTemplate.opsForValue();
        value.set(key, data, duration);
    }

    public String getValue(String key) {
        ValueOperations<String, String> value =  redisTemplate.opsForValue();
        return value.get(key);
    }

    public void deleteValue(String key) {
        redisPenTemplate.delete(key);
    }

    public void setPenValue(Pen pen, String penId) {
        String key = penKeyGenerate(penId);
        redisPenTemplate.opsForValue().set(key, pen);
        redisPenTemplate.expire(key,60, TimeUnit.MINUTES);
    }

    public void setPenValue(Pen pen, String penId, Duration duration) {
        String key = penKeyGenerate(penId);
        redisPenTemplate.opsForValue().set(key, pen,duration);
    }

    public Object getPenValue(String penId) {
        String key = penKeyGenerate(penId);
        return redisPenTemplate.opsForValue().get(key);
    }

    public void deletePenValue(String penId) {
        redisPenTemplate.delete(penKeyGenerate(penId));
    }
}
