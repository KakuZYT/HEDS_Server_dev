package com.heds.project.config.JWT;

import com.heds.project.config.token.TokenWhiteList;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private TokenWhiteList tokenWhiteList;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从请求头中获取 Authorization 字段
        String token = request.getHeader("Authorization");
        //放行预检请求
        //Release Preflight Request
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }

        if (token == null || !token.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
            response.getWriter().write("Missing or invalid token");
            return false;
        }

        // remove the Bearer prefix to get real token
        token = token.substring(7);
        // check if token is in whiltelist
        if (!tokenWhiteList.contains(token)) {
            response.getWriter().write("Token does not active");
            return false;
        }
        // Validate the token
        if (!jwtUtils.validateToken(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
            response.getWriter().write("Invalid or expired token");
            return false;
        }


        // 把用户名保存到 request attribute，后面 controller 可以使用
        // Save the username into the request attribute so that it can be used in the controller later
        String username = jwtUtils.getUsernameFromToken(token);
        request.setAttribute("token", username);
        return true;
    }

    //自动执行清楚过期token
    //automatically remove expired tokens
    @Scheduled(fixedRate = 60*1000)
    public void ClearExpiredTokens() {
        long now = System.currentTimeMillis();
        tokenWhiteList.getValidTokens().entrySet()
                .removeIf(entry ->entry.getValue() < now);
    }
}
