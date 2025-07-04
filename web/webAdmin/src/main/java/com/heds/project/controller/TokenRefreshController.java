package com.heds.project.controller;

import com.heds.project.config.JWT.JWTUtils;
import com.heds.project.config.token.TokenWhiteList;
import com.heds.project.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class TokenRefreshController {
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private TokenWhiteList tokenWhiteList;

    @PostMapping("/refresh")
    public Result<Map<String, String>> refreshToken(@RequestParam("refreshToken") String refreshToken) {
        try {
            String username = jwtUtils.getUsernameFromToken(refreshToken);

            // 验证是否过期
            // check if token has expired
            jwtUtils.validateRefreshToken(refreshToken);

            // new token
            String newAccessToken = jwtUtils.generateToken(username);
            String newRefreshToken = jwtUtils.generateRefreshToken(username);

            Map<String, String> data = new HashMap<>();
            data.put("accessToken", newAccessToken);
            data.put("refreshToken", newRefreshToken);

            // Add the accessToken to the whitelist
            // Add the accessToken to the whitelist
            tokenWhiteList.add(newAccessToken,jwtUtils.getExpirationDate(newAccessToken).getTime());
            tokenWhiteList.add(refreshToken,jwtUtils.getExpirationDateREF(newRefreshToken).getTime());
            tokenWhiteList.printAllTokens();

            return Result.ok(data);
        } catch (Exception e) {
            return Result.fail();
        }
    }
}
