package com.lin.blog.portal.controller;

import com.lin.blog.portal.exception.ServiceException;
import com.lin.blog.portal.model.Article;
import com.lin.blog.portal.model.Comment;
import com.lin.blog.portal.model.User;
import com.lin.blog.portal.service.IArticleService;
import com.lin.blog.portal.service.ICommentService;
import com.lin.blog.portal.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/comment")
public class CommentController
{
    @Resource
    private IArticleService articleService;

    @Resource
    private IUserService userService;

    @Resource
    private ICommentService commentService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_VISITOR')")
    public ResponseEntity<Comment> addComment(@AuthenticationPrincipal UserDetails userDetails, Integer articleId, String comment)
    {
        log.debug("{}對文章:{}新增評論:{}", userDetails.getUsername(), articleId, comment);

        if (articleId == null || comment.isEmpty())
        {
            throw new ServiceException("文章ID或評論內容不得為空!");
        }

        Article article = articleService.getById(articleId);
        if (article == null)
        {
            throw new ServiceException("文章不存在!");
        }

        User user = userService.getUserByUsername(userDetails.getUsername());

        Comment c = new Comment()
                .setArticleId(articleId)
                .setUserId(user.getId())
                .setNickname(user.getNickname())
                .setContent(comment)
                .setCreatedAt(LocalDateTime.now());

        commentService.save(c);

        return ResponseEntity.ok(c);
    }

    @GetMapping("/getCommentsByArticleId")
    public ResponseEntity<List<Comment>> getCommentsByArticleId(Integer articleId)
    {
        List<Comment> comments = commentService.getCommentsByArticleId(articleId);

        return ResponseEntity.ok(comments);
    }
}
