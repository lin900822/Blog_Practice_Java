package com.lin.blog.portal.service;

import com.lin.blog.portal.model.User;

public interface IUserService
{
    User getUserByUsername(String username);
}
