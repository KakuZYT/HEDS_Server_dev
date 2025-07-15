package com.heds.project.config.captcha;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


@Service
public class HcaptchaService {
    private static final String VERIFY_URL = "https://hcaptcha.com/siteverify";

    @Autowired
    private HcaptchaConfig config;

    public boolean verifyToken(String token) {
        //发送http请求
        //send http request
        RestTemplate restTemplate = new RestTemplate();

        //存储验证token
        //Store verify token
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("secret", config.getSecret());
        body.add("response", token);
        //设置请求头
        //set request head
        HttpHeaders headers = new HttpHeaders();
        //MediaType 设置数据类型
        //MediaType set data form
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(VERIFY_URL, request, Map.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            Map<String, Object> result = response.getBody();
            return Boolean.TRUE.equals(result.get("success"));
        }

        return false;
    }
}
