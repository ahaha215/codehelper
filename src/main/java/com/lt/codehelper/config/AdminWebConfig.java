package com.lt.codehelper.config;

import com.lt.codehelper.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * @ClassName AdminWebConfig
 * @Description web配置
 * @Author lt
 * @Version 1.0
 **/
@Configuration
public class AdminWebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")  //所有请求都被拦截包括静态资源
                .excludePathPatterns("/","/welcome","/login","/login.do","/register","/register.do","/resetPassword","/resetPassword.do","/sendVerificationCode.do","/privacy","/userAgreement",
                        "/index","/indexPage.do","/blog","/blogPage.do","/resource","/resourcePage.do","/wish","/wishPage.do","/partner","/partnerPage.do","/aboutWebMaster",
                        "/css/**","/images/**","/js/**","/lib/**"); //放行的请求
    }
}

