package com.lin.blog.portal.security;

import com.lin.blog.portal.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class WebConfig implements WebMvcConfigurer
{
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*");
    }

    @Resource
    private AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns(
                        "/auth/user",
                        "/auth/isAdmin",
                        "/comment/add",
                        "/article/save",
                        "/article/delete/*",
                        "/basic/save",
                        "/category/add",
                        "/category/delete/*",
                        "/resource/upload",
                        "/resource/allResources",
                        "/resource/delete/*"
                );
    }
}