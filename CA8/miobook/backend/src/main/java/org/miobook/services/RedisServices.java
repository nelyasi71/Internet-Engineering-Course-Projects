//package org.miobook.services;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.stereotype.Service;
//
//import java.time.Duration;
//import java.util.Map;
//
//@Service
//public class RedisServices {
//    @Autowired
//    private StringRedisTemplate redisTemplate;
//
//    public void saveToken(String token, String username, String role, Duration ttl) {
//        Map<String, String> sessionData = Map.of("username", username, "role", role);
//        redisTemplate.opsForHash().putAll("session:" + token, sessionData);
//        redisTemplate.expire("session:" + token, ttl);
//    }
//
//    public String getUsername(String token) {
//        return (String) redisTemplate.opsForHash().get("session:" + token, "username");
//    }
//
//    public String getUserRole(String token) {
//        return (String) redisTemplate.opsForHash().get("session:" + token, "role");
//    }
//
//    public void refreshToken(String token, Duration ttl) {
//        redisTemplate.expire("session:" + token, ttl);
//    }
//
//    public void deleteToken(String token) {
//        redisTemplate.delete("session:" + token);
//    }
//}