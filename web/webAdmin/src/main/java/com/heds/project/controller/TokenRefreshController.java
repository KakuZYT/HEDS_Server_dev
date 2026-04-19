package com.heds.project.controller;

import com.heds.project.config.JWT.JWTUtils;
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

    @PostMapping("/refresh")
    public Result<Map<String, String>> refreshToken(@RequestParam("refreshToken") String refreshToken) {
        try {
            if (!jwtUtils.validateRefreshToken(refreshToken)) {
                return Result.fail();
            }

            String username = jwtUtils.getUsernameFromRefreshToken(refreshToken);
            String newAccessToken = jwtUtils.generateToken(username);
            String newRefreshToken = jwtUtils.generateRefreshToken(username);

            Map<String, String> data = new HashMap<>();
            data.put("accessToken", newAccessToken);
            data.put("refreshToken", newRefreshToken);

            return Result.ok(data);
        } catch (Exception e) {
            return Result.fail();
        }
    }
}
