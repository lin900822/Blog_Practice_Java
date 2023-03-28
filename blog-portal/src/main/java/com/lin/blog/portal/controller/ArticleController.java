package com.lin.blog.portal.controller;

import com.lin.blog.portal.exception.ServiceException;
import com.lin.blog.portal.model.Article;
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
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.format.DateTimeFormatter;

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

    @GetMapping("/{id}")
    public ResponseEntity<ArticleVO> getArticleById(@PathVariable Integer id)
    {
        Article article = articleService.getById(id);

        ArticleVO articleVO = new ArticleVO()
                .setTitle(article.getTitle())
                .setSummary(article.getSummary())
                .setThumbnail(article.getThumbnail())
                .setContent(article.getContent())
                .setStatus(article.getStatus())
                .setCategory("")
                .setCreatedAt(DateTimeFormatter.ofPattern("yyyy年MM月dd日").format(article.getCreatedAt()))
                .setUpdatedAt(DateTimeFormatter.ofPattern("yyyy年MM月dd日").format(article.getUpdatedAt()));

        return ResponseEntity.ok(articleVO);
    }
}
