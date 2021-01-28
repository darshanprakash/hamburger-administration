package com.hackathon.restaurant.configuration;

import com.hackathon.restaurant.interceptor.ExecutionTimeInterceptor;
import com.hackathon.restaurant.interceptor.RequestValidatorInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    ExecutionTimeInterceptor executionTimeInterceptor;

    @Autowired
    RequestValidatorInterceptor requestValidatorInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(executionTimeInterceptor);
//        registry.addInterceptor(requestValidatorInterceptor)
//         .addPathPatterns("/api/location/*"); //.excludePathPatterns("/menus/*");

    }
}
