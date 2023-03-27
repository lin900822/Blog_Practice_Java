package com.lin.blog.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lin.blog.portal.model.User;
import com.lin.blog.portal.vo.RegisterVO;

public interface IUserService extends IService<User>
{
    User getUserByUsername(String username);

    void registerUser(RegisterVO registerVO);
}
