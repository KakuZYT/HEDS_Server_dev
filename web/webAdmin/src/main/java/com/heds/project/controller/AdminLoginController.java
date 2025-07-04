package com.heds.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.heds.project.Service.AdminService;
import com.heds.project.config.JWT.JWTUtils;
import com.heds.project.config.modul.Admin;
import com.heds.project.config.token.TokenWhiteList;
import com.heds.project.result.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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
    @Autowired
    private TokenWhiteList tokenWhiteList;

    @PostMapping("/login")
    public Result<Object> login(@RequestParam String username, @RequestParam String password,HttpServletRequest request) {
        List<Admin> list = adminService.list(new QueryWrapper<Admin>().like("name", "admin").like("password", "admin"));

        if(!list.isEmpty()){
            String accessToken = jwtUtils.generateToken(username);
            String refreshToken = jwtUtils.generateRefreshToken(username);
            Map<String, String> data = new HashMap<>();
            data.put("accessToken", accessToken);
            data.put("refreshToken", refreshToken);
            // Add the accessToken to the whitelist
            tokenWhiteList.add(accessToken,jwtUtils.getExpirationDate(accessToken).getTime());
            tokenWhiteList.add(refreshToken,jwtUtils.getExpirationDateREF(refreshToken).getTime());
            tokenWhiteList.printAllTokens();
            // session
            request.getSession().setAttribute("user", username);

            return Result.ok(data);
        }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
    }

    @PostMapping("/logout")
    public Result<String> logout(HttpServletRequest request) {
        // 获取 Authorization 头部
        // get the head of Authorization
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            tokenWhiteList.remove(token); // remove token from whitelist
            tokenWhiteList.printAllTokens();
            System.out.println("ok");
        }else{
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }

        // 清除 session
        // clear session
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return Result.ok("Logged out successfully.");
    }


}
