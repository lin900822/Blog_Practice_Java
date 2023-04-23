package com.lin.blog.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lin.blog.portal.model.Comment;

import java.util.List;

public interface ICommentService extends IService<Comment>
{
    List<Comment> getCommentsByArticleId(Integer articleId);
}
