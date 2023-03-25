package com.lin.blog.portal.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.annotation.Resource;

@Configuration
public class SecurityConfiguration
{
    @Resource
    private UserDetailsService userDetailsService;

    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws Exception
    {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Autowired
    void registerProvider(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception
    {
        return http.csrf().disable()
                .authorizeRequests()    // 開始配置請求路徑權限
                .anyRequest().permitAll().and().build();
    }
}
