package com.lin.blog.portal.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class RegisterVO implements Serializable
{
    @NotBlank
    private String username;
    @NotBlank
    private String email;
    @NotBlank
    private String nickname;
    @NotBlank
    private String password;
    @NotBlank
    private String confirmPassword;
}
