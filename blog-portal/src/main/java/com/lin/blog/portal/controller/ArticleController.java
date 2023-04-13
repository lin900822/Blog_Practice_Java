package com.lin.blog.portal.controller;

import com.github.pagehelper.PageInfo;
import com.lin.blog.portal.exception.ServiceException;
import com.lin.blog.portal.model.Article;
import com.lin.blog.portal.service.IArticleService;
import com.lin.blog.portal.vo.ArticleVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/article")
public class ArticleController
{
    @Resource
    private IArticleService articleService;

    @PostMapping("/save")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Integer> saveArticle(@Validated ArticleVO articleVO,
            /*@AuthenticationPrincipal UserDetails userDetails,*/
                                               BindingResult result)
    {
        log.debug("儲存文章:{}", articleVO);
        if (result.hasErrors())
        {
            String errorMessage = result.getFieldError().getDefaultMessage();
            throw new ServiceException(errorMessage);
        }

        Integer articleId = articleService.saveArticle(articleVO, null);

        return ResponseEntity.ok(articleId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable Integer id)
    {
        log.debug("查詢文章,ID:{}", id);
        Article article = articleService.getById(id);

        if (article == null) throw new ServiceException("找不到指定文章");

        return ResponseEntity.ok(article);
    }

    @GetMapping("/all")
    public ResponseEntity<PageInfo<Article>> getAllArticles(Integer pageNum)
    {
        PageInfo<Article> articles = articleService.getAllArticles(pageNum, 8);

        return ResponseEntity.ok(articles);
    }
}
