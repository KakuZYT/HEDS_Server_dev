package com.heds.project.config.captcha;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "hcaptcha")
public class HcaptchaConfig {
    private String secret;

    public String getSecret() {
        System.out.println(secret);
        return secret;
    }

    public void setSecret(String secret) {
        System.out.println(secret);
        this.secret = secret;
    }
}
