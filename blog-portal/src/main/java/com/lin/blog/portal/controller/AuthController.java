package com.lin.blog.portal.controller;

import com.lin.blog.portal.exception.CustomAuthenticationException;
import com.lin.blog.portal.exception.ServiceException;
import com.lin.blog.portal.model.User;
import com.lin.blog.portal.service.IUserService;
import com.lin.blog.portal.service.impl.JWTService;
import com.lin.blog.portal.vo.AuthenticationVO;
import com.lin.blog.portal.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/auth")
public class AuthController
{
    @Resource
    private JWTService jwtService;

    @PostMapping("/login")
    public String login(@Validated AuthenticationVO authenticationVO, BindingResult result)
    {
        log.debug("登入訊息:{}", authenticationVO);
        if (result.hasErrors())
        {
            String msg = result.getFieldError().getDefaultMessage();
            throw new CustomAuthenticationException(msg);
        }

        String token = jwtService.generateToken(authenticationVO);

        return token;
    }

    @Resource
    private IUserService userService;

    @PostMapping("/user")
    public UserVO getUser(@AuthenticationPrincipal UserDetails userDetails)
    {
        if (userDetails == null) return null;

        User user = userService.getUserByUsername(userDetails.getUsername());

        UserVO userVO = new UserVO()
                .setUsername(user.getUsername())
                .setNickname(user.getNickname())
                .setEmail(user.getEmail())
                .setCreatedAt(user.getCreatedAt());

        return userVO;
    }
}
