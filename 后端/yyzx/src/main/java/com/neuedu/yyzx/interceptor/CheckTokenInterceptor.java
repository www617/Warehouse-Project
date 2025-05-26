package com.neuedu.yyzx.interceptor;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CheckTokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 放行预检请求（重要！）
        if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
            // 设置CORS相关响应头（解决预检请求的跨域问题）
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            response.setHeader("Access-Control-Allow-Headers", "Content-Type, token"); // 允许token头
            response.setHeader("Access-Control-Max-Age", "3600"); // 缓存预检结果1小时
            return true;
        }

        // 关于图片的请求不做token检查
        if (request.getRequestURL().toString().contains("/images")) {
            return true;
        }

        // 获取token
        String token = request.getHeader("token");

        // 校验token
        if (token == null) {
            throw new Exception("token为空，请登录！！！");
        } else {
            // 校验token的有效性(正确性，时效性)
            try {
                JwtParser parser = Jwts.parser();
                // 解析token的SigningKey必须和生成token时设置密码一致
                parser.setSigningKey("neuedu123456");
                // 如果token正确（密码正确，有效期内）则正常执行，否则抛出异常
                Jws<Claims> claimsJws = parser.parseClaimsJws(token);
            } catch (Exception e) {
                throw new Exception("无效的token，请重新登录！", e);
            }
        }
        return true;
    }
}