package com.lin.blog.portal.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lin.blog.portal.exception.ServiceException;
import com.lin.blog.portal.mapper.UserMapper;
import com.lin.blog.portal.mapper.UserRoleMapper;
import com.lin.blog.portal.model.User;
import com.lin.blog.portal.model.UserRole;
import com.lin.blog.portal.service.IUserService;
import com.lin.blog.portal.vo.RegisterVO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService
{
    @Resource
    private UserMapper userMapper;

    @Override
    public User getUserByUsername(String username)
    {
        return userMapper.findUserByUsername(username);
    }

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Override
    @Transactional
    public void registerUser(RegisterVO registerVO)
    {
        User user = userMapper.findUserByUsername(registerVO.getUsername());
        if (user != null) throw new ServiceException("該用戶已存在!");

        String pwd = passwordEncoder.encode(registerVO.getPassword());

        User newUser = new User()
                .setUsername(registerVO.getUsername())
                .setPassword(pwd)
                .setNickname(registerVO.getNickname())
                .setEmail(registerVO.getEmail())
                .setCreatedAt(LocalDateTime.now());

        int num = userMapper.insert(newUser);
        if (num != 1) throw new ServiceException("資料庫發生錯誤!");

        UserRole userRole = new UserRole()
                .setUserId(newUser.getId())
                .setRoleId(2);

        num = userRoleMapper.insert(userRole);
        if(num != 1) throw new ServiceException("資料庫發生錯誤!");
    }
}
