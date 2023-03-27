package com.lin.blog.portal.controller;

import com.lin.blog.portal.exception.ServiceException;
import com.lin.blog.portal.service.IArticleService;
import com.lin.blog.portal.vo.ArticleVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@RequestMapping("/article")
public class ArticleController
{
    @Resource
    private IArticleService articleService;

    @PostMapping("/create")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity createArticle(@Validated ArticleVO articleVO,
                                                /*@AuthenticationPrincipal UserDetails userDetails,*/
                                                BindingResult result)
    {
        log.debug("新增文章:{}", articleVO);
        if (result.hasErrors())
        {
            String errorMessage = result.getFieldError().getDefaultMessage();
            throw new ServiceException(errorMessage);
        }

        articleService.createArticle(articleVO, null);

        return ResponseEntity.ok(null);
    }
}
