package com.lin.blog.portal.service.impl;

import com.lin.blog.portal.exception.CustomAuthenticationException;
import com.lin.blog.portal.mapper.UserMapper;
import com.lin.blog.portal.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;

@Service
public class SpringUserService implements UserDetailsService
{
    @Resource
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws CustomAuthenticationException
    {
        User user = userMapper.findUserByUsername(username);

        if(user == null) throw new CustomAuthenticationException("帳號或密碼錯誤!");

        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(Collections.emptyList())
                .accountLocked(false)
                .disabled(false)
                .build();

        return userDetails;
    }
}