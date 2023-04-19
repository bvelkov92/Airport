package com.my.airportproject.interceptors;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SiteViewInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            System.out.println("=================== INFORMATION ABOUT VIEW  =============================");
            String username = "Anonymous";
            if (request.getRemoteUser() != null) {
                username = request.getRemoteUser();
            }
            System.out.println("User: " + username + " with Ip: " + request.getRemoteAddr() + " view your site");
            return true;
    }
}
