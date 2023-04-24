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
import org.springframework.web.bind.annotation.*;

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
                .setStatus(1)
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

    @PostMapping("/update")
    @PreAuthorize("hasRole('ROLE_VISITOR')")
    public ResponseEntity updateComment(@AuthenticationPrincipal UserDetails userDetails, Integer id, String content)
    {
        Comment comment = commentService.getById(id);

        User user = userService.getById(comment.getUserId());

        if (!user.getUsername().equals(userDetails.getUsername()))
        {
            throw new ServiceException("您無法修改別人的評論!");
        }

        comment.setContent(content);
        commentService.updateById(comment);

        return ResponseEntity.ok(null);
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('ROLE_VISITOR')")
    public ResponseEntity deleteComment(@AuthenticationPrincipal UserDetails userDetails, Integer id)
    {
        Comment comment = commentService.getById(id);

        User user = userService.getById(comment.getUserId());

        if (!user.getUsername().equals(userDetails.getUsername()))
        {
            throw new ServiceException("您無法刪除別人的評論!");
        }

        comment.setStatus(0);
        commentService.updateById(comment);

        return ResponseEntity.ok(null);
    }
}
