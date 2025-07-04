package com.heds.project.config.token;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TokenWhiteList {
    private final Map<String,Long> validTokens = new ConcurrentHashMap<String,Long>();
    public void add(String token,long expireAt) {
        validTokens.put(token,expireAt);
    }
    public boolean contains(String token) {
        Long expireAt = validTokens.get(token);
        if (expireAt == null) return false;
        if (System.currentTimeMillis() > expireAt) {
            validTokens.remove(token); // 自动清理过期
            return false;
        }
        return true;
    }

    public void remove(String token) {
        validTokens.remove(token);
    }
    //return all validToken
    public Map<String, Long> getValidTokens() {
        return validTokens;
    }

    //测试
    //Test
    public void printAllTokens() {
        System.out.println("当前白名单中的 token 数量 number of tokens in whiltelist: " + validTokens.size());
        validTokens.forEach((token, expire) -> {
            System.out.println("token: " + token);
            System.out.println("过期时间 exp time: " + new java.util.Date(expire));
        });
    }

}
