package com.lin.blog.portal.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class UserVO implements Serializable
{
    private Integer id;
    private String username;
    private String nickname;
    private String email;
    private LocalDateTime createdAt;
}
