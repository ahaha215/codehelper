package com.lt.codehelper.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @ClassName LoginInterceptor
 * @Description 登录拦截器，登录检查
 * @Author lt
 * @Version 1.0
 **/
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * @MethodName preHandle
     * @Description 目标方法执行之前
     * @Author lt
     * @Param [request, response, handler]
     * @return boolean
     **/
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //登录检查逻辑
        HttpSession session = request.getSession();

        Object loginUser = session.getAttribute("loginUser");

        if(loginUser != null){
            // 放行
            return true;
        }
        // 拦截住，未登录，跳转到登录页
        request.setAttribute("login-msg","请先登录");
        request.getRequestDispatcher("/login").forward(request,response);
        return false;
    }

    /**
     * @MethodName postHandle
     * @Description 目标方法执行完成之后
     * @Author lt
     * @Param [request, response, handler, modelAndView]
     * @return void
     **/
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandle执行{}",modelAndView);
    }

    /**
     * @MethodName afterCompletion
     * @Description 页面渲染之后
     * @Author lt
     * @Param [request, response, handler, ex]
     * @return void
     **/
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("afterCompletion执行异常{}",ex);
    }
}
