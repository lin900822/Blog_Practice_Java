package com.lin.blog.portal.service.impl;

import com.lin.blog.portal.exception.CustomAuthenticationException;
import com.lin.blog.portal.mapper.UserMapper;
import com.lin.blog.portal.model.Role;
import com.lin.blog.portal.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailsService
{
    @Resource
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws CustomAuthenticationException
    {
        User user = userMapper.findUserByUsername(username);

        if (user == null) return null;

        List<Role> roles = userMapper.findRolesByUserId(user.getId());
        String[] auth = new String[roles.size()];
        int i = 0;
        for (Role r : roles)
        {
            auth[i] = r.getName();
            i++;
        }

        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(auth)
                .accountLocked(false)
                .disabled(false)
                .build();

        return userDetails;
    }
}