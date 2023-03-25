package com.lin.blog.portal.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class AuthenticationVO implements Serializable
{
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
