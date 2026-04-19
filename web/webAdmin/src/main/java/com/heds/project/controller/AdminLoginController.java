package com.heds.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.heds.project.Service.AdminService;
import com.heds.project.config.JWT.JWTUtils;
import com.heds.project.config.modul.Admin;
import com.heds.project.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminLoginController {
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public Result<Object> login(@RequestParam String username, @RequestParam String password) {
        List<Admin> list = adminService.list(new QueryWrapper<Admin>().eq("name", username).eq("password", password));
        if (!list.isEmpty()) {
            String accessToken = jwtUtils.generateToken(username);
            String refreshToken = jwtUtils.generateRefreshToken(username);
            Map<String, String> data = new HashMap<>();
            data.put("accessToken", accessToken);
            data.put("refreshToken", refreshToken);
            return Result.ok(data);
        }
        return Result.fail();
    }

    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.ok("Logged out successfully.");
    }
}
