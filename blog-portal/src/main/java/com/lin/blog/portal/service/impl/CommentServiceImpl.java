package com.lin.blog.portal.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lin.blog.portal.mapper.CommentMapper;
import com.lin.blog.portal.model.Comment;
import com.lin.blog.portal.service.ICommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService
{
    @Resource
    private CommentMapper commentMapper;

    @Override
    public List<Comment> getCommentsByArticleId(Integer articleId)
    {
        return commentMapper.findCommentsByArticleId(articleId);
    }
}
