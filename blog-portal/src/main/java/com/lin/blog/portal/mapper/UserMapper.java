package com.lin.blog.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lin.blog.portal.model.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<User>
{
    @Select("select * from users where username=#{username}")
    User findUserByUsername(String username);
}
