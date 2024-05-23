package com.zdf.mfwuser.config;

import com.zdf.mfwuser.interceptor.LogInInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *@Description WebConfig
 *@Author mrzhang
 *@Date 2024/5/22 21:43
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public LogInInterceptor logInInterceptor(){
        return new LogInInterceptor();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logInInterceptor())
                .addPathPatterns("/**");
    }
}
