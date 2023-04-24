package com.lin.blog.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lin.blog.portal.model.Comment;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentMapper extends BaseMapper<Comment>
{
    @Select("select * from comments where article_id=#{articleId} order by created_at")
    List<Comment> findCommentsByArticleId(Integer articleId);
}
