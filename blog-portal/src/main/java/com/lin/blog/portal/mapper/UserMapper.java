package com.lin.blog.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lin.blog.portal.model.Role;
import com.lin.blog.portal.model.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper extends BaseMapper<User>
{
    @Select("select * from users where username=#{username}")
    User findUserByUsername(String username);

    @Select("SELECT r.* FROM roles r \n" +
            "LEFT JOIN user_roles ur ON r.id=ur.role_id\n" +
            "LEFT JOIN users u ON ur.user_id=u.id\n" +
            "WHERE u.id=#{id};")
    List<Role> findRolesByUserId(Integer id);
}
