package com.lin.blog.portal.service.impl;

import com.lin.blog.portal.mapper.UserMapper;
import com.lin.blog.portal.model.User;
import com.lin.blog.portal.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements IUserService
{
    @Resource
    private UserMapper userMapper;

    @Override
    public User getUserByUsername(String username)
    {
        return userMapper.findUserByUsername(username);
    }
}
