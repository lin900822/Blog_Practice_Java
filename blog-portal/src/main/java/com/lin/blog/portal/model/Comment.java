package com.lin.blog.portal.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("comments")
public class Comment
{
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("article_id")
    private Integer articleId;

    @TableField("user_id")
    private Integer userId;

    @TableField("nickname")
    private String nickname;

    @TableField("content")
    private String content;

    @TableField("created_at")
    private LocalDateTime createdAt;
}
