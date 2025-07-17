package com.heds.project.controller;

import com.heds.project.config.captcha.HcaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CaptchaTestController {

    @Autowired
    private HcaptchaService hcaptchaService;

    @PostMapping("/test-captcha")
    public ResponseEntity<String> testCaptcha(@RequestParam("token") String token) {
//        System.out.println(token);
        boolean success = hcaptchaService.verifyToken(token);
        if (success) {
            return ResponseEntity.ok("Captcha 验证成功！");
        } else {
            return ResponseEntity.badRequest().body("Captcha 验证失败！");
        }
    }
}
