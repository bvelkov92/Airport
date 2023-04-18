package com.my.airportproject.interceptors;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;

public class LoggingInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        System.out.println(request.getRequestURI() +"  " + request.getMethod());
        Iterator<String>  iterator = request.getHeaderNames().asIterator();
        while (iterator.hasNext()){
            String headerName = iterator.next();
            String headerValue = request.getHeader(headerName);
            System.out.println(headerName + ": " +headerValue);

        }
        return  true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView==null){
            return;
        }
        for (String entry: modelAndView.getModel().keySet()){
            System.out.println(entry +": " + modelAndView.getModel().get(entry));
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        System.out.println("======================= AFTER COMPLETITION ==========================");
        if (ex!=null){
            ex.printStackTrace();
        }

        response.setStatus(200);
    }
}
