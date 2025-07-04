package com.heds.project.config.JWT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private JwtInterceptor jwtInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor) // 添加拦截器 add jwtInterceptor
                .addPathPatterns("/manage/**") // Intercept all requests under the '/admin' path
                .excludePathPatterns("/admin/auth"); // exclude ogin-related paths
    }

}
